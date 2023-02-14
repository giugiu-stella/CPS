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

}
