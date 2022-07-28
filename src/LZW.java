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
	static int index = 128;
	static String str = "";
	static String current = "";
	static String strNext = "";
	static String out = "";

	static HashMap<String, Integer> dic = new HashMap<>();

	public LZW(String str) {
		this.str = str;

	}

	public void Compress() {
		
		try {
			fileOutputStream = new FileOutputStream("Compressed file.bin");
			objectoutputstream = new ObjectOutputStream(fileOutputStream);
			fileOutputStream1 = new FileOutputStream("regular.bin");
			objectoutputstream1 = new ObjectOutputStream(fileOutputStream1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectoutputstream1.writeChars(str);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CompressStr();
//		String result =convertStringToBinary(out);
//		byte[] w = result.getBytes();
//		System.out.println(result);
//		System.out.println(prettyBinary(result, 8, " "));
		
		try {
		//	objectoutputstream.writeBytes(str);
			objectoutputstream.close();
			objectoutputstream1.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				if (ascii < 128) {

					out += ascii + "";
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
	

//-------------------------------------------------------------------------------------------
	public void Decompress() {

	}

}
