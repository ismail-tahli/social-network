package Project;

import java.util.Comparator;
import java.util.Map;

public class RankComparator implements Comparator<Sommet>{
	private Map<Sommet, Double> degree;
	//Importer la map contenant les degrees pour pouvoir
	//trier les sommets selon leur degree.
	public void ValueComparator(Map<Sommet, Double> d) {
        degree = d;
    }
	@Override
	//On utilisant la compareTo de String
	public int compare(Sommet arg0, Sommet arg1) {
		return degree.get(arg0).compareTo(degree.get(arg1));
		//return (int) (degree.get(arg0) - degree.get(arg1));
	}
	
}
