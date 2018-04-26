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
	public static HuffmanTempTree merge(HuffmanTempTree a, HuffmanTempTree b){
		HuffmanTempTree newTree = new HuffmanTempTree(0); // skal roden have en meningsfuld key?
		newTree.root.left = a.root;
		newTree.root.right = b.root; 
		return newTree; 
	}
	
    private String[] inOrderTreeWalkPath(Node x, String s, String[] res){
		String temp = s;
		if(x != null){
			inOrderTreeWalkPath(x.left, s+"L",res);
			res[i] = "Key " + x.key+" : "+ temp; 
            i++;
            inOrderTreeWalkPath(x.right, s+"R",res);
        }
		// An array containing the elements in 
		// increasing, sorted order is returned 
        return res;
    }
}