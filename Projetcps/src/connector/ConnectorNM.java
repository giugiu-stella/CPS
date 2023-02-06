package connector;

import java.util.Set;

import boundPort.FacadeInboundPort;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.NodeManagementCI;
import interfaces.node.PeerNodeAddressI;

public class ConnectorNM extends AbstractConnector implements NodeManagementCI {

	@Override
	public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
		return ((FacadeInboundPort)this.offering).join(a);
	}

	@Override
	public void leave(PeerNodeAddressI a) throws Exception {
		((FacadeInboundPort)this.offering).leave(a);
		
	}

	

}
