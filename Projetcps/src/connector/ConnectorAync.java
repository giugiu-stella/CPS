package connector;

import contenu.requetes.ContentDescriptorI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.FacadeContentManagementCI;

public class ConnectorAync extends AbstractConnector implements FacadeContentManagementCI{

	@Override
	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception {
		((FacadeContentManagementCI)this.offering).acceptFound(found, requestURI);
		
	}

}
