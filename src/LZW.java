import java.util.HashMap;

public class LZW {
	static int index=128;
	static String str="";
	static String current="";
	static String strNext="";
	static String out="";
	
	
	static HashMap< String,Integer> dic = new HashMap<>();
	
	
	
	
	public LZW (String str) {
		this.str=str;
		
	}
	
	
	
	
	public void Compress() {
		
//		dic.put("bi", 256);
//		dic.put("256g", 257);
//		dic.put("257b", 258);
//		dic.put("ba", 259);
//		dic.put("259l", 260);
//		dic.put("260o", 261);
//		dic.put("261o", 262);
//		dic.put("262n", 263);
//		index=256;
//		System.out.println(dic);
		while(true) {
			
			if(str.length()<=1) {
				int ascii = str.charAt(0);
				out+=ascii+" ";
				dic.put(str, index);
				System.out.println(out);
				System.out.println(dic);
				
				break;
			}
			int current = str.charAt(0);
			strNext = str.charAt(1)+"";
			checkBestonDic(current+"",1);
           			
//			if(str.length()==1) {
//				dic.put(str, index);
//				out+=str;
//				System.out.println(out);
//				System.out.println(dic);
//				break;
//			}
//			
//			current = str.charAt(0)+"";
//			strNext = str.charAt(1)+"";
//			
//			
//			
//			if(!dic.containsKey(current+strNext)) {
//				out+=current+" , ";
//				dic.put(current+strNext, index);
//				index++;
//				str=str.substring(1);
//			}
//			else {
//				String strNext2 = str.charAt(2)+"";
//				String newStr=dic.get(current+strNext)+strNext2;
//				if((!dic.containsKey(newStr))) {
//					out+=dic.get(current+strNext)+" , ";
//					dic.put(dic.get(current+strNext)+strNext2, index);
//					index++;
//					str=	str.substring(2);					
//				}else {
//					if(str.length()<=3) {
//						dic.put(dic.get(newStr)+"", index);
//						out+=dic.get(newStr);
//						System.out.println(out);
//						System.out.println(dic);
//						
//					}
//					
//					String strNext3 = str.charAt(3)+"";
//					out+=dic.get(newStr)+" , ";
//					dic.put(dic.get(newStr)+strNext3, index);
//					index++;
//					str=str.substring(3);
//				}
//			}
//			
//			
//			System.out.println(out);
//			System.out.println(dic);
//			
			
			
			
			
			
			
		}
		 System.out.println(out.length());
	}
	
	static void checkBestonDic(String toCheck ,int nextChar) {
		if(str.length()==nextChar){
			out+=toCheck+"";
			dic.put(toCheck, index);
			str=str.substring(nextChar);
			System.out.println(out);
			System.out.println(dic);
			return;
		}
		
		
		if(!dic.containsKey(toCheck+""+str.charAt(nextChar))) {
			System.out.println(toCheck+""+str.charAt(nextChar));
			if(toCheck.length()==1) {
			int ascii = toCheck.charAt(0);
			if(ascii<128) {
				System.out.println("the ascii "+ascii);
				out+=ascii+" ";
				dic.put(ascii+""+str.charAt(nextChar), index);
				index++;
				str=str.substring(nextChar);
				System.out.println(out);
				System.out.println(dic);
				return ;
			}
			}
			System.out.println("int "+nextChar);
			System.out.println(str);
			out+=toCheck+" ";
			dic.put(toCheck+str.charAt(nextChar), index);
			index++;
			str=str.substring(nextChar);
			System.out.println(out);
			System.out.println(dic);
			return ;
		}else {
			
			String toCheckAgain=toCheck+ str.charAt(nextChar);
			
			checkBestonDic( dic.get(toCheckAgain)+"" , nextChar+1);
		}
	}
	
	
	
//-------------------------------------------------------------------------------------------
	public void  Decompress() {
		
	}
	
	
}
