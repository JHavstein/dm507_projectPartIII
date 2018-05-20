/**
DM507, project part III
	Mathilde Blicher Christensen - mathc17 - 01-03-1997
	Jeanette Frieda Aviaya Sommer - jeaso17 - 08-05-1984
	Jonas Alexander Havstein Eriksen - joeri15 - 16-02-1993
*/

import java.io.*;
import java.util.*;

// Class for reading bytes from an input file, including methods for building arrays containg
public class ByteReader{
	private File inputFile; 
	private int[] counter = new int[256];
	
	public ByteReader(File file){
		this.inputFile = file;
	}
	
	// Constructor method that builds an array containing the frequency of each unique byte.
	public void byteReader(){
		try(FileInputStream byteInput = new FileInputStream(inputFile)){
            		// Counting the frequency of each unique byte in the input file.
            		int byteContent;
            		while((byteContent = byteInput.read()) != -1){
                		counter[byteContent] = counter[byteContent] + 1; 
            		}
        	}
        	catch (IOException e){
            		e.printStackTrace();
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

	public void printFrequencies(){
		for (int i=0; i<counter.length; i++){
			System.out.println(i + " " + counter[i]);
		}
	}
}
