import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
 *@Date 21/05/2018
 *
 * Class : Output
 * Purpose :

*/

public class Output {
	

	private static StringBuffer outputBuffer = new StringBuffer();

	/**
	 * Method : writeToFile
 	 * Parameters : String plainText ~ the plain text data
	 *			  : String key ~ the key data
	 *			  : String cipherText ~ the chiper text data
	 *			  : String outputFileName ~ outputfile name
	 * Return Type : void
 	 * Description : generate the output file's data
	*/
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
	/**
	 * Constructor : printToFile
	 * Parameters  : StringBuffer outputBuffer ~ output file buffer writer
	 *			   : String outputFileName ~ the name of the output file6
	 * Description : writes the data to output file and closes the file
	 */
	public static void printToFile(StringBuffer outputBuffer, String outputFileName) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileWriter(outputFileName));
		printWriter.append(outputBuffer); // append the output data to the print writer
		printWriter.close();	// close file
	}

}
