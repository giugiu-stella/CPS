package interfaces;

import java.util.Set;

import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface ContentManagementCI extends ContentManagementImplementationI,RequiredCI, OfferedCI {
	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception;
	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd,
			Set<ContentDescriptorI> matched,int hops) throws Exception;
}
