import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class IO {

	public static void main(String[] args) {
		String str = "";
		String src = "new Smiley.bmp";
		try {
			FileInputStream fileinputstream = new FileInputStream(src);
			byte[] fileBytes = new byte[fileinputstream.available()];
			fileinputstream.read(fileBytes);
			
			OutputStream outputstream = new FileOutputStream("ResultLZW.bin");
			ObjectOutputStream objectoutputstream = new ObjectOutputStream(outputstream);
			
		
			objectoutputstream.close();
			outputstream.close();
			fileinputstream.close();
			for(int x=0;x<fileBytes.length;x++) {
				
				str+=" "+fileBytes[x];
			System.out.println(str);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		
		// banabananamanaman
//		System.out.println(str.length()*3);
		LZW A = new LZW(str);
		A.Compress();

	}

}
