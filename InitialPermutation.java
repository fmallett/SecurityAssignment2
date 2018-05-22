
/**
 *@Name : Fiona Mallett
 *@Course : COMP3260
 *@StudentNumber : 3289339
 *@Date : 9/05/2018
 *
 * Class : InitialPermutation
 * Purpose : Defines the Initial Permutation which is performed on the plain text before
 *   		 any modifications are done during des
 */

public class InitialPermutation {

	//This is used before the 16 rounds

	private int[] IP = {
			58, 50, 42, 34, 26, 18, 10, 2,
			60, 52, 44, 36, 28, 20, 12, 4,
			62, 54, 46, 38, 30, 22, 14, 6,
			64, 56, 48, 40, 32, 24, 16, 8,
			57, 49, 41, 33, 25, 17,  9, 1,
			59, 51, 43, 35, 27, 19, 11, 3,
			61, 53, 45 ,37, 29, 21, 13, 5,
			63, 55, 47, 39, 31, 23, 15, 7
	};
	/**
	 *Method : performInitialPermutation
	 *Parameters : String keyInput ~ bit string to be permuted
	 *Return type: String outPut ~ returns the permuted bit string
	 *Description: builds the permuted bit string
	 */
	public String performInitialPermutation(String plainTextInput) {
		String output = "";
		//each of the 64 values in the initial permutation array will be used as an index.
		//This value in the plaintext at each index will be added to the new string (output) 
		//to form the permuted data
		for (int i = 0; i < IP.length; i++) {
			output += plainTextInput.charAt(IP[i]-1);
		
		}
		return output;
	}
}