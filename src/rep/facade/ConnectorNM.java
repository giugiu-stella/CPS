package rep.facade;

import java.util.Set;

import boundPort.FacadeInboundPort;
import fr.sorbonne_u.components.connectors.AbstractConnector;

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
