/**
DM507, project part III
	Mathilde Blicher Christensen - mathc17 - 01-03-1997
	Jeanette Frieda Aviaya Sommer - jeaso17 - 08-05-1984
	Jonas Alexander Havstein Eriksen - joeri15 - 16-02-1993
*/

public class HuffmanTempTree{
	
	public Node root;
	 
	// Constructor method 
	public HuffmanTempTree(int k){
		this.root = new Node(k);
	}
	
	// Method for combining two HuffmanTempTrees by making a new node
	// and making the two trees
	// a and b subtrees of this new root.
	public void merge(HuffmanTempTree a, HuffmanTempTree b){
		this.root.left = a.root;
		this.root.right = b.root;
	}
	
	// Method for traversing a tree and storing the paths from node x to each
	// leaf node in the tree.
	// Using following convention for the paths: 
	// 0 = left
	// 1 = right
    public String[] inOrderTreeWalkPath(Node x, String s, String[] res){
		String temp = s;
		if(x != null){
			inOrderTreeWalkPath(x.left, s+"0",res);
			if (x.right == null & x.left == null){
				res[x.key] = temp;
			}
            inOrderTreeWalkPath(x.right, s+"1",res);
        }
		// An array containing the paths of the elements is returned
        return res;
    }
}
