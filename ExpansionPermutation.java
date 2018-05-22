
/**
 *@Name : Fiona Mallett
 *@Course : COMP3260
 *@StudentNumber : 3289339
 *@Date : 7/05/2018
 *
 * Class : ExpansionPermutation
 * Purpose : Defines the ExpansionPermutation to be used in DES it takes a 32 bit input and provides a 48 bit output
 */

public class ExpansionPermutation {

	//The E function lookup table
	private int[] E_Table = {
			32, 1,   2,   3,  4,  5,
			4, 5,   6,   7,  8,  9,
			8, 9,  10,  11, 12, 13,
			12, 13, 14,  15, 16, 17,
			16, 17, 18,  19, 20, 21,
			20, 21, 22,  23, 24, 25,
			24, 25, 26,  27, 28, 29,
			28, 29, 30,  31, 32,  1
	};


	private int[] inverse_E_Table = 
		{  		2, 3, 4, 5,
				8, 9, 10, 11,
				14, 15, 16, 17,
				20, 21, 22, 23,
				26, 27, 28, 29,
				32, 33, 34, 35,
				38, 39, 40, 41,
				44, 45, 46, 47

		};

	/**
	 *Method : expand
	 *Parameters : String rightSide ~ bit string to expand
	 *Return type: String outPut ~ returns the expanded 48 bits
	 *Description: builds the expanded 48 bit string and returns it
	 */
	public String expand(String rightSide) {

		//takes 32 bit input (from the right) to produce 48 bit output
		//The result will be 8 6-bit values (mapped using E_table)
		String output = "";

		for (int i = 0; i < E_Table.length; i++) {
			output += rightSide.charAt(E_Table[i]-1);
		}
		return output;  
	}
	/**
	 *Method : inverseE
	 *Parameters : String rightSide ~ bit string to reduce
	 *Return type: String outPut ~ returns the reduced 32 bits
	 *Description: builds the reduced 32 bit string and returns it
	 */
	public String inverseE(String rightSide) {

		//takes 48 bit input (after XOR function) to produce 32 bit output
		//The result is mapped using inverse_E_table
		String output = "";

		for (int i = 0; i < inverse_E_Table.length; i++) {
			output += rightSide.charAt(inverse_E_Table[i]-1);
		}
		return output;  
	}

}
