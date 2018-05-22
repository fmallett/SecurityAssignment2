 /**
 * @Name :           Nathan Davidson
 * @Student Number : C3187164
 * @Corse :          COMP3260
 * @Date :           13/5/18
 *
 * Class : PC1
 * Purpose : Defines the PC1 Permutation for the key data which is used on a 64 bit key to produce a 56 bit key
 */
 public class PC1 {

     private int[] pc1 = {
        57, 49, 41, 33, 25, 17,  9,
         1, 58, 50, 42, 34, 26, 18,
        10,  2, 59, 51, 43, 35, 27,
        19, 11,  3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
         7, 62, 54, 46, 38, 30, 22,
        14,  6, 61, 53, 45, 37, 29,
        21, 13,  5, 28, 20, 12,  4


     };
     /**
      *Method : performPC1
      *Parameters : String keyInput ~ bit string to Permeated
      *Return type: String outPut ~ returns the Permeated bit string of 56 bits
      *Description: builds the permeated bit string of 56 bits from a 64 bit input
      */
     public String performPC1(String keyInput) {
         String output = "";
         //each of the 64 values in the initial permutation array will be used as an index.
         //This value in the keyInput at each index will be added to the new string (output)
         //to form the permuted data
         for (int i = 0; i < pc1.length; i++) {
             output += keyInput.charAt(pc1[i]-1);
         }
         return output;
     }
}
