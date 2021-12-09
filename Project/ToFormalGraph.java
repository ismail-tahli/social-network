package Project;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;

public abstract class ToFormalGraph {

	// ATTRIBUTS
	// Pour un ID unique
	private static int i = 0;
	
	
	
	public static Graph convert(Graphe model) {
		// Creation graphe
		Graph g = new DefaultGraph(String.valueOf(i));
		i++;
		
		// Ajout des sommets
		for (Sommet s : model.getSommets()) {
			// On utilise toString, pas getName
			g.addNode(s.toString());
		}
		// Ajout des arcs
		for (Graphe.Arcs a : model.getArcsSet()) {
			// Le nom des Edge est "src() : dest()"
			String arcName = a.src().toString() + " : " + a.dest().toString();
			g.addEdge(arcName, a.src().toString(), a.dest().toString(), true);
		}
		return g;
	}
}
