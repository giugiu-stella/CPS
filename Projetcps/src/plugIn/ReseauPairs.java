package plugIn;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;

public abstract class ReseauPairs extends AbstractPlugin {

	private static final long serialVersionUID = 1L;
	private final AtomicReference<String>		plugInURI;
	private final AtomicReference<ComponentI>	owner;
	private final AtomicReference<String>		preferredExecutorServiceURI;
	private final AtomicInteger					preferredExecutorServiceIndex;
	public ReseauPairs(AtomicReference<String> plugInURI,AtomicReference<ComponentI>	owner,AtomicReference<String> preferredExecutorServiceURI,AtomicInteger preferredExecutorServiceIndex) {
		this.preferredExecutorServiceIndex=preferredExecutorServiceIndex;
		this.plugInURI=plugInURI;
		this.owner=owner;
		this.preferredExecutorServiceURI=preferredExecutorServiceURI;
	}
	
	public void installOn(ComponentI owner) {
		/*this.owner.set(owner);
		if (this.getPreferredExecutionServiceURI() != null) {
			this.preferredExecutorServiceIndex.set(
					((AbstractComponent)this.getOwner()).
							getExecutorServiceIndex(
									this.getPreferredExecutionServiceURI()));
		}*/
		
	}
	
	

}
