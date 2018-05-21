import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
 *@Date 21/05/2018
*/

public class Output {
	

	private static StringBuffer outputBuffer = new StringBuffer();
	
	
	public void writeToFile(String plainText, String key, String cipherText, String outputFileName) throws IOException {
		//		StringBuffer outputBuffer;
		//		outputBuffer = new StringBuffer();

		outputBuffer.append("Plaintext P: " + plainText);
		outputBuffer.append(System.getProperty("line.separator"));

		outputBuffer.append("Key K: " + key);
		outputBuffer.append(System.getProperty("line.separator"));

		outputBuffer.append("Ciphertext C: " + cipherText);
		outputBuffer.append(System.getProperty("line.separator"));

		outputBuffer.append("Avalanche: " );
		outputBuffer.append(System.getProperty("line.separator"));

		printToFile(outputBuffer, outputFileName);
	}	
	
	public static void printToFile(StringBuffer outputBuffer, String outputFileName) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileWriter(outputFileName));
		printWriter.append(outputBuffer);
		printWriter.close();	
	}

}
