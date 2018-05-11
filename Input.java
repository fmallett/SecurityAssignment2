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
   * concept is we only process 64 bits i.e. 32 bit plain text 32 bit key
   * assumption is stated above.
   *
   */
     private InputStream inputStream;
     private int charCount = 0;
     private boolean eofFlag = false;

     int inputCharByte = 0;
     ArrayList plainText;
     ArrayList key;

     Input(InputStream inputStream_) throws java.io.IOException
     {
        inputStream = inputStream_;
         plainText = new ArrayList(16);
         key = new ArrayList(16);

        while(!eofFlag)
        {
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
                 charCount = 16;

            }

            else if (inputCharByte == 9) // tab
            {
            }

            if(inputCharByte == -1) // end of inputstream
            {
                eofFlag = true;
                charCount = 0;
            }


        char value = (char) inputCharByte;
        if(charCount <= 16)
        {
            plainText.add(inputCharByte);
        }
        else// charCount > 16
        {
            key.add(inputCharByte);
        }



      }

}