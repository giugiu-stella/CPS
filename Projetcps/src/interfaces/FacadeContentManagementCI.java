package interfaces;

import contenu.requetes.ContentDescriptorI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface FacadeContentManagementCI extends OfferedCI,RequiredCI, FacadeContentManagementI{
	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception;
	
}
