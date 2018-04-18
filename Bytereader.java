import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.File;
import java.io.IOException;

// Main Class
public class Encode{
	public static void main(String[] args){
		// Laver args om til File-objekter 
		if (args.length > 2){
			// Fejlinput
			System.out.println("Giv præcis to argumenter så følgende form følges:");
			System.out.println("    Java Encode nameOfOriginalFile nameOfCompressedFile");
		}
		else{
			// Laver Bytereaderobjekt, der tager inputstreng lavet om til File objekt som argument
			Bytereader test = new Bytereader(new File(args[0]));
			// Laver frekvens tabel 
			test.byteReader();
		}
		// Laver outputStream til fil fra args[1]
		try{
			FileOutputStream output = new FileOutputStream(args[1]);
			//output.write(222);
		}
		catch (IOException e ){
			e.printStackTrace(); 
		}
	}
}
import java.io.FileInputStream; 
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Bytereader{

	public File inputFil = null; 
	public int[] counter = new int[256];
	public Bytereader(File fil){
		this.inputFil = fil;
		this.counter = counter; 
	}
	
	public void byteReader(){
		// Array til at tælle frekvens af bytes
		//int[] counter = new int[256];
		// FileInputStream til at læse bytes fra en File
		FileInputStream byteInput = null;	
		try{
		 	byteInput = new FileInputStream(inputFil);
			// Tæller hyppighed af ASCII-bytes i inputfil
			int byteContent = byteInput.read();
			while(byteContent != -1){
				counter[byteContent] = counter[byteContent] + 1; 
				byteContent = byteInput.read();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		try{
			byteInput.close();
		}
		catch(IOException s){
			s.printStackTrace();
		}
	}
	
	public void printFrequencies(){
		for(int i = 0; i < counter.length; i++){
			System.out.println("Byte " + i + ": " + counter[i]);
		} 
	}	
	
	public int[] getFrequencies(){
		int[] copyOfCounter = Arrays.copyOf(counter, counter.length); 
		return copyOfCounter; 
	}		
}