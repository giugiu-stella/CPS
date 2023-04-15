package interfaces;

import java.util.Set;

import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;

public interface ContentManagementImplementationI {
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception;
	public Set<ContentDescriptorI> match(ContentTemplateI cd,
			Set<ContentDescriptorI> matched,int hops) throws Exception;
	public void find(ContentTemplateI cd, int hops, NodeAddressI requester, String requestURI) throws Exception;
	public void match(ContentTemplateI cd,int hops, NodeAddressI requester,String requestURI,Set<ContentDescriptorI> matched) throws Exception;
}
