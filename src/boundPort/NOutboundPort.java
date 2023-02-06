package boundPort;

import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import rep.facade.ConnectorN;
import rep.facade.NodeCI;
import rep.facade.PeerNodeAddressI;

public class NOutboundPort extends AbstractOutboundPort implements NodeCI {
	
	private static final long serialVersionUID = 1L;
	
	public NOutboundPort(ComponentI owner) throws Exception {
		super(NodeCI.class, owner);
	}

	public NOutboundPort(String uri,ComponentI owner)
			throws Exception {
		super(uri, NodeCI.class, owner);
	}

	@Override
	public PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception {
		
		return ((ConnectorN) getConnector()).connect(a);
	}

	@Override
	public void disconnect(PeerNodeAddressI a) throws Exception {
		((ConnectorN) getConnector()).disconnect(a);

	}

}
