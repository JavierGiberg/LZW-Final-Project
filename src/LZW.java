
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.HashMap;

public class LZW {
	private static FileOutputStream fileOutputStream;
	private static BitOutputStream bitOutputStream;
	private static BitInputStream bitInputStream;
	private static int Indexbook;
	private static String str = "";
	private static String out = "";
	private static int INTERVAL_READINF_SIZE;
	private static boolean BuildStrFlag;
	private static HashMap<String, Integer> bookOfMemoriesC;
	private static HashMap<Integer, String> bookOfMemoriesD;

	public LZW() {
		Indexbook = 511;
		INTERVAL_READINF_SIZE = 1;
	}

	public void Compress(String srcIn, String srcOut) throws IOException {

		long start = System.currentTimeMillis();
		System.out.println("Start Process...");
		System.out.println("Step 1 : Install variable for work");
		ResetVarToWork(srcIn, srcOut);

		System.out.println("Step 2 : Build string for compress");
		BuildStr();
		System.out.println("Step 3 : Start ()=>{ Compress() => Build bookOfMemories() => Write file() }");
		CompressStr();
		System.out.println("Step 4 : Close resources");
		bitOutputStream.flush();
		bitOutputStream.close();
		bitInputStream.close();
		long end = System.currentTimeMillis();
		System.out.println(bookOfMemoriesC);
//		System.out.println(out);
		System.out.println("Process Done! in : " + (end - start) + " mili seconds");
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
				bookOfMemoriesC.put(str, Indexbook);
				bitOutputStream.writeBits(8, ascii);
				break;
			}
			int current = str.charAt(0);
			checkBestobookOfMemories(current, 1);
		}
	}

//---------------------------check The Best-----------------------------------------------	
	private static void checkBestobookOfMemories(int toCheck, int nextChar) {
//		System.out.println(out);
		String toChecksrt = toCheck + "";
		if (str.length() == nextChar) {
			bookOfMemoriesC.put(toChecksrt, Indexbook);
			str = str.substring(nextChar);
			bitOutputStream.writeBits(16, toCheck);
//			out += toCheck + " ";
			return;
		}
		if (!bookOfMemoriesC.containsKey(toCheck + "" + str.charAt(nextChar))) {
			if (toChecksrt.length() == 1 || toCheck < 256) {
				int ascii = toChecksrt.charAt(0);
				if (toCheck < 256) {
					bookOfMemoriesC.put(toCheck + "" + str.charAt(nextChar), Indexbook);
					Indexbook++;
					str = str.substring(nextChar);
					bitOutputStream.writeBits(8, toCheck);
//					out += toCheck + " ";
					return;
				}
			}
			bookOfMemoriesC.put(toChecksrt + str.charAt(nextChar), Indexbook);
			Indexbook++;
			str = str.substring(nextChar);
			bitOutputStream.writeBits(16, Integer.parseInt(toChecksrt));
			out += Integer.parseInt(toChecksrt) + " ";
			return;
		} else {
			String toCheckAgain = toChecksrt + str.charAt(nextChar);
			checkBestobookOfMemories(bookOfMemoriesC.get(toCheckAgain), nextChar + 1);
		}
	}

//---------------------------------------------------------------------------------------------	    
	private static char convertStringToChar(String Codes) {
//		System.out.println(Codes);   
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

	public void setInterval(int size) {
		INTERVAL_READINF_SIZE = size / 8;
	}
	// ------------------in
	// process--------------------Decompress--------------------------------------------------
	// ------------------in
	// process--------------------Decompress--------------------------------------------------
	// ------------------in
	// process--------------------Decompress--------------------------------------------------
	// -------------------in
	// process-------------------Decompress--------------------------------------------------
	// -------------------in
	// process-------------------Decompress--------------------------------------------------
	// ------------------in
	// process--------------------Decompress--------------------------------------------------
	// ------------------in
	// process--------------------Decompress--------------------------------------------------

//------------------------in process--------------Decompress--------------------------------------------------
	public void Decompress(String srcIn, String srcOut) throws IOException {
		long start = System.currentTimeMillis();

		System.out.println("Start Process...");
		System.out.println("Step 1 : Install variable for work");
		ResetVarToWork(srcIn, srcOut);
		System.out.println("Step 2 : Build string for compress");
		BuildStrFlag = true;
		BuildStr();
		System.out.println("Step 3 : Start ()=>{ Decompress() => Build bookOfMemories() => Write file() }");
//		System.out.println(str);

		DecompressStr();
		System.out.println(out);
		long end = System.currentTimeMillis();
		System.out.println("Process Done Decompres! in : " + (end - start) + " mili seconds");
	}

//-------------------------in process-------------DecompressStr---------------------------------------	
	private static void DecompressStr() {

		while (true) {
			if (str.length() == 0) {
				break;
			}
			if (str.length() == 1) {
				int ascii = str.charAt(0);
				out += str.charAt(0);

				bitOutputStream.writeBits(8, str.charAt(0));
//				System.out.println(256 * ascii + "");

				break;
			}
			int current = 256 * str.charAt(0);
			int next = str.charAt(1);
			checkBestobookOfMemories1(current+next , 1);

		}
	}

//================================in process===============================================
	private static void checkBestobookOfMemories1(int toCheck, int nextChar) {
		System.out.println(Indexbook);
		int toConvert = toCheck+str.charAt(nextChar);
//		String currentBook =toConvert+"";
//		String nextBook = bookOfMemoriesD.get(nextChar);
//		System.out.println("current " + currentBook );
//		System.out.println(toCheck + nextBook + "");

		if (!bookOfMemoriesD.containsKey(toCheck)) {
			int bettwenStr = 256 * str.charAt(1) + str.charAt(2);
			if (bookOfMemoriesD.containsKey(bettwenStr)||Indexbook==256*str.charAt(1)+str.charAt(2)) {
				String nextCharFromBook = bookOfMemoriesD.get(bettwenStr);
				if(Indexbook==256*str.charAt(1)+str.charAt(2))
					nextCharFromBook=str.charAt(0)+"";

				bookOfMemoriesD.put(Indexbook , str.charAt(0) + "" + nextCharFromBook.charAt(0));
				Indexbook++;
//				System.out.println(str);
				out += str.charAt(0);
				str = str.substring(1);
				System.out.println(out);
				System.out.println(bookOfMemoriesD);
			} else {
//				System.out.println(256*str.charAt(1)+str.charAt(2)+"");
				if (bookOfMemoriesD.containsKey(256*str.charAt(1)+str.charAt(2))) {
					
					String nextTemp = bookOfMemoriesD.get(256*str.charAt(1)+str.charAt(2));
					
					bookOfMemoriesD.put(Indexbook , str.charAt(0) + "" + nextTemp.charAt(0));
					out += str.charAt(0);
					str = str.substring(1);
					System.out.println(out);
					System.out.println(bookOfMemoriesD);
				} else {
					bookOfMemoriesD.put(Indexbook , str.charAt(0) + "" + str.charAt(1));
					Indexbook++;
//					System.out.println(str);
					out += str.charAt(0);
					str = str.substring(1);
					System.out.println(out);
					System.out.println(bookOfMemoriesD);
				}
			}
		} else {
			
	//		int toConvert = Integer.parseInt(toCheck)+str.charAt(nextChar);
			if(str.length()<3) {
				out+=bookOfMemoriesD.get(toCheck);
				str=str.substring(1);
				return;
			}
			String toNextCkeck = bookOfMemoriesD.get(toCheck);
			if(bookOfMemoriesD.containsKey(256*str.charAt(2)+str.charAt(3))) {
				String addNext =  bookOfMemoriesD.get(256*str.charAt(2)+str.charAt(3));
				toNextCkeck+=addNext.charAt(0);
			}else {
				if(256*str.charAt(2)+str.charAt(3)==Indexbook) {
					toNextCkeck+=toNextCkeck.charAt(0);
				}else {
					toNextCkeck+=str.charAt(2);					
				}
					
			}
//			System.out.println("to chak bigger :"+toNextCkeck);
//			bookOfMemoriesD.put(111, "ban");
			if(bookOfMemoriesD.containsValue(toNextCkeck)) {
				System.out.println("biiiiiiiiiiiiiiiiiiiiiger");
			}else {
				bookOfMemoriesD.put(Indexbook,toNextCkeck );
				Indexbook++;
				out+=bookOfMemoriesD.get(toCheck);
				str=str.substring(nextChar+1);
				System.out.println(out);
				System.out.println(bookOfMemoriesD);
			}
				
		}
//		String temp = bookOfMemoriesD.get(currentBook);
		
	}

//==============================================================================================		

//-----------------------------------ResetVarToWork------------------------------------------
	static void ResetVarToWork(String srcIn, String srcOut) throws IOException {
		str = "";
		out = "";
		bookOfMemoriesD = new HashMap<>();
		bookOfMemoriesC = new HashMap<>();
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
//			System.out.println(str);
		}
	}
//----------------------------------------end----------------------------------------------	

}