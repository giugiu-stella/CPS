package boundPort;

import java.util.Set;

import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ContentManagementCI;
import interfaces.ContentRequestI;

public class CMInboundPort extends AbstractInboundPort implements ContentManagementCI {
		
	private static final long serialVersionUID = 1L;
	
	public CMInboundPort(ComponentI owner) throws Exception {
		super(ContentManagementCI.class, owner);
	}

	public CMInboundPort(String uri,ComponentI owner)
			throws Exception {
		super(uri, ContentManagementCI.class, owner);
	}

	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		return getOwner().handleRequest(
			
		);
	}

	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		return getOwner().handleRequest(
			);
	}

}
