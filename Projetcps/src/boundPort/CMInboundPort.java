package boundPort;

import java.util.Set;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ContentManagementCI;
import interfaces.ContentManagementImplementationI;
import interfaces.NodeAddressI;

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
		
		return this.getOwner().handleRequest(new AbstractComponent.AbstractService<ContentDescriptorI>() {
			@Override 
			public ContentDescriptorI call() throws Exception {
				return ((ContentManagementImplementationI) getOwner()).find(cd,hops) ;
			}
		}) ;
	
	}

	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		return this.getOwner().handleRequest(new AbstractComponent.AbstractService<Set<ContentDescriptorI>>() {
			@Override 
			public Set<ContentDescriptorI> call() throws Exception {
				System.out.println("matching " + cd);
				return ((ContentManagementImplementationI) getOwner()).match(cd,matched,hops) ;
			}
		}) ;
	}

	@Override
	public void find(ContentTemplateI cd, int hops, NodeAddressI requester, String requestURI)
			throws Exception {
		this.getOwner().runTask(
				o -> {	try {
							((ContentManagementImplementationI)o).find(cd,hops, requester, requestURI);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					 });
	}

}
