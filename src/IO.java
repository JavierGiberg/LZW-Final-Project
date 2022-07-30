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
		String srcIn = "Text.txt";
		// Smiley.bmp Text.txt Compressed file.bin
		String srcOut = "";

		LZW A = new LZW();

			srcOut = "LZWG compreses ["+1*8+"bits] Result.bin";
			A.Compress(srcIn, srcOut);
//			
//			srcOut = "LZWG decompress ["+1*8+"bits] Result.txt";
//			A.Decompress(srcIn, srcOut);

	}
}
