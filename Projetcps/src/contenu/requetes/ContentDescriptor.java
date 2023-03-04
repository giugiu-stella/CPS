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
		if (!cd.getTitle().equals(this.getTitle())) {
			return false;
		}
		if(!cd.getAlbumTitle().equals(this.getAlbumTitle())) {
			return false;
		}
		
		boolean ispresent=false;
		for(String interpreter1: cd.getInterpreters()) {
			for(String interpreter2: this.interpreters) {
				if(interpreter1.equals(interpreter2)){
					ispresent=true;
					break;
				}
			}
			if(!ispresent) {
				return false;
			}
		}
		
		ispresent=false;
		for(String composer1: cd.getComposers()) {
			for(String composer2: this.composers) {
				if(composer1.equals(composer2)){
					ispresent=true;
					break;
				}
			}
			if(!ispresent) {
				return false;
			}
		}
		return true;
	}
	
	public boolean egalitebool(boolean b1,boolean b2) {
		if(b1==b2) {
			return true;
		}
		return false;
		
	}
	@Override
	public boolean match(ContentTemplateI cd) {
		boolean titre=false;
		boolean albumtitre=false;
		boolean interpretes=false;
		boolean compositeurs=false;
		boolean restitre=false;
		boolean resalbumtitre=false;
		boolean resinterpretes=false;
		boolean rescompositeurs=false;
		if(!(cd.getTitle().isEmpty())) {
			titre=true;
			restitre=cd.getTitle().equals(this.getTitle());
		}
	
		if(!(cd.getAlbumTitle().isEmpty())) {
			albumtitre=true;
			resalbumtitre=cd.getAlbumTitle().equals(this.getAlbumTitle());
		}
		if(!(cd.getInterpreters().isEmpty())) {
			interpretes=true;
			boolean ispresent=false;
			for(String interpreter1: cd.getInterpreters()) {
				for(String interpreter2: this.interpreters) {
					if(interpreter1.equals(interpreter2)){
						ispresent=true;
						break;
					}
				}
				if(!ispresent) {
					resinterpretes= false;
				}
			}
			resinterpretes=true;
		}
		if(!(cd.getComposers().isEmpty())) {
			compositeurs=true;
			boolean ispresent=false;
			for(String composer1: cd.getComposers()) {
				for(String composer2: this.composers) {
					if(composer1.equals(composer2)){
						ispresent=true;
						break;
					}
				}
				if(!ispresent) {
					rescompositeurs=false;
				}
			}
			rescompositeurs=true;
		}
		return egalitebool(titre,restitre) && egalitebool(albumtitre,resalbumtitre) && egalitebool(interpretes,resinterpretes) && egalitebool(compositeurs,rescompositeurs);
	}
	/*
	public boolean match(ContentTemplateI cd) {

		if(!(cd.getTitle().isEmpty())) {
			return cd.getTitle().equals(this.getTitle());
		}
	
		if(!(cd.getAlbumTitle().isEmpty())) {
			return cd.getAlbumTitle().equals(this.getAlbumTitle());
		}
		if(!(cd.getInterpreters().isEmpty())) {
			boolean ispresent=false;
			for(String interpreter1: cd.getInterpreters()) {
				for(String interpreter2: this.interpreters) {
					if(interpreter1.equals(interpreter2)){
						ispresent=true;
						break;
					}
				}
				if(!ispresent) {
					return false;
				}
			}
			return true;
		}
		if(!(cd.getComposers().isEmpty())) {
			boolean ispresent=false;
			for(String composer1: cd.getComposers()) {
				for(String composer2: this.composers) {
					if(composer1.equals(composer2)){
						ispresent=true;
						break;
					}
				}
				if(!ispresent) {
					 return false;
				}
			}
			return true;
		}
		return false;
	}
	*/
	@Override
	public void afficherCD() {
		System.out.println("LE CONTENT DESCRIPTOR : ");
		System.out.println("-------------------------");
		System.out.println("titre : "+this.title);
		System.out.println("titre de l'album : "+this.albumTitle);
		System.out.println("interpretes : "+this.interpreters);
		System.out.println("composers : "+this.composers);
		
	}

}
