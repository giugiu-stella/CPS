package interfaces;

import java.util.Set;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import interfaces.node.PeerNodeAddressI;

public interface NodeManagementCI extends OfferedCI, RequiredCI {
	
	public Set<PeerNodeAddressI> join (PeerNodeAddressI a) throws Exception;
	public void leave (PeerNodeAddressI a) throws Exception;
	
}
