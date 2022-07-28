
public class IO {

	public static void main(String[] args) {
		String str = "banabananamanaman";
		// banabananamanaman
		System.out.println(str.length()*3);
		LZW A = new LZW(str);
		A.Compress();

	}

}
