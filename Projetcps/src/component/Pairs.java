package component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import boundPort.CMInboundPort;
import boundPort.CMOutboundPort;
import boundPort.FacadeOutboundPort;
import boundPort.NInboundPort;
import boundPort.NOutboundPort;
import connector.ConnectorN;
import connector.ConnectorNM;
import contenu.requetes.ContentDescriptor;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ContentManagementCI;
import interfaces.ContentManagementImplementationI;
import interfaces.NodeCI;
import interfaces.NodeManagementCI;
import interfaces.node.ContentNodeAddress;
import interfaces.node.PeerNodeAddressI;

@RequiredInterfaces(required = {NodeCI.class,ContentManagementCI.class,NodeManagementCI.class})
@OfferedInterfaces(offered = {NodeCI.class})
public class Pairs extends AbstractComponent implements ContentManagementImplementationI {
	public static final String Pip_URI="pip-uri";
	protected FacadeOutboundPort fop;
	protected NOutboundPort Nop;
	private CMOutboundPort CMoppair;
	private CMInboundPort CMippair;
	private ContentNodeAddress contentNodeAddress;
	private Set<PeerNodeAddressI> listevoisins;
	private HashMap<PeerNodeAddressI, String> listevoisins_noeud_et_Nop;
	private HashMap<PeerNodeAddressI, CMOutboundPort> listevoisins_noeud_et_CMop;
	private ArrayList<ContentDescriptor> contenu;
	private NInboundPort Nip;
	private NInboundPort Nip2;
	
	
	protected Pairs(ContentNodeAddress contentNodeAddress,ArrayList<ContentDescriptor> cd) throws Exception {
		super(2,0);
		this.contentNodeAddress= contentNodeAddress;
		this.listevoisins=new HashSet<PeerNodeAddressI>();
		this.listevoisins_noeud_et_Nop=new HashMap<>();
		this.listevoisins_noeud_et_CMop=new HashMap<>();
		this.contenu=cd;
		this.CMippair=new CMInboundPort("non", this);
		this.CMippair.publishPort();
	}
	
	
	public synchronized void start() throws ComponentStartException {
		try {
			this.fop= new FacadeOutboundPort(AbstractPort.generatePortURI(),this);
			this.CMoppair= new CMOutboundPort(contentNodeAddress.getContentManagementURI(), this);
			this.fop.publishPort();
			this.CMoppair.publishPort();
			this.doPortConnection(fop.getPortURI(),Facade.FIP_URI,ConnectorNM.class.getCanonicalName());
			this.Nop= new NOutboundPort(AbstractPort.generatePortURI(),this);
			this.Nop.publishPort();
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
		super.start();
	}
	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.fop.unpublishPort();
			this.Nop.unpublishPort();
			this.Nip.unpublishPort();
			this.Nip2.unpublishPort();
			this.CMippair.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
		Thread.sleep(5000L);
		this.fop.leave(contentNodeAddress);
		//this.Nop.disconnect(contentNodeAddress);
		super.finalise();
	}
	
	
	public void execute() throws Exception{
		
		System.out.println("Je suis dans execute de Pairs...");
		this.listevoisins=this.fop.join(contentNodeAddress);
		for(PeerNodeAddressI p: this.listevoisins) {
			this.Nip= new NInboundPort(p.getNodeURI(),this);
			this.Nip.publishPort();
			this.doPortConnection(this.Nop.getPortURI(),this.Nip.getPortURI(),ConnectorN.class.getCanonicalName());
			this.Nop.connect(contentNodeAddress);
		}
		
	}
	
	public PeerNodeAddressI connect(PeerNodeAddressI peer) throws Exception {
		this.Nip2= new NInboundPort(peer.getNodeURI(),this);
		this.Nip2.publishPort();
		System.out.println("je suis dans connect de Pairs...");		
		this.doPortConnection(this.Nop.getPortURI(),Nip2.getPortURI(),ConnectorN.class.getCanonicalName());
		this.listevoisins_noeud_et_Nop.put(peer,this.Nop.getPortURI());
		this.listevoisins_noeud_et_CMop.put(peer,this.CMoppair);
		//System.out.println(listevoisins_noeud_et_Nop);
		return this.contentNodeAddress;
	}
	
	
	public void disconnect(PeerNodeAddressI peer) throws Exception {
		System.out.println("je suis dans disconnect de Pairs...");
		String port_sortant_peer="";
		for(PeerNodeAddressI p: this.listevoisins_noeud_et_Nop.keySet()) {
			if(peer.equalPNA(p)) {
				port_sortant_peer= this.listevoisins_noeud_et_Nop.get(p);
			}
		}
		this.doPortDisconnection(port_sortant_peer);
		//System.out.println(port_sortant_peer);
	}
	
	public Set<PeerNodeAddressI> join() throws Exception {
		return null;	
	}
	
	public void leave() throws Exception {
		for(PeerNodeAddressI p: this.listevoisins) {
			Thread.sleep(5000L);
			disconnect(p);
		}
	}

	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		System.out.println("Je suis dans find de Pairs...");
		for (int i = 0; i < this.contenu.size(); i++) {
			ContentDescriptor res=this.contenu.get(i);
			if(res.match(cd)) {
				return res;
			}
		}
		hops--;
		
		if(hops !=0 && !(listevoisins_noeud_et_CMop.isEmpty())) {
			  for (PeerNodeAddressI m : listevoisins_noeud_et_CMop.keySet()){
				  listevoisins_noeud_et_CMop.get(m).find(cd, hops);
			  }
		}
		
		return null;
	}

	public void addCD(ContentDescriptor cd) {
		System.out.println("Je suis dans addCD de Pairs...");
		this.contenu.add(cd);
	}
	
	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		System.out.println("je suis dans match de Pairs...");
		boolean dejapresent=false;
		for (int i = 0; i < this.contenu.size(); i++) {
			ContentDescriptor res=this.contenu.get(i);
			if(res.match(cd)) {
				for(ContentDescriptorI cdi : matched) {
					if(res.equals(cdi)) {
						dejapresent=true;
						break;
					}
				}
				if(!dejapresent) {
					matched.add(res);
				}
			}
		}
		
		System.out.println("matched : " +matched);
		hops--;
		if(hops !=0 && !(listevoisins_noeud_et_CMop.isEmpty())) {
			  for (PeerNodeAddressI m : listevoisins_noeud_et_CMop.keySet()){
				  listevoisins_noeud_et_CMop.get(m).match(cd, matched,hops);
			  }
		}
		return matched;
	}
}
