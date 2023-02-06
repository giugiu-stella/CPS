package boundPort;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import rep.facade.NodeCI;
import rep.facade.Pairs;
import rep.facade.PeerNodeAddressI;

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
		return getOwner().handleRequest(new AbstractComponent.AbstractService<PeerNodeAddressI>() {
			@Override
			public PeerNodeAddressI call() throws Exception {
				return ((Pairs) getOwner()).connectBack(a);
			}
		});
	}

	@Override
	public void disconnect(PeerNodeAddressI a) throws Exception {
		getOwner().handleRequest(new AbstractComponent.AbstractService<Void>() {
			@Override
			public Void call() throws Exception {
				((Pairs) getOwner()).disconnectBack(a);
				return null;
			}
		});

	}

}
