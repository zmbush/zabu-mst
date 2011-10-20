package mst;

/**
 * 
 * Prim is the main class. It will run Prim's algorithm on a Tree.
 * 
 * @author Zachary Bush
 *  
 */
public class Prim {
	
	/**
	 * Main function. 
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args){
		Tree subject = new Tree(6);
		Prim mstGenerator = new Prim();
		for(int i = 0; i < 6; i++){
			subject.addNode(new Node("" + i));
		}
		subject.connectNodes(0, 1, 1);
		subject.connectNodes(0, 2, 2);
		subject.connectNodes(1, 2, 1);
		subject.connectNodes(1, 3, 1);
		subject.connectNodes(1, 4, 2);
		subject.connectNodes(2, 3, 2);
		subject.connectNodes(2, 5, 1);
		subject.connectNodes(3, 4, 2);
		subject.connectNodes(3, 5, 1);
		subject.connectNodes(4, 5, 1);
		
		System.out.println("Observing: " + subject);
		subject.displayTree();
		for(int i = 0; i < 6; i++){
			//Tree spanned = mstGenerator.primIt(subject, i);
			Tree spanned = subject.getMST(i);
			System.out.println("MST generated starting at " + i);
			System.out.println("Tree: " + spanned);
			System.out.println("Weight of MST: " + spanned.getWeight());
			spanned.displayTree();
			System.out.println();
		}
	}
	
	/**
	 * Runs Prim's algorithm on Tree starting with tree numbered startNode.
	 * @param subject The Tree to run Prim's algorithm on
	 * @param startNode The index of the Node to start with
	 * @return The new minimum spanning tree
	 * @deprecated Replaced by Tree::getMST();
	 */
	private Tree primIt(Tree subject, int startNode){
		System.out.println("Starting Prim's algorithm with tree of weight " + subject.getWeight());
		Node firstNode = subject.getNode(startNode);
		Node[] fromList = new Node[20];
		Node[] toList = new Node[20];
		int[] weight = new int[20];
		Tree newTree = new Tree(subject.countNodes());
		newTree.addNode(firstNode);
		if(firstNode != null){
			Node[] fromThis = subject.getNeighborsOf(firstNode);
			Integer[] fromWeights = subject.getWeightsOf(firstNode);
			
			for(int i = 0; i < fromThis.length; i++){
				fromList[i] = firstNode;
				toList[i] = fromThis[i];
				weight[i] = fromWeights[i];
				System.out.println("Adding potential connection: " + firstNode + " to " + fromThis[i] + " with weight: " + fromWeights[i]);
			}
			while(newTree.countNodes() != subject.countNodes()){
				Node minFrom = null;
				Node minTo = null;
				int minWeight = 1000;
				
				System.out.println("Potential Connections");
				for(int i = 0; i < fromList.length && fromList[i] != null; i++){
					System.out.println("\t" + fromList[i] + " => " + toList[i] + " (" + weight[i] + ")");
				}
				System.out.println();
				
				// Find the minimum weighted branch
				for(int i = 0; i < fromList.length && fromList[i] != null; i++){
					if(weight[i] < minWeight){
						minWeight = weight[i];
						minFrom = fromList[i];
						minTo = toList[i];
					}
				}
				
				int newix = newTree.addNode(minTo);
				int oldix = newTree.findNode(minFrom);
				if(newix >= 0 && oldix >= 0){
					System.out.println("Connecting " + minFrom + " to " + minTo);
					newTree.connectNodes(newix, oldix, minWeight);
				}
				
				// Fix the from / to lists.
				Node[] tmpFrom = fromList;
				Node[] tmpTo = toList;
				int[] tmpWeight = weight;
				fromList = new Node[20];
				toList = new Node[20];
				weight = new int[20];
				int j = 0;
				for(int i = 0; i < tmpFrom.length && tmpFrom[i] != null; i++){
					if(newTree.findNode(tmpTo[i]) == -1){
						fromList[j] = tmpFrom[i];
						toList[j] = tmpTo[i];
						weight[j++] = tmpWeight[i];
					}
				}
				
				Node[] toNeighbors = subject.getNeighborsOf(minTo);
				Integer[] toWeights = subject.getWeightsOf(minTo);
				for(int i = 0; i < toNeighbors.length && toNeighbors[i] != null; i++){
					if(newTree.findNode(toNeighbors[i]) == -1){
						fromList[j] = minTo;
						toList[j] = toNeighbors[i];
						weight[j++] = toWeights[i];
						System.out.println("Adding potential connection: " + minTo + " to " 
								+ toNeighbors[i] + " with weight: " + toWeights[i]);
					}
				}
			}
		}
		return newTree;
	}
}
