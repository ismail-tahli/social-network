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

	/*
	 * set.size() > 0 n'est pas vraiment necessaire (la methode ne renvoie jamais
	 * une valeur négative (Seulement *dans des cas ou il y a plusieurs threads
	 * c'est pas le notre) et s'il est vide ce n'est pas grave de le *prendre comme
	 * meme en parametre. Les attributs vaut mieux que leur type source soit la
	 * super interface et pas la classe, donc ça sera des Map mais qu'on initliase
	 * en HashMap et en plus c'est mieux si tu l'initalise, parce que ta Map pred
	 * n'est initiliasé nulle part. private Map<Sommet, Double> pageRank = new
	 * HashMap<Sommet, Double>(); initPred ne réduit pas le temps de calcul c'est
	 * seulement que c'est plus propre et qu'elle prepare la Map avant.(A mon avis).
	 * Ca sera mieuc si tu mets ton appel pred.put(s, p); après le for même si on
	 * obtiens la même chose mais c'est plus compréhensible. Ta boucle
	 * doHundredLoops() i"<="100 pour que tu obtiens 100 itérations. pour
	 * pageRank.keySet().size() tu pourrais faire plus façile je pense
	 * pageRank.size(); directement. Il manque les Contracts. Le reste je valide )
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
		for (Sommet s : pageRank.keySet()) {
			HashSet<Sommet> p = new HashSet<Sommet>();
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
