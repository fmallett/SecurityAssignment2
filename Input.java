import java.io.InputStream;

/**
 * @Name :           Nathan Davidson
 * @Student Number : C3187164
 * @Corse :          COMP3260
 * @Date :           8/5/18
 *
 * Class : Input
 * Purpose : Reads an input file using input stream and stores the data in as string for outside access
 *
 * structure of an input file is :
 * 0 or 1
 * Plaintext
 * Key
 *
 * EOF
 *
 * 0 = encryption file
 * 1 = decryption file
 */

public class Input
{

    private InputStream inputStream; // file input stream
    private int charCount = 0; // for counting char length
    private boolean eofFlag = false; // eof flag
    private boolean keyCheck = false; // to flag a state of reading in key data or not reading key data
    private char value = ' '; // used for casting from inputstream data back to real data
    private StringBuilder aStringBuilder = new StringBuilder();// for building strings
    private String pText; // plain text
    private String sKey; // the key
    private int line = 0; // line count
    private int inputCharByte = 0; // stores the inputstream byte character data
    private char choice = ' '; // used to store encryption or decryption ( 1 or 0)
    private int keyCount = 0; //


    /**
     *Constructor : Input
     *Parameters : InputStream inputStream_ ~ to define the InputStream data
     *Description: Initialise variables and the inputstream as and begins reading the inputstream
     */
    Input(InputStream inputStream_) throws java.io.IOException
    {
        inputStream = inputStream_;
        pText = "";
        sKey = "";

        //whitespace filter for first line
        inputCharByte = inputStream.read();
        while (inputCharByte == 10 || inputCharByte == 13 || inputCharByte == 32 || inputCharByte == 9)
        {
            inputCharByte = inputStream.read(); // filters any whitespace out that is present in the file before the actual data
        }
            choice = (char) inputCharByte; // first point of data is casted from byte value to a real value


        if(choice == '1' || choice == '0') // should be the "choice" so we check if this 1 or 0
        {
            //filter out enter and line feed char after the encryption (0) or decryption (1) on the first line

            if(safetyCheck()) // performs a safety check on the next bit of data to determine that only 1 input was given on line 1
                              // or that the next bit of data is not the end of the file
            {
                while(!eofFlag) // loop until we hit the end of the file
                {
                    readFile(); // read the input
                    inputCharByte = inputStream.read(); // update inputCharByte with new byte value
                }
            }
            else
            {
                System.exit(-1); // cannot continue force program shutdown to avoid random errors
            }
        }
        else
        {
            System.out.println("Missing Literal in file provided on line 1 that denotes Encryption (1) or Decryption (0)");
            System.exit(-1); // cannot continue force program shutdown to avoid random errors
        }
    }

    /**
     *Method : safetyCheck
     *Parameters : null
     *Return type: boolean ~ true if everything is safe, false if not
     *Description: performs sanity checks to ensure that no more data then one value was provided on the first line as well
     * as checking that we haven't reached the end of the file after reading the next input
     */
    private boolean safetyCheck() throws java.io.IOException
    {
        boolean check = true;
        inputCharByte = inputStream.read();
        char value = (char) inputCharByte;

        if(inputCharByte >32 && inputCharByte < 127)
        {
            System.out.println("Fist line contains more then 1 literal character");
            check = false;
            return check;
        }
        else
        {
            while (inputCharByte == 10 || inputCharByte == 13 || inputCharByte == 32 || inputCharByte == 9)
            {
                inputCharByte = inputStream.read();
                if(inputCharByte == -1)
                {
                    System.out.println("Reached end of file after reading first line ");
                    check = false;
                }
            }
        }
        return  check;
    }

    /**
     *Method : readFile
     *Parameters : null
     *Return type: void
     *Description: reads the inputstream character "inputCharByte" based on values provided which mean different characters such as
     * 10 and 13 represent enter and line feed character represent the end of a line the goal is to filter though inputstream and store the appropriate data
     */
    private void readFile() throws java.io.IOException
    {


        if(inputCharByte == 10 || inputCharByte == 13)
        {
            // line feed character (enter, new line would imply the key )
            // assumption is the key is next as we hit the end of the line
            // space = 32
                line++;

            if(charCount == 64 && line < 2) // we have just read the key, (count 64 characters and less then 2)
            {
                pText = aStringBuilder.toString(); // store plain text from string builder
                //clean the string builder
                aStringBuilder.delete(0,aStringBuilder.length());

                // char count must stay a 64 for flow sake as it locks in a state of "after key"
                charCount = 64;
                keyCheck = true;
            }
            else if( charCount > 64 && line < 2)
            {
                // plain text is longer then 64 char's
                System.out.println("plain text provided is larger then 64 bits not built to handle input size exiting system");
                System.exit(-1);
            }
            else if (keyCount == 64 && line < 3 || keyCount == 56 && line < 3) // key count is either 64 or 56 and the line is less then 3
            {
                sKey = aStringBuilder.toString();
                aStringBuilder.delete(0,aStringBuilder.length());
            }

        }

        else if (inputCharByte == 9 || inputCharByte == 32) // tab & space
        {
            // ignore current input its a space or a tab by forcing a read of the inputstream (gets next char)
            inputCharByte = inputStream.read();
        }

        if(inputCharByte == -1) // end of inputstream / file
        {
            eofFlag = true; // set eof flag true to kill loop
            // sanity check just in case we hit end of file before seeing a new line meaning we still need to assign key's data
            if(sKey.length() == 0 && keyCount == 64 || sKey.length() == 0 && keyCount == 56)
            {
                sKey = aStringBuilder.toString();
            }
            else
            {
                // error something went wrong, we saw no bits for the key
                System.out.println("Error found no valid key data either its not 56 or 64 bits or there was no key provided, exiting system");
                System.exit(-1);

            }
            charCount = 0;
            keyCount = 0;
        }

        value = (char) inputCharByte;
        if(!keyCheck && line < 3  && charCount < 64)
        {

            aStringBuilder.append(value);
            charCount++;
        }
        else if (line < 3 && keyCount < 64 && inputCharByte != 13 && inputCharByte !=10 )
        {
            keyCount++;
            aStringBuilder.append(value);

        }

    }


    /**
     *Method : getpText
     *Parameters : null
     *Return type: String pText ~ returns the plain text
     *Description: returns the plaintext data stored in pText (should be the same as input file)
     */
    public String getpText() {
        return pText;
    }

    /**
     *Method : getsKey
     *Parameters : null
     *Return type: String sKey ~ returns the key
     *Description: returns the key data stored in sKey (should be the same as input file)
     */
    public String getsKey() {
        return sKey;
    }

    /**
     *Method : getChoice
     *Parameters : null
     *Return type: String choice ~ returns the choice value 0 or 1
     *Description: returns the choice data 0 or 1 which was supplied in the input file
     */
    public char getChoice() {
        return choice;
    }
}