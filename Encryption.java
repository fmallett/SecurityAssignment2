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
 *@Name : Fiona Mallett
 *@Course : COMP3260
 *@StudentNumber : 3289339
 *@Date :
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
	private FinalPermutation finalPermutation = new FinalPermutation();
	private String Li = "";
	private String Ri = "";
	private ExpansionPermutation expansionPermutation = new ExpansionPermutation();
	private Permutation permutation = new Permutation(); 

	public void Encrypt(String plainText, String key) throws Exception { 

		//Initial permutation
		plainText = initialPermutation.performInitialPermutation(plainText);

		//--------------------Start of round function------------------------
		for (int round = 0; round < 16; round++) {
			roundFunction();
		//XOR Li with Ri, the result is the new Ri
		Ri = XOR32Bits(Li, Ri);
		//32 bit swap
		}

		//Final permutation
		plainText = finalPermutation.performFinalPermutation(plainText);

		writeToFile();
	}

	private void roundFunction() {
		//Split input (64 bits) into two 32 bit strings
		splitInput();

		//Expansion/permutation 
		Ri = expansionPermutation.expand(Ri);

		//XOR with key
		Ri = XOR(Ri, key); 

		//S-Box
		Ri = useSBox(Ri);

		//Permutation function(P)
		Ri = permutation.permutationFunctionP(Ri);


	}

	private void splitInput() {
		//Split input into left 32 bits and right 32 bits
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
	}

	private String XOR32Bits(String left, String right) {
		//check the left and right bits are 32 bits long
		if (left.length() != 32 && right.length() != 32){
			//Carry on with the operation anyway but print an error message to inform the user
			System.err.println("Left or right Strings are not 32 bits long");
		}

		String result = "";
		for (int i = 0; i < left.length(); i++) {
			//When the bits at the same position in the left and right Strings match, 
			//the XOR result is 0
			if(left.charAt(i) == right.charAt(i)) {
				result +=  "0";
			}
			//When the values at the same positions are different, the result will be one
			else {
				result +="1";
			}
		}
		if (result.length() != 32) {
			System.err.println("Result of XOR left 32 bits with right 32 bits is not 32 bits long");
		}
		return result;
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

		return result.toString();
	}


	private static void writeToFile() throws IOException {
		StringBuffer outputBuffer;
		outputBuffer = new StringBuffer();

		outputBuffer.append("Plaintext P: " + plainText);
		outputBuffer.append(System.getProperty("line.separator"));

		outputBuffer.append("Key K: " + key);
		outputBuffer.append(System.getProperty("line.separator"));

		//	outputBuffer.append("Ciphertext C: " + ciphertext);
		outputBuffer.append(System.getProperty("line.separator"));

		outputBuffer.append("Avalanche: " );
		outputBuffer.append(System.getProperty("line.separator"));

		printToFile(outputBuffer);
	}	

	public static void printToFile(StringBuffer outputBuffer) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileWriter("output.txt"));
		printWriter.append(outputBuffer);
		printWriter.close();	
	}

	//note we should do a check for the lengths of input. ie plaintext should be 64 and key 56
	//eg whitespace, no whitespace
}