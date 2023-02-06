package boundPort;

import java.util.Set;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import rep.facade.Facade;
import rep.facade.NodeManagementCI;
import rep.facade.PeerNodeAddressI;


public class FacadeInboundPort extends AbstractInboundPort implements NodeManagementCI {
	
	private static final long serialVersionUID = 1L;
	
	public FacadeInboundPort(ComponentI owner) throws Exception {
		super(NodeManagementCI.class, owner);
	}

	public FacadeInboundPort(String uri,ComponentI owner)
			throws Exception {
		super(uri,NodeManagementCI.class, owner);
	}
	

	@Override
	public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
		return getOwner().handleRequest(new AbstractComponent.AbstractService<Set<PeerNodeAddressI>>() {
			@Override
			public Set<PeerNodeAddressI> call() throws Exception {
				return ((Facade) getOwner()).join(a);
			}
		});
	}

	@Override
	public void leave(PeerNodeAddressI a) throws Exception {
		getOwner().handleRequest(new AbstractComponent.AbstractService<Void>() {
			@Override
			public Void call() throws Exception {
				((Facade) getOwner()).leave(a);
				return null;
			}
		});
		
	}
	

}
