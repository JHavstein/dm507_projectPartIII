public class Element {
	public int key;
	// OBS - DER ER LAVET ÆNDRNGER
	public HuffmanTempTree data;
	public Element(int i, HuffmanTempTree o){
		this.key = i;
		this.data = o;
	}
}