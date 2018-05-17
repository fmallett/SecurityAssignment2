/**
 * @Name :           Nathan Davidson
 * @Student Number : C3187164
 * @Corse :          COMP3260
 * @Date :           13/5/18
 */
public class PC2{
int[] pc2 = {
        14, 17, 11, 24,  1,  5,
         3, 28, 15,  6, 21, 10,
        23, 19, 12,  4, 26,  8,
        16,  7, 27, 20, 13,  2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
        };

public String performPC2 (String appendedCDkey) {
        String output = "";
        //each of the 64 values in the initial permutation array will be used as an index.
        //This value in the plaintext at each index will be added to the new string (output)
        //to form the permuted data
        for (int i = 0; i < pc2.length; i++) {
        output += appendedCDkey.charAt(pc2[i]-1);
        }
        return output;
        }
}
