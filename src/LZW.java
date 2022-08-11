/**
 * Final Project LZW: Javier Giberg. ID# 302280383  
 *                    Netanel Bitton. ID# 305484651
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class LZW {
	private static FileOutputStream fileOutputStream;
	private static BitOutputStream bitOutputStream;
	private static BitInputStream bitInputStream;
	private static int Indexbook;
	private static String str = "";
	private static String str_INTER = "";
	private static String out = "";
	private static int INTERVAL_READINF_SIZE;
	private static boolean BuildStrFlag;
	private static HashMap<String, Integer> bookOfMemoriesC;
	private static HashMap<Integer, String> bookOfMemoriesD;
	private static boolean T = false;

	public LZW() {
		Indexbook = 511;
		INTERVAL_READINF_SIZE = 1;
	}

	public String Compress(String srcIn, String srcOut) throws IOException {

		long start = System.currentTimeMillis();
		System.out.println("Start Process...");
		System.out.println("Step 1 : Install variable for work");
		ResetVarToWork(srcIn, srcOut);

		System.out.println("Step 2 : Build string for compress");
		System.out.println("Step 3 : Start ()=>{ Compress() => Build bookOfMemories() => Write file() }");
		BuildStr();
		while (str.length() > 0) {

			if (str.length() >= 26000) {
				str_INTER = str.substring(0, 26000);
     			str = str.substring(26000);
				CompressStr();
				bitOutputStream.writeBits(8, 'L');
				bitOutputStream.writeBits(8, 'Z');
				bitOutputStream.writeBits(8, 'W');
				
				str_INTER= "";
//				str = "";
				out = "";
				Indexbook = 511;
				bookOfMemoriesC = new HashMap<>();
			} else {
				str_INTER = str.substring(0, str.length());
				str = str.substring(str.length());
				CompressStr();
				str = "";
				out = "";
				Indexbook = 511;
				bookOfMemoriesC = new HashMap<>();
			}

		}
		System.out.println("Step 4 : Close resources");
		bitOutputStream.flush();
		bitOutputStream.close();
		bitInputStream.close();

		long end = System.currentTimeMillis();

//		System.out.println(out);
		return "Process Done! in : " + (end - start) + " mili seconds";
	}

//-----------------------------Compress str-----------------------------------------------	
	private static void CompressStr() {
		while (true) {
			if (str_INTER.length() == 0) {
				break;
			}
			if (str_INTER.length() == 1) {
				int ascii = str_INTER.charAt(0);
				bookOfMemoriesC.put(str_INTER, Indexbook);
				bitOutputStream.writeBits(8, ascii);
				break;
			}
			int current = str_INTER.charAt(0);
			checkBestobookOfMemoriesC(current, 1);
		}
	}

//---------------------------check The Best-----------------------------------------------	
	private static void checkBestobookOfMemoriesC(int toCheck, int nextChar) {
		String toChecksrt = toCheck + "";
		if (str_INTER.length() == nextChar) {
			bookOfMemoriesC.put(toChecksrt, Indexbook);
			str_INTER = str_INTER.substring(nextChar);
			bitOutputStream.writeBits(16, toCheck);
			return;
		}
		if (!bookOfMemoriesC.containsKey(toCheck + "" + str_INTER.charAt(nextChar))) {
			if (toChecksrt.length() == 1 || toCheck < 256) {
				int ascii = toChecksrt.charAt(0);
				if (toCheck < 256) {
					bookOfMemoriesC.put(toCheck + "" + str_INTER.charAt(nextChar), Indexbook);
					Indexbook++;
					str_INTER = str_INTER.substring(nextChar);
					bitOutputStream.writeBits(8, toCheck);
					return;
				}
			}
			bookOfMemoriesC.put(toChecksrt + str_INTER.charAt(nextChar), Indexbook);
			Indexbook++;
			str_INTER = str_INTER.substring(nextChar);
			bitOutputStream.writeBits(16, Integer.parseInt(toChecksrt));
			out += Integer.parseInt(toChecksrt) + " ";
			return;
		} else {
			String toCheckAgain = toChecksrt + str_INTER.charAt(nextChar);
			checkBestobookOfMemoriesC(bookOfMemoriesC.get(toCheckAgain), nextChar + 1);
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
		return completeBytes;
	}

	public void setInterval(int size) {
		INTERVAL_READINF_SIZE = size / 8;
	}

//------------------------------------Decompress--------------------------------------------------
	public String Decompress(String srcIn, String srcOut) throws IOException {
		long start = System.currentTimeMillis();

		System.out.println("Start Process...");
		System.out.println("Step 1 : Install variable for work");
		ResetVarToWork(srcIn, srcOut);
		System.out.println("Step 2 : Build string for compress");
		BuildStrFlag = true;

		System.out.println("Step 3 : Start ()=>{ Decompress() => Build bookOfMemories() => Write file() }");
		BuildStr();
		
			DecompressStr();
		
		

		bitOutputStream.flush();
		bitOutputStream.close();
		bitInputStream.close();
		long end = System.currentTimeMillis();
		System.out.println("Process Done Decompres! in : " + (end - start) + " mili seconds");
		return "Process Done Decompres! in : " + (end - start) + " mili seconds";

	}

//-------------------------in process-------------DecompressStr---------------------------------------	
	private static void DecompressStr() {
		while (true) {
		
			
			if (str.length() == 0) {
				break;
			}
			if (str.length() == 1) {
				
				out += str.charAt(0);
				bitOutputStream.writeBits(8, str.charAt(0));
				break;
			}
			if(str.charAt(0)=='L'&&str.charAt(1)=='Z'&&str.charAt(2)=='W') {
				System.out.println("innnnnnn");
				out="";
				str=str.substring(3);
				bookOfMemoriesD=new HashMap<>();
				bookOfMemoriesC=new HashMap<>();
				Indexbook=511;
				
			}
			int current = 256 * str.charAt(0) + str.charAt(1);
			checkBestobookOfMemoriesD(current, 1);
		}
	}

//================================in process===============================================
	private static void checkBestobookOfMemoriesD(int toCheck, int nextChar) {
	
			
	
		if (!bookOfMemoriesD.containsKey(toCheck) ) {
			if (str.length() <= 2) {
				out += str.charAt(0);
				out += str.charAt(1);
				bitOutputStream.writeBits(8, str.charAt(0));
				bitOutputStream.writeBits(8, str.charAt(1));
				str = str.substring(2);
				return;
			}
			int bettwenStr = 256 * str.charAt(1) + str.charAt(2);
			if (bookOfMemoriesD.containsKey(bettwenStr) || Indexbook == bettwenStr) {
				String nextCharFromBook = bookOfMemoriesD.get(bettwenStr);
				if (Indexbook == 256 * str.charAt(1) + str.charAt(2))
					nextCharFromBook = str.charAt(0) + "";
		

				bookOfMemoriesD.put(Indexbook, str.charAt(0) + "" + nextCharFromBook.charAt(0));
				bookOfMemoriesC.put(str.charAt(0) + "" + nextCharFromBook.charAt(0), Indexbook);
				Indexbook++;
				out += str.charAt(0);
				bitOutputStream.writeBits(8, str.charAt(0));
				str = str.substring(1);
			
			} else {
				if (bookOfMemoriesD.containsKey(256 * str.charAt(1) + str.charAt(2))) {
					String nextTemp = bookOfMemoriesD.get(256 * str.charAt(1) + str.charAt(2));
					
						bookOfMemoriesD.put(Indexbook, str.charAt(0) + "" + nextTemp.charAt(0));
						bookOfMemoriesC.put(str.charAt(0) + "" + nextTemp.charAt(0), Indexbook);
						out += str.charAt(0);
						bitOutputStream.writeBits(8, str.charAt(0));
						str = str.substring(nextChar);
					
				} else {
					bookOfMemoriesD.put(Indexbook, str.charAt(0) + "" + str.charAt(1));
					bookOfMemoriesC.put(str.charAt(0) + "" + str.charAt(1), Indexbook);
					Indexbook++;
					out += str.charAt(0);
					bitOutputStream.writeBits(8, str.charAt(0));
					str = str.substring(nextChar);
				}
			}
		} else {
			if (str.length() <= 3) {
				out += bookOfMemoriesD.get(toCheck);
				String toWrite = bookOfMemoriesD.get(toCheck);
				for (int i = 0; i < toWrite.length(); i++) {
					bitOutputStream.writeBits(8, toWrite.charAt(i));
				}
				str = str.substring(nextChar + 1);
				return;
			}
			String toNextCkeck = bookOfMemoriesD.get(toCheck);
			if (bookOfMemoriesD.containsKey(256 * str.charAt(2) + str.charAt(3))) {
				String addNext = bookOfMemoriesD.get(256 * str.charAt(2) + str.charAt(3));
				toNextCkeck += addNext.charAt(0);
			} else {
				if (256 * str.charAt(2) + str.charAt(3) == Indexbook) {
					toNextCkeck += toNextCkeck.charAt(0);
				} else {
					toNextCkeck += str.charAt(2);
				}
			}
			if (bookOfMemoriesD.containsValue(toNextCkeck)) {
				int toCheckAgain = bookOfMemoriesC.get(toNextCkeck);

				checkBestobookOfMemoriesD(toCheckAgain, nextChar + 1);
				return;

			} else {
				bookOfMemoriesD.put(Indexbook, toNextCkeck);
				bookOfMemoriesC.put(toNextCkeck, Indexbook);
				Indexbook++;
				out += bookOfMemoriesD.get(toCheck);
				String toWrite = bookOfMemoriesD.get(toCheck);
				for (int i = 0; i < toWrite.length(); i++) {
					bitOutputStream.writeBits(8, toWrite.charAt(i));
				}
				str = str.substring(nextChar +1);
			}
		}
	}

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
		}
	}

//----------------------------------------end----------------------------------------------	

}