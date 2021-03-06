package Project;
import java.util.Collection;
import java.util.Iterator;

import util.Contract;
/**
 * @inv 
 *      <pre>
 *      getFirstName() != null
 *     	!getFirstName().equals("")
 *      getAge() > 0
 *      getFullName() = getFirstName() + " " + getName()
 * 		</pre>
 * 
 * @cons
 * <pre>
 * 		$DESC$ Un sommet de name s, de prénom p et d'age a.
 * 		$ARGS$ String n, String p, int a
 * 		$PRE$
 *         p != null && !p.equals("") && a > 0
 *     	$POST$
 *         getName().equals(n)
 *         getFollowList().size() == 0
 *         getFirstName() == p
 *         getAge() == a
 * </pre>
 * @cons
 * <pre>
 * 		$DESC$ Un sommet de name s, de prénom p, 
 * 				d'age a et d'une collection de follow set.
 * 		$ARGS$ String n, String p, int a, Collection<? extends Sommet> set
 * 		$PRE$
 *         p != null && !p.equals("") && a > 0 && set != null
 *     	$POST$
 *         getName().equals(n)
 *         forall(s) in set
 *             getFollowList().contains(s)
 *         getFollowList().size() = set.size()
 *         getFirstName() == p
 *         getAge() == a
 * </pre>
 */


public class Utilisateur extends Sommet{
	//ATTRIBUTS
	private String firstName;
	private int age;
	
	//CONSTRUCTEURS
	
	//CONSTRUCTEURS
	Utilisateur(String n, String p, int a){
		super(n);
		initConstructor(p, a);
	}
	
	Utilisateur(String n,Collection<? extends Sommet> set, String p, int a){
		super(n,set);
		initConstructor(p, a);
	}
	// Factorisation
	private void initConstructor(String p, int a) {
		Contract.checkCondition(a > 0 && p != null && !p.equals(""),
				"Prénom ou age invalide !\n");
		firstName = p;
		age = a;
	}
	
	//REQUETES
	
	/*
	 * renvoie le prénom de l'utilisateur.
	 */
	
	public String getFirstName() {
		return firstName;
	}
	
	/*
	 * renvoie l'âge de l'utilisateur.
	 */
	
	public int getAge() {
		return age;
	}
	
	/*
	 * Renvoie le nom complet d'utilisateur.
	 */
	
	public String getFullName() {
		return getName() + " " + getFirstName();
	}
	
    public String toString() {
        return getFullName();
    }
    
	//COMMANDES
	public void setAge(int n) {
		Contract.checkCondition(n > 0 /*&& n != age*/
				, "L'age n n'est pas permis !\n");
		age = n;
	}
	public void setFirstName(String s) {
		Contract.checkCondition( s != null && !s.equals("") /*&& n != age*/
				, "Le prénom n'est pas valide !\n");
		firstName = s;
	}
	
	public boolean follow (Sommet s) {
		Contract.checkCondition(s != null 
				,"Somme s invalide !\n");
		return addSommet(s);
	}
	
	public boolean unfollow (Sommet s) {
		Contract.checkCondition(s != null && getFollowList().contains(s)
				,"Somme s invalide ou n'est pas déjà followed !\n");
		return removeSommet(s);
	}
    /*
     * Retourne les noms des sommets voisins sortants sous forme de chaîne de caractères 
    */
    public String getFollowsNames() {
        String str = "";
        
        Iterator<Sommet> it = getFollowList().iterator();
        
        if (it.hasNext()) {
            str = it.next().getName();
            
            while (it.hasNext()) {
                str += ", " + it.next().getName();
            }
        }
        
        return str;
    }
}
