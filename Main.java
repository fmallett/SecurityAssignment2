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
				ArrayList finalList = new ArrayList<String>();

				//for (int i = 0; i < plainTextI.size(); i++) {


				//Encrypt P1 under K
				PICiphersDES0 = encryption.encrypt(plainTextI.get(0), key, 0, outPutFileName);
				PICiphersDES1 = encryption.encrypt(plainTextI.get(0), key, 1, outPutFileName);
				PICiphersDES2 = encryption.encrypt(plainTextI.get(0), key, 2, outPutFileName);
				PICiphersDES3 = encryption.encrypt(plainTextI.get(0), key, 3, outPutFileName);

				finalList.add(PICiphersDES0);
				//}
				
				//Compare ciphers produced at each round : for all des
				System.out.println("Round      DES0     DES1     DES2     DES3");
				for (int i = 0; i < plaintextCiphersDES0.size(); i++) {
					
				System.out.println(i+ "           " +
				simpleXORTOFindNumberOfBitDifferences(plaintextCiphersDES0.get(i), PICiphersDES0.get(i)) + "        " +
				simpleXORTOFindNumberOfBitDifferences(plaintextCiphersDES1.get(i), PICiphersDES1.get(i)) + "        " +
				simpleXORTOFindNumberOfBitDifferences(plaintextCiphersDES2.get(i), PICiphersDES2.get(i)) + "        " +
				simpleXORTOFindNumberOfBitDifferences(plaintextCiphersDES3.get(i), PICiphersDES3.get(i)));
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
