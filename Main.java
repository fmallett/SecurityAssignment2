import java.io.*;


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
         /**args 0 = decryption or encryption denoted d or e (ingoreing case)
          args 1 = input file name
          args 2 = output file name
          */


         String input = "";
         String choice = "";
         String inputFileName = "inputFile";
         String outPutFileName = "outPutFile";
         Encryption encrypt;
         Input scanner;
         String plainText = "";
         String key = "";
         try
         {
               if(args.length==0)
               {
                    //Error
                    System.out.println("no arguments provided please " +
                                        "provide either d or e for decryption or encryption followed by \n" +
                                        "input file name \n" + "output file name");
               }
               else
               {
                    /**
                     * testing code :
                     for (int i = 0; i < args.length ; i++) {
                     System.out.println(args[i]);
                     }
                     */
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

                    if(args.length>1) // they have provided a file name
                    {
                        inputFileName=args[1];
                        System.out.println("im equal to inputFileName "+ args[1]);
                    }
                    else
                    {
                        //Error cant assume inputfile name
                    }

                    if(args.length>2) // they have provided a file name
                    {
                        outPutFileName =args[2];
                        System.out.println("im equal to outPutFileName "+ args[2]);
                    }
                    else
                    {
                        System.out.println("default output file name is :"+outPutFileName);
                    }

                    InputStream FileInputStream = new FileInputStream(inputFileName);
                    scanner = new Input(FileInputStream);
                    plainText = scanner.getpText();
                    key = scanner.getsKey();

                   // System.out.println(plainText + "\n" + key + " " + key.length()+ " " + plainText.length());

               }
               if(choice.equalsIgnoreCase("e") )
               {
                   // run encryption

                   encrypt = new Encryption(plainText,key);
                   //encrypt.Encrypt(painText, key);
                  // KeyGeneration theKey = new KeyGeneration(key);
                  // theKey.begin();

               }
               else if (choice.equalsIgnoreCase("d"))
               {
                  // run decryption


               }
         }
         catch (Exception e)
         {
                 System.out.println("Error with file handling, please ensure a valid data file was provided in the same location as the compiled java files");
                 e.printStackTrace();
         }

    }
}
