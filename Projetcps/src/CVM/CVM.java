package CVM;

import java.util.HashSet;

import component.Client;
import component.Facade;
import component.Pairs;
import contenu.requetes.ContentDescriptor;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplate;
import contenu.requetes.ContentTemplateI;
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
		ContentNodeAddress c3 = new ContentNodeAddress("URI3", "node3", "cmuri3", false,true);
		ContentNodeAddress c4 = new ContentNodeAddress("URI4", "node4", "cmuri4", false,true);
		ApplicationNodeAddress a = new ApplicationNodeAddress(AbstractPort.generatePortURI(), "", AbstractPort.generatePortURI(),true, false);
		ContentDescriptorI cd = new ContentDescriptor("The Nights", "Avicii", new HashSet<String>(), new HashSet<String>(),c,5);
		ContentTemplateI ct = new ContentTemplate("The Nights","", new HashSet<String>(), new HashSet<String>());
		AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] {c,cd});		
		AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[] {a});	
		AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] {c2,cd});
		AbstractComponent.createComponent(Client.class.getCanonicalName(),new Object[] {ct,1});	
	
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

