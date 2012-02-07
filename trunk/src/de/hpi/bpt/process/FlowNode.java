package de.hpi.bpt.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.hpi.bpt.hypergraph.abs.Vertex;

/**
 * Abstract base class for all model elements that take part in the control flow.
 * 
 * @author Tobias Hoppe
 *
 */
public abstract class FlowNode extends Vertex implements IFlowNode{
	
	/**
	 * {@link Set} of all {@link IDataNode}s that are read by this {@link FlowNode}.
	 */
	private Set<IDataNode> readDocuments = new HashSet<IDataNode>();
	
	/**
	 * {@link Set} of all {@link IDataNode}s that are written by this {@link FlowNode}.
	 */
	private Set<IDataNode> writeDocuments = new HashSet<IDataNode>();
	
	/**
	 * {@link Set} of all {@link IDataNode}s with unspecified access by this {@link FlowNode}.
	 */
	private Set<IDataNode> unspecifiedDocuments = new HashSet<IDataNode>();
	
	/**
	 * Contains the resources connected with this {@link FlowNode}.
	 */
	private Set<IResource> resources = new HashSet<IResource>();
	
	/**
	 * The model this {@link FlowNode} belongs to.
	 */
	private ProcessModel model = null;

	/**
	 * Creates a new {@link FlowNode} with an empty name.
	 */
	public FlowNode() {
		super();
	}

	/**
	 * Creates a new {@link FlowNode} with the given name and description.
	 * @param name of the {@link FlowNode}
	 * @param description of the {@link FlowNode}
	 */
	public FlowNode(String name, String description) {
		super(name, description);
	}

	/**
	 * Creates a new {@link FlowNode} with the given name.
	 * @param name of the {@link FlowNode}
	 */
	public FlowNode(String name) {
		super(name);
	}

	@Override
	public Collection<IResource> getResources() {
		return this.resources;
	}

	@Override
	public Collection<IDataNode> getReadDocuments() {
		return this.readDocuments;
	}

	@Override
	public Collection<IDataNode> getWriteDocuments() {
		return this.writeDocuments;
	}

	@Override
	public Collection<IDataNode> getReadWriteDocuments() {
		Collection<IDataNode> result = new ArrayList<IDataNode>();
		result.addAll(this.readDocuments);
		result.retainAll(this.writeDocuments);
		return result;
	}

	@Override
	public Collection<IDataNode> getUnspecifiedDocuments() {
		return this.unspecifiedDocuments;
	}

	@Override
	public void addResource(IResource resource) {
		this.resources.add(resource);
	}

	@Override
	public void addReadDocument(IDataNode document) {
		this.readDocuments.add(document);
		this.addNonFlowNodeToModel((NonFlowNode) document);
		document.addReadingFlowNode(this);
	}

	@Override
	public void addWriteDocument(IDataNode document) {
		this.writeDocuments.add(document);
		this.addNonFlowNodeToModel((NonFlowNode) document);
		document.addWritingFlowNode(this);
	}

	@Override
	public void addReadWriteDocument(IDataNode document) {
		this.readDocuments.add(document);
		this.writeDocuments.add(document);
		this.addNonFlowNodeToModel((NonFlowNode) document);
		document.addReadWriteFlowNode(this);
	}

	@Override
	public void addUnspecifiedDocument(IDataNode document) {
		this.unspecifiedDocuments.add(document);
		this.addNonFlowNodeToModel((NonFlowNode) document);
	}

	/**
	 * Add the given {@link NonFlowNode} to the model of this {@link FlowNode}.
	 * @param nonFlowNode to add to the model
	 */
	private void addNonFlowNodeToModel(NonFlowNode nonFlowNode) {
		if (this.model != null){
			this.model.addNonFlowNode(nonFlowNode);
		}
	}

	/**
	 * Get the model of this {@link FlowNode}.
	 * @return the model
	 */
	public ProcessModel getModel() {
		return model;
	}

	/**
	 * Set the model of this {@link FlowNode}.
	 * @param model the model to set
	 */
	public void setModel(ProcessModel model) {
		this.model = model;
	}
	
	@Override
	public FlowNode clone(){
		FlowNode clone = (FlowNode) super.clone();
		clone.readDocuments = new HashSet<IDataNode>();
		for (IDataNode node : this.readDocuments){
			clone.readDocuments.add(((DataNode) node).clone());
		}
		clone.writeDocuments = new HashSet<IDataNode>();
		for (IDataNode node : this.writeDocuments){
			clone.writeDocuments.add(((DataNode) node).clone());
		}
		clone.unspecifiedDocuments = new HashSet<IDataNode>();
		for (IDataNode node : this.unspecifiedDocuments){
			clone.unspecifiedDocuments.add(((DataNode) node).clone());
		}
		clone.resources = new HashSet<IResource>();
		for (IResource node : this.resources){
			clone.resources.add(((Resource) node).clone());
		}
		return clone;
	}
}
