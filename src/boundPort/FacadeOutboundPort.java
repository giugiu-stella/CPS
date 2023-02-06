package boundPort;

import java.util.Set;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import rep.facade.ConnectorNM;
import rep.facade.NodeManagementCI;
import rep.facade.PeerNodeAddressI;

public class FacadeOutboundPort extends AbstractOutboundPort implements NodeManagementCI{

	
	private static final long serialVersionUID = 1L;

	public FacadeOutboundPort(ComponentI owner) throws Exception {
		super(NodeManagementCI.class, owner);
		
	}

	public FacadeOutboundPort(String uri,ComponentI owner)
			throws Exception {
		super(uri, NodeManagementCI.class, owner);
	}

	@Override
	public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
		
		return ((ConnectorNM) getConnector()).join(a);
	}

	@Override
	public void leave(PeerNodeAddressI a) throws Exception {
		((ConnectorNM) getConnector()).leave(a);
	}

}