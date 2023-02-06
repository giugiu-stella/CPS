package rep.facade;

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

@RequiredInterfaces(required = {PeerNodeAddressI.class})
public class Pairs extends AbstractComponent {
	
	protected Pairs(int nbT, int nbST) {
		super(1,0);
	}
	//def internes à définir
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
	
	
	public synchronized void execute() throws Exception{
		super.execute();
		this.logMessage("execution en cours !") ;
		this.runTask(
			new AbstractComponent.AbstractTask() {
				public void run() {
					try { 
						((Pairs)this.getTaskOwner()).Join(uriGetterPort  );
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}) ;
	}
	
	public PeerNodeAddressI connectBack(PeerNodeAddressI peer) throws Exception {
		System.out.println("connect back to " + peer);
		OutPortNode port = new OutPortNode(this);
		port.publishPort();
		doPortConnection(port.getPortURI(), peer.getNodeURI(),
				ConnectorNode.class.getCanonicalName());
		connectOutPort.put(peer, port);
		OutPortContentManagement portContent = new OutPortContentManagement(this);
		portContent.publishPort();
		doPortConnection(portContent.getPortURI(),
				((ContentNodeAddressI) peer).getContentManagementURI(),
				ConnectorContentManagement.class.getCanonicalName());
		connectNodeContent.put(peer, portContent);
		return contentDescriptorI.getContentNodeAddress();
	}
	
	public void disconnectBack(PeerNodeAddressI peer) throws Exception {
		System.out.println("disconnect back from " + peer);
		OutPortNode port = connectOutPort.get(peer);
		connectOutPort.remove(peer);
		port.doDisconnection();
		OutPortContentManagement portContent = connectNodeContent.get(peer);
		portContent.doDisconnection();
		connectNodeContent.remove(peer);
		
	}
	
	public void connect(PeerNodeAddressI peer) throws Exception {
		
	}
	
	public void disconnect(PeerNodeAddressI peer) throws Exception {
		
	}
	
	public Set<PeerNodeAddressI> join() throws Exception {
		
	}
	
	public void leave() throws Exception {
		
	}
}
