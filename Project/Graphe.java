package Project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.Contract;

/**
 * @author devr0s
 *
 */
	public class Graphe {
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
	
	  //ATTRIBUTS
		
	  private Set < Utilisateur > utilisateurSet = new HashSet < Utilisateur > ();
	  private Set < Page > pageSet = new HashSet < Page > ();
	  private Set < Arcs > arcSet = new HashSet < Arcs > ();
	  private Set < Sommet > sommetSet = new HashSet < Sommet > ();	  
	  private Map<Sommet, LinkedList<Sommet>> listAdj;
	  //CONSTRUCTEURS
	  
	  Graphe(Collection < ? extends Sommet > s) {
	    Contract.checkCondition(s != null, "Collection invalide.");
	    sommetSet.addAll(s);
	    initialize(s);
	    adjListInit(s);
	  }
	  
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
	   *     getUtilisateurNb.size() > 0
	   */
	  
	  public int avgAge() {
		Contract.checkCondition(getUtilisateurNb.size() > 0, "avgAge AS Error");
	    int sum = 0;
	    for (Utilisateur u: getUtilisateurSet()) {
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
	   * Renvoie une liste des sommets trié par rank
	   */
	  public List<Sommet> sortedByRank(){
		  List<Sommet> list = new ArrayList<Sommet>(sommetSet);
		  Map <Sommet,Double> rank = PageRank.compute(sommetSet);
		  RankComparator comp = new RankComparator();
		  comp.ValueComparator(rank);
		  Collections.sort(list, comp);
		  return list;
	  } 
	  
	  //COMMANDES
	  
	  /*
	   * Ajouter un arc au graphe
	   */
	
	  public boolean addArc(Utilisateur u, Sommet s) {
	    Contract.checkCondition(u != null && s != null && pageSet.contains(s) && utilisateurSet.contains(u),
	      "L'utilisateur u ou sommet s est invalide.");
		u.follow(s);
	    return arcSet.add(new Arcs(u, s));
	  }
	
	  /*
	   * Supprimer un arc au graphe
	   */
	  
	  public boolean removeArc(Arcs arc) {
	    Contract.checkCondition(arcSet.contains(arc),
	      "arc n'existe pas dans arcSet.");
	    arc.src.unfollow(arc.dest);
	    return arcSet.remove(arc);
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
	   * TO DO ---- SI LE NOM EST PRIS ON RAJOUTE PAS
	   * @post 
	   * 	getSommets().contains(s)
	   */
	  public boolean addSommet(Sommet s) {
		  Contract.checkCondition(s != null, "addSommet AS Error: null sommet");
		  if (s instanceof Page) {
			  return pageSet.add((Page) s);
		  }
		  if (s instanceof Utilisateur) {
			  boolean result = utilisateurSet.add((Utilisateur) s);
			  for(Sommet d : s.getFollowList()) {
				  Contract.checkCondition(getSommets().contains(d), "addSommet AS Error: d not in graphe");
				  addArc((Utilisateur) s, d);
			  }
			  return result;
		  }
		  Contract.checkCondition(false, "addSommet AS Error: Sommet type not supported");
		  return false;
	  }
	  
	  //Outils
	  
	  // Trie les sommets dans les deux Set interne en fonction de si ils sont
	  // des Page ou des Utilisateur.
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
	  private void adjListInit(Collection < ? extends Sommet > s) {
		  listAdj = new HashMap<Sommet, LinkedList<Sommet>>();
		  LinkedList<Sommet> succ;
		  for (Sommet sommet: s) {
			  succ = new LinkedList<Sommet>(sommet.getFollowList());
			  listAdj.put(sommet, succ);
		  }
	  }
}
