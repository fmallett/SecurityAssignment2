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



	public void Encrypt(String plainText, String key) throws Exception { 
		
		//Split input into left and right
		String L1 = "";
		String R1 = "";
		int counter = 0;
		for (int i = 0; i < plainText.length(); i++) {
			//if input file has whitespace, ignore it
			if(plainText.charAt(i) != ' ' && counter <= 31) {
				L1 += plainText.charAt(i);
				counter++;
				if(counter == 32) {
					i++;
				}
			}
			if (plainText.charAt(i) != ' ' && L1.length() > 31 && counter < 64) {
				R1 += plainText.charAt(i);
				counter++;
			}
			
		}
		//Printing for debugging purposes
		System.out.println("L1 : " + L1);
		System.out.println("R1 : " + R1);
		
		
		
		//Expansion/permutation 
		
		//XOR with key
		XOR(L1, key); //just testing with L1 this needs to change
		
		
		//S-Box
		
		//Permutation
		
		//XOR

	}

	private String XOR(String bitsToXOR, String key) {
		//Key is 56 bits 
		if(key.length() != 56) {
			System.err.println("Key length should be 56 bits in input file");
		}
		
		//We will store the result of the XOR operating in result
		String result = "";

		for (int i = 0; i < key.length(); i++) {
			//Padding zero bits to the left so that we XOR with 2 48bits
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

		return result;
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