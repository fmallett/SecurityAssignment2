import java.io.*;
import java.util.ArrayList;


/**
 * @Name :           Nathan Davidson
 * @Student Number : C3187164
 * @Corse :          COMP3260
 * @Date :           12/5/18
 */

public class Main
{
	public static void main(String[] args)
	{
		/**args 0 = decryption or encryption denoted d or e (ingoreing case) been over ruled changed to reading first line of file for a 0 or a 1
          args 1 = input file name
          args 2 = output file name
		 */


		String input = "";

		char choice = ' ';
		String inputFileName = "inputFile.txt";
		String outPutFileName = "outputFile.txt";
		Encryption encryption;
		Input scanner;
		String plainText = "";
		String key = "";
		ArrayList<String> plaintextCiphersDES0 = new ArrayList<String>();
		ArrayList<String> plaintextCiphersDES1 = new ArrayList<String>();
		ArrayList<String> plaintextCiphersDES2 = new ArrayList<String>();
		ArrayList<String> plaintextCiphersDES3 = new ArrayList<String>();
		ArrayList<String> plainTextI = new ArrayList<String>();
		Avalanche avalanche = new Avalanche();
		ArrayList<String> PICiphersDES0 = new ArrayList<String>();
		ArrayList<String> PICiphersDES1 = new ArrayList<String>();
		ArrayList<String> PICiphersDES2 = new ArrayList<String>();
		ArrayList<String> PICiphersDES3 = new ArrayList<String>();
		ArrayList<ArrayList<String>> allPIciphersDES0 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> allPIciphersDES1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> allPIciphersDES2 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> allPIciphersDES3 = new ArrayList<ArrayList<String>>();
		int sum, average;

		try
		{
			if(args.length==0)
			{
				//Error
				System.out.println("No arguments provided, Please provide a" + "Input file name \n" + "and a Output file name");
			}
			else
			{
				/**
				 * testing code :
                     for (int i = 0; i < args.length ; i++) {
                     System.out.println(args[i]);
                     }
				 */
				/**
				 * old code:
                    input = args[0];
                    if(input.equalsIgnoreCase("e")) // encryption
                    {
                        choice=args[0];
                        System.out.println("im equal to e "+ args[0]);
                    }
                    else if(input.equalsIgnoreCase("d")) // decryption
                    {
                        choice=args[0];
                        System.out.println("im equal to d "+ args[0]);
                    }
                    else
                    {
                        //Error
                    }
				 */
				if(args.length>0) // they have provided a file name
				{
					inputFileName=args[0];
					//System.out.println("im equal to inputFileName "+ args[0]);
				}
				else
				{
					//Error cant assume inputfile name
				}

				if(args.length>1) // they have provided a file name
				{
					outPutFileName =args[1];
					System.out.println("im equal to outPutFileName "+ args[1]);
				}
				else
				{
					System.out.println("Default output file name is :"+outPutFileName);
				}

				InputStream FileInputStream = new FileInputStream(inputFileName);
				scanner = new Input(FileInputStream);
				plainText = scanner.getpText();
				key = scanner.getsKey();
				choice = scanner.getChoice();

				System.out.println(plainText + "\n" + key + " " + plainText.length() + " " + key.length());

			}
			if(choice == '0' )
			{
				//Run Encryption
				encryption = new Encryption(plainText,key);

				//Avalanche Under P
				plaintextCiphersDES0 =	encryption.encrypt(plainText, key, 0, outPutFileName);
				plaintextCiphersDES1 =  encryption.encrypt(plainText, key, 1, outPutFileName);
				plaintextCiphersDES2 =	encryption.encrypt(plainText, key, 2, outPutFileName);
				plaintextCiphersDES3 =	encryption.encrypt(plainText, key, 3, outPutFileName);

				//Create plaintextPI -- (64 different plaintexts differing by 1 bit from original plaintext)
				plainTextI = avalanche.permutePByOneBit(plainText);
				//				ArrayList finalList = new ArrayList<String>();

				for (int i = 0; i < plainTextI.size(); i++) {
					//Encrypt P1 under K
					PICiphersDES0 = encryption.encrypt(plainTextI.get(i), key, 0, outPutFileName);
					PICiphersDES1 = encryption.encrypt(plainTextI.get(i), key, 1, outPutFileName);
					PICiphersDES2 = encryption.encrypt(plainTextI.get(i), key, 2, outPutFileName);
					PICiphersDES3 = encryption.encrypt(plainTextI.get(i), key, 3, outPutFileName);


					//allPIciphersDES0 is an array list, containing 64 rows and 16 columns 
					//the first 'row' contains the plaintext altered by one bit, and each 'column' or index at this row 
					//is the result after each of the 16 rounds
					allPIciphersDES0.add(PICiphersDES0);
					allPIciphersDES1.add(PICiphersDES1);
					allPIciphersDES2.add(PICiphersDES2);
					allPIciphersDES3.add(PICiphersDES3);


				}



				int sumOfBitDifferences = 0;
				//Compare ciphers produced at each round : for all des
				System.out.println("Round      DES0     DES1     DES2     DES3");
				ArrayList<Integer> averages = new ArrayList<Integer>();
				for (int i = 0; i < plaintextCiphersDES0.size(); i++) {//for each of the 16 rounds
					for (int j = 0; j < allPIciphersDES0.size(); j++) {//for all 64 different plaintext variations


						sumOfBitDifferences += 
								simpleXORTOFindNumberOfBitDifferences(plaintextCiphersDES0.get(i), 
										allPIciphersDES0.get(j).get(i));
					}
					sum = sumOfBitDifferences;
					average = calculateAverage(sum, 16);
					//need to store every average in an array
					averages.add(average);

					//Reset these variables for the next round
					average = 0;
					sumOfBitDifferences =0;
					sum=0;
				}

				for (int i = 0; i < averages.size(); i++) {
					System.out.println(i + "          " + averages.get(i));
				}
			}






			else if (choice == '1')
			{
				//Run Decryption
			}
		}
		catch (Exception e)
		{
			System.out.println("Error with file handling, please ensure a valid data file was provided in the same location as the compiled java files");
			e.printStackTrace();
		}

	}

	public static int calculateAverage(int sum, int count) {

		return sum / count;
	}

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


}
