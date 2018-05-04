import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.DataOutputStream; 
import java.io.File;
import java.io.IOException;

// Main class for the encoding 
public class Encode{
	public static void main(String[] args){
		// Terminerer program, hvis fejlinput fra brugeren
		if (args.length != 2){
			if(args.length == 1){
				System.out.println("Programmet kræver præcis 2 argumenter. Du gav 1 argument. Terminerer program.");
			}
			else{
				System.out.println("Programmet kræver præcis 2 argumenter. Du gav " + args.length +  " argumenter. Terminerer program.");
				
			}
			System.exit(0);
		}
		else{
			// Initializing Bytereader object which takes a File object as an argument
			// for its constructor method 
			Bytereader br = new Bytereader(new File(args[0]));
			
			// Making Huffman tree and Huffman codes
			br.byteReader();
			PQHeap n = makeHuffmanTree(br.getFrequencies());
			String[] table = makeHuffmanTable(n);

            try{
				// Opening input-/outputstreams 
                FileInputStream input = new FileInputStream(args[0]);
				FileOutputStream outUnderlying = new FileOutputStream(args[1]);
				BitOutputStream outputFreq = new BitOutputStream(outUnderlying);
				                
				// Writing the frequenct table to the outputfile
				// Format: The frequencies are written as consecutive ints 
				// in range [0;255]. 
				for (int i = 0; i <= 255; i++){
					int out = br.freqAtIndex(i);
					outputFreq.writeInt(out);
				} 
												
				// Writing Huffman codes to the output file 
				int x;
                while((x = input.read())!= -1){
                    String in = table[x];
                    for (int i=0; i < in.length(); i++){
                        int out = Character.getNumericValue(in.charAt(i));
                        outputFreq.writeBit(out);
                    }
                }
				
				// Closing all open streams
                input.close();
				outputFreq.close(); 
				
            }
            catch (IOException e ){
                e.printStackTrace();
            }
		}
		// Laver outputStream til fil fra args[1]
	}
	
	// Skal nok gøres non-static
	public static PQHeap makeHuffmanTree(int[] a){
		PQHeap HuffmanTree = new PQHeap(a.length);
		for (int i = 0; i < a.length; i++){
			Element tmp = new Element(a[i], new HuffmanTempTree(i));
			HuffmanTree.insert(tmp);
		}
		// Mangler merge-skridtene
		for (int i = 0; i < a.length-1; i++){
			Element x = HuffmanTree.extractMin();
			Element y = HuffmanTree.extractMin();
			int zFreq = x.key + y.key; 
			HuffmanTempTree ztree = HuffmanTempTree.merge((HuffmanTempTree)x.data, (HuffmanTempTree)y.data);
			Element z = new Element(zFreq, ztree);
			HuffmanTree.insert(z);
		}
		return HuffmanTree; 
	}

	public static String[] makeHuffmanTable( PQHeap t ) {
		String[] out = new String[256];
		HuffmanTempTree huff = (HuffmanTempTree) t.extractMin().data;
		return huff.inOrderTreeWalkPath(huff.root, "", out);
	}
}