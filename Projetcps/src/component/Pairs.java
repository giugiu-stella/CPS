package component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
//import boundPort.CMInboundPort;
import boundPort.FacadeOutboundPort;
import boundPort.NInboundPort;
import boundPort.NOutboundPort;
import connector.ConnectorN;
import connector.ConnectorNM;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
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
	protected FacadeOutboundPort pip;
	protected NOutboundPort port_sortant;
	protected NOutboundPort port_sortant_node;
	private ContentNodeAddress contentNodeAddress;
	//private CMInboundPort CMip;
	//private NInboundPort Nip;
	private Set<PeerNodeAddressI> listevoisins;
	private HashMap<PeerNodeAddressI, String> liaisonA_portsortant;
	private NInboundPort port_entrant;
	
	
	protected Pairs(ContentNodeAddress contentNodeAddress) {
		super(2,0);
		this.contentNodeAddress= contentNodeAddress;
		this.listevoisins=new HashSet<PeerNodeAddressI>();
		this.liaisonA_portsortant=new HashMap<>();
	}
	
	
	public synchronized void start() throws ComponentStartException {
		try {
			this.pip= new FacadeOutboundPort(contentNodeAddress.getNodeURI(),this);
			//this.CMip= new CMInboundPort(.getContentNodeAddress().getContentManagementURI(), this);
			this.pip.publishPort();
			this.doPortConnection(pip.getPortURI(),Facade.FIP_URI,ConnectorNM.class.getCanonicalName());
			//this.CMip.publishPort();
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
		super.start();
	}
	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.pip.unpublishPort();
			this.port_sortant.unpublishPort();
			this.port_sortant_node.unpublishPort();
			this.port_entrant.unpublishPort();
			//this.CMip.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
		
		this.port_sortant.disconnect(contentNodeAddress);
		
		super.finalise();
	}
	
	
	public void execute() throws Exception{
		
		this.logMessage("execution en cours !");
		this.listevoisins=this.pip.join(contentNodeAddress);
		this.port_sortant= new NOutboundPort("Sortant-uri",this);
		this.port_sortant.publishPort();
		
		this.port_entrant= new NInboundPort(contentNodeAddress.getNodeURI(),this);
		this.port_entrant.publishPort();
		
		for(PeerNodeAddressI p: this.listevoisins) {
			this.doPortConnection(port_sortant.getPortURI(),p.getNodeURI(),ConnectorN.class.getCanonicalName());
			this.port_sortant.connect(contentNodeAddress);
		}
		
	}
	
	public PeerNodeAddressI connect(PeerNodeAddressI peer) throws Exception {
		
		System.out.println("je suis dans connect...");
		this.port_sortant_node= new NOutboundPort("Sortant-uri",this);
		this.port_sortant_node.publishPort();
		this.doPortConnection(this.port_sortant_node.getPortURI(),peer.getNodeURI(),ConnectorN.class.getCanonicalName());
		this.liaisonA_portsortant.put(peer,port_sortant.getPortURI());
		System.out.println(liaisonA_portsortant);
		return this.contentNodeAddress;
	}
	
	
	public void disconnect(PeerNodeAddressI peer) throws Exception {
		System.out.println("je suis dans disconnect...");
		String port_sortant_peer="";
		for(PeerNodeAddressI p: this.liaisonA_portsortant.keySet()) {
			if(peer.equalPNA(p)) {
				port_sortant_peer= this.liaisonA_portsortant.get(p);
			}
		}
		this.doPortDisconnection(port_sortant_peer);
		System.out.println(port_sortant_peer);
	}
	
	public Set<PeerNodeAddressI> join() throws Exception {
		return null;	
	}
	
	public void leave() throws Exception {
	}


	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		System.out.println("je suis dans find...");
		return null;
	}


	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		System.out.println("je suis dans match...");
		return null;
	}
}
