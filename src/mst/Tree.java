package mst;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.io.*;

/**
 * A group of Nodes that are connected together with Connection objects.
 * @author Zachary Bush
 *
 */
public class Tree {
	private Node[] nodes;
	private int nodeCount;
	private int count;
	private int maxWeight; 
	
	private ArrayList<Connection> connections;
	
	/**
	 * Constructs an empty tree
	 * @param nodeCount The maximum number of nodes in the tree
	 */
	Tree(int nodeCount){
		this.nodeCount = nodeCount;
		this.nodes = new Node[nodeCount];
		this.count = 0;
		this.maxWeight = 0;
		connections = new ArrayList<Connection>();
	}
	
	/**
	 * Runs Prim's algorithm on the Tree starting at the numbered node
	 * @param index The index to start from
	 * @return A new tree that is a minimum spanning tree.
	 */
	public Tree getMST(int index) {
		System.out.println("Starting Prim's Algorithm at index: " + index);
		Node firstNode = getNode(index);
		Tree newTree = new Tree(countNodes());
		PriorityQueue<Connection> potentialConnections = 
			new PriorityQueue<Connection>();
		if(firstNode != null){
			newTree.addNode(firstNode);
			Node[] neighbors = getNeighborsOf(firstNode);
			Integer[] weights = getWeightsOf(firstNode);
			for(int i = 0; i < neighbors.length; i++){
				potentialConnections.offer(new 
						Connection(firstNode, weights[i], neighbors[i]));
			}
			int times = 0;
			while(newTree.countNodes() != countNodes()){
				System.out.println("Starting loop number " + ++times);
				Connection newCon = potentialConnections.element();
				potentialConnections.remove();
				newTree.connect(newCon);
				System.out.println("Selected Connection: " + newCon);
				
				neighbors = getNeighborsOf(newCon.getTwo());
				weights = getWeightsOf(newCon.getTwo());
				
				Iterator<Connection> cons = potentialConnections.iterator();
				ArrayList<Connection> toRemove = new ArrayList<Connection>();
				while(cons.hasNext()){
					Connection c = cons.next();
					if(c.hasNode(newCon.getTwo())){
						System.out.println("Connection: " + c 
								+ " will be removed");
						toRemove.add(c);
					}
				}
				
				for(int i = 0; i < toRemove.size(); i++){
					potentialConnections.remove(toRemove.get(i));
				}
				
				System.out.println("Adding new potential connections");
				for(int i = 0; i < neighbors.length; i++){
					if(newTree.findNode(neighbors[i]) == -1){
						System.out.println("Adding connection to: " 
								+ neighbors[i]);
						potentialConnections.offer(new
							Connection(newCon.getTwo(), 
									   weights[i], 
									   neighbors[i]));
					}
				}
			}
		}
		return newTree;
	}

	/**
	 * Gets the nodes that are connected to the given node.
	 * @param n The Node to look for
	 * @return An array of the Nodes that are connected to n.
	 */
	public Node[] getNeighborsOf(Node n){
		ArrayList<Node> retval = new ArrayList<Node>();
		for(int i = 0; i < connections.size(); i++){
			if(connections.get(i).hasNode(n)){
				retval.add(connections.get(i).getOtherNode(n));
			}
		}
		Node[] toReturn = new Node[retval.size()];
		return retval.toArray(toReturn);
	}

	/**
	 * Gets the weights of the connections to the current node.
	 * @param n The Node to look for
	 * @return An array of the weights of the connections to n.
	 */
	public Integer[] getWeightsOf(Node n){
		ArrayList<Integer> retval = new ArrayList<Integer>();
		for(int i = 0; i < connections.size(); i++){
			if(connections.get(i).hasNode(n)){
				retval.add(connections.get(i).getWeight());
			}
		}
		Integer[] toReturn = new Integer[retval.size()];
		return retval.toArray(toReturn);
	}

	/**
	 * Gets the weights of the connections to the current node.
	 * @param n The Node to look for
	 * @return An array of the weights of the connections to n.
	 */
	public Connection[] getConnectionsTo(Node n){
		ArrayList<Connection> retval = new ArrayList<Connection>();
		for(int i = 0; i < connections.size(); i++){
			if(connections.get(i).hasNode(n)){
				retval.add(connections.get(i));
			}
		}
		Connection[] toReturn = new Connection[retval.size()];
		return retval.toArray(toReturn);
	}
	
	/**
	 * @return The number of Nodes that have been added to the Tree
	 */
	public int countNodes() {
		return count;
	}

	/**
	 * Searches for the given Node in the Tree
	 * @param subject The Node to look for
	 * @return The index of the requested Node, or -1 on failure.
	 */
	public int findNode(Node subject) {
		for(int i = 0; i < count; i++){
			if(nodes[i].toString().equals(subject.toString())){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Add a Node to the Tree
	 * @param n the node to add
	 * @return the index of the Node 
	 */
	int addNode(Node n){
		if(count < nodeCount && findNode(n) == -1){
			this.nodes[count] = n;
			return count++;
		}
		return -1;
	}

	/**
	 * Get the Node at the givin index
	 * @param index the index of the Node
	 * @return the requested Node
	 */
	public Node getNode(int index) {
		if(index >= count) return null;
		return nodes[index];
	}

	/**
	 * Gets the sum total of the weights of the connections
	 * @return the total weight
	 */
	public int getWeight() {
		int retval = 0;
		for(int i = 0; i < connections.size(); i++){
			retval += connections.get(i).getWeight();
		}
		return retval;
	}

	/**
	 * Connects two nodes together
	 * @param x Index of the first node
	 * @param y Index of the second node
	 * @param weight Weight of the connection
	 * @return True if successful
	 */
	boolean connectNodes(int x, int y, int weight){
		if(x >= count || y >= count) return false;
		if(x == y) return false;
		Node first = nodes[x];
		Node second = nodes[y];
		connections.add(new Connection(first, weight, second));
		if(weight > maxWeight)
			maxWeight = weight;
		return true;		
	}

	private void connect(Connection newCon) {
		this.addNode(newCon.getOne());
		this.addNode(newCon.getTwo());
		this.connections.add(newCon);
	}

	/**
	 * Gets the String that represents the tree in the GraphViz language.
	 * @return The string
	 */
	public String getDot(){
		String retval = "";
		retval += "graph {\n";
		for(int i = 0; i < connections.size(); i++){
			retval += connections.get(i).getOne() + " -- " + connections.get(i).getTwo() 
				+ " [penwidth=" + connections.get(i).getWeight() 
				+ ",label=" + connections.get(i).getWeight() 
				+ "]\n";
		}
		retval += "}";
		return retval;
	}

	/**
	 * Draws the Tree using dot and then displays it with feh.
	 */
	public void displayTree(){
		try{
			BufferedWriter b = new BufferedWriter(new FileWriter("tree.dot"));
			b.write(this.getDot());
			b.close();
			Runtime r = Runtime.getRuntime();
			Process p = r.exec("dot -Tpng -otree.png tree.dot");
			p.waitFor();
			p = r.exec("feh tree.png");
			p.waitFor();
		}catch(Exception e){
			
		}
	}

	/**
	 * Converts the Tree to a string
	 */
	public String toString(){
		return connections.toString();
	}
}
