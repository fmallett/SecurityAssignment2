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
		System.out.println("Encryption");
		// create new key
		SecretKey secretKey = null;
		try {
			secretKey = KeyGenerator.getInstance("DES").generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// get base64 encoded version of the key
		String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

		//May remove these later - checking it all works
		//System.out.println("Encoded key " + encodedKey);
		//System.out.println("Secret key " + secretKey.getEncoded());

		//Create DES cipher
		Cipher cipher = Cipher.getInstance("DES");
		//Initialize cipher using des
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		//changing plaintext to ciphertext using the cipher created above
		byte[] ciphertext = cipher.doFinal(plainText.getBytes());
		
		System.out.println("Ciphertext C: " + ciphertext);
		
		//sealed object is a different implementation -- may delete and keep it simple
		SealedObject sealedObject = new SealedObject(plainText, cipher);
		//System.out.println("Sealed object " + sealedObject);
		
		writeToFile(ciphertext);
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


}