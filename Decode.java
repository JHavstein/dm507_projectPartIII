import java.io.*;
import java.util.*;

// Main class for the decoding  
public class Decode{
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
			try{
				// Åbner inputstreams
				FileInputStream fileIn = new FileInputStream(args[0]);
            	BitInputStream bitIn = new BitInputStream(fileIn);
				
				// Åbner outputstreams
				FileOutputStream fileOut = new FileOutputStream(args[1]); 
				BitOutputStream bitOut = new BitOutputStream(fileOut); 
			
				// Læser hyppighedstabel fra input og tæller totale antal tegn -
				// hvilket gemmes i totalChar
				int[] freqTable = new int[256];
				int totalChar = 0; 
				for (int i = 0; i <= 255; i++){
					freqTable[i] = bitIn.readInt();
					if(freqTable[i] > 0){
						totalChar = totalChar + freqTable[i];
					}
				}
				
				// Laver Huffmantræ ud fra hyppighedstabel læst fra inputfil
				PQHeap n = makeHuffmanTree(freqTable);
				
				// Laver Huffmantabel ud fra Huffmantræet
				String[] table = makeHuffmanTable(n);
				
				// Dekoder tekst og skriver til output
				// NB: virker ikke pt. (29-04-18)
				int nBit;
				String temp = ""; 
				while((nBit = bitIn.readBit()) != -1){
					temp = temp + String.valueOf(nBit); 
					for(int i = 0; i < table.length; i++){
						if (temp == table[i]){
							bitOut.writeBit(i); 
						}
					}
				}
				bitIn.close();
				bitOut.close(); 
								
			}
			catch(IOException e){
				e.printStackTrace(); 
			}
		}
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