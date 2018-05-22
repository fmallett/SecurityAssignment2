/**
 * @Name :           Nathan Davidson
 * @Student Number : C3187164
 * @Corse :          COMP3260
 * @Date :           13/5/18
 *
 * Class : PC2
 * Purpose : Defines the PC2 Permutation for the final permutation applied on the key generation for each round
 * takes a 56 bit input outputs 48 bits
 */
public class PC2{
private int[] pc2 = {
        14, 17, 11, 24,  1,  5,
         3, 28, 15,  6, 21, 10,
        23, 19, 12,  4, 26,  8,
        16,  7, 27, 20, 13,  2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
        };

        /**
         *Method : performPC2
         *Parameters : String appendedCDkey ~ bit string to Permeated
         *Return type: String outPut ~ returns the Permeated bit string of 48 bits
         *Description: builds the permeated bit string of 48 bits from a 56 bit input
         */
public String performPC2 (String appendedCDkey) {
        String output = "";
        //each of the 64 values in the initial permutation array will be used as an index.
        //This value in the appendedCDkey at each index will be added to the new string (output)
        //to form the permuted data
        for (int i = 0; i < pc2.length; i++) {
        output += appendedCDkey.charAt(pc2[i]-1);
        }
        return output;
        }
}
