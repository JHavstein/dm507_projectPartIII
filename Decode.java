/**
DM507, project part III
	Mathilde Blicher Christensen - mathc17 - 01-03-1997
	Jeanette Frieda Aviaya Sommer - jeaso17 - 08-05-1984
	Jonas Alexander Havstein Eriksen - joeri15 - 16-02-1993
*/

import java.io.*;
import java.util.*;

// Main class for the decoding  
public class Decode{
	public static void main(String[] args){
		if (args.length != 2){
			// Terminates program if user does not give correct number of arguments.
			if(args.length == 1){
				System.out.println("There needs to be to arguments. You gave 1 argument. The program will now terminate");
			}
			else{
				System.out.println("There needs to be to arguments. You gave " + args.length +  " arguments. The program will now terminate.");
				
			}
			System.exit(0);
		}
		else{
			BitInputStream bitIn = null;
            		BitOutputStream bitOut = null;
            		try{
                		// Opening input-/outputstreams.
                		bitIn = new BitInputStream(new FileInputStream(args[0]));
                		bitOut = new BitOutputStream(new FileOutputStream(args[1])); 
				// Reading the frequency table from the encoded file while 
				// also counting the total number of characters which is the 
				// sum of the frequencies. 	
				int[] freqTable = new int[256];
				int totalChar = 0; 
				for (int i = 0; i <= 255; i++){
					freqTable[i] = bitIn.readInt();
					if(freqTable[i] > 0){
						totalChar = totalChar + freqTable[i];
					}
				}

				// Making Huffman Tree on the basis of the frequency table. 
				// Same implementation as in Encode.java.
				PQHeap n = makeHuffmanTree(freqTable);
				
				// Making Huffman Codes in the basis of the Huffman Table. 
				// Same implementation as in Encode.java.
				String[] table = makeHuffmanTable(n);
				
				int nBit; 
				String outByte; 
				String temp = ""; 	
				int charCounter = 0; // Counts number of bytes outputted at any given time.
				
				// Initializing a HashMap with capacity 256 and load factor 100% for storing 
				// byte-length bit patterns.
				Map<Integer,String> byteMap = new HashMap<Integer,String>(256,100);
				
				// Reads the Huffman codeword from input and writes to output.
				// The first condition for the while loop states that there must be more to read from the file and that
				// there should only be written the same number of bytes to output as contained in the original file
				// compressed by Encode.java.
				while((nBit = bitIn.readBit()) != -1 & charCounter < totalChar){
					// Reading input with concatenating temp.
					temp = temp + String.valueOf(nBit); 
					for(int i = 0; i < table.length; i++){
						// Checks if temp matches any of the Huffman codes at this point.
						if (table[i].equals(temp)){
							// Checks if a given Huffman code has been encountered before.
							if (byteMap.containsKey(i)){ 
								outByte = byteMap.get(i); 
							} 
							else{
								// The given Huffman code is encountered for the first time
								// and must therefore be converted from int to binary.
								outByte = convertToBinary(i); 
								byteMap.put(i, outByte); 
							}
							// Writing byte to output bitwise.
							for (int r = 0; r < 8; r++){
								int outputBit = Character.getNumericValue(outByte.charAt(r)); 
								bitOut.writeBit(outputBit); 
							}
							temp = ""; // resetting temp.
							charCounter++; 
						}
					}
				}				
			}
			catch(IOException e){
				e.printStackTrace(); 
			}
			finally{
                		// Closes IOstreams
                		if(bitIn != null){
                    			try{
                        			bitIn.close();
                    			}
                    			catch(IOException e){
                        			e.printStackTrace(); 
                    			}
                		}
                		if(bitOut != null){
                    			try{
                        			bitOut.close();
                    			}
                    			catch(IOException e){
                        			e.printStackTrace(); 
                    			}
                		} 
            		}
		}
	}
	
	// Same implementation as in Encode.java (see comments there)
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
            HuffmanTempTree ztree = new HuffmanTempTree(0);
            ztree.merge((HuffmanTempTree)x.data, (HuffmanTempTree)y.data);
            Element z = new Element(zFreq, ztree);
            HuffmanTree.insert(z);
		}
		return HuffmanTree; 
	}
	
	// Same implementation as in Encode.java (see comments there)
	public static String[] makeHuffmanTable(PQHeap t) {
		String[] out = new String[256];
		HuffmanTempTree huff = (HuffmanTempTree) t.extractMin().data;
		return huff.inOrderTreeWalkPath(huff.root, "", out);
	}
	
	// Method for converting an int to a binary of fixed 
	// length 8. A string representation is returned 
	// to maintain the binary representation. 
	// i: int to be converted. Must be in range [0;255]
	public static String convertToBinary(int i){
		int q; // quotient from integer division  
		int m; // remainder from integer division 
		String s = ""; 
		while (i > 0){
			q = i / 2; 
			m = i % 2; 
			s = s + Integer.toString(m); 
			i = q; 
		}
		// Padding with 0's if necessary to reach length 8.
		while(s.length() < 8){
			s = s + "0"; 
		}
		// Reading the resulting string from above backwards to 
		// get the result. 
		String p = "";  
		for (int j = 7; j >= 0; j--){
			p = p + s.charAt(j); 
		}
		return p; 
	}
}
