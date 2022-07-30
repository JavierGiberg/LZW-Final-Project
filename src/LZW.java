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
	private static FileOutputStream fileOutputStream;

	private static BitOutputStream bitOutputStream;

	private static InputStream inputstream;
	private static ObjectInputStream objectinputstream;

	private static BitInputStream bitInputStream;
	private static FileInputStream inputStream;
	private static int index = 16777216;
	private static String str = "";
	private static String out = "";
	private static String Url = "";
	private static int INTERVAL_READINF_SIZE ;
	private static HashMap<String, Integer> bookOfMemories = new HashMap<>();

	public LZW() {

		INTERVAL_READINF_SIZE = 1;
	}

	public void Compress(String srcIn,String srcOut) throws IOException {
//		dic.put("98i", 65536);
//		dic.put("256g", 65537);
//		dic.put("257b", 65538);
//		dic.put("ba", 65539);
//		dic.put("259l", 65540);
//		dic.put("260o", 65541);
//		dic.put("261o", 65542);
//		dic.put("262n", 65543);
//		index=65543;
//		System.out.println(dic);
		
		
		inputStream = new FileInputStream(srcIn);
		bitInputStream = new BitInputStream(inputStream);
		fileOutputStream = new FileOutputStream(srcOut);
		bitOutputStream = new BitOutputStream(fileOutputStream);

		System.out.println("Step 1");
		while (true) {
			String current = convertBytesToSring();
			String next = convertBytesToSring();

			if (current.length() == 0)
				break;
			str += convertStringToChar(current);
			if (next.length() == 0)
				break;
			str += convertStringToChar(next);
		}
		System.out.println("Step 2");
		CompressStr();
//		System.out.println(out);
//		System.out.println(dic);
		bitOutputStream.flush();
		System.out.println("Done!");
	}

//-----------------------------Compress str-----------------------------------------------	
	static void CompressStr() {
		while (true) {
			if (str.length() == 0) {
				break;
			}
			if (str.length() == 1) {
				int ascii = str.charAt(0);
				out += ascii + "";
				bookOfMemories.put(str, index);
				bitOutputStream.writeBits(8, ascii);
				break;
			}
			int current = str.charAt(0);
			checkBestonDic(current, 1);
		}
	}

//---------------------------check The Best-----------------------------------------------	
	static void checkBestonDic(int toCheck, int nextChar) {
		String toChecksrt = toCheck + "";
		if (str.length() == nextChar) {
			bookOfMemories.put(toChecksrt, index);
			str = str.substring(nextChar);
			if (toCheck < index) {
				bitOutputStream.writeBits(8, toCheck);
				out += toCheck + " ";
			} else {// write byte

				bitOutputStream.writeBits(16, toCheck);
				out += toCheck + " ";
			}
			return;
		}

		if (!bookOfMemories.containsKey(toCheck + "" + str.charAt(nextChar))) {

			if (toChecksrt.length() == 1 || toCheck < 256) {
				int ascii = toChecksrt.charAt(0);
				if (toCheck < 256) {

					bookOfMemories.put(toCheck + "" + str.charAt(nextChar), index);
					index++;
					str = str.substring(nextChar);

					bitOutputStream.writeBits(8, toCheck);
					out += toCheck + " ";

					return;
				}
			}
			bookOfMemories.put(toChecksrt + str.charAt(nextChar), index);
			index++;
			str = str.substring(nextChar);
			bitOutputStream.writeBits(16, Integer.parseInt(toChecksrt));
			out += Integer.parseInt(toChecksrt) + " ";
			return;
		} else {
			String toCheckAgain = toChecksrt + str.charAt(nextChar);
			checkBestonDic(bookOfMemories.get(toCheckAgain), nextChar + 1);
		}
	}

//---------------------------------------------------------------------------------------------	    
	static char convertStringToChar(String Codes) {
		char Return;
		Return = (char) Integer.parseInt(Codes, 2);
		return Return;
	}

//-------------------------readBytesFromFile-------------------------------------------
	static String convertBytesToSring() throws IOException {
		int current = -1;
		String Byte = "";
		String completeBytes = "";
		for (int i = 0; i < INTERVAL_READINF_SIZE; i++) {
			
				current = bitInputStream.readBits(8);
				if (current == -1)
					break;
				String completeByte = Integer.toBinaryString(current);
				for (int j = 0; j < (8 - completeByte.length()); j++) {
					Byte = Byte.concat("0");
				}
				Byte = Byte.concat(completeByte);
				completeBytes = completeBytes.concat(Byte);
				Byte = "";
			
		}
//		System.out.println(completeBytes);
		return completeBytes;
	}

//-------------------------------------------------------------------------------------------
	public void Decompress() {

		//Amit close your eyes , imagine there is a code and believe me it works!

	}

}
