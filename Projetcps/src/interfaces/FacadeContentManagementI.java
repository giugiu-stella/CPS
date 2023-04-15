package interfaces;

import java.util.Set;

import contenu.requetes.ContentDescriptorI;

public interface FacadeContentManagementI {
	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception;
	public void acceptMatched(Set<ContentDescriptorI> found, String requestURI) throws Exception;
}
