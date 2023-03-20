package component;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

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
import interfaces.FacadeContentManagementCI;
import interfaces.FacadeContentManagementI;
import interfaces.NodeAddressI;
import interfaces.NodeCI;
import interfaces.NodeManagementCI;
import interfaces.App.ApplicationNodeAddress;
import interfaces.node.ContentNodeAddressI;
import interfaces.node.PeerNodeAddressI;

@RequiredInterfaces(required = { NodeCI.class, ContentManagementCI.class })
@OfferedInterfaces(offered = { NodeManagementCI.class, ContentManagementCI.class, FacadeContentManagementCI.class})

public class Facade extends AbstractComponent implements ContentManagementImplementationI, FacadeContentManagementI{
	
	public static final String FIP_URI=AbstractPort.generatePortURI();
	public static final String FIP_URI_CM=AbstractPort.generatePortURI();
	public static final NodeAddressI FNA = new ApplicationNodeAddress( FIP_URI,"fna-uri",FIP_URI_CM,true,false);
	protected FacadeInboundPort fip;
	private CMOutboundPort CMopfacade;
	private CMInboundPort CMipfacade;
	private Set<PeerNodeAddressI> liste_pairs;
	private HashMap<PeerNodeAddressI, CMOutboundPort> liste_racine;
	private int valeur=4;
	CMOutboundPort cmop_join;
	
	protected Facade(ApplicationNodeAddress applicationNodeAddress) throws Exception{
		super(1,0);
		this.liste_pairs=new HashSet<PeerNodeAddressI>();
		this.liste_racine= new HashMap<PeerNodeAddressI,CMOutboundPort>();
		this.fip= new FacadeInboundPort(FIP_URI,this);
		this.fip.publishPort();
		this.CMipfacade=new CMInboundPort(FIP_URI_CM,this);
		this.CMipfacade.publishPort();
		
	}
	
	public synchronized void start() throws ComponentStartException {
		try {
			this.CMopfacade= new CMOutboundPort(AbstractPort.generatePortURI(),this);
			this.CMopfacade.publishPort();
			System.out.println(CMopfacade.getPortURI());
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
			this.cmop_join.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
		doPortDisconnection(this.cmop_join.getPortURI());
		super.finalise();
	}
	
	public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
		System.out.println("Je suis dans join de Facade...");
		this.liste_pairs.add(a);
		if(this.valeur==4) {
			this.cmop_join=new CMOutboundPort(AbstractPort.generatePortURI(),this);
			this.cmop_join.publishPort();
			doPortConnection(this.cmop_join.getPortURI(),((ContentNodeAddressI)a).getContentManagementURI(),ConnectorCM.class.getCanonicalName());
			this.liste_racine.put(a,this.cmop_join);
			this.valeur=0;
			System.out.println("liste_racine " +this.liste_racine);
			return new HashSet<PeerNodeAddressI>(this.liste_pairs);
		}
		else {
			this.valeur++;
			return new HashSet<PeerNodeAddressI>(this.liste_pairs);
		}
	}
	
	public void leave(PeerNodeAddressI a) throws Exception {
		System.out.println("je suis dans leave de Facade...");
		this.liste_pairs.remove(a);
	}

	
	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		System.out.println("Je suis dans find de Facade...");
		for(Entry<PeerNodeAddressI,CMOutboundPort> e : liste_racine.entrySet()) {
			ContentDescriptorI cdI=e.getValue().find(cd, hops);
			if(cdI!=null) {
				return cdI;
			}
		}
		return null;
	}
	
	
	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		System.out.println("Je suis dans match de Facade...");
		for(Entry<PeerNodeAddressI,CMOutboundPort> e : liste_racine.entrySet()) {
			Set<ContentDescriptorI> cdI=e.getValue().match(cd, matched,hops);
			break;	
		}
		return matched;
	}

	@Override
	public void find(ContentTemplateI cd, int hops, NodeAddressI requester, String requestURI)
			throws Exception {
		for(Entry<PeerNodeAddressI,CMOutboundPort> e : liste_racine.entrySet()) {
			e.getValue().find(cd, hops,requester,requestURI);
			
		}
		
	}

	@Override
	public void acceptFound(ContentDescriptorI found, String requestURI) throws Exception {
		found.afficherCD();
		this.traceMessage(found + "\n");
		
	}
	
	
}