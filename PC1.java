 /**
 * @Name :           Nathan Davidson
 * @Student Number : C3187164
 * @Corse :          COMP3260
 * @Date :           13/5/18
 */

 /**
  * all permutation classes use the exact same format(premutation data + an array exact same code) , these classes could be converted into 1 class
  * and all we would have to do is call multiple methods from that class that access the specific permutation table
  *
  */
 public class PC1 {

     int[] pc1 = {
        57, 49, 41, 33, 25, 17,  9,
         1, 58, 50, 42, 34, 26, 18,
        10,  2, 59, 51, 43, 35, 27,
        19, 11,  3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
         7, 62, 54, 46, 38, 30, 22,
        14,  6, 61, 53, 45, 37, 29,
        21, 13,  5, 28, 20, 12,  4


     };

     public String performPC1(String keyInput) {
         String output = "";
         //each of the 64 values in the initial permutation array will be used as an index.
         //This value in the plaintext at each index will be added to the new string (output)
         //to form the permuted data
         for (int i = 0; i < pc1.length; i++) {
             output += keyInput.charAt(pc1[i]-1);
         }
         return output;
     }
}
