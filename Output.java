import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
 *@Date 21/05/2018
 */

public class Output {


	private static StringBuffer outputBuffer = new StringBuffer();


	//Used for encryption
	public void writeToFile(String plainText, String key, String cipherText, String outputFileName,
			ArrayList<ArrayList<Integer>> avalancheResultsPandPiUnderK, ArrayList<ArrayList<Integer>> avalancheResultsPunderKandKi) throws IOException {
		
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
		for (int j = 0; j < 16; j++) { //for each round (16)
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
		for (int j = 0; j < 16; j++) { //for each round (16)
			outputBuffer.append(String.format("%-11s %-8s %-8s %-8s %-8s", j,
					avalancheResultsPunderKandKi.get(0).get(j),
					avalancheResultsPunderKandKi.get(1).get(j),
					avalancheResultsPunderKandKi.get(2).get(j),
					avalancheResultsPunderKandKi.get(3).get(j)));
			outputBuffer.append(System.getProperty("line.separator"));	
		}



		printToFile(outputBuffer, outputFileName);
	}	

	public static void printToFile(StringBuffer outputBuffer, String outputFileName) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileWriter(outputFileName));
		printWriter.append(outputBuffer);
		printWriter.close();	
	}

}
