package mst;

/**
 * Connects two Nodes together with weight.
 * @author Zachary Bush
 *
 */
public class Connection implements Comparable<Connection>{
	private Node one, two;
	private int weight;
	
	/**
	 * Main constructor. 
	 * @param one One of the Nodes to be connected
	 * @param weight Weight of the connection
	 * @param two The other Node to be connected
	 */
	Connection(Node one, int weight, Node two){
		this.one = one;
		this.weight = weight;
		this.two = two;
	}
	
	/**
	 * @return the first Node
	 */
	public Node getOne(){
		return one;
	}
	
	public void setOne(Node o){
		one = o;
	}

	/**
	 * @return the second node
	 */
	public Node getTwo(){
		return two;
	}
	
	public void setTwo(Node o){
		two = o;
	}
	
	/**
	 * @return the weight of the connection
	 */
	public int getWeight(){
		return weight;
	}

	public void setWeight(int w){
		weight = w;
	}
	
	/**
	 * @param n The node to look for
	 * @return True if either one or two is equal to n 
	 */
	public boolean hasNode(Node n){
		return (n == one || n == two);
	}

	/**
	 * @param n One of the two nodes in the Connection. 
	 * @return The other node
	 */
	public Node getOtherNode(Node n){
		if(hasNode(n)){
			if(n == one){
				return two;
			}
			return one; 
		}
		return null;
	}

	/**
	 * Converts the Connection into a string.
	 */
	public String toString(){
		return one + " <=> " + two + " (" + weight + ")";
	}

	@Override
	public int compareTo(Connection o) {
		if(o.weight < this.weight)
			return 1;
		else if(o.weight > this.weight)
			return -1;
		else 
			return 0;
	}
}
