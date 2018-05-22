import java.util.ArrayList;

/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
 *@Date 21/05/2018
 */

//This class calculates the average avalanche effect at each of the 16 rounds
//and each version of DES (DES0, DES1, DES2, DES3) compared with the original plaintext

public class Avalanche {
	public Avalanche(String plainText, String key, String outputFile) {
		this.key = key;
		this.plainText = plainText;
		this.outputFile = outputFile;
	}

	String key, plainText, outputFile;
	private ArrayList<ArrayList<Integer>> finalAveragePandPiUnderK = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> finalAveragePUnderKandKi = new ArrayList<ArrayList<Integer>>();
	

	public ArrayList<ArrayList<Integer>> calculateAvalancheWhenPChanges() throws Exception {
		Encryption encryption = new Encryption(plainText, key);

		ArrayList<String> plaintextCiphersDES0 = new ArrayList<String>();
		ArrayList<String> plaintextCiphersDES1 = new ArrayList<String>();
		ArrayList<String> plaintextCiphersDES2 = new ArrayList<String>();
		ArrayList<String> plaintextCiphersDES3 = new ArrayList<String>();
		ArrayList<String> plainTextI = new ArrayList<String>();
		AvalanchePermutations avalanche = new AvalanchePermutations();
		ArrayList<String> PICiphersDES0 = new ArrayList<String>();
		ArrayList<String> PICiphersDES1 = new ArrayList<String>();
		ArrayList<String> PICiphersDES2 = new ArrayList<String>();
		ArrayList<String> PICiphersDES3 = new ArrayList<String>();
		ArrayList<ArrayList<String>> allPIciphersDES0 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> allPIciphersDES1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> allPIciphersDES2 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> allPIciphersDES3 = new ArrayList<ArrayList<String>>();
		int averageDES0 = 0, averageDES1 = 0, averageDES2 = 0, averageDES3 = 0;

		ArrayList<Integer> allAverages0 = new ArrayList<Integer>();
		ArrayList<Integer> allAverages1 = new ArrayList<Integer>();
		ArrayList<Integer> allAverages2 = new ArrayList<Integer>();
		ArrayList<Integer> allAverages3 = new ArrayList<Integer>();

		//Avalanche Under P (plaintext) for each version of DES (DES0, DES1 ..)
		plaintextCiphersDES0 =	encryption.encrypt(plainText, key, 0, outputFile);
		plaintextCiphersDES1 =  encryption.encrypt(plainText, key, 1, outputFile);
		plaintextCiphersDES2 =	encryption.encrypt(plainText, key, 2, outputFile);
		plaintextCiphersDES3 =	encryption.encrypt(plainText, key, 3, outputFile);

		//Create plaintextPI -- (64 different plaintexts differing by 1 bit from original plaintext)
		plainTextI = avalanche.permutePByOneBit(plainText);

		for (int i = 0; i < plainTextI.size(); i++) {
			//Encrypt Pi under K
			PICiphersDES0 = encryption.encrypt(plainTextI.get(i), key, 0, outputFile);
			PICiphersDES1 = encryption.encrypt(plainTextI.get(i), key, 1, outputFile);
			PICiphersDES2 = encryption.encrypt(plainTextI.get(i), key, 2, outputFile);
			PICiphersDES3 = encryption.encrypt(plainTextI.get(i), key, 3, outputFile);

			//allPIciphersDES0 is an array list, containing 64 rows and 16 columns 
			//the first 'row' contains the plaintext altered by one bit, and each 'column' or index at this row 
			//is the result after each of the 16 rounds
			allPIciphersDES0.add(PICiphersDES0);
			allPIciphersDES1.add(PICiphersDES1);
			allPIciphersDES2.add(PICiphersDES2);
			allPIciphersDES3.add(PICiphersDES3);
		}

		//variables used to calculate average number of bit changes
		int sumOfBitDifferencesDES0 = 0, sumOfBitDifferencesDES1 = 0, 
				sumOfBitDifferencesDES2 = 0, sumOfBitDifferencesDES3 = 0;

		//Compare ciphers produced at each round : for all des	
		for (int i = 0; i < plaintextCiphersDES0.size(); i++) {//for each of the 16 rounds
			for (int j = 0; j < allPIciphersDES0.size(); j++) {//for all 64 different plaintext variations

				//store the sum of how many bits differ 
				//We compare plaintext at round 1 with the first Pi at round 1, 
				//then the second Pi at round 1 ..up until all 64Pi
				//The result is the total number of differing bits at each round


				//First for DES0
				sumOfBitDifferencesDES0 += 
						simpleXORTOFindNumberOfBitDifferences(plaintextCiphersDES0.get(i), 
								allPIciphersDES0.get(j).get(i));

				//Repeated for each version of DES
				sumOfBitDifferencesDES1 += 
						simpleXORTOFindNumberOfBitDifferences(plaintextCiphersDES1.get(i), 
								allPIciphersDES1.get(j).get(i));

				sumOfBitDifferencesDES2 += 
						simpleXORTOFindNumberOfBitDifferences(plaintextCiphersDES2.get(i), 
								allPIciphersDES2.get(j).get(i));

				sumOfBitDifferencesDES3 += 
						simpleXORTOFindNumberOfBitDifferences(plaintextCiphersDES3.get(i), 
								allPIciphersDES3.get(j).get(i));
			}

			//Calculate the average number of bit differences at each round and each version of DES
			averageDES0 = calculateAverage(sumOfBitDifferencesDES0, 64);
			averageDES1 = calculateAverage(sumOfBitDifferencesDES1, 64);
			averageDES2 = calculateAverage(sumOfBitDifferencesDES2, 64);
			averageDES3 = calculateAverage(sumOfBitDifferencesDES3, 64);

			//need to store every average in an arrayList
			allAverages0.add(averageDES0);
			allAverages1.add(averageDES1);
			allAverages2.add(averageDES2);
			allAverages3.add(averageDES3);

			//Reset these variables for the next round
			averageDES0 = 0;
			averageDES1 = 0;
			averageDES2 = 0;
			averageDES3 = 0;
			sumOfBitDifferencesDES0 = 0;
			sumOfBitDifferencesDES1 = 0;
			sumOfBitDifferencesDES2 = 0;
			sumOfBitDifferencesDES3 = 0;
		}

		//After looping through all 64 plaintexts, 16 rounds and 4 versions of Des
		//Store all the averages in a 2d arraylist 
		finalAveragePandPiUnderK.add(allAverages0);
		finalAveragePandPiUnderK.add(allAverages1);
		finalAveragePandPiUnderK.add(allAverages2);
		finalAveragePandPiUnderK.add(allAverages3);

		calculateAvalancheWhenKeyChanges();
		return finalAveragePandPiUnderK;
	}

	public void calculateAvalancheWhenKeyChanges () throws Exception {
		Encryption encryption = new Encryption(plainText, key);

		ArrayList<String> originalKeyCiphersDES0 = new ArrayList<String>();
		ArrayList<String> originalKeyCiphersDES1 = new ArrayList<String>();
		ArrayList<String> originalKeyCiphersDES2 = new ArrayList<String>();
		ArrayList<String> originalKeyCiphersDES3 = new ArrayList<String>();
		ArrayList<String> keysI = new ArrayList<String>();
		AvalanchePermutations avalanche = new AvalanchePermutations();
		ArrayList<String> KiCiphersDES0 = new ArrayList<String>();
		ArrayList<String> KiCiphersDES1 = new ArrayList<String>();
		ArrayList<String> KiCiphersDES2 = new ArrayList<String>();
		ArrayList<String> KiCiphersDES3 = new ArrayList<String>();
		ArrayList<ArrayList<String>> allKiciphersDES0 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> allKiciphersDES1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> allKiciphersDES2 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> allKiciphersDES3 = new ArrayList<ArrayList<String>>();
		int averageDES0 = 0, averageDES1 = 0, averageDES2 = 0, averageDES3 = 0;

		ArrayList<Integer> allAverages0 = new ArrayList<Integer>();
		ArrayList<Integer> allAverages1 = new ArrayList<Integer>();
		ArrayList<Integer> allAverages2 = new ArrayList<Integer>();
		ArrayList<Integer> allAverages3 = new ArrayList<Integer>();

		//Avalanche Under P (plaintext) for each version of DES (DES0, DES1 ..)
		originalKeyCiphersDES0 =	encryption.encrypt(plainText, key, 0, outputFile);
		originalKeyCiphersDES1 =  encryption.encrypt(plainText, key, 1, outputFile);
		originalKeyCiphersDES2 =	encryption.encrypt(plainText, key, 2, outputFile);
		originalKeyCiphersDES3 =	encryption.encrypt(plainText, key, 3, outputFile);

		//Create Ki possible keys
		keysI = avalanche.permutePByOneBit(key);

		for (int i = 0; i < keysI.size(); i++) {
			//Encrypt P under Ki for each DES version
			KiCiphersDES0 = encryption.encrypt(plainText, keysI.get(i), 0, outputFile);
			KiCiphersDES1 = encryption.encrypt(plainText, keysI.get(i), 1, outputFile);
			KiCiphersDES2 = encryption.encrypt(plainText, keysI.get(i), 2, outputFile);
			KiCiphersDES3 = encryption.encrypt(plainText, keysI.get(i), 3, outputFile);

			//the result after each of the 16 rounds is stored in an arrayList of arraylists
			allKiciphersDES0.add(KiCiphersDES0);
			allKiciphersDES1.add(KiCiphersDES1);
			allKiciphersDES2.add(KiCiphersDES2);
			allKiciphersDES3.add(KiCiphersDES3);
		}

		//variables used to calculate average number of bit changes
		int sumOfBitDifferencesDES0 = 0, sumOfBitDifferencesDES1 = 0, 
				sumOfBitDifferencesDES2 = 0, sumOfBitDifferencesDES3 = 0;

		//Compare ciphers produced at each round : for all des	
		for (int i = 0; i < originalKeyCiphersDES0.size(); i++) {//for each of the 16 rounds
			for (int j = 0; j < allKiciphersDES0.size(); j++) {//for all 64 different plaintext variations

				//store the sum of how many bits differ 
				//We compare plaintext at round 0 with the first Key at round 0, 
				//then the second Ki at round 1 ..up until all 56Ki
				//The result is the total number of differing bits at each round


				//First for DES0
				sumOfBitDifferencesDES0 += 
						simpleXORTOFindNumberOfBitDifferences(originalKeyCiphersDES0.get(i), 
								allKiciphersDES0.get(j).get(i));

				//Repeated for each version of DES
				sumOfBitDifferencesDES1 += 
						simpleXORTOFindNumberOfBitDifferences(originalKeyCiphersDES1.get(i), 
								allKiciphersDES1.get(j).get(i));

				sumOfBitDifferencesDES2 += 
						simpleXORTOFindNumberOfBitDifferences(originalKeyCiphersDES2.get(i), 
								allKiciphersDES2.get(j).get(i));

				sumOfBitDifferencesDES3 += 
						simpleXORTOFindNumberOfBitDifferences(originalKeyCiphersDES3.get(i), 
								allKiciphersDES3.get(j).get(i));
			}

			//Calculate the average number of bit differences at each round and each version of DES
			averageDES0 = calculateAverage(sumOfBitDifferencesDES0, 56);
			averageDES1 = calculateAverage(sumOfBitDifferencesDES1, 56);
			averageDES2 = calculateAverage(sumOfBitDifferencesDES2, 56);
			averageDES3 = calculateAverage(sumOfBitDifferencesDES3, 56);

			//need to store every average in an arrayList
			allAverages0.add(averageDES0);
			allAverages1.add(averageDES1);
			allAverages2.add(averageDES2);
			allAverages3.add(averageDES3);

			//Reset these variables for the next round
			averageDES0 = 0;
			averageDES1 = 0;
			averageDES2 = 0;
			averageDES3 = 0;
			sumOfBitDifferencesDES0 = 0;
			sumOfBitDifferencesDES1 = 0;
			sumOfBitDifferencesDES2 = 0;
			sumOfBitDifferencesDES3 = 0;
		}

		//After looping through all 56 keys, 16 rounds and 4 versions of Des
		//Store all the averages in a 2d arraylist 
		finalAveragePUnderKandKi.add(allAverages0);
		finalAveragePUnderKandKi.add(allAverages1);
		finalAveragePUnderKandKi.add(allAverages2);
		finalAveragePUnderKandKi.add(allAverages3);
	}

	public static int calculateAverage(int sum, int count) {
		return sum / count;
	}

	//Calculates the total number of bits which differ in the 2 input variables
	//it uses xor but instead of returning the xor result, it returns the total which is used to 
	//calculate the average for avalanche
	public static int simpleXORTOFindNumberOfBitDifferences(String plainText, String plainTextI) {
		int bitDifferences = 0;
		for (int j = 0; j < plainText.length(); j++) {
			//When 2 input bits are not the same
			if(plainText.charAt(j) != plainTextI.charAt(j)) {
				//add up how many 1's are in the output from XOR operation
				//this will be the number of bit differences
				bitDifferences++;
			}
		}
		return bitDifferences;
	}

	
	//Getters used for printing
	public ArrayList<ArrayList<Integer>> getFinalAveragePandPiUnderK() {
		return finalAveragePandPiUnderK;
	}

	public ArrayList<ArrayList<Integer>> getFinalAveragePUnderKandKi() {
		return finalAveragePUnderKandKi;
	}
}
