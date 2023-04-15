package connector;

import java.util.Set;

import contenu.requetes.ContentDescriptorI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.FacadeContentManagementCI;

public class ConnectorAync extends AbstractConnector implements FacadeContentManagementCI{

	@Override
	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception {
		((FacadeContentManagementCI)this.offering).acceptFound(found, requestURI);
		
	}

	@Override
	public void acceptMatched(Set<ContentDescriptorI> found, String requestURI) throws Exception {
		((FacadeContentManagementCI)this.offering).acceptMatched(found, requestURI);
	}

}
