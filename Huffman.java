/**
DM507, project part III
	Mathilde Blicher Christensen - mathc17 - 01-03-1997
	Jeanette Frieda Aviaya Sommer - jeaso17 - 08-05-1984
	Jonas Alexander Havstein Eriksen - joeri15 - 16-02-1993
*/

public class Huffman{
    	private int[] frequency;
    	private String[] codes = new String[256];
    
    	public Huffman(int[] freq){
        	this.frequency = freq;
    	}

	// Method for extracting the Huffman Codes from a 
	// Huffman Tree (PQHeap).
    	public String[] makeCodes(){
        	HuffmanTempTree tree = makeTree();
		return tree.inOrderTreeWalkPath(tree.root, "", codes);
    	}
	
	// Method for making the a Huffman Tree from a PQHeap.
	// Runs the Huffman Algorithm which extracts the two
	// smallest elements from the PQHeap and merges these into 
	// a new element, which is then inserted into the PQHeap. 
	// This is done n-1 = 255 times.
	// Typecasting from Element.data (Object) to HuffmanTempTree
	// is used.
    	private HuffmanTempTree makeTree() {
        	PQHeap queue = makeQueue();
        	for (int i = 0; i < frequency.length - 1; i++) {
            	HuffmanTempTree zTree = new HuffmanTempTree(0);
            	Element x = queue.extractMin();
            	Element y = queue.extractMin();
            	int zFreq = x.key + y.key;
            	zTree.merge((HuffmanTempTree) x.data, (HuffmanTempTree) y.data);
            	queue.insert(new Element(zFreq, zTree));
        	}
        	return (HuffmanTempTree) queue.extractMin().data;
    	}
       
    	private PQHeap makeQueue(){
		// Creating a new Element for each of the
		// 256 possible bit patterns (represented as an int)
		// and inserts it into the PQHeap.
        	PQHeap queue = new PQHeap(frequency.length);
        	for (int i = 0; i < frequency.length; i++) {
            		queue.insert(new Element(frequency[i], new HuffmanTempTree(i)));
        	}
        	return queue;
    	}
	
}
