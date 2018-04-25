import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.File;
import java.io.IOException;

// Main Class
public class Encode{
	public static void main(String[] args){
		// Laver args om til File-objekter 
		if (args.length != 2){
			// Fejlinput
			System.out.println("To argumenter ikke givet. Terminerer program.");
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
