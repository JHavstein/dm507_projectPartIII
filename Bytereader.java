/**
DM507, project part III
	Mathilde Blicher Christensen - mathc17 - 01-03-1997
	Jeanette Frieda Aviaya Sommer - jeaso17 - 08-05-1984
	Jonas Alexander Havstein Eriksen - joeri15 - 16-02-1993
*/

import java.io.*;
import java.util.*;

public class Bytereader{

	private File inputFil = null; 
	private int[] counter = new int[256];
	public Bytereader(File fil){
		this.inputFil = fil;
		this.counter = counter; 
	}
	
	// Constructor method that builds an array containing the frequency of each unique byte
	public void byteReader(){
		FileInputStream byteInput = null;
		try{
		 	byteInput = new FileInputStream(inputFil);
			// Counting the frequency of each unique byte in the input file.
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
	
	// Getter-method for retrieving the frequencies
	public int[] getFrequencies(){
		int[] copyOfCounter = Arrays.copyOf(counter, counter.length); 
		return copyOfCounter; 
	}
	
	// Returns the frequency of a given byte pattern given by index i
	public int freqAtIndex(int i){
		return counter[i]; 
	}		
}