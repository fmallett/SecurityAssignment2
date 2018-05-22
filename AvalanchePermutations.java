import java.util.ArrayList;

/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
 *@Date 19/05/2019
 */
//The purpose of this class is to permute the plaintext and key by one bit in each round
//starting at bit 0, then bit 1, bit 2,  ... bit 63 (for plaintext) and 55 for key
//to explore the avalanche effect at each round
public class AvalanchePermutations {

	public ArrayList<String> permutePByOneBit(String plaintextP) {
		StringBuilder tempPermutedP = new StringBuilder();
		ArrayList<String> listOfPermutations = new ArrayList<String>();

		//This will loop through the length of the plaintext (64 bits assumed) (56 for key)
		//It permutes one bit each round and stores the result (Pi) each time in an arraylist
		//E.g. round 1 the first but will be permuted
		//round 2 only the 2nd bit will be permuted 
		//if the plaintext input was : 1111
		//the output will be:
		// 0111
		// 1011
		// 1101
		// 1110
		for (int i = 0; i < plaintextP.length(); i++) {

			if (plaintextP.charAt(i) == '0') {
				tempPermutedP.append(plaintextP.substring(0, i) +
						"1" + plaintextP.substring(i+1, plaintextP.length()));
			}
			else {
				tempPermutedP.append(plaintextP.substring(0, i) + 
						"0" + plaintextP.substring(i+1, plaintextP.length()));
			}

			String permutedP = tempPermutedP.toString();
			listOfPermutations.add(permutedP);
			//clears the string builder to start fresh for the next round
			tempPermutedP.setLength(0);
		}
		return listOfPermutations;
	}
}
