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
	public static void main(String[] args) throws IOException {
		String srcIn = "Romeo and Juliet  Entire Play.txt";
		//Smiley.bmp Romeo and Juliet  Entire Play.txt Compressed file.bin
		String srcOut = "Result.bin";	
		
		LZW A = new LZW();
	
		A.Compress(srcIn,srcOut);
	}
}
