import java.io.FileInputStream; 
import java.io.File;
import java.io.IOException;

public class ByteToIntReader{
	public static void main(String[] args){
		// Input fil som File objekt 
		File testFil = new File("testtest.txt");
		readByteAsInt(testFil);	
	}
	
	public static void readByteAsInt(File fil){
		FileInputStream inp = null;
		// Starter en FileInputStream som skal være underliggende for BitInputStream
		try{
			inp = new FileInputStream(fil); 
			// Starter en BitInputStream
			BitInputStream bitInp = new BitInputStream(inp);
			System.out.println("BitInputStream started");
			// Printer bits 
			int i = 1;
			while(i <= 4){
				System.out.println(bitInp.readInt());
				i++;
			}
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
