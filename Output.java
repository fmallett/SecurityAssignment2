import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
 *@Date 21/05/2018
 *
 * Class : Output
 * Purpose : output the plaintext key and cipher text along with the avalanche effect to be displayed in to a file

*/

public class Output {

	//Used for encryption

		private static StringBuffer outputBuffer = new StringBuffer();

	/**
	 * Method : writeToFile
 	 * Parameters : String plainText ~ the plain text data
	 *			  : String key ~ the key data
	 *			  : String cipherText ~ the chiper text data
	 *			  : String outputFileName ~ outputfile name
	 *			  : ArrayList<ArrayList<Integer>> avalancheResultsPandPiUnderK ~ 
	 *			  : ArrayList<ArrayList<Integer>> avalancheResultsPunderKandKi ~
	 * Return Type : void
 	 * Description : generate the output file's data
	*/
	public void writeToFile(String plainText, String key, String cipherText, String outputFileName,
							ArrayList<ArrayList<Integer>> avalancheResultsPandPiUnderK,
							ArrayList<ArrayList<Integer>> avalancheResultsPunderKandKi) throws IOException {
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

		outputBuffer.append("P and Pi under K: " );
		outputBuffer.append(System.getProperty("line.separator"));
		outputBuffer.append("Round      DES0     DES1     DES2     DES3");
		outputBuffer.append(System.getProperty("line.separator"));

		//Format avalanche data in a table (P and Pi under K)
		for (int j = 0; j < avalancheResultsPandPiUnderK.get(0).size(); j++) { //for each round (16)
			outputBuffer.append(String.format("%-11s %-8s %-8s %-8s %-8s", j,
					avalancheResultsPandPiUnderK.get(0).get(j),
					avalancheResultsPandPiUnderK.get(1).get(j),
					avalancheResultsPandPiUnderK.get(2).get(j),
					avalancheResultsPandPiUnderK.get(3).get(j)));
			outputBuffer.append(System.getProperty("line.separator"));
		}

		outputBuffer.append(System.getProperty("line.separator"));
		outputBuffer.append("P under K and Ki: " );
		outputBuffer.append(System.getProperty("line.separator"));
		outputBuffer.append("Round      DES0     DES1     DES2     DES3");
		outputBuffer.append(System.getProperty("line.separator"));

		//Format the data in a table for avalanche P under K and Ki
		for (int j = 0; j < avalancheResultsPunderKandKi.get(0).size(); j++) { //for each round (16)
			outputBuffer.append(String.format("%-11s %-8s %-8s %-8s %-8s", j,
					avalancheResultsPunderKandKi.get(0).get(j),
					avalancheResultsPunderKandKi.get(1).get(j),
					avalancheResultsPunderKandKi.get(2).get(j),
					avalancheResultsPunderKandKi.get(3).get(j)));
			outputBuffer.append(System.getProperty("line.separator"));
		}



		printToFile(outputBuffer, outputFileName);
	}
	/**
	 * Method : printToFile
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
