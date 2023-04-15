package boundPort;

import java.util.Set;
import java.util.concurrent.RejectedExecutionException;

import contenu.requetes.ContentDescriptorI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
import interfaces.FacadeContentManagementCI;
import interfaces.FacadeContentManagementI;

public class AsyncInboundPort extends AbstractInboundPort implements FacadeContentManagementCI{

	private static final long serialVersionUID = 1L;
	
	public AsyncInboundPort(ComponentI owner) throws Exception{
		super(FacadeContentManagementCI.class, owner);
		assert	owner instanceof FacadeContentManagementI;
	}

	public	AsyncInboundPort(String uri, ComponentI owner)throws Exception{
		super(uri,FacadeContentManagementCI.class, owner);
		assert	owner instanceof FacadeContentManagementI;
	}
	@Override
	public void acceptFound(ContentDescriptorI found, String requestURI) {
		try {
			this.getOwner().runTask(
					o -> {	try {
								((FacadeContentManagementI)o).
										acceptFound(found, requestURI);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						 });
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		} catch (AssertionError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void acceptMatched(Set<ContentDescriptorI> found, String requestURI) throws Exception {
		try {
			this.getOwner().runTask(
					o -> {	try {
								((FacadeContentManagementI)o).
										acceptMatched(found, requestURI);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						 });
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		} catch (AssertionError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}


}
