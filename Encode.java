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
			test.getFrequencies();
			PQHeap n = makeHuffmanTree(test.getFrequencies());
			String[] table = makeHuffmanTable(n);
			test.printFrequencies();
			for (int i = 0; i < table.length; i++) {
				System.out.println("key " + i + " : " + table[i]);
			}
            try{
                FileInputStream input = new FileInputStream(args[0]);
                BitOutputStream output = new BitOutputStream ( new FileOutputStream(args[1]));
                //output.write(222);
                int x;

                while((x = input.read())!=-1){
                    String in = table[x];
                    for (int i=0; i<in.length(); i++){
                        int out = Character.getNumericValue(in.charAt(i));
                        output.writeBit(out);
                    }
                }

                input.close();
                output.close();
            }
            catch (IOException e ){
                e.printStackTrace();
            }
		}
		// Laver outputStream til fil fra args[1]

	}
	
	// Skal nok gøres non-static
	public static PQHeap makeHuffmanTree(int[] a){
		PQHeap HuffmanTree = new PQHeap(a.length);
		for (int i = 0; i < a.length; i++){
			Element tmp = new Element(a[i], new HuffmanTempTree(i));
			HuffmanTree.insert(tmp);
		}
		// Mangler merge-skridtene
		for (int i = 0; i < a.length-1; i++){
			Element x = HuffmanTree.extractMin();
			Element y = HuffmanTree.extractMin();
			int zFreq = x.key + y.key; 
			HuffmanTempTree ztree = HuffmanTempTree.merge(x.data, y.data);
			Element z = new Element(zFreq, ztree);
			HuffmanTree.insert(z);
		}
		return HuffmanTree; 
	}

	public static String[] makeHuffmanTable( PQHeap t ) {
		String[] out = new String[256];
		HuffmanTempTree huff = t.extractMin().data;
		return huff.inOrderTreeWalkPath(huff.root, "", out);
	}
}