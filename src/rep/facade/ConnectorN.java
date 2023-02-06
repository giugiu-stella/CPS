package rep.facade;

import boundPort.NInboundPort;
import fr.sorbonne_u.components.connectors.AbstractConnector;

public class ConnectorN extends AbstractConnector implements NodeCI {

	@Override
	public PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception {
		return ((NInboundPort)this.offering).connect(a);
	}

	@Override
	public void disconnect(PeerNodeAddressI a) throws Exception {
		((NInboundPort)this.offering).disconnect(a);
	}

}
