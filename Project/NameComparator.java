package Project;

import java.util.Comparator;

public class NameComparator implements Comparator<Sommet>{

	@Override
	//On utilisant la compareTo de String
	public int compare(Sommet arg0, Sommet arg1) {
		return arg0.getName().compareTo(arg1.getName());
	}
	
}
