package contenu.requetes;

import java.util.HashMap;
import java.util.Set;

import interfaces.node.ContentNodeAddressI;

public class ContentDescriptor extends ContentTemplate implements ContentDescriptorI {
	
	private ContentNodeAddressI nodeAddress;
	private long size;
	
	
	public ContentDescriptor(String title, String albumTitle, Set<String> set, Set<String> set2,
			ContentNodeAddressI nodeAddress, long size) {
		super(title,albumTitle,set,set2); 
		this.nodeAddress = nodeAddress;
		this.size = size;
	}
	
	public ContentDescriptor(HashMap<String, Object> toLoad,ContentNodeAddressI nodeAddress) {
        super(toLoad);
        this.size = (Long) toLoad.get("size");
        this.nodeAddress = nodeAddress;
    }
	@Override
	public String toString() {
		return this.nodeAddress.getNodeURI();
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
		System.out.println("je passe dans equals ... ");
		if (!cd.getTitle().equals(this.getTitle())) {
			System.out.println("je sors dans equals ... ");
			return false;
		}
		if(!cd.getAlbumTitle().equals(this.getAlbumTitle())) {
			System.out.println("je sors dans equals ... ");
			return false;
		}
		
		boolean ispresent=false;
		for(String interpreter1: cd.getInterpreters()) {
			for(String interpreter2: getInterpreters()) {
				if(interpreter1.equals(interpreter2)){
					ispresent=true;
					break;
				}
			}
			if(!ispresent) {
				System.out.println("je sors dans equals ... ");
				return false;
			}
		}
		
		ispresent=false;
		for(String composer1: cd.getComposers()) {
			for(String composer2: getComposers()) {
				if(composer1.equals(composer2)){
					ispresent=true;
					break;
				}
			}
			if(!ispresent) {
				System.out.println("je sors dans equals ... ");
				return false;
			}
		}
		System.out.println("je sors dans equals ... ");
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
			//System.out.println("Liste pb = "+ this.interpreters);
			if ( getInterpreters()==null) {
				return false;
			}
			resinterpretes=getInterpreters().equals(cd.getInterpreters());
		}
		if(!(cd.getComposers().isEmpty())) {
			compositeurs=true;
			//System.out.println("Liste pb = "+ this.composers);
			if ( getComposers()==null) {
				return false;
			}
			rescompositeurs=getComposers().equals(cd.getComposers());
			
		}
		if(titre==false && albumtitre==false && interpretes==false && compositeurs==false) {
			return false;
		}
		return egalitebool(titre,restitre) && egalitebool(albumtitre,resalbumtitre) && egalitebool(interpretes,resinterpretes) && egalitebool(compositeurs,rescompositeurs);
	}
	
}
