/**
DM507, project part III
	Mathilde Blicher Christensen - mathc17 - 01-03-1997
	Jeanette Frieda Aviaya Sommer - jeaso17 - 08-05-1984
	Jonas Alexander Havstein Eriksen - joeri15 - 16-02-1993
*/

public class HuffmanTempTree{
	
	public Node root;
	private int k; 
	 
	// Constructor method 
	public HuffmanTempTree(int k){
		this.root = new Node(k);
	}
	
	// Method for combining two HuffmanTempTrees by making a new node and making the two trees
	// a and b subtrees of this new root.
	public static HuffmanTempTree merge(HuffmanTempTree a, HuffmanTempTree b){
		HuffmanTempTree newTree = new HuffmanTempTree(0); // skal roden have en meningsfuld key?
		newTree.root.left = a.root;
		newTree.root.right = b.root; 
		return newTree; 
	}
	
	// Method for traversing a tree and storing the paths from node x to each
	// leaf node in the tree.
	// Using following convention for the paths: 
	// 0 = left
	// 1 = right
    public static String[] inOrderTreeWalkPath(Node x, String s, String[] res){
		String temp = s;
		if(x != null){
			inOrderTreeWalkPath(x.left, s+"0",res);
			if (x.right == null & x.left == null){
				res[x.key] = temp; 
			}
            //i++;
            inOrderTreeWalkPath(x.right, s+"1",res);
        }
		// An array containing the paths of the elements in
		// increasing, sorted order is returned 
        return res;
    }
}