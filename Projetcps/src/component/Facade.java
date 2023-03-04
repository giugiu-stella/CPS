package component;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import boundPort.CMInboundPort;
import boundPort.CMOutboundPort;
import boundPort.FacadeInboundPort;
import connector.ConnectorCM;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ContentManagementCI;
import interfaces.ContentManagementImplementationI;
import interfaces.NodeCI;
import interfaces.NodeManagementCI;
import interfaces.App.ApplicationNodeAddress;
import interfaces.node.PeerNodeAddressI;

@RequiredInterfaces(required = { NodeCI.class, ContentManagementCI.class })
@OfferedInterfaces(offered = { NodeManagementCI.class, ContentManagementCI.class })

public class Facade extends AbstractComponent implements ContentManagementImplementationI{
	
	public static final String FIP_URI="fip-uri";
	protected FacadeInboundPort fip;
	private ApplicationNodeAddress applicationNodeAddress;
	private CMOutboundPort CMopfacade;
	private CMInboundPort CMipfacade;
	private Set<PeerNodeAddressI> liste_pairs;
	private HashMap<PeerNodeAddressI, CMOutboundPort> liste_racine;
	private int valeur=4;
	
	protected Facade(ApplicationNodeAddress applicationNodeAddress) throws Exception{
		super(1,0);
		this.liste_pairs=new HashSet<PeerNodeAddressI>();
		this.liste_racine= new HashMap<PeerNodeAddressI,CMOutboundPort>();
		this.applicationNodeAddress=applicationNodeAddress;
		this.fip= new FacadeInboundPort(FIP_URI,this);
		this.CMipfacade=new CMInboundPort("oui",this);
		this.fip.publishPort();
		this.CMipfacade.publishPort();
	}
	
	public synchronized void start() throws ComponentStartException {
		try {
			this.CMopfacade= new CMOutboundPort(AbstractPort.generatePortURI(),this);
			this.CMopfacade.publishPort();
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
		super.start();
	}
	
	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.fip.unpublishPort();
			this.CMipfacade.unpublishPort();
			this.CMopfacade.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
		super.finalise();
	}
	
	public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
		System.out.println("Je suis dans join de Facade...");
		this.liste_pairs.add(a);
		System.out.println(this.valeur);
		if(this.valeur==4) {
			this.liste_racine.put(a,this.CMopfacade);
			this.valeur=0;
			System.out.println("liste_racine " +this.liste_racine);
			return this.liste_pairs;
		}
		else {
			this.valeur++;
			return this.liste_pairs;
		}
	}
	
	public void leave(PeerNodeAddressI a) throws Exception {
		System.out.println("je suis dans leave de Facade...");
		this.liste_pairs.remove(a);
	}

	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		System.out.println("Je suis dans find de Facade...");
		doPortConnection(this.CMopfacade.getPortURI(),"non",ConnectorCM.class.getCanonicalName());
		int n = liste_racine.size();
		int intRand= (int) (Math.random() * n);
		System.out.println("taille "+ n);
		Object uri=liste_racine.keySet().toArray()[intRand];
		//SI PB C EST À CAUSE DE INTRAND QUI EST EN DEHORS DE LA LISTE
		CMOutboundPort port=liste_racine.get(uri);
		return port.find(cd, hops);
	}
	
	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		System.out.println("Je suis dans match de Facade...");
		doPortConnection(this.CMopfacade.getPortURI(),"non",ConnectorCM.class.getCanonicalName());
		int n = liste_racine.size();
		int intRand= (int) (Math.random() * n);
		Object uri=liste_racine.keySet().toArray()[intRand];
		CMOutboundPort port=liste_racine.get(uri);
		return port.match(cd, matched, hops);
	}
	
	
}