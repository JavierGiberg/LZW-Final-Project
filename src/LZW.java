
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.HashMap;


public class LZW {
	private static FileOutputStream fileOutputStream;
	private static BitOutputStream bitOutputStream;
	private static BitInputStream bitInputStream;
	private static int index = 256;
	private static String str = "";
	private static String out = "";
	private static int INTERVAL_READINF_SIZE;
	private static HashMap<String, Integer> bookOfMemoriesC;
	private static HashMap<Integer,String> bookOfMemoriesD;

	public LZW() {

		INTERVAL_READINF_SIZE = 1;
	}

	public void Compress(String srcIn, String srcOut) throws IOException {

		long start = System.currentTimeMillis();
		System.out.println("Start Process...");
		System.out.println("Step 1 : Install variable for work");
		ResetVarToWork(srcIn,srcOut);

		System.out.println("Step 2 : Build string for compress");
		BuildStr();
		System.out.println("Step 3 : Start ()=>{ Compress() => Build bookOfMemories() => Write file() }");
		CompressStr();
		System.out.println("Step 4 : Close resources");
		bitOutputStream.flush();
		bitOutputStream.close();
	    bitInputStream.close();
		long end = System.currentTimeMillis();
		System.out.println("Process Done! in : "+(end - start) + " mili seconds");
	}

//-----------------------------Compress str-----------------------------------------------	
	private static void CompressStr() {
		while (true) {
			if (str.length() == 0) {
				break;
			}
			if (str.length() == 1) {
				int ascii = str.charAt(0);
//				out += ascii + "";
				bookOfMemoriesC.put(str, index);
				bitOutputStream.writeBits(8, ascii);
				break;
			}
			int current = str.charAt(0);
			checkBestobookOfMemories(current, 1);
		}
	}

//---------------------------check The Best-----------------------------------------------	
	private static void checkBestobookOfMemories(int toCheck, int nextChar) {
		String toChecksrt = toCheck + "";
		if (str.length() == nextChar) {
			bookOfMemoriesC.put(toChecksrt, index);
			str = str.substring(nextChar);
			if (toCheck < index) {
				bitOutputStream.writeBits(8, toCheck);
//				out += toCheck + " ";
			} else {// write byte

				bitOutputStream.writeBits(16, toCheck);
//				out += toCheck + " ";
			}
			return;
		}

		if (!bookOfMemoriesC.containsKey(toCheck + "" + str.charAt(nextChar))) {

			if (toChecksrt.length() == 1 || toCheck < 256) {
				int ascii = toChecksrt.charAt(0);
				if (toCheck < 256) {

					bookOfMemoriesC.put(toCheck + "" + str.charAt(nextChar), index);
					index++;
					str = str.substring(nextChar);

					bitOutputStream.writeBits(8, toCheck);
					out += toCheck + " ";

					return;
				}
			}
			bookOfMemoriesC.put(toChecksrt + str.charAt(nextChar), index);
			index++;
			str = str.substring(nextChar);
			bitOutputStream.writeBits(16, Integer.parseInt(toChecksrt));
//			out += Integer.parseInt(toChecksrt) + " ";
			return;
		} else {
			String toCheckAgain = toChecksrt + str.charAt(nextChar);
			checkBestobookOfMemories(bookOfMemoriesC.get(toCheckAgain), nextChar + 1);
		}
	}

//---------------------------------------------------------------------------------------------	    
	private static char convertStringToChar(String Codes) {
		char Return;
		Return = (char) Integer.parseInt(Codes, 2);
		return Return;
	}

//-------------------------readBytesFromFile-------------------------------------------
	private static String convertBytesToString() throws IOException {
		
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

	public  void setInterval(int size) {
		INTERVAL_READINF_SIZE=size/8;
	}
//-------------------------------------------------------------------------------------------
	public void Decompress(String srcIn, String srcOut) throws IOException {
		System.out.println("Start Process...");
		System.out.println("Step 1 : Install variable for work");
		ResetVarToWork(srcIn,srcOut);
		System.out.println("Step 2 : Build string for compress");
		BuildStr();
		System.out.println("Step 3 : Start ()=>{ Compress() => Build bookOfMemories() => Write file() }");
		System.out.println(str);
		
//		for(int i=0;i<str.length();i++) {
//			int x = str.charAt(i);
//			System.out.print(x+" ");
//		}
		DecompressStr();

	}
//--------------------------------------DecompressStr---------------------------------------	
	private static void DecompressStr() {


		while (true) {
			if (str.length() == 0) {
				break;
			}
			if (str.length() == 1) {
				int ascii = str.charAt(0);
				out += ascii + "";
				System.out.println(ascii + "");
				
				bitOutputStream.writeBits(8, ascii);
				break;
			}
			int current = str.charAt(0);
			checkBestobookOfMemories1(current, 1);
			
		}
	}
		
//================================in process===============================================
	private static void checkBestobookOfMemories1(int toCheck, int nextChar) {
		
		System.out.println(bookOfMemoriesD.get(toCheck));
		if(!bookOfMemoriesD.containsKey(toCheck)) {
			for(int i=0;i<20;i++) {
			System.out.println(str.charAt(i)+str.charAt(i+1));
			System.out.println(convertStringToChar(str.charAt(i)+""+str.charAt(i+1)));
			}
		}
	}
		
//==============================================================================================		
		
		
		
		
	
//-----------------------------------ResetVarToWork------------------------------------------
	static void ResetVarToWork(String srcIn, String srcOut) throws IOException {
		str = "";
		out = "";
		bookOfMemoriesD = new HashMap<>();
		bitInputStream = new BitInputStream(srcIn);
		fileOutputStream = new FileOutputStream(srcOut);
		bitOutputStream = new BitOutputStream(fileOutputStream);
	}
//------------------------------------BuildStr----------------------------------------------
	static void BuildStr() throws IOException {
		while (true) {
			String current = convertBytesToString();
			String next = convertBytesToString();

			if (current.length() == 0)
				break;
			str += convertStringToChar(current);
			if (next.length() == 0)
				break;
			str += convertStringToChar(next);
		}
	}

//----------------------------------------end----------------------------------------------	

}













