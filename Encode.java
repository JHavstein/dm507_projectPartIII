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
			// Laver Bytereaderobjekt, der tager inputstreng lavet om til File objekt som argument
			Bytereader br = new Bytereader(new File(args[0]));
			
			// Laver frekvenstabel 
			br.byteReader();
			PQHeap n = makeHuffmanTree(br.getFrequencies());
			String[] table = makeHuffmanTable(n);
			
			// Næste fire linjer kun til testformål! Slettes inden aflevering
			/*br.printFrequencies();
			for (int i = 0; i < table.length; i++) {
				System.out.println("key " + i + " : " + table[i]);
			}*/
            try{
                FileInputStream input = new FileInputStream(args[0]);
				
				FileOutputStream outUnderlying = new FileOutputStream(args[1]);
				BitOutputStream outputFreq = new BitOutputStream(outUnderlying);
				
				// BitOutputStream for writing Huffman Codes to output
                BitOutputStream outputBit = new BitOutputStream (new FileOutputStream(args[1]));
                
				// Skriver hyppighedstabellen outputfilen
				// Format: hyppighederne skives som ints (4 bytes) fra 0-255. 
				//Efter sidste hyppighed laves et linebreak
				// som kontrol
				/* NB: kan ikke helt se om den reelt skriver ints til output ...*/
				for (int i = 0; i <= 255; i++){
					int out = br.freqAtIndex(i);
					outputFreq.writeInt(out);
				} 
								
				// Lukker FileOutputStream til hyppighedstabellen
				outputFreq.close(); 
				
				// Skriver Huffman-koder til outputfilen
				int x;
                while((x = input.read())!=-1){
                    String in = table[x];
                    for (int i=0; i<in.length(); i++){
                        int out = Character.getNumericValue(in.charAt(i));
                        outputBit.writeBit(out);
                    }
                }
				
				// Lukker alle åbne input- og outputstreams
                input.close();
                outputBit.close();
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
			HuffmanTempTree ztree = HuffmanTempTree.merge(x.data, y.data);
			Element z = new Element(zFreq, ztree);
			HuffmanTree.insert(z);
		}
		return HuffmanTree; 
	}

	public static String[] makeHuffmanTable( PQHeap t ) {
		String[] out = new String[256];
		HuffmanTempTree huff = t.extractMin().data;
		return huff.inOrderTreeWalkPath(huff.root, "", out);
	}
}