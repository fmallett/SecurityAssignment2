import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *@Name : Fiona Mallett
 *@Course : COMP3260
 *@StudentNumber : 3289339
 *@Date :
 */

public class Application {

	private static String plaintext, key;
	private static Encryption encrypt;
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		if(args.length > 0) {
			plaintext = getPlainText(args[0]);
			key = getKey(args[0]);


			System.out.println("Plaintext P: " + plaintext);
			System.out.println("Key K: " + key);
			encrypt = new Encryption (plaintext, key);
			encrypt.Encrypt(plaintext, key);
		
		}
		else {
			System.err.println("No input file to read, please provide it");	


			plaintext = getPlainText("C:/Users/user/workspace/DESS/src/input.txt");
			key = getKey("C:/Users/user/workspace/DESS/src/input.txt");

			System.out.println("Plaintext: " + plaintext);
			System.out.println("Key: " + key);
			encrypt = new Encryption (plaintext, key);
			encrypt.Encrypt(plaintext, key);
		}
	}



	public static String getPlainText(String args) throws IOException {
		//change this to args[0] -- this is just for debugging in Eclipse
		plaintext = Files.readAllLines(Paths.get(args)).get(0);
		return plaintext;
	}

	public static String getKey(String args) throws IOException {
		key = Files.readAllLines(Paths.get(args)).get(1);
		return key;
	}
}
