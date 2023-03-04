package CVM;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
		ContentNodeAddress cbis = new ContentNodeAddress("URIbis", "node1bis", "cmuribis", false,true);
		ContentNodeAddress c2 = new ContentNodeAddress("URI2", "node2", "cmuri2", false,true);
		ContentNodeAddress c3 = new ContentNodeAddress("URI3", "node3", "cmuri3", false,true);
		ContentNodeAddress c4 = new ContentNodeAddress("URI4", "node4", "cmuri4", false,true);
		ApplicationNodeAddress a = new ApplicationNodeAddress(AbstractPort.generatePortURI(), "", AbstractPort.generatePortURI(),true, false);
		ContentDescriptor cd = new ContentDescriptor("The Nights", "Avicii", new HashSet<String>(), new HashSet<String>(),c,5);
		ContentDescriptor cd2 = new ContentDescriptor("The Nights", "IDK", new HashSet<String>(), new HashSet<String>(),cbis,5);
		ContentTemplateI ct = new ContentTemplate("The Nights","", new HashSet<String>(), new HashSet<String>());
		ArrayList<ContentDescriptor> contenu = new ArrayList<>();
		contenu.add(cd);
		ArrayList<ContentDescriptor> contenu2 = new ArrayList<>();
		contenu2.add(cd2);
		AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] {c,contenu});		
		AbstractComponent.createComponent(Facade.class.getCanonicalName(), new Object[] {a});	
		AbstractComponent.createComponent(Pairs.class.getCanonicalName(),new Object[] {c2,contenu2});
		AbstractComponent.createComponent(Client.class.getCanonicalName(),new Object[] {ct,2});	
		/*
		HashSet<ContentDescriptorI> descriptors =new HashSet<ContentDescriptorI>();
		for(int i=0;i<10;i++) {
			FileInputStream f = new FileInputStream("Test/descriptors"+i);
			ObjectInputStream of =new ObjectInputStream(f);
			HashMap<String,Object> data = (HashMap<String,Object>)(of.readObject());
			ContentNodeAddress cdescrip= new ContentNodeAddress(AbstractPort.generatePortURI(), AbstractPort.generatePortURI(),AbstractPort.generatePortURI(), false,true);
			descriptors.add(new ContentDescriptor((String) data.get("title"), (String) data.get("album-title"), new HashSet<String>((List) data.get("interpreters")), new HashSet<String>((List) data.get("composers")),cdescrip,(Long) data.get("size")));
			
		}
		System.out.println(descriptors);
		
		HashSet<ContentTemplateI> templates =new HashSet<ContentTemplateI>();
		for(int i=0;i<2;i++) {
			FileInputStream f = new FileInputStream("Test/templates"+i);
			ObjectInputStream of =new ObjectInputStream(f);
			HashMap<String,Object> data = (HashMap<String,Object>)(of.readObject());
			templates.add(new ContentTemplate((String) data.get("title"), (String) data.get("album-title"), new HashSet<String>((List) data.get("interpreters")), new HashSet<String>((List) data.get("composers"))));
		}
		*/
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

