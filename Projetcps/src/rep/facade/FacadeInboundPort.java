package rep.facade;

import java.util.Set;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class FacadeInboundPort extends AbstractInboundPort implements NodeManagementCI {

	
	private static final long serialVersionUID = 1L;

	public FacadeInboundPort(ComponentI owner) throws Exception {
		super(NodeManagementCI.class, owner);
		
	}

	public FacadeInboundPort(String uri,ComponentI owner)
			throws Exception {
		super(uri, NodeManagementCI.class, owner);
	}

	@Override
	public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
		
		return this.getOwner().handleRequestSync(c ->((Facade)c).recherche(a));
	}

	@Override
	public void leave(PeerNodeAddressI a) throws Exception {
		

	}

}
