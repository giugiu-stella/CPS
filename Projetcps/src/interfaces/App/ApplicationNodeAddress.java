package interfaces.App;

public class ApplicationNodeAddress implements ApplicationNodeAddressI {
	
	private String nodeManagementURI;
	private String nodeIdentifier;
	private String contentManagementURI;
	private boolean isFacade;
	private boolean isPeer;
	
	public ApplicationNodeAddress(String managementURI, String nodeIdentifier, String contentManagementURI,
			boolean isFacade, boolean isNode) {
		super();
		this.nodeManagementURI = managementURI;
		this.nodeIdentifier = nodeIdentifier;
		this.contentManagementURI = contentManagementURI;
		this.isFacade = isFacade;
		this.isPeer = isNode;
	}

	@Override
	public String getNodeManagementURI() {
		return nodeManagementURI;
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

}
