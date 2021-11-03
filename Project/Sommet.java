package Project;
import java.util.*;

import util.Contract;
/**
 * @inv 
 *      <pre>
 *      getName() != null
 * 		!getName().equals("")
 * 		getFollowList() != null
 * 		getFollowList().size() >= 0
 * 		</pre>
 * 
 * @cons
 * <pre>
 * 		$DESC$ Un sommet de name "nom".
 * 		$ARGS$ String nom
 * 		$PRE$
 *         nom != null && !nom.equals("")
 *     	$POST$
 *         getName().equals(nom)
 *         getFollowList().size() == 0
 * </pre>
 * 
 * @cons
 * <pre>
 * 		$DESC$ Un sommet de name "nom" et de liste de follow set.
 * 		$ARGS$ String nom, Collection<? extends Sommet> set
 * 		$PRE$
 *         nom != null && !nom.equals("") && set != null
 *     	$POST$
 *         getName().equals(nom)
 *         getFollowList() == set
 *         getFollowList().size() = set.size()
 * </pre>
 * 
 * 
 * 
 */
public abstract class Sommet  {
	//ATTRIBUTS
	
	//Impossibilité de changer le nom.
	private final String name;
	//Les doublons ne seront pas autorisé avec le Set.
	private final Set<Sommet> follow = new HashSet<Sommet>(); 
		
	//CONSTRUCTEURS
	Sommet(String nom){ //Plus pour les pages
		Contract.checkCondition(nom != null && !nom.equals(""),
				"Le nom est invalide !\n");
		name = nom;
	}
	
	Sommet(String nom, Collection<? extends Sommet> set){
		Contract.checkCondition(nom != null && !nom.equals("") && set != null,
				"Le nom ou le set follow est invalide !\n");
		name = nom;
		follow.addAll(set);
	}
		
	//REQUETES
	
	/*
	 * Renvoie le nom du sommet.
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * Renvoie une image instantanée de l'ensemble follow.
	 */
	public Set<Sommet> getFollowList(){
		return new HashSet<Sommet> (follow); //Renvoyer une copie de follow pour raison de sécurité
	}
				
	
	//COMMANDES

	//OUTILS
		public int compareTo(Sommet other) { //Pour le tri par nom
			return this.getName().compareTo(other.getName());
		}
		//Fonction d'ajout au Set<Sommet> follow
		protected boolean addSommet(Sommet s) {
			assert s != null ;
			return follow.add(s);
		}
		//Fonction de retrait au Set<Sommet> follow		
		protected boolean removeSommet(Sommet s) {
			assert s != null ;
			return follow.remove(s);
		}

}