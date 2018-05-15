
/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
 */

public class Permutation {

	/**
	 * Used at the end of function(F)
	 * It takes a 32 bit input and produces 32 bit output
	 */

	private int[] P =  {
			16,  7, 20, 21, 29, 12, 28, 17,
			1, 15, 23, 26, 5, 18, 31, 10,
			2,  8, 24, 14, 32, 27,  3,  9,
			19, 13, 30,  6, 22, 11,  4, 25
	};

	public String permutationFunctionP(String rightSide) {
		String output = "";
		//The result is a 32-bit output from a 32-bit input
		//the bits of the input block are permuted according to (P).
		for (int i = 0; i < P.length; i++) {
			output += rightSide.charAt(P[i]-1);
		}
		return output;
	}
}
