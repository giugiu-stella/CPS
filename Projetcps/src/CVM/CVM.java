package CVM;

import java.util.HashSet;

import component.Facade;
import component.Pairs;
import contenu.requetes.ContentDescriptor;
import contenu.requetes.ContentDescriptorI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import interfaces.App.ApplicationNodeAddress;
import interfaces.node.ContentNodeAddress;

public class CVM extends AbstractCVM {

	public CVM() throws Exception {
	}
	
	
	public void deploy() throws Exception {
		ContentNodeAddress c = new ContentNodeAddress("URI", "node1", "cmuri", false,true);
		ContentNodeAddress c2 = new ContentNodeAddress("URI2", "node2", "cmuri2", false,true);
		ApplicationNodeAddress a = new ApplicationNodeAddress(AbstractPort.generatePortURI(), "", AbstractPort.generatePortURI(),true, false);
		ContentDescriptorI cd = new ContentDescriptor("The Nights", "Avicii", new HashSet<>(), new HashSet<>(),c,5);
		AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] {cd,a.getNodeManagementURI()});	
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

