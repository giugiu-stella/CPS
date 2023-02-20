package contenu.requetes;

import interfaces.node.ContentNodeAddressI;

public interface ContentDescriptorI extends ContentTemplateI{
	public ContentNodeAddressI getContentNodeAddress();
	public long getSize();
	public boolean equals(ContentDescriptorI cd);
	public boolean match(ContentTemplateI t);

}
