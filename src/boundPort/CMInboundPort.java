package boundPort;

import java.util.Set;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ContentDescriptorI;
import interfaces.ContentManagementCI;
import interfaces.ContentRequestI;
import interfaces.ContentTemplateI;

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
		return getOwner().handleRequest(new AbstractComponent.AbstractService<ContentDescriptorI>() {
			@Override
			public ContentDescriptorI call() throws Exception {
				
				return ((ContentRequestI)getOwner()).find(cd,hops);
			}
		});
	}

	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		return getOwner().handleRequest(new AbstractComponent.AbstractService< Set<ContentDescriptorI> >() {
			@Override
			public  Set<ContentDescriptorI>  call() throws Exception {
				
				return ((ContentRequestI)getOwner()).match(cd,matched,hops);
			}
		});
	}

}
