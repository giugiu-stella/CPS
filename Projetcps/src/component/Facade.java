package component;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import boundPort.CMInboundPort;
import boundPort.CMOutboundPort;
import boundPort.FacadeInboundPort;
import boundPort.NOutboundPort;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
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
	private CMOutboundPort CMip;
	private Set<PeerNodeAddressI> liste_pairs;
	private HashMap<PeerNodeAddressI, CMOutboundPort> liste_racine;
	private int valeur=4;
	
	protected Facade(ApplicationNodeAddress applicationNodeAddress) throws Exception{
		super(1,0);
		this.fip= new FacadeInboundPort(FIP_URI,this);
		this.fip.publishPort();
		this.applicationNodeAddress=applicationNodeAddress;
		this.liste_pairs=new HashSet<PeerNodeAddressI>();
		this.liste_racine= new HashMap<PeerNodeAddressI,CMOutboundPort>();
		
	}
	
	public synchronized void start() throws ComponentStartException {
		try {
			this.CMip= new CMOutboundPort(applicationNodeAddress.getContentManagementURI(),this);
			this.fip.publishPort();
			this.CMip.publishPort();
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
		super.start();
	}
	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.fip.unpublishPort();
			this.CMip.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
		super.finalise();
	}
	
	public Set<PeerNodeAddressI> join(PeerNodeAddressI a) throws Exception {
		System.out.println("je join...");
		this.liste_pairs.add(a);
		
		if(this.valeur==4) {
			this.liste_racine.put(a,this.CMip);
			this.valeur=0;
		}
		else {
			this.valeur++;
		}
		
		return this.liste_pairs;
		
	}
	
	public void leave(PeerNodeAddressI a) throws Exception {
		System.out.println("je suis dans leave de facade...");
		this.liste_pairs.remove(a);
	}

	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		ContentDescriptorI CD = null;
		for(PeerNodeAddressI noeud: liste_racine.keySet()) {
			CD=liste_racine.get(noeud).find(cd, hops);
			
		}
		return CD;
	}

	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		return null;
	}
	
	
}