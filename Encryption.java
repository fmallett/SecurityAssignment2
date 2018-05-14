import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
 */

public class Encryption{


	public Encryption(String plainText, String key) {
		super();
		this.plainText = plainText;
		this.key = key;

	}

	//Read file for input and key
	private static String plainText;
	private static String key;
	private SBox SBox = new SBox();
	private InitialPermutation initialPermutation = new InitialPermutation();


	public void Encrypt(String plainText, String key) throws Exception { 
		
		//Initial permutation
		System.out.println("Before  permuation: " + plainText);
		plainText = initialPermutation.performInitialPermutation(plainText);

		System.out.println("Initial permuation: " + plainText);
		
		//Split input into left and right
		String Li = "";
		String Ri = "";
		int counter = 0;
		for (int i = 0; i < plainText.length(); i++) {
			//if input file has whitespace, ignore it
			if(plainText.charAt(i) != ' ' && counter <= 31) {
				Li += plainText.charAt(i);
				counter++;
				if(counter == 32) {
					i++;
				}
			}
			if (plainText.charAt(i) != ' ' && Li.length() > 31 && counter < 64) {
				Ri += plainText.charAt(i);
				counter++;
			}
			
		}
		//Printing for debugging purposes
		System.out.println("Li : " + Li);
		System.out.println("Ri : " + Ri);
		
		
		
		//Expansion/permutation 
		
		
		//XOR with key
		Ri = XOR(Ri, key); //just testing with RI this needs to change to the result from permutation not yet implemented above
		
		//S-Box
		Ri = useSBox(Ri);
		
		//Permutation
		
		//XOR

	}

	private String XOR(String bitsToXOR, String key) {
		String temp = "";
		//Key is 56 bits 
		if(key.length() != 56) {
			System.err.println("Key length should be 56 bits in input file");
		}
		
		//We will store the result of the XOR operating in result
		String result = "";

		for (int i = 0; i < key.length(); i++) {
			//Padding zero bits to the left so that we XOR with 2 56 bits
			while(bitsToXOR.length() < key.length()) {
				bitsToXOR = "0"  + bitsToXOR;
			}			

			//XOR function
			//If the key and plaintext are the same value, the XOR result will be zero
			if(key.charAt(i) == bitsToXOR.charAt(i)) {
				result +=  "0";
			}
			//When the key and plaintext values are different, the result will be one
			else  
			{
				result +="1";
			}
		}

		System.out.println("--------- XOR Operation ---------");
		System.out.println(key +  "     key");
		System.out.println(bitsToXOR + "     input");
		System.out.println(result + "     XOR Result");
		System.out.println("--------- XOR Finished ---------");

		//We want a result with 48 bits
		//As we XORd with 56 bit key, the result will be 56 bits
		//So we will keep XORing with the key until we have 8 leading zeros (48 bit result)
		for (int i = 0; i < 8; i++) {
			if(result.charAt(i) != '0'){
				return XOR(result, key);
			}
		}
		
		//removing leading 8 zeros
		for (int j = 8; j < 56; j++) {
	     temp += result.charAt(j);
		}
		return temp;
	}
	
	private String useSBox(String R1beforeSBox) {
		//S-box takes 48 bit input and returns 32 bit output
		//result will store the values returned by the s-box
				StringBuilder result = new StringBuilder();
				
				//Split string into bits of 6 and pass these values into each s-box
				//the numbers 1 -8 represent s-box1, s-box2, s-box3 .... s-box8
				result.append(SBox.getSboxValue(1, R1beforeSBox.substring(0, 6)));
				result.append(SBox.getSboxValue(2, R1beforeSBox.substring(6, 12)));
				result.append(SBox.getSboxValue(3, R1beforeSBox.substring(12, 18)));
				result.append(SBox.getSboxValue(4, R1beforeSBox.substring(18, 24)));
				result.append(SBox.getSboxValue(5, R1beforeSBox.substring(24, 30)));
				result.append(SBox.getSboxValue(6, R1beforeSBox.substring(30, 36)));
				result.append(SBox.getSboxValue(7, R1beforeSBox.substring(36, 42)));
				result.append(SBox.getSboxValue(8, R1beforeSBox.substring(42, 48)));

				System.out.println("SBox result " + result);
				return result.toString();
	}


	private static void writeToFile(byte[] ciphertext) throws Exception {
		StringBuffer outputBuffer;
		outputBuffer = new StringBuffer();

		outputBuffer.append("Plaintext P: " + plainText);
		outputBuffer.append(System.getProperty("line.separator"));

		outputBuffer.append("Key K: " + key);
		outputBuffer.append(System.getProperty("line.separator"));

		outputBuffer.append("Ciphertext C: " + ciphertext);
		outputBuffer.append(System.getProperty("line.separator"));

		printToFile(outputBuffer);
	}	

	public static void printToFile(StringBuffer outputBuffer) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileWriter("output.txt"));
		printWriter.append(outputBuffer);
		printWriter.close();	
	}

//note we should do a check for the lengths of input. ie plaintext should be 64 and key 56
}