package component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import boundPort.CMInboundPort;
import boundPort.CMOutboundPort;
import boundPort.FacadeOutboundPort;
import boundPort.NInboundPort;
import boundPort.NOutboundPort;
import connector.ConnectorCM;
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
import interfaces.node.ContentNodeAddressI;
import interfaces.node.PeerNodeAddressI;

@RequiredInterfaces(required = {NodeCI.class,ContentManagementCI.class,NodeManagementCI.class})
@OfferedInterfaces(offered = {NodeCI.class})
public class Pairs extends AbstractComponent implements ContentManagementImplementationI {
	public static final String Pip_URI="pip-uri";
	protected FacadeOutboundPort fop;
	protected NInboundPort Nip;
	private CMInboundPort CMippair;
	private ContentDescriptor contentNodeAddress;
	private Set<PeerNodeAddressI> listevoisins;
	private HashMap<PeerNodeAddressI, String> listevoisins_noeud_et_Nop;
	private HashMap<PeerNodeAddressI, CMOutboundPort> listevoisins_CMop;
	private HashMap<PeerNodeAddressI, NOutboundPort> listevoisins_Nop;

	
	
	protected Pairs(ContentDescriptor cd) throws Exception {
		super(1,0);
		this.contentNodeAddress= cd;
		this.listevoisins=new HashSet<PeerNodeAddressI>();
		this.listevoisins_Nop= new HashMap<>();
		this.listevoisins_CMop=new HashMap<>();
		this.listevoisins_noeud_et_Nop=new HashMap<>();
		this.CMippair=new CMInboundPort(cd.getContentNodeAddress().getContentManagementURI(), this);
	}
	
	
	public synchronized void start() throws ComponentStartException {
		try {
			this.fop= new FacadeOutboundPort(AbstractPort.generatePortURI(),this);
			this.fop.publishPort();
			this.doPortConnection(fop.getPortURI(),Facade.FIP_URI,ConnectorNM.class.getCanonicalName());
			this.Nip= new NInboundPort(contentNodeAddress.getContentNodeAddress().getNodeURI(),this);
			this.Nip.publishPort();
			this.CMippair.publishPort();
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
		super.start();
	}
	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.fop.unpublishPort();
			this.Nip.unpublishPort();
			this.CMippair.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
	//	this.fop.leave(contentNodeAddress.getContentNodeAddress());
	//	this.Nip.disconnect(contentNodeAddress.getContentNodeAddress());
		super.finalise();
	}
	
	
	public void execute() throws Exception{
		
		System.out.println("Je suis dans execute de Pairs...");
		this.listevoisins=this.fop.join(contentNodeAddress.getContentNodeAddress());
		//Thread.sleep(1000L);
		for(PeerNodeAddressI p: this.listevoisins) {
			if(p.equals(contentNodeAddress.getContentNodeAddress())) {
				continue;
			}
			System.out.println(p.toString());
			NOutboundPort Nip;
			Nip= new NOutboundPort(AbstractPort.generatePortURI(),this);
			Nip.publishPort();
			this.doPortConnection(Nip.getPortURI(),p.getNodeURI(),ConnectorN.class.getCanonicalName());
			CMOutboundPort CMop=new CMOutboundPort(AbstractPort.generatePortURI(),this);
			CMop.publishPort();
			this.doPortConnection(CMop.getPortURI(),((ContentNodeAddressI)p).getContentManagementURI(),ConnectorCM.class.getCanonicalName());
			this.listevoisins_Nop.put(p,Nip);
			this.listevoisins_CMop.put(p,CMop);
			Nip.connect(contentNodeAddress.getContentNodeAddress());
		}

	}
	
	public PeerNodeAddressI connect(PeerNodeAddressI peer) throws Exception {
		System.out.println("je suis dans connect de Pairs...");
		NOutboundPort Nip2;
		Nip2= new NOutboundPort(AbstractPort.generatePortURI(),this);
		Nip2.publishPort();
		CMOutboundPort CMop2=new CMOutboundPort(AbstractPort.generatePortURI(),this);
		CMop2.publishPort();
		this.doPortConnection(CMop2.getPortURI(),((ContentNodeAddressI)peer).getContentManagementURI(),ConnectorCM.class.getCanonicalName());
		this.doPortConnection(Nip2.getPortURI(),peer.getNodeURI(),ConnectorN.class.getCanonicalName());
		this.listevoisins_Nop.put(peer,Nip2);
		this.listevoisins_CMop.put(peer,CMop2);
		return this.contentNodeAddress.getContentNodeAddress();
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
		if (hops==0) {
			return null;
		}
	
		if(this.contentNodeAddress.match(cd)) { //pb dans le match
			return this.contentNodeAddress;
		}
		
		hops--;
		if(hops !=0 && !(listevoisins_CMop.isEmpty())) {
			for (Entry<PeerNodeAddressI,CMOutboundPort> e : listevoisins_CMop.entrySet()) {
				  e.getValue().find(cd,hops);
				  break;
			}
		}
		
		return null;
	}

	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		System.out.println("Je suis dans match de Pairs...");
		if (hops==0) {
			return matched;
		}
		if(this.contentNodeAddress.match(cd)) {
			matched.add(this.contentNodeAddress);
		}
		
		hops--;
		System.out.println(listevoisins_CMop);
		System.out.println("hops "+ hops);
		if(hops !=0 && !(listevoisins_CMop.isEmpty())) {
			for (Entry<PeerNodeAddressI,CMOutboundPort> e : listevoisins_CMop.entrySet()) {
				  e.getValue().match(cd,matched,hops);
				  
				  break;
			}
		}
		
	
		return matched;
	}
}
