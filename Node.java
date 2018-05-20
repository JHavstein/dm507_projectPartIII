/**
DM507, project part III
	Mathilde Blicher Christensen - mathc17 - 01-03-1997
	Jeanette Frieda Aviaya Sommer - jeaso17 - 08-05-1984
	Jonas Alexander Havstein Eriksen - joeri15 - 16-02-1993
*/

// Class for Node objects for HuffmanTempTree.

public class Node {
    public int key;
	// Pointers to right- and left child.
    public Node left;
    public Node right;

	// Constructor method for Node object.
    public Node(int key){
		this.key = key;
		// Node objects initiated with empty subtrees.
		this.left = null;
		this.right = null;
    }
}
