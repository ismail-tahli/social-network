package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Contract;

/**
 * @author devr0s
 *
 */

	public class Graphe extends Observable{
		  /**
		   * Représente les Graphes de compte, avec les sommets qui sont des utilisateurs, ou des pages.
		   * 
		   * @inv 
		   *  <pre>
		   * 	getUtilisateurSet() != null && getPageSet() != null
		   * 	getSommets() != null 
		   * 	getSommets().size() == getUtilisateurSet().size() + getPageSet().size()
		   * 	getArcsSet() != null
		   *    forall a in getArcsSet():
		   *
		   *    avgAge() == sum(u.age) / getUtilisateurNb() forall u in getUtilisateurSet()
		   *        
		   *  </pre>
		   * 
		   * @cons
		   *  <pre>
		   * 		$DESC$ Représenter un graphe à patir d'une Collection
		   * 		$ARGS$ Collection<? extends Sommet> s
		   * 		$PRE$
		   *			s != null
		   *     	$POST$
		   *     		sommetSet = s
		   *			forall sommet in getSommets(): 
		   *					sommet instanceof Utilisateur 
		   *							==> getUtilisateurs().contains(s)
		   *								forall v in sommet.getFollowList():
		   *									Let arc ::= Arc(sommet, v)
		   *									getArcs().contains(arc)
		   * 					sommet instanceof Page 
		   *							==> getPage().contains(s)
		   *  </pre>
		   */
	    // CONSTANTES
	    
	    /*private static final Pattern REGEX_USER 
	        = Pattern.compile("^\\{name = (?<name>.+), firstname = (?<firstname>.+), age = (?<age>[0-9]+), neighbors = \\[(?<neighbors>((\\W+, )+\\W+)|\\W*)\\]\\}$");
	    
	    private static final Pattern REGEX_PAGE 
	        = Pattern.compile("^\\{name = (?<name>.+), admins = \\[(?<admins>((\\W+, )+\\W)|\\W*)\\]\\}$");
	      */
		
	    /*
	     * .+ Match un ensemble (de taille > 0) des caractères sauf le saut de ligne.
	     * \w match une chaine de caractère
	     * (?<name>regex) : Captures the text matched by “regex” into the group “name”. 
	     * The name can contain letters and numbers but must start with a letter.
	     */
		
	    private static final Pattern REGEX_USER 
	    = Pattern.compile("^\\{name = (?<name>.+), firstname = (?<firstname>.+), age = (?<age>[0-9]+), neighbors = \\[(?<neighbors>((\\w, )+\\w)|.*)\\]\\}$");

	    private static final Pattern REGEX_PAGE 
	    = Pattern.compile("^\\{name = (?<name>.+), admins = \\[(?<admins>((\\w, )+\\w)|.*)\\]\\}$");
	    
	  //ATTRIBUTS
		
	  private Set < Utilisateur > utilisateurSet = new HashSet < Utilisateur > ();
	  private Set < Page > pageSet = new HashSet < Page > ();
	  private Set < Arcs > arcSet = new HashSet < Arcs > ();
	  private Set < Sommet > sommetSet = new HashSet < Sommet > ();	  
	  private Map<Sommet, LinkedList<Sommet>> listAdj;
	  //CONSTRUCTEURS
	  public Graphe(Collection < ? extends Sommet > s) {
	    Contract.checkCondition(s != null, "Collection invalide.");
	    sommetSet.addAll(s);
	    initialize(s);
	    adjListInit(s);
	  }
	  
	  public Graphe() {
		  //Rien
	  }
	  
	  //CLASSES INTERNES
	  
	  /*
	   * Classe interne statique qui représente les arcs du graphe.
	   */
	  
	  public static class Arcs {
	    /** @inv 
	     *  <pre>
	     *	src() != null
	     *  dest() != null
	     *  </pre>
	     * 
	     * @cons
	     *  <pre>
	     * 		$DESC$ Créer l'arc de source s et de destination d
	     * 		$ARGS$ Utilisateur s, Sommet d
	     * 		$PRE$
	     *   	s != null && d != null
	     *     	$POST$
	     *      src() != s
	     *  	dest() != d
	     *  </pre>
	     */
		  
	    private Utilisateur src;
	    private Sommet dest;
	    private Arcs(Utilisateur s, Sommet d) {
	      assert s != null && d != null;
	      src = s;
	      dest = d;
	    }
	
	    public Utilisateur src() {
	      return src;
	    }
	
	    public Sommet dest() {
	      return dest;
	    }
	    public String toString() {
			return "("+src+"->"+dest+")";
	    	
	    }
	  }
	
	  // REQUETES
	  
	  /*
	   * Renvoie une copie instantanée de la liste des utilisateurs.
	   */
	  
	  public Set < Utilisateur > getUtilisateurSet() {
	    return new HashSet < Utilisateur > (utilisateurSet);
	  }
	
	  /*
	   * Renvoie une copie instantanée de la liste des pages.
	   */
	  
	  public Set < Page > getPageSet() {
	    return new HashSet < Page > (pageSet);
	  }
	
	  /*
	   *  L'ensemble des sommets.
	   */
	  
	  public Set < Sommet > getSommets() {
	    return new HashSet < Sommet > (sommetSet);
	  }
	
	  /*
	   * L'ensemble des arcs
	   */
	  
	  public Set < Arcs > getArcsSet() {
	    return new HashSet < Arcs > (arcSet);
	  }
	  
	  /*
	   * L'age moyen de tous les utilisateurs
	   * @pre
	   *     getUtilisateurNb() > 0
	   */
	  
	  public double avgAge() {
		Contract.checkCondition(getUtilisateurNb() > 0, "avgAge AS Error");
	    int sum = 0;
	    for (Utilisateur u : getUtilisateurSet()) {
	      sum += u.getAge();
	    }
	    return sum / getUtilisateurNb();
	  }
	  
	  /*
	   * Renvoie le nombre de sommets.
	   */
	  
	  public int getSommetNb() {
	    return sommetSet.size();
	  }
	
	  /*
	   * Renvoie le nombre d'utilisateurs.
	   */
	  
	  public int getUtilisateurNb() {
	    return utilisateurSet.size();
	  }
	
	  /*
	   * Renvoie le nombre de pages.
	   */
	  public int getPageNb() {
	    return pageSet.size();
	  }
	
	  /*
	   * Renvoie le nombre d'arcs.
	   */
	  
	  public int arcsNb() {
	    return getArcsSet().size();
	  }
	  /*
	   * Renvoie une liste des sommets trié par nom
	   */
	  public List<Sommet> sortedByName(){
		  List<Sommet> list = new ArrayList<Sommet>(sommetSet);
		  Collections.sort(list, new NameComparator());
		  return list;
	  } 
	  
	  /*
	   * Renvoie une liste des sommets trié par degré sortant (d+)
	   */
	  public List<Sommet> sortedByDegre(){
		  List<Sommet> list = new ArrayList<Sommet>(sommetSet);
		  Collections.sort(list, new DegreComparator());
		  return list;
	  } 
	  
	  /*
	   * Renvoie le PageRank calculé
	   */
	  public Map<Sommet,Double> pageRank(){
		  return PageRank.compute(sommetSet);
	  }
	  
	  /*
	   * Renvoie une liste des sommets trié par rank
	   */
	  public List<Sommet> sortedByRank(){
		  List<Sommet> list = new ArrayList<Sommet>(sommetSet);
		  Map <Sommet,Double> rank = pageRank();
		  RankComparator comp = new RankComparator(rank);
		  Collections.sort(list, comp);
		  System.out.println(list.toString());
		  for(Sommet s : sommetSet) {
			  System.out.println(s.getName() + "=>" + rank.get(s));
		  }
		  return list;
	  } 

	   /* Retourne le sommet de nom 'name' si il existe,
		*  Retourne null sinon
		* @pre <pre>
		*  name != null </pre>
		*/
		public Sommet getVerticeByName(String name) {
			Contract.checkCondition(name != null, "Le nom est invalide.");
			for (Sommet s : sommetSet) {
				if (s.getName().equals(name)) {
					return s;
				}
			}
			return null;
		}
	  
	  //COMMANDES
	  
	  /*
	   * Ajouter un arc au graphe
	   */
	
	  public boolean addArc(Utilisateur u, Sommet s) {
	    Contract.checkCondition(u != null && s != null && sommetSet.contains(s) && utilisateurSet.contains(u),
	      "L'utilisateur u ou sommet s est invalide.");
	    boolean ret = u.follow(s);
	    if(ret) {
	    	arcSet.add(new Arcs(u, s));
	    }
	    setChanged();
	    notifyObservers();
	    return ret;
	  }
	
	  /*
	   * Supprimer un arc au graphe
	   */
	  
	  public boolean removeArc(Arcs arc) {
	    Contract.checkCondition(arcSet.contains(arc),
	      "arc n'existe pas dans arcSet.");
	    boolean ret = arc.src().unfollow(arc.dest());
	    if(ret) {
	    	arcSet.remove(arc);
	    }
	    setChanged();
	    notifyObservers();
	    return ret;
	  }
	
	  public boolean removeArc(Utilisateur u, Sommet v) {
		    Contract.checkCondition(u != null && v != null && sommetSet.contains(v) && utilisateurSet.contains(u),
		  	      "L'utilisateur u ou sommet s est invalide.");
		    for(Arcs a : arcSet) {
		    	if(a.src() == u && a.dest() == v) {
		    		return removeArc(a);
		    	}
		    }
		    return false;
	  }
	  /*
	   * Rajoute le sommet s au graphe.
	   * 
	   * @pre 
	   * 	s != null
	   * 	forall d in s.getFollowList() 
	   * 		getSommets().contains(d)
	   * 
	   * 	s instanceof Utilisateur || s instanceof Page
	   * 
	   *  SI LE NOM EST PRIS ON RAJOUTE PAS
	   * @post 
	   * 	getSommets().contains(s)
	   *  	s instanceof Utilisateur => getUtilisateurSet().contains(s)
	   *  								forall u in s.getFollowList() : getArcs().contains(Arc(s,u))							
	   *  	s instanceof Page => getPageSet().contains(s)
	   */
	  
	  //Question s'il existe déjà il va retourner false mais.
	  public boolean addSommet(Sommet s) {
		  Contract.checkCondition(s != null, "addSommet AS Error: null sommet");
		  Contract.checkCondition(!isTaken(s), "Le nom existe déjà.");
		  sommetSet.add(s);
		  if (s instanceof Page) {
			  boolean ret =  pageSet.add((Page) s);
			  setChanged();
			  notifyObservers();
			  return ret;
		  }
		  if (s instanceof Utilisateur) {
			  boolean result = utilisateurSet.add((Utilisateur) s);
			  for(Sommet d : s.getFollowList()) {
				  Contract.checkCondition(getSommets().contains(d), "addSommet AS Error: d not in graphe");
				  addArc((Utilisateur) s, d);
			  }
			  setChanged();
			  notifyObservers();
			  return result;
		  }
		  Contract.checkCondition(false, "addSommet AS Error: Sommet type not supported");
		  return false;
	  }
	  
	  /*
       * Retire le sommet du graphe
       *
       * @pre
       *     s != null
       */
      public boolean removeSommet(Sommet s) {
    	  Contract.checkCondition(s != null && sommetSet.contains(s), "Erreur : Le sommet n'est pas valide.");
    	  /*
    	   * Je sais que cette condition  "sommetSet.contains(s)" n'est pas vraiment necessaire 
    	   * mais on découvre aussitot que le sommesSet ne contiens pas s et on lève un exception
    	   */
          for (Arcs a : getArcsSet()) {
              if (a.src() == s || a.dest() == s) {
                  removeArc(a);
              }
          }
          if(s instanceof Utilisateur) {
              utilisateurSet.remove(s);
          } else if (s instanceof Page) {
              pageSet.remove(s);
          }
		    boolean ret = sommetSet.remove(s);
		    setChanged();
		    notifyObservers();
		    return ret; 
      }
      

	  	/* Calcule la plus petite distance entre le sommet s et les sommets du graphe
	     * @pre <pre>
	     *  s != null </pre>
	     */
	    public void computeSmallestDistanceFrom(Sommet s) {
	        Contract.checkCondition(s != null, "computeSmallestDistanceFrom AS Error");
	        
	        // Initialisation des distances entre chaque sommet et la source à 10000000
	        for (Sommet u : sommetSet) {
	            u.setDistance(s, 10000000);
	        }
	        
	        // Initialisation de la distance de s à 0
	        s.clearDistance();
	        
	        // p prend l'ensemble des sommets du graphe
	        Set<Sommet> p = new HashSet<Sommet>(sommetSet);
	        
	        // Calcul de la plus petite distance entre le sommet et la source
	        while (!p.isEmpty()) {
	            // On prend le sommet u le plus proche de la source
	            Sommet u = getClosestVertice(s, p);
	            if (u != null) {
	                // On supprime u de p
	                p.remove(u);
	        
	                for (Sommet v : u.getFollowList()) {
	                    int alt = u.getDistance(s) + 1;
	                    if (alt <= v.getDistance(s)) {
	                        v.setDistance(s, alt);
	                    }
	                }
	            }
	        }
	    }

	    /** Sauvegarde le graphe dans un fichier.
	     * 
	     * @pre <pre>
	     *  file != null </pre>
	     * 
	     * @post <pre>
	     *  le contenu du graphe est sauvegardé dans file </pre>
	     * 
	     * @throws <pre>
	     *  IOException: Erreur d'entrée/sortie </pre>
	     */
	    
	    public void saveGraph(File file) throws IOException {
	        Contract.checkCondition(file != null, "saveGraph AS Error");
	        
	        PrintWriter output = new PrintWriter(new FileWriter(file));
	        
	        try {
	        
	            String str = "";
	            
	            for (Sommet s : sommetSet) {
	                str = "";
	                
	                if (s instanceof Utilisateur) {
	                    Utilisateur u = (Utilisateur) s;
	                    str = "{name = " + u.getName() + ", firstname = " + u.getFirstName() 
	                        + ", age = " + u.getAge() + ", "
	                        + "neighbors = [" + u.getFollowsNames() + "]}\n"; 
	                } else if (s instanceof Page) {
	                    Page p = (Page) s;
	                    str = "{name = " + p.getName() 
	                        + ", admins = [" + p.getAdminsNames() + "]}\n";
	                }
	                
	                output.print(str);
	            }

	        } finally {
	            output.close();
	        }
	        setChanged();
	        notifyObservers();
	    }
	    
	    /** Charge le graphe depuis un fichier
	     * @pre <pre>
	     *  file != null </pre>
	     * 
	     * @post <pre>
	     *  Le graphe est chargé avec le contenu du fichier 
	     *  </pre> 
	     *          
	     *
	     * @throws <pre>
	     *  IOException: Erreur d'entrée/sortie
	     *  BadSyntaxException: La ligne lue dans le fichier ne correspond ni à un utilisateur, ni à une page, 
	     *                      le fichier est donc corrompu </pre>
	    */
	    
	    public void loadGraph(File file) throws IOException, BadSyntaxException {
	        Contract.checkCondition(file != null, "loadGraph AS Error");
	        
	        this.clear();
	        
	        BufferedReader input = new BufferedReader(new FileReader(file));
	        
	        //Clé c'est un sommet et la valeur c'est tout ses voisins sortants
	        Map<Sommet, List<String>> neighborsMap = new HashMap<Sommet, List<String>>();
	        	        
	        try {
	            String line = input.readLine();
	            
	            while (line != null) {
	                
	                if (REGEX_USER.matcher(line).matches()) {
	                    // La ligne correspond à un utilisateur
	                    Matcher m = REGEX_USER.matcher(line);
	                    if (m.find()) {
	                        String name = m.group("name");
	                        int age = Integer.parseInt(m.group("age"));
	                        String firstname = m.group("firstname");
	                        String strneighbors = m.group("neighbors");
	                        
	                        Utilisateur u = new Utilisateur(name, firstname, age);
	                        
	                        
	                        if (!strneighbors.isEmpty()) {
	                            List<String> neighborsList = new LinkedList<String>();
	                            StringTokenizer st = new StringTokenizer(strneighbors, ", ");
	                            while (st.hasMoreTokens()) {
	                                String s = st.nextToken();
	                                neighborsList.add(s);
	                            }
	                            neighborsMap.put(u, neighborsList);
	                            
	                        }
	                  
	                        addSommet(u);
	                    }
	                    
	                } else if (REGEX_PAGE.matcher(line).matches()) {
	                    // La ligne correspond à une page
	                    Matcher m = REGEX_PAGE.matcher(line);
	                    if (m.find()) {
	                        String name = m.group("name");
	                        String stradmins = m.group("admins");
	                        
	                        Page p = new Page(name);
	                        
	                        if (!stradmins.isEmpty()) {
	                        
	                            LinkedList<String> adminsList = new LinkedList<String>();
	                            StringTokenizer st = new StringTokenizer(stradmins, ", ");
	                            
	                            while (st.hasMoreTokens()) {
	                                String s = st.nextToken();
	                                adminsList.add(s);
	                            }
	                            
	                            neighborsMap.put(p, adminsList);
	                        }
	                        //Test != null
	                        addSommet(p);
	                        
	                    }
	                
	                } else {
	                	/*
	                	 * Si jamais il réussi à identifier qu'une partie du fichier mais un moment 
	                	 * il rencontre une ligne qui ne matche avec un aucun REGEX,
	                	 * donc on ce cas on supprime tout ce qu'on a load avant et on lève 
	                	 * une Exception e de type BadSyntaxException
	                	 */
	                	
	                    this.clear();
	                    throw new BadSyntaxException();
	                }
	                
	                line = input.readLine();
	            }
	            
	        for (Sommet s : neighborsMap.keySet()) {
	            
	            if (s instanceof Utilisateur) {
	                for (String name : neighborsMap.get(s)) {
	                	Sommet u = getVerticeByName(name);
	                    addArc((Utilisateur) s,u);
	                }
	            } else {
	                for (String name : neighborsMap.get(s)) {
	                    ((Page) s).addAdmin((Utilisateur) getVerticeByName(name));
	                }
	            }
	        }
	        	            
	        } finally {
	            input.close();
	        }
	        
	        setChanged();
	        notifyObservers();
	    }
	    
	//OUTILS
	    
		  private void initialize(Collection < ? extends Sommet > s) {
		    for (Sommet sommet: s) {
		      if (sommet instanceof Utilisateur) {
		        utilisateurSet.add((Utilisateur) sommet);
		        for (Sommet v: sommet.getFollowList()) {
		          arcSet.add(new Arcs((Utilisateur) sommet, v));
		        }
		      } else if (sommet instanceof Page) {
		        pageSet.add((Page) sommet);
		      }
		    }
		  }
		  //Calcule la liste d'adjacence
		  private void adjListInit(Collection < ? extends Sommet > s) {
			  listAdj = new HashMap<Sommet, LinkedList<Sommet>>();
			  LinkedList<Sommet> succ;
			  for (Sommet sommet: s) {
				  succ = new LinkedList<Sommet>(sommet.getFollowList());
				  listAdj.put(sommet, succ);
			  }
		  }
	  private Sommet getClosestVertice(Sommet s, Set<Sommet> verticesSet) {
	        Sommet result = null;
	        int dist;
	        
	        Iterator<Sommet> it = verticesSet.iterator();
	        
	        if (it.hasNext()) {
	            result = it.next();
	            dist = result.getDistance(s);

	            while (it.hasNext()) {
	                Sommet v = it.next();
	                if (v.getDistance(s) < dist) {
	                    result = v;
	                    dist = v.getDistance(s);
	                }
	            }
	        }
	        
	        return result;
	    }
	  	//Clear le Graphe : Rénitiliser le Graphe
	    private void clear() {
	    	arcSet.clear();
	    	pageSet.clear();
	    	utilisateurSet.clear();
	        sommetSet.clear();
	    }
	    /*
	     * Vérifie si le nom du sommet s est déjà existant 
	     * (ie: que il existe déjà un sommet du graphe qui a le même nom )
	     */
	    public boolean isTaken(Sommet s) {
	    	assert s != null;
	    	for(Sommet u: sommetSet) {
	    		if(s.getName().equals(u.getName())) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }
}
