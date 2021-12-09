package Project;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import util.Contract;

/**
 * 
 * @author thomas et ismail
 *
 *         Cette classe calcule le PageRank, de tout les sommets d'un graphe,
 *         représenté par la liste de sommets donnée. Calcule automatiquement, a
 *         la création, le PageRank selon l'algorithme donnée en énoncé.
 *
 *         La seule methode public est compute().
 * 
 */
class PageRank {

	/**
	 * La seule methode accessible. renvoie une Map qui associe a chaque sommet, le
	 * PageRank associé. A noté que le comportement n'est pas défini si un sommet
	 * pointe sur un sommet qui n'est pas dans cette ensemble.
	 * 
	 * Il est conseillé de ne pas appeler cette fonction intempestivement car le
	 * temps de calcul peut etre long.
	 * 
	 * @pre set != null
	 */
	
	//ATTRIBUTS
	private Map<Sommet, Double> pageRank;
	private Map<Sommet, Set<Sommet>> pred;
	


	//CONSTRUCTEURS
	/**
	 * Un PageRanker qui calcule l'ensemble s. N'a pas effectué de calcul, il n'a
	 * que initialisé sa HashMap pour les PageRank.
	 * 
	 */
	private PageRank(Set<Sommet> s) {
		pageRank = new HashMap<Sommet, Double>();
		for (Sommet c : s) {
			Contract.checkCondition(c != null, "Erreur PageRank Constructeur : Le sommet est invalide");
			pageRank.put(c, 1.);
		}
	}
	
	/**
	 * La seule methode accessible. renvoie une Map qui associe a chaque sommet, le
	 * PageRank associé. A noté que le comportement n'est pas défini si un sommet
	 * pointe sur un sommet qui n'est pas dans cette ensemble.
	 * 
	 * Il est conseillé de ne pas appeler cette fonction intempestivement car le
	 * temps de calcul peut etre long.
	 * 
	 * @pre set != null
	 */
	
	public static Map<Sommet, Double> compute(Set<Sommet> set) {
		Contract.checkCondition(set != null, "compute AS Error");
		//Initialiaser le pageRank à 1.
		PageRank ranker = new PageRank(set);
		ranker.initPred();
		ranker.doHundredLoops();
		return ranker.pageRank;
	}
	//OUTILS

	/**
	 * Initie les listes des predecesseur pour chaque sommet.
	 */
	private void initPred() {
		pred = new HashMap<Sommet, Set<Sommet>>();
		for (Sommet s : pageRank.keySet()) {
			Set<Sommet> p = new HashSet<Sommet>();
			for (Sommet c : pageRank.keySet()) {
				if (c.getFollowList().contains(s)) {
					p.add(c);
				}
			}
			pred.put(s, p);
		}
	}

	/**
	 * Appele doOneLoop 100 fois. Le resultat dans PageRank sera satisfaisant.
	 */
	private void doHundredLoops() {
		for (int i = 1; i <= 100; i++) {
			doOneLoop();
		}
	}

	/**
	 * update le PageRank une fois. il faut l'appeler au moins 100 fois pour avoir
	 * une valeur satisfaisante.
	 */
	private void doOneLoop() {
		int size = Math.abs(pageRank.size());
		for (Sommet v : pageRank.keySet()) {
			double a = 0.15 / size;
			double b = 0;
			for (Sommet u : pred.get(v)) {
				b += pageRank.get(u) / u.getFollowList().size();
			}
			b *= 0.85;
			pageRank.put(v, a + b);
		}
	}

}
