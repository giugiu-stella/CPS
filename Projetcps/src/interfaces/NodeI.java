package interfaces;

import interfaces.node.PeerNodeAddressI;

public interface NodeI {
	public void acceptConnected(PeerNodeAddressI neighbour) throws Exception;
	public void connectAsync(PeerNodeAddressI neighbour) throws Exception;
}
