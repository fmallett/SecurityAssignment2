import java.io.*;
import java.util.ArrayList;


/**
 * @Name :           Nathan Davidson
 * @Student Number : C3187164
 * @Corse :          COMP3260
 * @Date :           12/5/18
 */

public class Application
{
	public static void main(String[] args)
	{
		/**args 0 = decryption or encryption denoted d or e (ignoring case) been over ruled changed to reading first line of file for a 0 or a 1
          args 1 = input file name
          args 2 = output file name
		 */
		String input = "";

		char choice = ' ';
		String inputFileName = "inputFile.txt";
		String outPutFileName = "outputFile.txt";
		Encryption encryption;
		Decryption decryption;
		Input scanner;
		String plainText = "";
		String key = "";
		Avalanche avalanche;
		Output output;

		try
		{
			if(args.length==0)
			{
				//Error
				System.out.println("No arguments provided, Please provide a" + "Input file name \n" + "and a Output file name");
			}
			else
			{
				if(args.length>0) // they have provided a file name
				{
					inputFileName=args[0];
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
			}
			if(choice == '0')
			{
				//Run Encryption
				encryption = new Encryption(plainText,key);
				encryption.encrypt(plainText, key, 0, outPutFileName);
				String cipherText = encryption.getCipherText();
				//Calculate Avalanche
				avalanche = new Avalanche(plainText, key, outPutFileName);
				avalanche.calculateAvalancheWhenPChanges();
				
				//Store avalanche results in arrayList variables
				ArrayList<ArrayList<Integer>> avalancheResultsPandPiUnderK = avalanche.getFinalAveragePandPiUnderK();
				ArrayList<ArrayList<Integer>> avalancheResultsPUnderKandKi =  avalanche.getFinalAveragePUnderKandKi();
			
				//Write to output class
				output = new Output();
				output.writeToFileEncryption(plainText, key, cipherText, outPutFileName, avalancheResultsPandPiUnderK, avalancheResultsPUnderKandKi);
			}
			
			else if (choice == '1')
			{
				//Run Decryption
				decryption = new Decryption(plainText,key,outPutFileName);
				//Write to output class
				output = new Output();
				output.writeToFileDecryption(decryption.getCipherText(), key, decryption.getPlainText(), outPutFileName);
			}
		}
		catch (Exception e)
		{
			System.out.println("Error with file handling, please ensure a valid data file was provided in the same location as the compiled java files");
			e.printStackTrace();
		}
	}
}
