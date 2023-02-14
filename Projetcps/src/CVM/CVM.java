package CVM;

import component.Facade;
import component.Pairs;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import interfaces.App.ApplicationNodeAddress;
import interfaces.node.ContentNodeAddress;

public class CVM extends AbstractCVM {

	public CVM() throws Exception {
		// TODO Auto-generated constructor stub
	}
	
	public void deploy() throws Exception {
		ContentNodeAddress c = new ContentNodeAddress("URI", "node1", "cmuri", false,true);
		ContentNodeAddress c2 = new ContentNodeAddress("URI2", "node2", "cmuri2", false,true);
		ApplicationNodeAddress a = new ApplicationNodeAddress(AbstractPort.generatePortURI(), "", AbstractPort.generatePortURI(),true, false);
		AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] {c});		
		AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[] {a});	
		AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] {c2});
	
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

