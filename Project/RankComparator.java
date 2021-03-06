package Project;

import java.util.Comparator;
import java.util.Map;

//En peut déclarer cette classe comme anonyme imbriquée dans la classe Graphe
//Cette classe trie les sommets par PageRank
//Pour pouvoir trier ces sommets il faut savoir leurs degrés, et du coup la méthode
//ValueComparator recupére la map des contenant les degré de tous les sommets depuis la classe 
//Graphe.
public class RankComparator implements Comparator<Sommet>{
	private Map<Sommet, Double> degree;
	//Importer la map contenant les degrees pour pouvoir
	//trier les sommets selon leur degree.
	public RankComparator(Map<Sommet, Double> d) {
		assert d != null;
        degree = d;

	}
	@Override
	//On utilisant la compareTo de Double
	public int compare(Sommet arg0, Sommet arg1) {
		//La methodes static compareTo de la Classe Double retourne -1,0,1 selon les cas.
		return degree.get(arg1).compareTo(degree.get(arg0));
	}
	
}
