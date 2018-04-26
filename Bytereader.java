import java.io.*;
import java.util.*;

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