package boundPort;

import java.util.Set;

import contenu.requetes.ContentDescriptorI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.FacadeContentManagementCI;

public class AsyncOutboundPort extends AbstractOutboundPort implements FacadeContentManagementCI{

	private static final long serialVersionUID = 1L;

	public	AsyncOutboundPort(ComponentI owner) throws Exception {
		super(FacadeContentManagementCI.class, owner);
	}

	public AsyncOutboundPort(String uri,ComponentI owner) throws Exception{
		super(uri, FacadeContentManagementCI.class, owner);
	}

	@Override
	public void acceptFound(ContentDescriptorI found, String requestURI)throws Exception{
		try {
			((FacadeContentManagementCI)this.getConnector()).acceptFound(found, requestURI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void acceptMatched(Set<ContentDescriptorI> found, String requestURI) throws Exception {
		try {
			((FacadeContentManagementCI)this.getConnector()).acceptMatched(found, requestURI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
