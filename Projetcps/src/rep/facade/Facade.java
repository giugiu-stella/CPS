package rep.facade;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered = {NodeManagementCI.class})
public class Facade extends AbstractComponent{
	
	public static final String FIP_URI="fip-uri";
	protected FacadeInboundPort fip;
	
	protected Facade() throws Exception{
		super(1,0);
		this.fip= new FacadeInboundPort(FIP_URI,this);
		this.fip.publishPort();
	}
	//def internes à définir
	public String recherche(String element) {
		return element;
	}
	
	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.fip.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
}


//creation d'un composant dans le code framework
//String uri=AbstractComponent.createComponent(Facade.class.getCanonicalName(),new Object[] {1,0});