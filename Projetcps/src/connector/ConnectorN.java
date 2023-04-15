package connector;


import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.NodeCI;
import interfaces.node.PeerNodeAddressI;

public class ConnectorN extends AbstractConnector implements NodeCI {

	@Override
	public PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception {
		return ((NodeCI)this.offering).connect(a);
	}

	@Override
	public void disconnect(PeerNodeAddressI a) throws Exception {
		((NodeCI)this.offering).disconnect(a);
	}

	@Override
	public void acceptConnected(PeerNodeAddressI neighbour) throws Exception {
		((NodeCI)this.offering).acceptConnected(neighbour);
		
	}

	@Override
	public void connectAsync(PeerNodeAddressI neighbour) throws Exception {
		((NodeCI)this.offering).connectAsync(neighbour);
		
	}


	

}
