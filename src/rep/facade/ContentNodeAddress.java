package rep.facade;

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
		// TODO Auto-generated method stub
		return nodeURI;
	}

	@Override
	public String getNodeIdentifier() {
		// TODO Auto-generated method stub
		return nodeIdentifier;
	}

	@Override
	public boolean isFacade() {
		// TODO Auto-generated method stub
		return isFacade;
	}

	@Override
	public boolean isPeer() {
		// TODO Auto-generated method stub
		return isPeer;
	}

	@Override
	public String getContentManagementURI() {
		// TODO Auto-generated method stub
		return contentManagementURI;
	}

}
