public class HuffmanTempTree{
	
	// Should contain the temporary trees from the running of the Huffman Algorithm.
	// Will be saved as part of Element objects.
	
	private Node root; 
	private int k; 
	 
	// Constructor method
	public HuffmanTempTree(int k){
		this.root = new Node(k);
	}
	
	// Combines two trees by making a new node and making the to input trees
	// subtrees of the root.
	public HuffmanTempTree merge(HuffmanTempTree a, HuffmanTempTree b){
		HuffmanTempTree newTree = new HuffmanTempTree(0); // skal roden have en meningsfuld key?
		newTree.root.left = a.root;
		newTree.root.right = b.root; 
		return newTree; 
	}
}