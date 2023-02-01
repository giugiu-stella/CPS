package interfaces;

import java.util.Set;

public interface NodeManagementImpl {
	public Set<PeerNodeAddressI> join(PeerNodeAddressI a);
	public void leave(PeerNodeAddressI a);
}
