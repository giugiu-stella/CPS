package contenu.requetes;

import java.util.Set;

public interface ContentTemplateI {
	public String getTitle();
	public String getAlbumTitle();
	public Set<String> getInterpreters();
	public Set<String> getComposers();
	public void afficherCD();
}
