package interfaces.node;

import interfaces.NodeAddressI;

public interface PeerNodeAddressI extends NodeAddressI{
	public String getNodeURI();
	public boolean equalPNA(PeerNodeAddressI p);

}
