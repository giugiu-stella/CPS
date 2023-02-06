package rep.facade;

import java.util.HashSet;

import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import interfaces.ContentDescriptor;
import interfaces.ContentDescriptorI;

public class CVM extends AbstractCVM {

	public CVM() throws Exception {
		// TODO Auto-generated constructor stub
	}
	
	public void deploy() throws Exception {
		ContentNodeAddressI pair = new ContentNodeAddress(AbstractPort.generatePortURI(),"nodeVALD",AbstractPort.generatePortURI(), true, false);
		ContentDescriptorI c = new ContentDescriptor("V.A.L.S.E", "NQNTMQMB", new HashSet<>(), new HashSet<>(), pair,300);
		ApplicationNodeAddress ana = new ApplicationNodeAddress(AbstractPort.generatePortURI(), "facadeVald", AbstractPort.generatePortURI(),true, false);
		AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] { c, ana.getNodeManagementURI() });			
		AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[] {ana});				
		super.deploy();
	}

	public static void main(String[] args) {
		try {
			CVM c=new CVM();
			c.startStandardLifeCycle(2000L);
			Thread.sleep(5000L);
			System.exit(0);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
		
	}

