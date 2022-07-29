import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class LZW {
	static FileOutputStream fileOutputStream; 
	static ObjectOutputStream objectoutputstream;
	static FileOutputStream fileOutputStream1; 
	static ObjectOutputStream objectoutputstream1;
	static BitInputStream bitInputStream;
	static FileInputStream inputStream;
	static int index = 256;
	static String str = "";
	static String current = "";
	static String strNext = "";
	static String out = "";
	static String Url ="";
	static int READ_WRITE_CYCLES;
	static HashMap<String, Integer> dic = new HashMap<>();

	public LZW(String Url) {
		this.Url = Url;
		try {
			inputStream = new FileInputStream(Url);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		READ_WRITE_CYCLES= 1;
		bitInputStream = new BitInputStream(inputStream);
	}

	public void Compress() {
		
	    while(true) {
	    	String c= readBytesFromFile();
	    	String n= readBytesFromFile();
		//System.out.println(str);
		if(c.length() == 0) // EOF CHECK
            break;
		str+=convertStringToChar(c);
		str+=convertStringToChar(n);
	//	System.out.println(str);
	
	    }
	    
		current = current.concat(strNext);
		System.out.println(current);
		System.out.println(strNext);
		try {
			fileOutputStream = new FileOutputStream("Compressed file.bin");
			objectoutputstream = new ObjectOutputStream(fileOutputStream);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	
		CompressStr();
//		String result =convertStringToBinary(out);
//		byte[] w = result.getBytes();
//		System.out.println(result);
//		System.out.println(prettyBinary(result, 8, " "));
		
		try {
		//	objectoutputstream.writeBytes(str);
			objectoutputstream.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//-----------------------------Compress str-----------------------------------------------	
	static void CompressStr() {
		while (true) {
			strNext = readBytesFromFile();
			
			current = current.concat(strNext);
			System.out.println(current);
			System.out.println(strNext);
			if (str.length() == 0) {
				System.out.println("Done!");

				break;
			}

			if (str.length() == 1) {
				int ascii = str.charAt(0);
				out += ascii + "";
				dic.put(str, index);
				System.out.println(out);
				System.out.println(dic);
				System.out.println("Done!");
				break;
			}
			int current = str.charAt(0);
			strNext = str.charAt(1) + "";
			checkBestonDic(current + "", 1);
		}
	}

//---------------------------check The Best-----------------------------------------------	
	static void checkBestonDic(String toCheck, int nextChar) {
	
		if (str.length() == nextChar) {
			out += toCheck + "";
			dic.put(toCheck, index);
			str = str.substring(nextChar);
			System.out.println(out);
			System.out.println(dic);
			return;
		}
	
		if (!dic.containsKey(toCheck + "" + str.charAt(nextChar))) {

			if (toCheck.length() == 1) {
				int ascii = toCheck.charAt(0);
				if (ascii < 256) {

					out += ascii + " ";
					dic.put(ascii + "" + str.charAt(nextChar), index);
					index++;
					str = str.substring(nextChar);
					try {
						objectoutputstream.writeByte(ascii);//write byte
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				System.out.println(out);
				System.out.println(dic);
					return;
				}
			}
			System.out.println("int "+nextChar);
//			System.out.println(str);
			out += toCheck + " ";
			dic.put(toCheck + str.charAt(nextChar), index);
			index++;
			str = str.substring(nextChar);
			try {
				objectoutputstream.writeChars(toCheck);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//write byte
			System.out.println(out);
			System.out.println(dic);
			return;
		} else {

			String toCheckAgain = toCheck + str.charAt(nextChar);

			checkBestonDic(dic.get(toCheckAgain) + "", nextChar + 1);
		}
	}
//---------------------------------convertStringToBinary------------------------------------------------
	   public static String convertStringToBinary(String input) {

	        StringBuilder result = new StringBuilder();
	        char[] chars = input.toCharArray();
	        for (char aChar : chars) {
	            result.append(
	                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
	                            .replaceAll(" ", "0")                         // zero pads
	            );
	        }
	        
	        
	        return result.toString();

	    }
	    static String prettyBinary(String binary, int blockSize, String separator) {

	        List<String> result = new ArrayList<>();
	        int index = 0;
	        while (index < binary.length()) {
	            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
	            index += blockSize;
	        }

	        return result.stream().collect(Collectors.joining(separator));
	    }
//---------------------------------------------------------------------------------------------	    
	    static char convertStringToChar(String Codes) {
		
			
			char Return;

//			for (int i = 0; i < Codes.length(); i++) {
//				tempBuilder.append(Codes.get(bytes[i]));
//			}
//
//			if (tempBuilder.length() % 8 == 0) {
//				length = (tempBuilder.length() / 8);
//			} else {
//				length = (tempBuilder.length() / 8) + 1;
//			}
//
//			Return = new byte[length];
//			for (int i = 0; i < Codes.length(); i = (i + 8)) {
//
//				if (Codes.length() < i + 8) {
//					temp = Codes.substring(i);
//				} else {
//					temp = Codes.substring(i, (i + 8));
				//}
				Return = (char) Integer.parseInt(Codes, 2);
				//index++;

			//}
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

	        //System.out.println("[DEBUG] Reading from file.");
	        // Read N amount of bytes from file
	        for (int i = 0; i < READ_WRITE_CYCLES; i++) {
	            try {
	                currentByte = bitInputStream.readBits(8);

	                if(currentByte == EOF)
	                    break;

	                String nonFormattedByte = Integer.toBinaryString(currentByte);

	                for(int j = 0; j < (8 - nonFormattedByte.length()); j++){
	                    formattedByte = formattedByte.concat(ZERO_CHAR);
	                }

	                formattedByte = formattedByte.concat(nonFormattedByte);

	                //System.out.printf("[DEBUG] Unformatted = \"%s\", Formatted = \"%s\".\n", nonFormattedByte, formattedByte);

	                parsedBytes = parsedBytes.concat(formattedByte);

	                formattedByte = "";

	            } catch (IOException e) {
	                System.out.println("[ERROR] Cannot read file.");
	                Thread.currentThread().interrupt();
	            }

	        }

	        //System.out.printf("[DEBUG] Entire formatted string \"%s\".\n",parsedBytes);
	        System.out.println(parsedBytes);
	        return parsedBytes;
	    }


//-------------------------------------------------------------------------------------------
	public void Decompress() {

	}

}
