package rep.facade;

import java.util.Set;

import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.ContentDescriptorI;
import interfaces.ContentManagementCI;
import interfaces.ContentTemplateI;

public class ConnectorCM extends AbstractConnector implements ContentManagementCI {

	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
