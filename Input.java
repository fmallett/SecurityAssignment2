import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**Name :           Nathan Davidson
 * Student Number : C3187164
 * Date :           8/5/18
 */

public class Input
{
    /** Read input file:
     * file expected format is
     * Plaint text
     * followed by a key
     * concept is we only process 64 bits for plaintext and key
     * assumption is stated above.
     */
    private InputStream inputStream;
    private int charCount = 0;
    private boolean eofFlag = false;
    private boolean keyCheck = false;
    private char value = ' ';
    private StringBuilder aStringBuilder = new StringBuilder();
    private StringBuffer aStringBuffer = new StringBuffer();
    private String pText;
    private String sKey;
    private int line = 0;

    private int inputCharByte = 0;

    int count = 0;
    ArrayList plainText;
    ArrayList key;
    //15 spaces if broken into sets of length 4 resulting in 16 sets * 4
    // however if data is actually less then length 64 before new line would imply padding is needed, dont believe it will be pre padded
    Input(InputStream inputStream_) throws java.io.IOException
    {
        inputStream = inputStream_;
        plainText = new ArrayList(64);
        key = new ArrayList(64);
        pText = "";
        sKey = "";

        while(!eofFlag)
        {
            //testing code:  System.out.println("im reading the characters from file " + inputCharByte +" value: " + value);
            read();
        }


    }



    private void read() throws java.io.IOException
    {

        inputCharByte = inputStream.read();

        if(inputCharByte == 10 || inputCharByte == 13)
        {
            // line feed character (enter, new line would imply the key )
            // assumption is the key is next as we hit the end of the line
            // space = 32
            line++;


            if(charCount == 64 && line < 2)
            {
                pText = aStringBuilder.toString();
                aStringBuilder.delete(0,aStringBuilder.length());


                charCount = 64;
                keyCheck = true;
            }
            else if(charCount < 64 && line < 2)
            {
                // need to do some padding

                charCount = 64;
                keyCheck = true;

            }
            else if( charCount > 64 && line < 2)
            {
                // plain text is longer then 64 char's , what to do?

                charCount = 64;
                keyCheck = true;

            }
            else if (charCount == 64 && line < 3)
            {
                sKey = aStringBuilder.toString();
                aStringBuilder.delete(0,aStringBuilder.length());
            }



        }

        else if (inputCharByte == 9 || inputCharByte == 32) // tab
        {
            // ingore current input its a space or a tab by forcing a read of the inputstream (gets next char)
            inputCharByte = inputStream.read();
        }

        if(inputCharByte == -1) // end of inputstream
        {
            eofFlag = true;
            charCount = 0;
            //System.out.println(pText + "\n" + sKey);
        }



        value = (char) inputCharByte;
        if(!keyCheck && line < 3  && charCount < 64)
        {
            plainText.add(value);

            aStringBuilder.append(value);

            charCount++;
        }
        else if (line < 3 && count < 57 && inputCharByte != 13 && inputCharByte !=10 ) // charCount > 64
        {
            count++;

            key.add(value);

            aStringBuilder.append(value);

        }




    }

    public String getpText() {
        return pText;
    }

    public String getsKey() {
        return sKey;
    }

}