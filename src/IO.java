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
		//new Smiley.bmp 
	
		
		// banabananamanaman
//		System.out.println(str.length()*3);
		LZW A = new LZW(src);
		A.Compress();

	}

}
