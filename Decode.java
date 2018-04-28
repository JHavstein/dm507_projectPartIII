import java.io.*;

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
			
				// Laver hyppighedstabel
				int[] freqTable = new int[256];
				for (int i = 0; i <= 255; i++){
					freqTable[i] = bitIn.readInt();
				}
				
				// Udregner samlet antal tegn
				long totalChar = 0; 
				for(int i = 0; i < freqTable.length; i++){
					totalChar = totalChar + (long)freqTable[i];
					
				}				
				
			}
			catch(IOException e){
				e.printStackTrace(); 
			}
		}
	}
}