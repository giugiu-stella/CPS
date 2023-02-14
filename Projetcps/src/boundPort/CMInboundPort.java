package boundPort;

import java.util.Set;

import component.Pairs;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.ContentManagementCI;
import interfaces.ContentRequestI;
import interfaces.node.PeerNodeAddressI;

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
				return (()) getOwner()).find(cd,hops) ;
			}// faire une interface implement !!!
		}) ;
	
	}

	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		return this.getOwner().handleRequest(new AbstractComponent.AbstractService<Set<ContentDescriptorI>() {
			@Override 
			public Set<ContentDescriptorI> call() throws Exception {
				return (()) getOwner()).match(cd,matched,hops) ;
			}
		}) ;
	}

}
