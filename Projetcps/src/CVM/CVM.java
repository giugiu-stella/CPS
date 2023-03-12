package CVM;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

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
import fr.sorbonne_u.utils.aclocks.ClocksServer;
import interfaces.App.ApplicationNodeAddress;
import interfaces.node.ContentNodeAddress;

public class CVM extends AbstractCVM {

	public static final String	MY_DATA_DIR_NAME = "/Users/giulianicarla/eclipse-cps/Projetcps/Test";
	protected static final boolean	DEBUG = true;										
	protected ArrayList<HashMap<String,Object>> readTemplates;
	protected ArrayList<HashMap<String,Object>> readDescriptors;
	protected static final int HOPS = 3;
	protected static final long	DELAY_TO_START_IN_NANOS =TimeUnit.SECONDS.toNanos(5);
	public static final String CLOCK_URI = "my-clock";
	
	
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
		
		long unixEpochStartTimeInNanos =TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis())
				+ CVM.DELAY_TO_START_IN_NANOS;
		
		Instant	startInstant = Instant.parse("2023-02-07T08:00:00Z");
		double accelerationFactor = 60.0;
		AbstractComponent.createComponent(ClocksServer.class.getCanonicalName(),
				new Object[]{CVM.CLOCK_URI, unixEpochStartTimeInNanos,
							 startInstant, accelerationFactor});
		

			this.readDescriptors=ContentDataManager.readDescriptors(1);
			for(HashMap<String,Object> hm:this.readDescriptors) {
				ContentNodeAddress c = new ContentNodeAddress(AbstractPort.generatePortURI(), AbstractPort.generatePortURI(),AbstractPort.generatePortURI(), false,true);
				ContentDescriptor cd = new ContentDescriptor(hm,c);
				AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] {cd});
			}
			ApplicationNodeAddress a = new ApplicationNodeAddress(AbstractPort.generatePortURI(),AbstractPort.generatePortURI(), AbstractPort.generatePortURI(),true, false);
			AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[] {a});
			
			ContentTemplateI ct = new ContentTemplate("","Brandebourg Concertos", new HashSet<String>(), new HashSet<String>());
			AbstractComponent.createComponent(Client.class.getCanonicalName(),new Object[] {ct,HOPS});
			
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

