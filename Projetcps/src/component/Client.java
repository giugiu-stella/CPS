package component;


import boundPort.CMOutboundPort;
import connector.ConnectorCM;
import contenu.requetes.ContentDescriptorI;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.ContentManagementCI;


@RequiredInterfaces(required = {ContentManagementCI.class })
@OfferedInterfaces(offered = {ContentManagementCI.class })
public class Client extends AbstractComponent{
	private ContentTemplateI ct;
	private int hops;
	private CMOutboundPort CMopclient;
	private String port_facade_ip;
	
	protected Client(ContentTemplateI ct,int hops) throws Exception {
		super(1,0);
		this.ct= ct;
		this.hops=hops;
		this.CMopclient= new CMOutboundPort("fip-client-uri-1",this);
		this.CMopclient.publishPort();
		this.port_facade_ip="oui";
		
	}
	public synchronized void start() throws ComponentStartException {
		try {
			doPortConnection(this.CMopclient.getPortURI(),this.port_facade_ip,ConnectorCM.class.getCanonicalName());
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
		super.start();
	}
	public void execute() throws Exception{
		System.out.println("Je suis dans execute du Client...");
		ContentDescriptorI cd=this.CMopclient.find(this.ct,this.hops);
		cd.afficherCD();
		
	}
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.CMopclient.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
		super.finalise();
	}

}
