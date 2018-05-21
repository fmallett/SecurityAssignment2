import java.util.ArrayList;

/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
 *@Date 19/05/2019
 */
//The purpose of this class is to permute the plaintext and key by one bit each 
//to explore the avalanche effect at each round
public class AvalanchePermutations {

	public String permutePByFirstBit(String plaintextP) {
		StringBuilder tempPermutedP = new StringBuilder();

		if (plaintextP.charAt(0) == '0') {
			//Change the first zero to one, then append the rest of the string
			tempPermutedP.append("1" + plaintextP.substring(1, plaintextP.length()));
		}
		else {
			// if the first bit is a one, then change it to a zero and append the rest of the string
			tempPermutedP.append("0" + plaintextP.substring(1, plaintextP.length()));
		}

		String permutedP = tempPermutedP.toString();
		return permutedP;
	}

	public ArrayList<String> permutePByOneBit(String plaintextP) {
		StringBuilder tempPermutedP = new StringBuilder();
		ArrayList<String> listOfPermutations = new ArrayList<String>();

		//This will loop through the length of the plaintext (64 bits assumed)
		//It permutes one bit each round and store the result (Pi) each time in an arraylist
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
