package interfaces;

import rep.facade.ContentNodeAddressI;

public interface ContentDescriptorI extends ContentTemplateI{
	public ContentNodeAddressI getContentNodeAddress();
	public long getSize();
	public boolean equals(ContentDescriptorI cd);
	public boolean match(ContentDescriptorI t);

}
