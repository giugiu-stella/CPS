package component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import boundPort.CMInboundPort;
import boundPort.CMOutboundPort;
import boundPort.FacadeInboundPort;
import boundPort.FacadeOutboundPort;
import boundPort.NInboundPort;
import boundPort.NOutboundPort;
import connector.ConnectorNM;
import contenu.requetes.ContentDescriptorI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ContentManagementCI;
import interfaces.NodeCI;
import interfaces.NodeManagementCI;
import interfaces.App.ApplicationNodeAddress;
import interfaces.node.ContentNodeAddress;
import interfaces.node.ContentNodeAddressI;
import interfaces.node.PeerNodeAddressI;

@RequiredInterfaces(required = {NodeCI.class,ContentManagementCI.class,NodeManagementCI.class})
@OfferedInterfaces(offered = {NodeCI.class})
public class Pairs extends AbstractComponent {
	public static final String Pip_URI="pip-uri";
	protected FacadeOutboundPort pip;
	protected NOutboundPort port_sortant;
	private ContentNodeAddress contentNodeAddress;
	private CMInboundPort CMip;
	private NInboundPort Nip;
	private Set<PeerNodeAddressI> liste;
	
	protected Pairs(ContentNodeAddress contentNodeAddress) {
		super(1,0);
		this.contentNodeAddress= contentNodeAddress;
		this.liste=new HashSet<PeerNodeAddressI>();
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
			//this.CMip.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
		
		super.finalise();
	}
	
	
	public void execute() throws Exception{
		this.logMessage("execution en cours !") ;
		this.liste=this.pip.join(contentNodeAddress);
		
	}
	
	//creer liste voisins
	// creeer autres variables que port_sortant pour pas ecraser
	
	public PeerNodeAddressI connect(PeerNodeAddressI peer) throws Exception {
		System.out.println("je suis dans connect...");
		this.port_sortant= new NOutboundPort("Sortant-uri",this);
		this.port_sortant.publishPort();
		this.doPortConnection(port_sortant.getPortURI(),peer.getNodeURI(),ConnectorNM.class.getCanonicalName());
		
		for(PeerNodeAddressI p: this.liste) {
			this.port_sortant= new NOutboundPort("Sortant-uri",this);
			this.port_sortant.publishPort();
			this.doPortConnection(port_sortant.getPortURI(),peer.getNodeURI(),ConnectorNM.class.getCanonicalName());
			this.port_sortant.connect(p);
			// creer equals pour les string => getURI()
		}
		return this.contentNodeAddress;
		
		
	}
	
	public void disconnect(PeerNodeAddressI peer) throws Exception {
		
	}
	
	public Set<PeerNodeAddressI> join() throws Exception {
		
		return null;
		
	}
	
	public void leave() throws Exception {
		
	}
}
