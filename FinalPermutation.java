
/**
 *@Name : Fiona Mallett
 *@Course : COMP3260
 *@StudentNumber : 3289339
 *@Date :
 *
 *Class : FinalPermutation
 *Purpose : applies the Final Permutation defined for DES on a String
 * takes a 64 bit input and outputs 64 bits
 */


public class FinalPermutation {

	private int[] inverseIP = {
			40, 8, 48, 16, 56, 24, 64, 32,
			39, 7, 47, 15, 55, 23, 63, 31,
			38, 6, 46, 14, 54, 22, 62, 30,
			37, 5, 45, 13, 53, 21, 61, 29,
			36, 4, 44, 12, 52, 20, 60, 28,
			35, 3, 43, 11, 51, 19, 59, 27,
			34, 2, 42, 10, 50, 18, 58, 26,
			33, 1, 41,  9, 49, 17, 57, 25
	};


	/**
	 *Method : performFinalPermutation
	 *Parameters : String plainTextInput ~ bit string to Permeated
	 *Return type: String outPut ~ returns the Permeated bit string
	 *Description: builds the permeated bit string
	 */
	public String performFinalPermutation(String plainTextInput) {
		String output = "";
		//each of the 64 values in the final permutation array will be used as an index.
		//This value in the plaintext at each index will be added to the new string (output) 
		//to form the final permuted data
		for (int i = 0; i < inverseIP.length; i++) {
			output += plainTextInput.charAt(inverseIP[i]-1);
		}
		return output;
	}
}
