package interfaces;

import java.util.Set;

import contenu.requetes.ContentDescriptorI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface FacadeContentManagementCI extends OfferedCI,RequiredCI, FacadeContentManagementI{
	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception;
	public void acceptMatched(Set<ContentDescriptorI> found, String requestURI) throws Exception;
	
}
