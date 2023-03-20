package component;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import CVM.CVM;
import boundPort.CMInboundPort;
import boundPort.CMOutboundPort;
import boundPort.FacadeOutboundPort;
import boundPort.NInboundPort;
import boundPort.NOutboundPort;
import connector.ConnectorCM;
import connector.ConnectorN;
import connector.ConnectorNM;
import contenu.requetes.ContentDescriptor;
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
import interfaces.node.ContentNodeAddressI;
import interfaces.node.PeerNodeAddressI;
import fr.sorbonne_u.utils.aclocks.AcceleratedClock;
import fr.sorbonne_u.utils.aclocks.ClocksServer;
import fr.sorbonne_u.utils.aclocks.ClocksServerCI;
import fr.sorbonne_u.utils.aclocks.ClocksServerConnector;
import fr.sorbonne_u.utils.aclocks.ClocksServerOutboundPort;

@RequiredInterfaces(required = {NodeCI.class,ContentManagementCI.class,NodeManagementCI.class, ClocksServerCI.class})
@OfferedInterfaces(offered = {NodeCI.class,ContentManagementCI.class})

public class Pairs extends AbstractComponent implements ContentManagementImplementationI {
	public static final String Pip_URI=AbstractPort.generatePortURI();
	protected FacadeOutboundPort fop;
	protected NInboundPort Nip;
	private CMInboundPort CMippair;
	private ContentDescriptor contentNodeAddress;
	private Set<PeerNodeAddressI> listevoisins;
	private HashMap<PeerNodeAddressI, CMOutboundPort> listevoisins_CMop; //liste pour find et match
	private HashMap<PeerNodeAddressI, NOutboundPort> listevoisins_Nop; 
	protected ClocksServerOutboundPort csop;
	
	
	protected Pairs(ContentDescriptor cd) throws Exception {
		super(1,1); // 1 pour les threads et 1 pour le schedule de l'horloge 
		this.contentNodeAddress= cd;
		this.listevoisins=new HashSet<PeerNodeAddressI>();
		this.listevoisins_Nop= new HashMap<>();
		this.listevoisins_CMop=new HashMap<>();
		this.CMippair=new CMInboundPort(cd.getContentNodeAddress().getContentManagementURI(), this);
		this.csop = new ClocksServerOutboundPort(this);
		this.csop.publishPort();
	}
	
	
	public synchronized void start() throws ComponentStartException {
		try {
			this.fop= new FacadeOutboundPort(AbstractPort.generatePortURI(),this);
			this.fop.publishPort();
			this.doPortConnection(fop.getPortURI(),Facade.FIP_URI,ConnectorNM.class.getCanonicalName());
			this.Nip= new NInboundPort(contentNodeAddress.getContentNodeAddress().getNodeURI(),this);
			this.Nip.publishPort();
			this.CMippair.publishPort();
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
		super.start();
	}
	@Override
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			
			this.fop.unpublishPort();
			this.Nip.unpublishPort();
			this.CMippair.unpublishPort();
			this.csop.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
	 
		this.doPortDisconnection(this.csop.getPortURI());
		
		this.doPortDisconnection(this.fop.getPortURI());
		
		for (Entry<PeerNodeAddressI, CMOutboundPort> e : listevoisins_CMop.entrySet()) {
				disconnect(e.getKey());
				e.getValue().unpublishPort();
				listevoisins_Nop.get(e.getKey()).unpublishPort();
			}  	
		super.finalise();
	}
	
	
	public void execute() throws Exception{
		
		System.out.println("Je suis dans execute de Pairs...");
		this.doPortConnection(
				this.csop.getPortURI(),
				ClocksServer.STANDARD_INBOUNDPORT_URI,
				ClocksServerConnector.class.getCanonicalName());
		AcceleratedClock clock = this.csop.getClock(CVM.CLOCK_URI);
		Instant startInstant = clock.getStartInstant();
		clock.waitUntilStart();
		long delayInNanos =
				clock.nanoDelayUntilAcceleratedInstant(
												startInstant.plusSeconds(150));
		
		this.scheduleTask(
				o -> {
					try {
						((Pairs)o).action();
					} catch (Exception e) {
						e.printStackTrace();
					}
				},
				delayInNanos,
				TimeUnit.NANOSECONDS);

	}
	
	public PeerNodeAddressI connect(PeerNodeAddressI peer) throws Exception {
		System.out.println("je suis dans connect de Pairs...");
		NOutboundPort Nip2;
		Nip2= new NOutboundPort(AbstractPort.generatePortURI(),this);
		Nip2.publishPort();
		CMOutboundPort CMop2=new CMOutboundPort(AbstractPort.generatePortURI(),this);
		CMop2.publishPort();
		this.doPortConnection(CMop2.getPortURI(),((ContentNodeAddressI)peer).getContentManagementURI(),ConnectorCM.class.getCanonicalName());
		this.doPortConnection(Nip2.getPortURI(),peer.getNodeURI(),ConnectorN.class.getCanonicalName());
		this.listevoisins_Nop.put(peer,Nip2);
		this.listevoisins_CMop.put(peer,CMop2);
		
		return this.contentNodeAddress.getContentNodeAddress();
	}
	
	
	public void disconnect(PeerNodeAddressI peer) throws Exception {
		System.out.println("je suis dans disconnect de Pairs...");
		
		String port_sortant_peer="";
		for (Entry<PeerNodeAddressI, NOutboundPort> e : listevoisins_Nop.entrySet()) {
			if(peer.equalPNA(e.getKey())) {
				port_sortant_peer= e.getValue().getPortURI();
				break;
			}  	
		}
		String port_sortant_peer_cm="";
		for (Entry<PeerNodeAddressI, CMOutboundPort> e : listevoisins_CMop.entrySet()) {
			if(peer.equalPNA(e.getKey())) {
				port_sortant_peer_cm= e.getValue().getPortURI();
				break;
			}  	
		}
	
		this.doPortDisconnection(port_sortant_peer);
		this.doPortDisconnection(port_sortant_peer_cm);
		
	}
	
	public Set<PeerNodeAddressI> join() throws Exception {
		return null;	
	}
	
	public void leave() throws Exception {
		
	}

	@Override
	public ContentDescriptorI find(ContentTemplateI cd, int hops) throws Exception {
		System.out.println("Je suis dans find de Pairs...");
		if (hops==0) {
			return null;
		}
	
		if(this.contentNodeAddress.match(cd)) {
			return this.contentNodeAddress;
		}
		
		hops--;
		if(hops !=0 && !(listevoisins_CMop.isEmpty())) {
			for (Entry<PeerNodeAddressI,CMOutboundPort> e : listevoisins_CMop.entrySet()) {
				  e.getValue().find(cd,hops);
				  break;
			}
		}
		
		return null;
	}

	@Override
	public Set<ContentDescriptorI> match(ContentTemplateI cd, Set<ContentDescriptorI> matched, int hops)
			throws Exception {
		System.out.println("Je suis dans match de Pairs...");
		if (hops==0) {
			return matched;
		}
		if(this.contentNodeAddress.match(cd)) {
			matched.add(this.contentNodeAddress);
		}
		
		hops--;
		System.out.println(listevoisins_CMop);
		System.out.println("hops "+ hops);
		if(hops !=0 && !(listevoisins_CMop.isEmpty())) {
			for (Entry<PeerNodeAddressI,CMOutboundPort> e : listevoisins_CMop.entrySet()) {
				 e.getValue().match(cd,matched,hops); // si on se stop ici => le pb est l'interblocage à régler 
				  
				  break;
			}
		}
		
		return matched;
	}
	public void		action() throws Exception
	{System.out.println("Pairs acting now: " + System.currentTimeMillis());
		
		this.listevoisins=this.fop.join(contentNodeAddress.getContentNodeAddress());
		
		for(PeerNodeAddressI p: this.listevoisins) {
			if(p.equals(contentNodeAddress.getContentNodeAddress())) {
				continue;
			}
			System.out.println(p.toString());
			NOutboundPort Nip;
			Nip= new NOutboundPort(AbstractPort.generatePortURI(),this);
			Nip.publishPort();
			this.doPortConnection(Nip.getPortURI(),p.getNodeURI(),ConnectorN.class.getCanonicalName());
			CMOutboundPort CMop=new CMOutboundPort(AbstractPort.generatePortURI(),this);
			CMop.publishPort();
			this.doPortConnection(CMop.getPortURI(),((ContentNodeAddressI)p).getContentManagementURI(),ConnectorCM.class.getCanonicalName());
			this.listevoisins_Nop.put(p,Nip);
			this.listevoisins_CMop.put(p,CMop);
			Nip.connect(contentNodeAddress.getContentNodeAddress());
		}
	}
}
