package Project;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
 *         forall(s) in set
 *             getFollowList().contains(s)
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
    private Map<Sommet, Integer> distance;

		
	//CONSTRUCTEURS
	Sommet(String nom){ //Plus pour les pages
		Contract.checkCondition(nom != null && !nom.equals(""),
				"Le nom est invalide !\n");
		name = nom;
        distance = new HashMap<Sommet, Integer>();
	}
	
	Sommet(String nom, Collection<? extends Sommet> set){
		Contract.checkCondition(nom != null && !nom.equals("") && set != null,
				"Le nom ou le set follow est invalide !\n");
		name = nom;
		follow.addAll(set);
        distance = new HashMap<Sommet, Integer>();
	}
		
	//REQUETES
	
	/*
	 * Renvoie le nom du sommet.
	 */
	public String getName() {
		return name;
	}
	
    /*
     * Renvoie la distance entre ce sommet et le sommet s
     */
    public int getDistance(Sommet s) {
        Contract.checkCondition(s != null);
        return distance.get(s);
    }
    
	/*
	 * Renvoie une image instantanée de l'ensemble follow.
	 */
	public Set<Sommet> getFollowList(){
		return new HashSet<Sommet> (follow); //Renvoyer une copie de follow pour raison de sécurité
	}
	
    /* Donne la distance 'dist' entre ce sommet et le sommet s
     * @pre <pre>
     *  s != null
     *  dist >= 0 </pre>
     * 
     * @post <pre>
     *  getDistance(s) == dist </pre>
     */
	
    public void setDistance(Sommet s, int dist) {
        Contract.checkCondition(s != null && dist >= 0);
        distance.put(s, dist);
    }
    
    /* Supprime toutes les distances de ce sommet
     *  @post <pre>
     *      distance.size() == 0
     */
    public void clearDistance() {
        distance.clear();
    }		
	
	//COMMANDES

	//OUTILS
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
