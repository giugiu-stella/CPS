package boundPort;

import component.Pairs;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.NodeCI;
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


}
