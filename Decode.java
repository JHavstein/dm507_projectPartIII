/**
DM507, project part III
	Mathilde Blicher Christensen - mathc17 - 01-03-1997
	Jeanette Frieda Aviaya Sommer - jeaso17 - 08-05-1984
	Jonas Alexander Havstein Eriksen - joeri15 - 16-02-1993
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
			BitInputStream input = null;
            		FileOutputStream output = null;
            		try{
                		// Opening input-/outputstreams.
                		input = new BitInputStream(new FileInputStream(new File(args[0])));
                		output = new FileOutputStream(new File(args[1])); 
				// Reading the frequency table from the encoded file while 
				// also counting the total number of characters which is the 
				// sum of the frequencies. 	
				int[] freqTable = new int[256];
				int totalChar = 0; 
				for (int i = 0; i <= 255; i++){
					freqTable[i] = input.readInt();
					if(freqTable[i] > 0){
						totalChar = totalChar + freqTable[i];
					}
				}

				// Making Huffman Tree and codes on the basis of the frequency table. 
                		// Same implementation as in Encode.java.
                		Huffman huffman = new Huffman(freqTable);
                		String[] table = huffman.makeCodes();
				
				int bit; 
				String temp = ""; 	
				int charCounter = 0; // Counts number of bytes outputted at any given time.
				
				// Reads the Huffman codeword from input and writes to output.
				// The first condition for the while loop states that there must be more to read from the file and that
				// there should only be written the same number of bytes to output as contained in the original file
				// compressed by Encode.java.
				while((bit = input.readBit()) != -1 & charCounter < totalChar){
                    			// Reading input with concatenating temp.
                    			temp = temp + String.valueOf(bit); 
                    			for(int i = 0; i < table.length; i++){
                       				// Checks if temp matches any of the Huffman codes at this point.
                        			if (table[i].equals(temp)){
                            				output.write(i);
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
                		if(input != null){
                    			try{
                        			input.close();
                    			}
                    			catch(IOException e){
                        			e.printStackTrace(); 
                    			}
                		}
                		if(output != null){
                    			try{
                        			output.close();
                    			}
                    			catch(IOException e){
                        			e.printStackTrace(); 
                    			}
                		} 
            		}
		}
	}
}
