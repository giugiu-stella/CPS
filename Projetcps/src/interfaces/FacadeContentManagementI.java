package interfaces;

import contenu.requetes.ContentDescriptorI;

public interface FacadeContentManagementI {
	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception;
}
