package connector;

import java.util.Set;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
import interfaces.ContentManagementCI;
import interfaces.NodeAddressI;

public class ConnectorCM extends AbstractConnector implements ContentManagementCI {

	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		return ((ContentManagementCI)this.offering).find(cd,hops);
	}

	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		
		return ((ContentManagementCI)this.offering).match(cd,matched,hops);
	}

	@Override
	public void find(ContentTemplateI cd, int hops, NodeAddressI requester, String requestURI)
			throws Exception {
		((ContentManagementCI)this.offering).find(cd,hops,requester,requestURI);
		
	}

	@Override
	public void match(ContentTemplateI cd, int hops, NodeAddressI requester, String requestURI,
			Set<ContentDescriptorI> matched) throws Exception {
		((ContentManagementCI)this.offering).match(cd,hops,requester,requestURI, matched);
		
	}

}
