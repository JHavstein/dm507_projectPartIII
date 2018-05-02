import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.DataOutputStream; 
import java.io.File;
import java.io.IOException;
import java.util.*;

// Main class for the decoding  
public class Decode{
	public static void main(String[] args){
		if (args.length != 2){
			// Terminerer program, hvis fejlinput fra brugeren
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
				// Åbner IOstreams
				FileInputStream fileIn = new FileInputStream(args[0]);
            	BitInputStream bitIn = new BitInputStream(fileIn);
				FileOutputStream fileOut = new FileOutputStream(args[1]); 
				BitOutputStream bitOut = new BitOutputStream(fileOut); 
					
				// Læser hyppighedstabel fra input og tæller samtidig det samlede antal tegn 
				int[] freqTable = new int[256];
				int totalChar = 0; 
				for (int i = 0; i <= 255; i++){
					freqTable[i] = bitIn.readInt();
					if(freqTable[i] > 0){
						totalChar = totalChar + freqTable[i];
					}
				}

				// Laver Huffmantræ ud fra hyppighedstabel læst fra inputfil
				// Samme implementation af makeHuffmanTree() som i Encode.java
				PQHeap n = makeHuffmanTree(freqTable);
				
				// Laver Huffmantabel ud fra Huffmantræet
				// Samme implementation af makeHuffmanTable() som i Encode.java
				String[] table = makeHuffmanTable(n);
				
				int nBit; 
				String outByte; 
				String temp = ""; 	
				int charCounter = 0; // tæller hvor mange bytes, der er skrevet til output	
				
				// Laver HashMap til at opbevare bitmønstre i
				Map<Integer,String> byteMap = new HashMap<Integer,String>(256,100);
				
				// Læser Huffman-kodeord fra input og skriver til output	
				// Første betingelse for while-løkken er, at der er mere at læse fra filen
				// og at der der kun skrives det antal bytes til output som der var 
				// i den originale fil, der blev komprimeret med Encode.java.	
				while((nBit = bitIn.readBit()) != -1 & charCounter < totalChar){
					// Læser input og konkatenerer i temp
					temp = temp + String.valueOf(nBit); 
					for(int i = 0; i < table.length; i++){
						// Tjekker om temp passer med et af Huffman-kodeordene
						if (table[i].equals(temp)){
							// Tjekker om kodeorder er mødt før
							if (byteMap.containsKey(i)){
								// Gemmer bitmønster i byteMap 
								outByte = byteMap.get(i); 
							} 
							else{
								// Kodeordet er mødt før, så det slås op i byteMap
								outByte = convertToBinary(i); 
								byteMap.put(i, outByte); 
							}
							// Skriver bits fra byte bag Huffman kodeord til output
							for (int r = 0; r < 8; r++){
								int outputBit = Character.getNumericValue(outByte.charAt(r)); 
								bitOut.writeBit(outputBit); 
							}
							temp = ""; 
							charCounter++; 
						}
					}
				}
				
				// Lukker IOstreams
				bitIn.close();
				bitOut.close(); 
								
			}
			catch(IOException e){
				e.printStackTrace(); 
			}
		}
	}
	
	// Samme implementation som i Encode.java
	public static PQHeap makeHuffmanTree(int[] a){
		PQHeap HuffmanTree = new PQHeap(a.length);
		for (int i = 0; i < a.length; i++){
			Element tmp = new Element(a[i], new HuffmanTempTree(i));
			HuffmanTree.insert(tmp);
		}
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
	
	// Samme implementation som i Encode.java
	public static String[] makeHuffmanTable(PQHeap t) {
		String[] out = new String[256];
		HuffmanTempTree huff = t.extractMin().data;
		return huff.inOrderTreeWalkPath(huff.root, "", out);
	}
	
	// Metode til at konvertere en int til 
	// binær (d.v.s. 10-talssystem --> binært talsystem).
	// i skal være i [0; 255]. Output har fixed længde 8. 
	public static String convertToBinary(int i){
		int q; // kvotient 
		int m; // rest 
		String s = ""; 
		while (i > 0){
			q = i / 2; 
			m = i % 2; 
			s = s + Integer.toString(m); 
			i = q; 
		}
		// padder med 0'er
		while(s.length() < 8){
			s = s + "0"; 
		}
		// Vender streng
		String p = "";  
		for (int j = 7; j >= 0; j--){
			p = p + s.charAt(j); 
		}
		return p; 
	}
}