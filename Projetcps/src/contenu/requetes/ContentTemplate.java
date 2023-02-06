package contenu.requetes;

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

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	@Override
	public String getAlbumTitle() {
		// TODO Auto-generated method stub
		return albumTitle;
	}

	@Override
	public Set<String> getInterpreters() {
		// TODO Auto-generated method stub
		return interpreters;
	}

	@Override
	public Set<String> getComposers() {
		// TODO Auto-generated method stub
		return composers;
	}

}
