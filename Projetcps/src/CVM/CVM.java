package CVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import component.Client;
import component.Facade;
import component.Pairs;
import contenu.requetes.ContentDescriptor;
import contenu.requetes.ContentTemplate;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.cps.p2Pcm.dataread.ContentDataManager;
import interfaces.App.ApplicationNodeAddress;
import interfaces.node.ContentNodeAddress;
import interfaces.node.ContentNodeAddressI;

public class CVM extends AbstractCVM {

	public static final String	MY_DATA_DIR_NAME = "/Users/giulianicarla/eclipse-cps/Projetcps/Test";
	protected static final boolean	DEBUG = true;										
	protected ArrayList<HashMap<String,Object>> readTemplates;
	protected ArrayList<HashMap<String,Object>> readDescriptors;
	protected static final int HOPS = 3;
	public CVM() throws Exception {
		ContentDataManager.DATA_DIR_NAME=MY_DATA_DIR_NAME;
		
	}
	
	
	public void deploy() throws Exception {
		
			/* TEST DE BASE FAIT PAR NOUS = 
			 * for(int i=0;i<2;i++) {
				ContentNodeAddress c = new ContentNodeAddress(AbstractPort.generatePortURI(), AbstractPort.generatePortURI(),AbstractPort.generatePortURI(), false,true);
				HashSet<String> inter=new HashSet<String>();
				inter.add("carl");
				HashSet<String> compo=new HashSet<String>();
				compo.add("stella");
				ContentDescriptor cd = new ContentDescriptor("The Nights", "album", inter, compo,c,2);
				
				AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] {cd});
			}
		
		
		ApplicationNodeAddress a = new ApplicationNodeAddress(AbstractPort.generatePortURI(),AbstractPort.generatePortURI(), AbstractPort.generatePortURI(),true, false);
		AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[] {a});	
		ApplicationNodeAddress a2 = new ApplicationNodeAddress(AbstractPort.generatePortURI(), AbstractPort.generatePortURI(), AbstractPort.generatePortURI(),true, false);
	
		ContentTemplateI ct = new ContentTemplate("The Nights","", new HashSet<String>(), new HashSet<String>());
		AbstractComponent.createComponent(Client.class.getCanonicalName(),new Object[] {ct,1});
		*/
		ContentNodeAddress c2 = new ContentNodeAddress(AbstractPort.generatePortURI(), AbstractPort.generatePortURI(),AbstractPort.generatePortURI(), false,true);
		HashSet<String> inter=new HashSet<String>();
		inter.add("carl");
		HashSet<String> compo=new HashSet<String>();
		compo.add("stella");
		ContentDescriptor cd2 = new ContentDescriptor("The Nights", "album", inter, compo,c2,2);
			this.readDescriptors=ContentDataManager.readDescriptors(1);
			System.out.println(this.readDescriptors);
			for(HashMap<String,Object> hm:this.readDescriptors) {
				ContentNodeAddress c = new ContentNodeAddress(AbstractPort.generatePortURI(), AbstractPort.generatePortURI(),AbstractPort.generatePortURI(), false,true);
				ContentDescriptor cd = new ContentDescriptor(hm,c);
				//cd.afficherCD();
				AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] {cd});
			}
			ApplicationNodeAddress a = new ApplicationNodeAddress(AbstractPort.generatePortURI(),AbstractPort.generatePortURI(), AbstractPort.generatePortURI(),true, false);
			AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[] {a});
			
			ContentTemplateI ct = new ContentTemplate("","Brandebourg Concertos", new HashSet<String>(), new HashSet<String>());
			AbstractComponent.createComponent(Client.class.getCanonicalName(),new Object[] {ct,4});
			
		super.deploy();
	}

	public static void main(String[] args) {
		try {
			CVM c=new CVM();
			c.startStandardLifeCycle(50000L);
			Thread.sleep(5000L);
			System.exit(0);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
		
	}

