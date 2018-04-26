public class Huffmantree{
	private Node root; 
    private int elms;
    private int i = 0;
	private int[] frequencyTable;
	
	// Constructor method 
	public Huffmantree(){
        this.root = null;
        this.elms = 0;
		this.frequencyTable = frequencyTable;
	}
	
	public Huffmantree makeTree(Huffmantree a, Huffmantree b){
		PQHeap huffmanTree = new PQHeap(frequencyTable.length); 
		for (int i = 0; i < frequencyTable.length; i++){
			int tmpInt = i; 
			Element tmpElm = new Element(tmpInt, )
			huffmanTree.insert
		}
	}
	
	// Returns element with highest frequency
	public Element compare(Element a, Element b){
		if (a.key > b.key){
			return a;
		}
		return b;
	}
}