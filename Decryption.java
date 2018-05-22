/**
 *
 * @Name :           Nathan Davidson
 * @Student Number : C3187164
 * @Corse :          COMP3260
 * @Date :           12/5/18
 *
 * Class : Decryption
 * Purpose : decrypt the des algorithm
 */

public class Decryption {

    private String plainText;
    private String key;
    private String cipherText;
	private String left = "";
    private String right = "";
    private String tempRight = "";
    private String prevRight = "";
    private int InvertedRoundNumber = 15; // used to reference the key list in reverse (as an index so 15 = Round 16)

    private String outPutFileName = "";

    private SBox SBox = new SBox();
    private InitialPermutation initialPermutation = new InitialPermutation();
    private FinalPermutation finalPermutation = new FinalPermutation();
    private ExpansionPermutation expansionPermutation = new ExpansionPermutation();
    private InverseETable inverseETable = new InverseETable();
    private Permutation permutation = new Permutation();
    private	KeyGeneration keyGeneration;



    /**
     * Constructor : Decryption
     * Parameters :
     * Description: initialises the output file name for displaying, cipher text for decryption, the key used to decrypt the plaintext.
     * Generates the key list using the supplied key
     * performs the intial split of the cipher text into the 32 bit halves (left and right) and begins starts rounds for des
     */
    Decryption (String cipherText_, String key_, String outPutFileName_)
    {
        //setup output file
        outPutFileName = outPutFileName_;
        //setup cipher text
        cipherText = cipherText_;
        //setup the key
        key = key_;

        //generate the key list from given key
        keyGeneration = new KeyGeneration(key);
        // perform the initial Permutation on the cipherText
        plainText = initialPermutation.performInitialPermutation(cipherText);
        //initialise LHS and RHS for the rounds
        right = plainText.substring(32,64);
        left = plainText.substring(0,32);
        //start rounds
        round();

        //output the results
        outPut();

    }

    /**
     * Method : round
     * Parameters : null
     * Return Type : void
     * Description: initialise and manages the rounds of des
     */
    private void round()
    {
        for (int round = 0; round <16 ; round++)
        {
            prevRight = right; // maintain the last right created value for the next right calculation

            //begin decryption of des0
            roundFunctionDES0(right,round);

            //XOR Left with Right, the result is the new Right
            tempRight = XOR32Bits(left,right);

            //update left
            left = prevRight;
            //update right
            right = tempRight;
            InvertedRoundNumber--;
        }

        //32 bit swap
        plainText = right + left;

        //Final permutation (Inverse IP)
        plainText = finalPermutation.performFinalPermutation(plainText);
    }

    /**
     * Method : roundFunctionDES0
     * Parameters : String Ri ~
     *            : int roundNumber ~ reference to the current round
     * Return Type : void
     * Description: apply the round function of des which applies the expansion permutation (EP), xor's the EP with the
     * generated keys (inverted as this is decryption) then applies the Sbox modifications and the final permutation updating the original rhs variable
     */
    private void roundFunctionDES0(String Ri, int roundNumber) {
        //Expansion/permutation
        Ri = expansionPermutation.expand(Ri);

        //XOR with key
        Ri = XOR(Ri, keyGeneration.getFinalKeyList().get(InvertedRoundNumber)); //subkey[i] ----- 16 rounds

        //S-Box
        Ri = useSBox(Ri);

        //Permutation function(P)
        right = permutation.permutationFunctionP(Ri);
    }
    /**
     * Method : XOR32Bits
     * Parameters : String left ~ data to be xored
     *            : String right ~ data to be xored
     * Return Type : String result ~ result of the xor
     * Description: xor 32bit length data and return the xor result
     */
        private String XOR32Bits(String left, String right)
        {
        //check the left and right bits are 32 bits long
        if (left.length() != 32 && right.length() != 32){
            //Carry on with the operation anyway but print an error message to inform the user
            System.err.println("Method XOR32Bits: Left or right Strings are not 32 bits long");
        }

        StringBuilder result = new StringBuilder();
        String temp ="";
        for (int i = 0; i < left.length(); i++) {
            //When the bits at the same position in the left and right Strings match,
            //the XOR result is 0
            if(left.charAt(i) == right.charAt(i)) {
                result.append("0");
            }
            //When the values at the same positions are different, the result will be one
            else {
                result.append("1");
            }
        }
        return result.toString();
    }

    /**
     * Method : XOR
     * Parameters : String bitsToXOR ~ bits that xor is being applied to
     *            : String key ~ the key to xor the bits with
     * Return Type : String result ~ returns the result of the xor
     * Description: apply the xor operator on the "bitsToXOR" with the key and return the result
     */
        private String XOR(String bitsToXOR, String key) {
        String temp = "";
        //Key is 56 bits
        if(key.length() != 48) {
            System.err.println("Key length should be 56 bits in input file");
        }

        //We will store the result of the XOR operating in result
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < key.length(); i++) {
            //Padding zero bits to the left so that we XOR with 2 56 bits
            while(bitsToXOR.length() < key.length()) {
                bitsToXOR = "0"  + bitsToXOR;
            }

            //XOR function
            //If the key and plaintext are the same value, the XOR result will be zero
            if(key.charAt(i) == bitsToXOR.charAt(i)) {
                result.append("0");
            }
            //When the key and plaintext values are different, the result will be one
            else
            {
                result.append("1");
            }
        }
        return result.toString();
    }

    /**
     * Method : useSBox
     * Parameters : String R1beforeSBox ~ a bit string that is ready to be sboxed that is it IP, Expansion and XOR with key
     * Return type : String result ~ returns the new values calculated from the sbox (a new bit string)
     * Description: applies the sbox modifications to the bit data for des
     */
    private String useSBox(String R1beforeSBox) {
        //S-box takes 48 bit input and returns 32 bit output
        //result will store the values returned by the s-box
        StringBuilder result = new StringBuilder();
        //Split string into bits of 6 and pass these values into each s-box
        //the numbers 1 -8 represent s-box1, s-box2, s-box3 .... s-box8
        result.append(SBox.getSboxValue(1, R1beforeSBox.substring(0, 6)));
        result.append(SBox.getSboxValue(2, R1beforeSBox.substring(6, 12)));
        result.append(SBox.getSboxValue(3, R1beforeSBox.substring(12, 18)));
        result.append(SBox.getSboxValue(4, R1beforeSBox.substring(18, 24)));
        result.append(SBox.getSboxValue(5, R1beforeSBox.substring(24, 30)));
        result.append(SBox.getSboxValue(6, R1beforeSBox.substring(30, 36)));
        result.append(SBox.getSboxValue(7, R1beforeSBox.substring(36, 42)));
        result.append(SBox.getSboxValue(8, R1beforeSBox.substring(42, 48)));

        return result.toString();
    }

    /**
     * Method : outPut
     * Parameters : null
     * Return type : void
     * Description: outputs data to screen
     */
    private void outPut()
    {
        // will write to file eventually...
        System.out.println("PlainText : " +plainText);
        System.out.println("Key :" + key);
        System.out.println("CipherText " + cipherText);
    }

    //Getter for printing
    public String getCipherText() {
		return cipherText;
	}
    //Getter for printing
    public String getPlainText() {
		return plainText;
	}

}
