package component;


import java.util.HashSet;
import java.util.Set;

import boundPort.CMInboundPort;
import boundPort.FacadeInboundPort;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ContentManagementCI;
import interfaces.NodeCI;
import interfaces.NodeManagementCI;
import interfaces.App.ApplicationNodeAddress;
import interfaces.node.PeerNodeAddressI;

@RequiredInterfaces(required = { NodeCI.class, ContentManagementCI.class })
@OfferedInterfaces(offered = { NodeManagementCI.class, ContentManagementCI.class })

public class Facade extends AbstractComponent{
	
	public static final String FIP_URI="fip-uri";
	protected FacadeInboundPort fip;
	private ApplicationNodeAddress applicationNodeAddress;
	private CMInboundPort CMip;
	private Set<PeerNodeAddressI> liste_pairs;

	
	protected Facade(ApplicationNodeAddress applicationNodeAddress) throws Exception{
		super(1,0);
		this.fip= new FacadeInboundPort(FIP_URI,this);
		this.fip.publishPort();
		this.applicationNodeAddress=applicationNodeAddress;
		this.liste_pairs=new HashSet<PeerNodeAddressI>();
		
	}
	
	public synchronized void start() throws ComponentStartException {
		try {
			this.CMip= new CMInboundPort(applicationNodeAddress.getContentManagementURI(),this);
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
		return this.liste_pairs;
		
	}
	
	public void leave(PeerNodeAddressI a) throws Exception {
		
	}
	
	
}


//creation d'un composant dans le code framework
//String uri=AbstractComponent.createComponent(Facade.class.getCanonicalName(),new Object[] {1,0});