package component;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import CVM.CVM;
import boundPort.CMOutboundPort;
import connector.ConnectorCM;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.AbstractPort;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.utils.aclocks.AcceleratedClock;
import fr.sorbonne_u.utils.aclocks.ClocksServer;
import fr.sorbonne_u.utils.aclocks.ClocksServerCI;
import fr.sorbonne_u.utils.aclocks.ClocksServerConnector;
import fr.sorbonne_u.utils.aclocks.ClocksServerOutboundPort;
import interfaces.ContentManagementCI;


@RequiredInterfaces(required = {ContentManagementCI.class, ClocksServerCI.class})
@OfferedInterfaces(offered = {ContentManagementCI.class })
public class Client extends AbstractComponent{
	private ContentTemplateI ct;
	private int hops;
	private CMOutboundPort CMopclient;
	protected ClocksServerOutboundPort csop;
	
	protected Client(ContentTemplateI ct,int hops) throws Exception {
		super(1,1);
		this.ct= ct;
		this.hops=hops;
		this.CMopclient= new CMOutboundPort(AbstractPort.generatePortURI(),this);
		this.CMopclient.publishPort();
		this.csop = new ClocksServerOutboundPort(this);
		this.csop.publishPort();
	}
	
	
	public synchronized void start() throws ComponentStartException {
		try {
			
			doPortConnection(this.CMopclient.getPortURI(),Facade.FIP_URI_CM,ConnectorCM.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
		super.start();
	}
	public void execute() throws Exception{
		System.out.println("Je suis dans execute du Client...");
		this.doPortConnection(
				this.csop.getPortURI(),
				ClocksServer.STANDARD_INBOUNDPORT_URI,
				ClocksServerConnector.class.getCanonicalName());
		AcceleratedClock clock = this.csop.getClock(CVM.CLOCK_URI);
		Instant startInstant = clock.getStartInstant();
		clock.waitUntilStart();
		long delayInNanos =
				clock.nanoDelayUntilAcceleratedInstant(
												startInstant.plusSeconds(400));
		this.scheduleTask(
				o -> {
					try {
						((Client)o).action();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				},
				delayInNanos,
				TimeUnit.NANOSECONDS);
	
	}
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.CMopclient.unpublishPort();
			this.csop.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
		this.doPortDisconnection(this.csop.getPortURI());
		this.doPortDisconnection(this.CMopclient.getPortURI());
		super.finalise();
	}
	
	public void		action() throws Exception
	{
		System.out.println("Client acting now: " + System.currentTimeMillis());
		System.out.println(" ");
		System.out.println("Content Template que nous cherchons :");
		System.out.println("--------------------------------------");
		ct.afficherCD();
		System.out.println(" "); 
		Set<ContentDescriptorI> cd=this.CMopclient.match(this.ct,new HashSet<>(), this.hops);
		if(cd==null) {
			System.out.println("null");
		}

		else {
			for(ContentDescriptorI cdi: cd) {
				System.out.println(" ");
				System.out.println("Content Descriptor trouvé :");
				System.out.println("----------------------------");
				cdi.afficherCD();
			}
		}
	}

}
