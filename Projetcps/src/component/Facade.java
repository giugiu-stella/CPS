package component;

import fr.sorbonne_u.components.AbstractComponent;

public class Facade extends AbstractComponent{
	protected Facade(int nbT, int nbST) {
		super(nbT,nbST);
	}
	//def internes à définir
}

//creation d'un composant dans le code framework
//String uri=AbstractComponent.createComponent(Facade.class.getCanonicalName(),new Object[] {1,0});