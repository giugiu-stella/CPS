package interfaces;

public interface ContentDescriptorI extends ContentTemplateI{
	public ContentNodeAddressI getContentNodesAddress();
	public long getSize();
	public boolean equals(ContentDescriptorI cd);
	public boolean match(ContentTemplateI t);

}
