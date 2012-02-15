package org.jbpt.petri.unf;

import org.jbpt.hypergraph.abs.Vertex;
import org.jbpt.petri.Node;

/**
 * Unfolding node - event or condition
 * 
 * @author Artem Polyvyanyy
 */
public abstract class BPNode extends Vertex {

	@Override
	public String getName() {
		return super.getName();
	}
	
	public abstract Node getNode();
}