package Project;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import util.Contract;

/**
 * @inv 
 *      <pre>
 *      getAdmins != null
 *      getAdmins.size() >= 0
 *      getFollowList().size() == 0
 * 		</pre>
 * 
 * @cons
 * <pre>
 * 		$DESC$ Un sommet de name s, et d'une Collection d'administrateur admin
 * 		$ARGS$ String s, Collection<Utilisateur> admin)
 * 		$PRE$
 * 			admin != null
 *     	$POST$
 *         getName().equals(n)
 *         getAdmins() == admin
 * </pre>
 * 
 * @cons
 * <pre>
 * 		$DESC$ Un sommet de name s
 * 		$ARGS$ String s
 * 		$PRE$
 *     	$POST$
 *         getName().equals(n)
 *         getAdmins().size() == 0
 * </pre>
 */

public class Page extends Sommet{
	  //ATTRIBUTS
	
	  private final Set < Utilisateur > admins = new HashSet < Utilisateur > ();
	
	  //CONSTRUCTEUR
	  
	  Page(String s, Collection < Utilisateur > admin) {
	    super(s);
	    Contract.checkCondition(admin != null,
	      "Collection d'administrateurs n'est pas valide.\n");
	    admins.addAll(admin);
	  }
	
	  Page(String s) {
	    this(s, new HashSet < Utilisateur > ());
	  }
	  
	  //REQUETES
	  
	  public Set < Utilisateur > getAdmins() {
	    return new HashSet < Utilisateur > (admins);
	  }
	    public String toString() {
	        return getName();
	    }
	  //COMMANDE
	  
	  public void addAdmin(Utilisateur u) {
	    Contract.checkCondition(u != null, "Utilisateur invalide");
	    admins.add(u);
	  }
	
	  public void removeAdmin(Utilisateur u) {
	    Contract.checkCondition(admins.contains(u),
	      "Utilisateur invalide ou n'appartient à la liste des administrateur.");
	    admins.remove(u);
	  }
	    /*
	     *  Renvoie les ID des administrateurs sous forme de chaîne de caractères
	     */
	    public String getAdminsNames() {
	        String str = "";
	        
	        Iterator<Utilisateur> it = admins.iterator();
	        
	        if (it.hasNext()) {
	        	 str = it.next().getFullName();
	             
	             while (it.hasNext()) {
	                 str += ", " + it.next().getFullName();
	             }
	        }
	        
	        return str;
	    }
	  
}