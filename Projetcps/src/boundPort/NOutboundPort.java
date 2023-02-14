package boundPort;


import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.NodeCI;
import interfaces.node.PeerNodeAddressI;

public class NOutboundPort extends AbstractOutboundPort implements NodeCI{
	
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
		return ((NodeCI) getConnector()).connect(a);
	}

	@Override
	public void disconnect(PeerNodeAddressI a) throws Exception {
		((NodeCI) getConnector()).disconnect(a);

	}

	
	

}
