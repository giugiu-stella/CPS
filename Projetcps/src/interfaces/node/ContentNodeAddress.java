package interfaces.node;

public class ContentNodeAddress implements ContentNodeAddressI {
	
	private String nodeURI;
	private String nodeIdentifier;
	private String contentManagementURI;
	private boolean isFacade;
	private boolean isPeer;
	
	public ContentNodeAddress(String nodeURI, String nodeIdentifier, String contentManagementURI, boolean isFacade,
			boolean isPeer) {
		super();
		this.nodeURI = nodeURI;
		this.nodeIdentifier = nodeIdentifier;
		this.contentManagementURI = contentManagementURI;
		this.isFacade = isFacade;
		this.isPeer = isPeer;
	}

	@Override
	public String getNodeURI() {
		return nodeURI;
	}

	@Override
	public String getNodeIdentifier() {
		return nodeIdentifier;
	}

	@Override
	public boolean isFacade() {
		return isFacade;
	}

	@Override
	public boolean isPeer() {
		return isPeer;
	}

	@Override
	public String getContentManagementURI() {
		return contentManagementURI;
	}

	@Override
	public boolean equalPNA(PeerNodeAddressI p) {
		if(this.nodeURI != p.getNodeURI() || this.nodeIdentifier != p.getNodeIdentifier()) {
			return false;
		}
		return true;
	}

	

}
