package boundPort;

import java.util.concurrent.RejectedExecutionException;

import component.Pairs;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.NodeCI;
import interfaces.NodeI;
import interfaces.node.PeerNodeAddressI;

public class NInboundPort extends AbstractInboundPort implements NodeCI {


	private static final long serialVersionUID = 1L;
	
	public NInboundPort(ComponentI owner) throws Exception {
		super(NodeCI.class, owner);
	}

	public NInboundPort(String uri,ComponentI owner)
			throws Exception {
		super(uri, NodeCI.class, owner);
	}
	

	@Override
	public PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception {
		return this.getOwner().handleRequest(new AbstractComponent.AbstractService<PeerNodeAddressI>() {
			@Override 
			public PeerNodeAddressI call() throws Exception {
				return ((Pairs) getOwner()).connect(a) ;
			}
		}) ;
	}
	

	@Override
	public void disconnect(PeerNodeAddressI a) throws Exception {
		this.getOwner().handleRequest(new AbstractComponent.AbstractService<Void>() {
			@Override 
			public Void call() throws Exception {
				((Pairs) getOwner()).disconnect(a) ;
				return null;
			}
		}) ;
	}
	@Override
	public void acceptConnected(PeerNodeAddressI neighbour) throws Exception {
		try {
			this.getOwner().runTask(
					o -> {	try {
								((NodeI)o).acceptConnected(neighbour);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						 });
	
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		} catch (AssertionError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}

	@Override
	public void connectAsync(PeerNodeAddressI neighbour) throws Exception {
		this.getOwner().runTask(
				o -> {	try {
							((NodeI)o).connectAsync(neighbour);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					 });
		
	}


}
