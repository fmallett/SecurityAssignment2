
/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
 *@Date 7/05/2018
 *
 * Class : InverseETable
 * Purpose : Defines the InverseETable which is defined in the assignment spec to be
 * used with DES2 and DES3
 * takes a 32 bit input and provides a 32 bit output
*/

public class InverseETable {
	
	//This class is used for DES2 and DES3
	//It permutes 32bit input to a 32 bit output using the inverse_E_table
	
	private int[] inverse_E_table = {
			2, 3, 4, 5,
			8, 9, 10, 11,
			14, 15, 16, 17,
			20, 21, 22, 23,
			26, 27, 28, 29,
			32, 33, 34, 35,
			38, 39, 40, 41,
			44, 45, 46, 47
	};

	/**
	 *Method : performInversePermutation
	 *Parameters : String plainTextInput ~ bit string to be permuted
	 *Return type: String outPut ~ returns the permuted bit string
	 *Description: builds the permuted bit string
	 */
	public String performInversePermutation(String plainTextInput) {
		String output = "";

		for (int i = 0; i < inverse_E_table.length; i++) {
			output += plainTextInput.charAt(inverse_E_table[i]-1);
		}
		return output;
	}
	
}
