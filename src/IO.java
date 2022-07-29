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

	public static void main(String[] args) throws FileNotFoundException {
		String str = "";
		String srcIn = "Romeo and Juliet  Entire Play.txt";
		//new Smiley.bmp Romeo and Juliet  Entire Play.txt Compressed file.bin
		String srcOut = "Result.bin";
		
		// banabananamanaman
//		System.out.println(str.length()*3);
		LZW A = new LZW();
		try {
			A.Compress(srcIn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
