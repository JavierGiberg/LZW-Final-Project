import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class LZW {
	static FileOutputStream fileOutputStream;
	
	static BitOutputStream bitOutputStream;
	
	static InputStream inputstream ;
	static ObjectInputStream objectinputstream;
	
	
	
	static BitInputStream bitInputStream;
	static FileInputStream inputStream;
	static int index = 256;
	static String str = "";
	static String out = "";
	static String Url = "";
	static int READ_WRITE_CYCLES;
	static HashMap<String, Integer> dic = new HashMap<>();

	public LZW() {	
			
			READ_WRITE_CYCLES = 1;
	}

	public void Compress(String Url) throws FileNotFoundException {
		inputStream = new FileInputStream(Url);
		bitInputStream = new BitInputStream(inputStream);
		fileOutputStream = new FileOutputStream("Compressed file.bin");
		bitOutputStream = new BitOutputStream(fileOutputStream);

		System.out.println("Step 1");
		while (true) {
			String c = readBytesFromFile();
			String n = readBytesFromFile();
	
			if (c.length() == 0)
				break;
			str += convertStringToChar(c);
			if (n.length() == 0) 
				break;
			str += convertStringToChar(n);
		}

		

		System.out.println("Step 2");
		
		CompressStr();
		
		System.out.println(out);
//		System.out.println(dic);
	
			bitOutputStream.flush();

	
	}

//-----------------------------Compress str-----------------------------------------------	
	static void CompressStr() {
		while (true) {

			if (str.length() == 0) {
				System.out.println("Done!");

				break;
			}

			if (str.length() == 1) {
				int ascii = str.charAt(0);
				out += ascii + "";
				dic.put(str, index);
				
					bitOutputStream.writeBits(8, ascii);
//					System.out.println("write byte");
				
					
				System.out.println("Done!");
				break;
			}
			int current = str.charAt(0);
//			strNext = str.charAt(1) + "";
			checkBestonDic(current , 1);
		}
	}

//---------------------------check The Best-----------------------------------------------	
	static void checkBestonDic(int toCheck, int nextChar) {
		String toChecksrt = toCheck+"";
		if (str.length() == nextChar) {
			
			dic.put(toChecksrt, index);
			str = str.substring(nextChar);
			if (toCheck < 256) {
				
				bitOutputStream.writeBits(8,toCheck);
//					System.out.println("write byte "+toCheck);
					out += toCheck + " ";
				
		     
			}else {// write byte
				
			     	bitOutputStream.writeBits(16,toCheck);
//					System.out.println("write char "+toCheck);
					out += nextChar + " ";
			}
			return;
		}

		if (!dic.containsKey(toCheck + "" + str.charAt(nextChar))) {

			if (toChecksrt.length() == 1||toCheck<256) {
				int ascii = toChecksrt.charAt(0);
//				System.out.println(toCheck);
				if (toCheck < 256) {

					
					dic.put(toCheck + "" + str.charAt(nextChar), index);
					index++;
					str = str.substring(nextChar);
					
					bitOutputStream.writeBits(8,toCheck);// write byte
//						System.out.println("write byte "+toCheck);
						out += toCheck + " ";
					
					return;
				}
			}
	//		System.out.println("int " + nextChar);
			// System.out.println(str);
			
			dic.put(toChecksrt + str.charAt(nextChar), index);
			index++;
			str = str.substring(nextChar);
			
//			System.out.println(Integer.parseInt(toChecksrt));
			
			bitOutputStream.writeBits(16,Integer.parseInt(toChecksrt));
			out += nextChar + " ";
//				System.out.println("write char "+Integer.parseInt(toChecksrt));
			 // write byte
//			System.out.println(out);
//			System.out.println(dic);
			return;
		} else {

			String toCheckAgain = toChecksrt + str.charAt(nextChar);

			checkBestonDic(dic.get(toCheckAgain) , nextChar + 1);
		}
	}

//---------------------------------------------------------------------------------------------	    
	static char convertStringToChar(String Codes) {

		char Return;

		Return = (char) Integer.parseInt(Codes, 2);
		return Return;
	}

//-------------------------Test----------------------------------------------------
	static String readBytesFromFile() {
		// Variables & Declarations
		final int EOF = -1;
		final String ZERO_CHAR = "0";
		int currentByte = -1;
		String formattedByte = "";
		String parsedBytes = "";

		// System.out.println("[DEBUG] Reading from file.");
		// Read N amount of bytes from file
		for (int i = 0; i < READ_WRITE_CYCLES; i++) {
			try {
				currentByte = bitInputStream.readBits(8);

				if (currentByte == EOF)
					break;

				String nonFormattedByte = Integer.toBinaryString(currentByte);

				for (int j = 0; j < (8 - nonFormattedByte.length()); j++) {
					formattedByte = formattedByte.concat(ZERO_CHAR);
				}

				formattedByte = formattedByte.concat(nonFormattedByte);

				// System.out.printf("[DEBUG] Unformatted = \"%s\", Formatted = \"%s\".\n",
				// nonFormattedByte, formattedByte);

				parsedBytes = parsedBytes.concat(formattedByte);

				formattedByte = "";

			} catch (IOException e) {
				System.out.println("[ERROR] Cannot read file.");
				Thread.currentThread().interrupt();
			}

		}

		// System.out.printf("[DEBUG] Entire formatted string \"%s\".\n",parsedBytes);
		System.out.println(parsedBytes);
		return parsedBytes;
	}

//-------------------------------------------------------------------------------------------
	public void Decompress() {

//		try {
//			inputstream = new FileInputStream(input_names[0]);
//			objectinputstream = new ObjectInputStream(inputstream);
//			byte[] fileBytes = (byte[]) objectinputstream.readObject();
//			HashMap<Byte, String> map = (HashMap<Byte, String>) objectinputstream.readObject();
//		
//			OutputStream outputstream = new FileOutputStream(output_names[0]);
//			outputstream.write(Reault);
//			outputstream.close();
//			objectinputstream.close();
//			inputstream.close();
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
		
	}

}
