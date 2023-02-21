package component;

import java.util.Set;

import boundPort.CMInboundPort;
import contenu.requetes.ContentDescriptor;
import contenu.requetes.ContentTemplateI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import interfaces.node.ContentNodeAddressI;

public class Client extends AbstractComponent{
	private ContentTemplateI ct;
	private int hops;
	private CMInboundPort CMipclient;
	
	public Client(String title, String albumTitle, Set<String> interpreters, Set<String> composers,
			ContentNodeAddressI nodeAddress, long size,int hops) throws Exception {
		super(1,0);
		this.ct= new ContentDescriptor(title,albumTitle,interpreters,composers,nodeAddress,size);
		this.hops=hops;
		this.CMipclient= new CMInboundPort("fip-client-uri",this);
		this.CMipclient.publishPort();
		
	}
	public synchronized void start() throws ComponentStartException {
		try {
			this.CMipclient.find(this.ct,this.hops);
		} catch (Exception e) {
			throw new ComponentStartException(e);
		}
		super.start();
	}
	
	public synchronized void shutdown() throws ComponentShutdownException {
		try {
			this.CMipclient.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	public synchronized void finalise() throws Exception{
		super.finalise();
	}

}
