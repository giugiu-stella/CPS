package contenu.requetes;

import java.util.Set;

import interfaces.node.ContentNodeAddressI;

public class ContentDescriptor implements ContentDescriptorI {
	
	private String title;
	private String albumTitle;
	private Set<String> interpreters;
	private Set<String> composers;
	private ContentNodeAddressI nodeAddress;
	private long size;
	
	
	public ContentDescriptor(String title, String albumTitle, Set<String> interpreters, Set<String> composers,
			ContentNodeAddressI nodeAddress, long size) {
		super();
		this.title = title;
		this.albumTitle = albumTitle;
		this.interpreters = interpreters;
		this.composers = composers;
		this.nodeAddress = nodeAddress;
		this.size = size;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getAlbumTitle() {
		return albumTitle;
	}

	@Override
	public Set<String> getInterpreters() {
		return interpreters;
	}

	@Override
	public Set<String> getComposers() {
		return composers;
	}

	@Override
	public ContentNodeAddressI getContentNodeAddress() {
		return nodeAddress;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public boolean equals(ContentDescriptorI cd) {
		return false;
	}

	@Override
	public boolean match(ContentTemplateI cd) {
		if(!(cd.getTitle().isEmpty())) {
			return cd.getTitle().equals(this.getTitle());
		}
	
		if(!(cd.getAlbumTitle().isEmpty())) {
			return cd.getAlbumTitle().equals(this.getAlbumTitle());
		}
		return false;
	}

}
