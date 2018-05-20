/**
DM507, project part III
	Mathilde Blicher Christensen - mathc17 - 01-03-1997
	Jeanette Frieda Aviaya Sommer - jeaso17 - 08-05-1984
	Jonas Alexander Havstein Eriksen - joeri15 - 16-02-1993
*/
import java.io.*; 

// Main class for the encoding part
public class Encode{
	public static void main(String[] args){
		// Terminates program if user does not give correct number of arguments.
		if (args.length != 2){
			if(args.length == 1){
				System.out.println("There needs to be to arguments. You gave 1 argument. The program will now terminate.");
			}
			else{
				System.out.println("There needs to be to arguments. You gave " + args.length +  " arguments. The program will now terminate.");
				
			}
			System.exit(0);
		}
		else{
			// Initializing Bytereader object which takes a File object as an argument
			// for its constructor method.
			ByteReader br = new ByteReader(new File(args[0]));
			
			// Making Huffman tree and Huffman codes.
			br.byteReader();
			PQHeap n = makeHuffmanTree(br.getFrequencies());
			String[] table = makeHuffmanTable(n);
			FileInputStream input = null;
            		BitOutputStream outputFreq = null;
            		try{
				// Opening input-/outputstreams.
                		input = new FileInputStream(args[0]);
                		outputFreq = new BitOutputStream(new FileOutputStream(args[1]));
				                
				// Writing the frequency table to the outputfile.
				// Format: The frequencies are written as consecutive non-separated integers
				// in range [0;255]. 
				for (int i = 0; i <= 255; i++){
					int out = br.freqAtIndex(i);
					outputFreq.writeInt(out);
				} 
												
				// Writing Huffman codes to the output file.
				int x;
                		while((x = input.read())!= -1){
                    			String in = table[x];
                    			for (int i=0; i < in.length(); i++){
                        		int out = Character.getNumericValue(in.charAt(i));
                        		outputFreq.writeBit(out);
                    			}
                		}
            		}
            		catch (IOException e ){
				e.printStackTrace();
           		}
			finally{
                	// Closing all open input-/outputstreams.	
                		if(input != null){
                    			try{
                        			input.close();
                    			}
                    			catch(IOException e){
                        			e.printStackTrace(); 
                    			}
                		}
                		if(outputFreq != null){
                    			try{
                        			outputFreq.close();
                    			}
                    			catch(IOException e){
                        			e.printStackTrace(); 
                    			}
                		} 
            		}
		}
	}
	
	// Method for making the a Huffman Tree from a PQHeap.
	// The method takes an int[] as a parameter, which should
	// be the frequency table created by the Bytereader object.
	public static PQHeap makeHuffmanTree(int[] a) {
		PQHeap HuffmanTree = new PQHeap(a.length);
		// Creating a new Element for each of the
		// 256 possible bit patterns (represented as an int)
		// and inserts it into the PQHeap
		for (int i = 0; i < a.length; i++) {
			Element tmp = new Element(a[i], new HuffmanTempTree(i));
			HuffmanTree.insert(tmp);
		}
		// Runs the Huffman Algorithm which extracts the two
		// smallest elements from the PQHeap and merges these into 
		// a new element, which is then inserted into the PQHeap. 
		// This is done n-1 = 255 times.
		// Typecasting from Element.data (Object) to HuffmanTempTree
		// is used.
		for (int i = 0; i < a.length - 1; i++) {
			Element x = HuffmanTree.extractMin();
			Element y = HuffmanTree.extractMin();
			int zFreq = x.key + y.key;
			HuffmanTempTree ztree = new HuffmanTempTree(0);
			ztree.merge((HuffmanTempTree) x.data, (HuffmanTempTree) y.data);
			Element z = new Element(zFreq, ztree);
			HuffmanTree.insert(z);
		}
		return HuffmanTree; 
	}
	// Method for extracting the Huffman Codes from a 
	// Huffman Tree (PQHeap).
	// Typecasting from Element.data (Object) to HuffmanTempTree
	// is used.
	public static String[]  makeHuffmanTable( PQHeap t ) {
		String[] out = new String[256];
		HuffmanTempTree huff = (HuffmanTempTree) t.extractMin().data;
		return huff.inOrderTreeWalkPath(huff.root, "", out);
	}
}
