package interfaces;

import java.util.Set;

public interface ContentRequestI {
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception;

	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)throws Exception;
}
