import java.io.*;
import Input.java;


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
        try
        {
        if(args.length==0)
        {
        //Error
        }
        else
        {
            input = args[0];
        if(input.equalsIgnoreCase("e")) // encryption
        {
        choice=args[0];
        }
        else if(input.equalsIgnoreCase("d")) // decryption
        {
        choice=args[0];
        }
        else
        {
        //Error
        }

        if(args.length>1) // they have provided a file name
        {
        inputFileName=args[1];
        }
        else
        {
        //Error cant assume inputfile name
        }

        if(args.length>2) // they have provided a file name
        {
            outPutFileName =args[2];
        }
        else
        {
        System.out.println("default output file name is :"+outPutFileName);
        }

        InputStream FileInputStream = new FileInputStream(inputFileName);
        Input scanner = new Input(FileInputStream);

        }
        }
        catch (Exception e)
        {
        System.out.println("Error with file handling, please ensure a valid data file was provided in the same location as the compiled java files");
        e.printStackTrace();
        }

        }
        }
