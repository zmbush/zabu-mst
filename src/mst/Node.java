package mst;

/**
 * A semantic class that represents a node in a Tree
 * @author Zachary Bush
 *
 */
public class Node {
	private String name;
	
	/**
	 * makes a node
	 * @param name The name of the Node
	 */
	Node(String name){
		this.name = name;
	}
	
	/**
	 * Turns the Node to a String
	 */
	public String toString(){
		return name;
	}
}
