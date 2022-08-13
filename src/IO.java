/**
 * Final Project LZW: Javier GibergD
 */
import java.io.IOException;


public class IO {
	public static void main(String[] args) throws IOException {
		String srcIn = "LZWG compreses [8bits] Result.bin ";
		// Smiley.bmp Text.txt  OnTheOrigin.txt   Compressed file.bin LZWG compreses [8bits] Result.bin 
		String srcOut = "";

		LZW A = new LZW();

//			srcOut = "LZWG compreses ["+1*8+"bits] Result.bin";
//			A.Compress(srcIn, srcOut);
			

			srcOut = "LZWG decompress Result.txt";
			A.Decompress(srcIn, srcOut);

	}
}
