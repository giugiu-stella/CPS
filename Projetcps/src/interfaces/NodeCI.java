package interfaces;

import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
import interfaces.node.PeerNodeAddressI;

public interface NodeCI extends OfferedCI,RequiredCI{
	public PeerNodeAddressI connect(PeerNodeAddressI a) throws Exception;
	public void disconnect(PeerNodeAddressI a) throws Exception;
	public void acceptConnected(PeerNodeAddressI neighbour) throws Exception;
	public void connectAsync(PeerNodeAddressI neighbour)throws Exception;
}
