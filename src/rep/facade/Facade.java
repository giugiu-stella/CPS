package rep.facade;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import boundPort.CMInboundPort;
import boundPort.CMOutboundPort;
import boundPort.FacadeInboundPort;
import boundPort.NOutboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ContentManagementCI;

@RequiredInterfaces(required = { NodeCI.class, ContentManagementCI.class })
@OfferedInterfaces(offered = { NodeManagementCI.class, ContentManagementCI.class })

public class Facade extends AbstractComponent{
	
	public static final String FIP_URI="fip-uri";
	protected FacadeInboundPort fip;
	private ApplicationNodeAddress applicationNodeAddress;
	private CMInboundPort CMip;

	private Map<PeerNodeAddressI, NOutboundPort> connectToNode;
	private Map<PeerNodeAddressI, CMOutboundPort> connectNodeContent;
	
	protected Facade(ApplicationNodeAddress applicationNodeAddress) throws Exception{
		super(1,0);
		this.fip= new FacadeInboundPort(FIP_URI,this);
		this.fip.publishPort();
		this.applicationNodeAddress = applicationNodeAddress;
		this.connectToNode = new HashMap<>();
		this.connectNodeContent = new HashMap<>();
	}
	//def internes à définir
	public String recherche(String element) {
		return element;
	}
	
	public synchronized void start() throws ComponentStartException {
		try {
			this.fip= new FacadeInboundPort(applicationNodeAddress.getNodeManagementURI(),this);
			this.CMip= new CMInboundPort(applicationNodeAddress.getContentManagementURI(),this);
			this.fip.publishPort();
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
		super.start();
	}
	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.fip.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
		for (Map.Entry<PeerNodeAddressI,NOutboundPort> port : connectToNode.entrySet()) {
			port.getValue().doDisconnection();
		}
		for (Map.Entry<PeerNodeAddressI,CMOutboundPort> port : connectNodeContent.entrySet()) {
			port.getValue().doDisconnection();
		}
		super.finalise();
	}
	
	public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
		return null;
		
	}
	
	public void leave(PeerNodeAddressI a) throws Exception {
		
	}
	
	
}


//creation d'un composant dans le code framework
//String uri=AbstractComponent.createComponent(Facade.class.getCanonicalName(),new Object[] {1,0});