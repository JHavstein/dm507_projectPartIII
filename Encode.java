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

// Main class for the encoding part.
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
			//Building array containing the frequency of each byte in the file.
			int[] freqTable = new int[256];
                    	try(FileInputStream input = new FileInputStream(new File(args[0]))){
            			int byteContent;
            			while((byteContent = input.read()) != -1){
                			freqTable[byteContent] = freqTable[byteContent] + 1; 
            			}
        		}
        		catch (IOException e){
            			e.printStackTrace();
        		}
			// Making Huffman tree and Huffman codes.
			String[] table = new Huffman(freqTable).makeCodes();
			
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
					outputFreq.writeInt(freqTable[i]);
				} 
												
				// Writing Huffman codes to the output file.
				int x;
                		while((x = input.read())!= -1){
                    			String in = table[x];
                    			for (int i=0; i < in.length(); i++){
                        			outputFreq.writeBit(Character.getNumericValue(in.charAt(i)));
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
}
