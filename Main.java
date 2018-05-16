import java.io.*;


/**Name :           Nathan Davidson
 * Student Number : C3187164
 * Date :           8/5/18
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
                String painText = "";
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
                                painText = scanner.getpText();
                                key = scanner.getsKey();

                                //System.out.println(painText + "\n" + key);

                        }
                        if(choice.equalsIgnoreCase("e") )
                        {
                                // run encryption
                                encrypt = new Encryption(painText,key);
                                encrypt.Encrypt(painText, key);

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
