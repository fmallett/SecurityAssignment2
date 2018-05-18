import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
	private static String cipherText;
	private SBox SBox = new SBox();
	private InitialPermutation initialPermutation = new InitialPermutation();
	private FinalPermutation finalPermutation = new FinalPermutation();
	private String left = "";
	private String right = "";
	private String tempRight = "";
	private ExpansionPermutation expansionPermutation = new ExpansionPermutation();
	private Permutation permutation = new Permutation(); 
	
	KeyGeneration keyGeneration ;//= new KeyGeneration(key);
	
	public void Encrypt(String plainText, String key) throws Exception { 

		keyGeneration = new KeyGeneration(key);
		
		//Initial permutation
		cipherText = initialPermutation.performInitialPermutation(plainText);

		//Split input (64 bits) into two 32 bit strings
		splitInput();

		//--------------------Start of round function------------------------
		for (int round = 0; round < 16; round++) {
			roundFunction(right, round);
			//XOR Left with Right, the result is the new Right
			tempRight = XOR32Bits(left, right);
			//32 bit swap
			left = right;
			right = tempRight;
		}
		cipherText = left+right;
		
		//Final permutation (Inverse IP)
		cipherText = finalPermutation.performFinalPermutation(cipherText);

		writeToFile();
	}

	private void roundFunction(String tempRi, int roundNumber) {

		//Expansion/permutation 
		tempRi = expansionPermutation.expand(tempRi);

		//XOR with key
		tempRi = XOR(tempRi, keyGeneration.getFinalKeyList().get(roundNumber)); //subkey[i] ----- 16 rounds

		//S-Box
		tempRi = useSBox(tempRi);

		//Permutation function(P)
		tempRi = permutation.permutationFunctionP(tempRi);
	}

	private void splitInput() {
		//Split input into left 32 bits and right 32 bits
		int counter = 0;
		for (int i = 0; i < plainText.length(); i++) {
			//if input file has whitespace, ignore it
			if(plainText.charAt(i) != ' ' && counter <= 31) {
				left += plainText.charAt(i);
				counter++;
				if(counter == 32) {
					i++;
				}
			}
			if (plainText.charAt(i) != ' ' && left.length() > 31 && counter < 64) {
				right += plainText.charAt(i);
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
		if(key.length() != 48) {
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
		return result;
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

		outputBuffer.append("Ciphertext C: " + cipherText);
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