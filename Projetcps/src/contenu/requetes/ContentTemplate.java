package contenu.requetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ContentTemplate implements ContentTemplateI {
	private String title;
	private String albumTitle;
	private Set<String> interpreters;
	private Set<String> composers;
	
	public ContentTemplate(String title, String albumTitle,
			Set<String> interpreters, Set<String> composers) {
		super();
		this.title = title;
		this.albumTitle = albumTitle;
		this.interpreters = interpreters;
		this.composers = composers;
	}

	public ContentTemplate(HashMap<String, Object> toLoad) {
        super();
        
        if(toLoad.get("title")==null) {
        	this.title="";
        }
        else{
        	this.title = (String) toLoad.get("title");
        }
        if(toLoad.get("album-title")==null) {
        	this.albumTitle="";
        }
        else{
        	this.albumTitle = (String) toLoad.get("album-title");
        }
        this.composers = new HashSet<String>();
        this.interpreters = new HashSet<String>();

        ArrayList<?> composersBeforeCast = (ArrayList<?>) toLoad.get("composers");
       if(composersBeforeCast!=null) {
        for (Object object : composersBeforeCast)
            this.composers.add((String) object);
       }
        ArrayList<?> intepretersBeforeCast = (ArrayList<?>) toLoad.get("interpreters");
        if(intepretersBeforeCast!=null){
            for (Object object : intepretersBeforeCast)
            this.interpreters.add((String) object);
        }
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
	public void afficherCD() {
		System.out.println("LE CONTENT DESCRIPTOR : ");
		System.out.println("-------------------------");
		System.out.println("titre : "+this.title);
		System.out.println("titre de l'album : "+this.albumTitle);
		System.out.println("interpretes : "+this.interpreters);
		System.out.println("composers : "+this.composers);
		
	}

}
