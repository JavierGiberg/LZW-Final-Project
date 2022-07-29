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
	private static int READ_WRITE_CYCLES;
	private static HashMap<String, Integer> dic = new HashMap<>();

	public LZW() {

		READ_WRITE_CYCLES = 3;
	}

	public void Compress(String Url) throws IOException {
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
				dic.put(str, index);
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
			dic.put(toChecksrt, index);
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

		if (!dic.containsKey(toCheck + "" + str.charAt(nextChar))) {

			if (toChecksrt.length() == 1 || toCheck < 256) {
				int ascii = toChecksrt.charAt(0);
				if (toCheck < 256) {

					dic.put(toCheck + "" + str.charAt(nextChar), index);
					index++;
					str = str.substring(nextChar);

					bitOutputStream.writeBits(8, toCheck);// write byte
					out += toCheck + " ";

					return;
				}
			}
			dic.put(toChecksrt + str.charAt(nextChar), index);
			index++;
			str = str.substring(nextChar);
			bitOutputStream.writeBits(16, Integer.parseInt(toChecksrt));
			out += Integer.parseInt(toChecksrt) + " ";
			return;
		} else {
			String toCheckAgain = toChecksrt + str.charAt(nextChar);
			checkBestonDic(dic.get(toCheckAgain), nextChar + 1);
		}
	}

//---------------------------------------------------------------------------------------------	    
	static char convertStringToChar(String Codes) {
		char Return;
		Return = (char) Integer.parseInt(Codes, 2);
		return Return;
	}

//-------------------------Test----------------------------------------------------
	static String readBytesFromFile() throws IOException {
		int current = -1;
		String formattedByte = "";
		String parsedBytes = "";
		for (int i = 0; i < READ_WRITE_CYCLES; i++) {
			
				current = bitInputStream.readBits(8);
				if (current == -1)
					break;
				String nonFormattedByte = Integer.toBinaryString(current);
				for (int j = 0; j < (8 - nonFormattedByte.length()); j++) {
					formattedByte = formattedByte.concat("0");
				}
				formattedByte = formattedByte.concat(nonFormattedByte);
				parsedBytes = parsedBytes.concat(formattedByte);
				formattedByte = "";
			
		}
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
