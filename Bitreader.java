import java.io.FileInputStream; 
import java.io.File;
import java.io.IOException;

public class Bitreader{
	public static void main(String[] args){
		// Input fil som File objekt 
		File testFil = new File("testtest.txt");
		readBits(testFil);	
	}
	
	private static void readBits(File fil){
		FileInputStream inp = null;
		// Starter en FileInputStream som skal være underliggende for BitInputStream
		try{
			inp = new FileInputStream(fil); 
			// Starter en BitInputStream
			BitInputStream bitInp = new BitInputStream(inp);
			System.out.println("BitInputStream started");
			// Printer bits 
			int bitContent = bitInp.readBit(); 
			int counter = 0;
			while(bitContent != -1){
				System.out.println(bitContent);
				bitContent = bitInp.readBit(); 
				counter++;
			}
			System.out.println("Number of bits outputted: "+ counter);
			// Lukker BitInputStream
			bitInp.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		// Lukker FileInputStream		
		try{
			inp.close();
		}
		catch(IOException s){
			s.printStackTrace();
		}
	}
}