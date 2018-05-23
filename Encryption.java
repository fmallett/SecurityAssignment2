import java.util.ArrayList;

/**
 *@Name : Fiona Mallett
 *@Course : COMP3260
 *@StudentNumber : 3289339
 *@Date : 4/05/2018
 *
 * Class : Encryption
 * Purpose : perform the encryption method DES, and 3 more variations defined in assignment specifications that removed different
 * components of des or replaced components with an inverse expansion table which is used to explore the avalanche effect
 */

public class Encryption{

	/**
	 * Constructor : Encryption
	 * Parameters : String plainText ~ the plainText data read by input
	 *            : String key ~ the key data read by input
	 * Description: initialises the class and variable data
	 */
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
	private InverseETable inverseETable = new InverseETable();
	private Permutation permutation = new Permutation(); 
	private	KeyGeneration keyGeneration ;
	private String prevRight = "";
	int number =0;

	
	//This is the main encryption method used.
	//It handels which version of DES to encrypt
	//It returns an ArrayList of Ciphers (produced at each of the 16 rounds of DES) for avalanche
	/**
	 * Method : encrypt
	 * Parameters : String plainText ~ the plainText data read by input
	 *            : String key ~ the key data read by input
	 *            : int DESVersion ~ represents which des version we are running 0-3
	 *            : String outputFileName ~ reference to the output file name for printing
	 * Return Type : ArrayList<String> ~ returns the list of ciphers using p (produced at each of the 16 rounds of DES)
	 * Description: This is the main encryption method handles which version of des is being performed DES0-3
	 * generated keys then applies the Sbox modifications and the final permutation updating the original rhs variable and also
	 * It returns an ArrayList<string> of Ciphers (produced at each of the 16 rounds of DES) for avalanche
	 */
	public ArrayList<String> encrypt(String plainText, String key, int DESVersion, String outputFileName) throws Exception { 

		keyGeneration = new KeyGeneration(key);

		//Creating a list of ciphers (produced at each round) to be used in avalanche later
		ArrayList<String> listOfCiphersUsingP = new ArrayList<String>();
		//Initial permutation
		cipherText = initialPermutation.performInitialPermutation(plainText);

		//Split input (64 bits) into two 32 bit strings
		splitInput();
		
	
		//--------------------Start of round function------------------------
		for (int round = 0; round < 16; round++) {

			//Avalanche Effect
			//saving ciphertext at each round for original plaintextP
			listOfCiphersUsingP.add(left+right);

			prevRight = right;
			
			//Depending on which DES version is supplied, a different roundFunction 
			//will be executed
			if(DESVersion == 0){
				roundFunctionDES0(right, round);
			}
			else if(DESVersion == 1){
				roundFunctionDES1(right, round);
			}
			else if(DESVersion == 2){
				roundFunctionDES2(right, round);
			}
			else if(DESVersion == 3){
				roundFunctionDES3(right, round);
			}
			//XOR Left with Right, the result is the new Right
			tempRight = XOR32Bits(left, right);
		
			left = prevRight;
			right = tempRight;
		}

		//adding the 16th round to avalanche effect
		listOfCiphersUsingP.add(left+right);
		
		//32 bit swap
		cipherText = right + left;
		
		//reset left and right so we can start fresh for DES1, DES2 DES3 ..
		left = "";
		right = "";

		//Final permutation (Inverse IP)
		cipherText = finalPermutation.performFinalPermutation(cipherText);
		//return list of ciphers (produced at each round) to use in avalanche effect
		return listOfCiphersUsingP;
	}

	/**
	 * Method : roundFunctionDES0
	 * Parameters : String tempRi ~ the rhs of the bit string (32 bits)
	 *            : int roundNumber ~ reference to the current round
	 * Return Type : void
	 * Description: apply the round function of des which applies the expansion permutation (EP), xor's the EP with the
	 * generated keys then applies the Sbox modifications and the final permutation updating the original rhs variable
	 */
	private void roundFunctionDES0(String tempRi, int roundNumber) {
		//Expansion/permutation
		tempRi = expansionPermutation.expand(tempRi);

		//XOR with key
		tempRi = XOR(tempRi, keyGeneration.getFinalKeyList().get(roundNumber)); //subkey[i] ----- 16 rounds

		//S-Box
		tempRi = useSBox(tempRi);

		//Permutation function(P)
		right = permutation.permutationFunctionP(tempRi);
	}

	/**
	 * Method : roundFunctionDES1
	 * Parameters : String tempRi ~ the rhs of the bit string (32 bits)
	 *            : int roundNumber ~ reference to the current round
	 * Return Type : void
	 * Description: apply the round function of des1 which applies the expansion permutation (EP), xor's the EP with the
	 * generated keys then applies the Sbox modifications NOT doing the final permutation, updating the original rhs variable
	 */
	private void roundFunctionDES1(String tempRi, int roundNumber) {
		//PERMUTATION MISSING FROM DES1
		//Expansion/permutation 
		tempRi = expansionPermutation.expand(tempRi);
		//XOR with key
		tempRi = XOR(tempRi, keyGeneration.getFinalKeyList().get(roundNumber)); //subkey[i] ----- 16 rounds
		//S-Box
		right = useSBox(tempRi);
	}

	/**
	 * Method : roundFunctionDES2
	 * Parameters : String tempRi ~ the rhs of the bit string (32 bits)
	 *            : int roundNumber ~ reference to the current round
	 * Return Type : void
	 * Description: apply the round function of des1 which applies the expansion permutation (EP), xor's the EP with the
	 * generated keys then applies the "inverse expansion table" and the final permutation, updating the original rhs variable
	 */
	private void roundFunctionDES2(String tempRi, int roundNumber) {
		//S-box is replaced with the inverse of
		//the expansion permutation (E-table)

		//Expansion/permutation 
		tempRi = expansionPermutation.expand(tempRi);
		//XOR with key
		tempRi = XOR(tempRi, keyGeneration.getFinalKeyList().get(roundNumber)); 

		//S-Box replaced here for DES2
		tempRi = inverseETable.performInversePermutation(tempRi);

		//Permutation function(P)
		right = permutation.permutationFunctionP(tempRi);
	}
	/**
	 * Method : roundFunctionDES3
	 * Parameters : String tempRi ~ the rhs of the bit string (32 bits)
	 *            : int roundNumber ~ reference to the current round
	 * Return Type : void
	 * Description: apply the round function of des1 which applies the expansion permutation (EP), xor's the EP with the
	 * generated keys then applies the "inverse expansion table" and NOT doing the final permutation, updating the original rhs variable
	 */
	private void roundFunctionDES3(String tempRi, int roundNumber) {
		//The permutation P is missing 
		//S-box is replaced with the inverse of
		//the expansion permutation (E-table)

		//Expansion/permutation 
		tempRi = expansionPermutation.expand(tempRi);
		//XOR with key
		tempRi = XOR(tempRi, keyGeneration.getFinalKeyList().get(roundNumber)); 

		//S-Box replaced here for DES3
		tempRi = inverseETable.performInversePermutation(tempRi);

		//final permutation missing
	}

	/**
	 * Method : splitInput
	 * Parameters : null
	 * Return Type : void
	 * Description: basic whitespace error checking of the input plaintext which
	 * then performs a split to break the plaintext into two 32 bit halves
	 */
	private void splitInput() {
		//Split input into left 32 bits and right 32 bits
		int counter = 0;
		for (int i = 0; i < cipherText.length(); i++) {
			//if input file has whitespace, ignore it
			if(plainText.charAt(i) != ' ' && counter <= 31) {
				//create the left substring
				left += cipherText.charAt(i);
				counter++;
				if(counter == 32) {
					i++;
				}
			}
			//create the right substring
			if (cipherText.charAt(i) != ' ' && left.length() > 31 && counter < 64) {
				right += cipherText.charAt(i);
				counter++;
			}					
		}		
	}

	/**
	 * Method : XOR32Bits
	 * Parameters : String left ~ data to be xored
	 *            : String right ~ data to be xored
	 * Return Type : String result ~ result of the xor
	 * Description: xor 32bit length data and return the xor result
	 */
	private String XOR32Bits(String left, String right) {
		//check the left and right bits are 32 bits long
		if (left.length() != 32 && right.length() != 32){
			//Carry on with the operation anyway but print an error message to inform the user
			System.err.println("Method XOR32Bits: Left or right Strings are not 32 bits long");
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
		return result;
	}


	/**
	 * Method : XOR
	 * Parameters : String bitsToXOR ~ bits that xor is being applied to
	 *            : String key ~ the key to xor the bits with
	 * Return Type : String result ~ returns the result of the xor
	 * Description: apply the xor operator on the "bitsToXOR" with the key and return the result
	 */
	private String XOR(String bitsToXOR, String key) {

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

	/**
	 * Method : useSBox
	 * Parameters : String R1beforeSBox ~ a bit string that is ready to be sboxed that is it IP, Expansion and XOR with key
	 * Return type : String result ~ returns the new values calculated from the sbox (a new bit string)
	 * Description: applies the sbox modifications to the bit data for des
	 */
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

	/**
	 * Method : getCipherText
	 * Parameters : null
	 * Return type : String cipherText ~ the cipherText data created by the encryption class
	 * Description: returns the cipher text
	 */
	public static String getCipherText() {
		return cipherText;
	}

	/**
	 * Method : setCipherText
	 * Parameters : String cipherText ~ set the cipherText equal to the parameter
	 * Return type : void
	 * Description: updates the cipherText variable
	 */
	public static void setCipherText(String cipherText) {
		Encryption.cipherText = cipherText;
	}
}