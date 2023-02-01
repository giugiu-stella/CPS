package interfaces;

import java.util.Set;

public interface ContentManagementImp {
	public ContentDescriptorI find(ContentTemplateI cd , int hops);
	public Set<ContentDescriptorI> match(ContentTemplateI cd , Set<ContentDescriptorI> matched , int hops);
}
