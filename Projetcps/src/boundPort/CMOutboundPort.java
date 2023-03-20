package boundPort;

import java.util.Set;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;
import interfaces.ContentManagementCI;
import interfaces.NodeAddressI;

public class CMOutboundPort extends AbstractOutboundPort implements ContentManagementCI {
	
	private static final long serialVersionUID = 1L;
	
	public CMOutboundPort(ComponentI owner) throws Exception {
		super(ContentManagementCI.class, owner);
	}

	public CMOutboundPort(String uri, ComponentI owner)
			throws Exception {
		super(uri, ContentManagementCI.class, owner);
	}

	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		System.out.println("CMOut find " + this.getConnector());
		
		return((ContentManagementCI) getConnector()).find(cd, hops);
	}

	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		return ((ContentManagementCI) getConnector()).match(cd, matched, hops);
	}

	@Override
	public void find(ContentTemplateI cd, int hops, NodeAddressI requester, String requestURI)
			throws Exception {
		((ContentManagementCI)this.getConnector()).find(cd,hops,requester,requestURI);

	}

}
