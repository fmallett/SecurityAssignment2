import java.util.ArrayList;

/**
 * @Name :           Nathan Davidson
 * @Student Number : C3187164
 * @Corse :          COMP3260
 * @Date :           13/5/18
 *
 * Class : KeyGeneration
 * Purpose :
 * KeyGeneration manages the key of the Des algorithm
 * The class provides:
 * key padding for 56 bit key inputs
 * generates a list of 16 keys by applying the appropriate DES procedure from a given input of 56 or 64 bits
 * all data is stored in an array list which is accessed via a get method to access the list of generated keys
 */

public class KeyGeneration {

    private String key = ""; // the key
    private ArrayList<String> D; // set D first set of the key pairs
    private ArrayList<String> C; // set C second set of key pairs
    private StringBuilder aStringBuilder = new StringBuilder(); // string builder for building all strings
    private ArrayList<String> concatenatedCDkey; // used to store the concatenated C D key pair
    private ArrayList<String> finalKeyList; // storage for the final key after the entire process
    private PC1 pc1 = new PC1(); // object data to perform PC1
    private PC2 pc2 = new PC2(); // object data to perform PC2

    /**
     *Constructor : KeyGeneration
     *Parameters : String key ~ to define the key
     *Description: Initialise variables initialise the preSetup for key generation and begins the key generation
     */
     KeyGeneration(String key_)
    {
        key = key_;
        finalKeyList = new ArrayList<String>(16);
        D = new ArrayList<String>(16);
        C = new ArrayList<String>(16);
        concatenatedCDkey = new ArrayList<String>(16);
        setup();
        begin();
    }
    /**
     * Method : keyPadCheck
     * Parameters : null
     * Return type : void
     * Description : checks the length of the key less then 64 if so it will append the key every 7 bits with the odd parity check sum bit
     */
    private void keyPadCheck()
    {

        if(key.length() <64)
        {
            //Padding the key is needed or PC1
            StringBuilder finalKey = new StringBuilder(); // final storage
            while (finalKey.length() < 64) // keep looping until we have the length needed for PC1 (64 bits)
            {
                for (int i = 0; i < 7 ; i++) // get the first 7 bits
                {
                    aStringBuilder.append(key.charAt(i)); // returns char location index i
                }
                // clean the original key string to allow looping of first 7 to be "new" values
                key = key.substring(7);
                //appending an odd-parity checksum bit
                aStringBuilder.append(checksum(aStringBuilder.toString())); // checksum() method performs the odd parity bit checksum
                //store the new "8 bits" original 7 bits + new odd parity bit from the checksum
                finalKey.append(aStringBuilder.toString());
                //clean the string builder for reuse
                aStringBuilder.delete(0,aStringBuilder.length());
            }

            key = finalKey.toString(); // update the original key variable
            //clean the string builder to be nice
            finalKey.delete(0,finalKey.length());
        }
        else
        {
            //Don't pad key
        }
    }
    /**
     * Method : checksum
     * Parameters : String sevenBits ~ 7 bits of data that need a check sum performed on returning an odd parity bit
     * Return type : String ~ odd parity bit
     * Description : performs a odd parity bit checksum on 7 bits returning the odd parity bit
     */
    private String checksum(String sevenBits)
    {
        String parityBit = "e"; // e = error...
        int bitCount = 0; // count for amount of ones
        for (int i = 0; i < sevenBits.length(); i++) // loop for length of 7 bits and count each occurrence of "1"
        {
            if( sevenBits.charAt(i) == '1')
            {
                bitCount++; // seen a 1 so count
            }
            else
            {
                // don't count
            }

        }
        // based on the amount of ones assign the parity bit to be returned
        if ( bitCount == 0 || bitCount == 2 || bitCount == 4 || bitCount == 6)
        {
            parityBit = "1";
        }
        else if ( bitCount == 1 || bitCount == 3 || bitCount == 5 || bitCount == 7)
        {
            parityBit = "0";
        }

        return parityBit;
    }

    /**
     * Method : setup
     * Parameters : null
     * Return type : void
     * Description : performs the initialisation of the key data, where it checks the padding legit of the key is valid for PC1,
     * then performs PC1 along with splitting the key up into two sets of 28 bits for building the initial 2 set of shifted keys
     * which are then concatenated later
     */
    private void setup()
    {
        //step 1, setup C0,D0 28 bits each half, c0 gets 0 -> 28; d0 gets 29 -> 56;
        //step 2, after setup begin computing the 16 rounds which involves
        //setup of c1-15 and d1-15
        //generate key 1-16 using c0d0->16 with PC-2 for each pair (16 times)
        String pc1Key = "";
        //Safety check
        keyPadCheck();

        //Initialise the PC1 permutation on the input key and store it
        pc1Key = pc1.performPC1(key);

        //being setup of C0 D0 (28 bits splitting PC1 key in 2 equal halves of 56)
        for (int i = 0; i < 28 ; i++)
        {
            aStringBuilder.append(pc1Key.charAt(i));
        }
        // Setup first half of 56 bits (0-28)
        C.add(aStringBuilder.toString());
        // Clean the string builder
        aStringBuilder.delete(0,aStringBuilder.length());

        for (int i = 28; i < pc1Key.length(); i++)
        {
            aStringBuilder.append(pc1Key.charAt(i));
        }
        // Setup second half of 56 bits (28-56)
        D.add(aStringBuilder.toString());

        // Clean the string builder
        aStringBuilder.delete(0,aStringBuilder.length());

    }

    /**
     * Method : begin
     * Parameters : null
     * Return type : void
     * Description : performs the 16 rounds of key generation initially it performs the left shift which builds the two
     * sets of left shifted keys which is then concatenated into the key for each round 0-15
     * where it is used in the key generation to generate the final key for the respective round
     */
    private void begin()
    {
        for (int i = 0; i < 16 ; i++) // c0->16 and d0->16
        {
           // System.out.println("round "+i);

            //Along with doing that each pair that is generated will be pushed to generated its key within the round
            //Setup of c1-15 and d1-15
            leftShift(i);
            //Generate Key from Key pairs C1D1 -> 16
            generateKeyFromKeyPairs(i);

        }
    }

    /**
     * Method : leftShift
     * Parameters : int currentRound ~ denotes the round we are in
     * Return type : void
     * Description : performs a left shift which is associated to the round, the left shift is applied to the 28 bit key pairs
     * where each new shifted key pair is stored(arraylist C, arraylist D) for later use
     */
    private void leftShift(int currentRound)
    {
        char firstPos; // single shifts
        char secondPos;// double shifts
        String currentC; // for set C
        String currentD; // for set D
        /* round to shift relationship
         Round   Shift
           1         1
           2         1
           3         2
           4         2
           5         2
           6         2
           7         2
           8         2
           9         1
          10         2
          11         2
          12         2
          13         2
          14         2
          15         2
          16         1
         */
        if(currentRound == 0 || currentRound == 1 || currentRound == 8 || currentRound == 15)// 1 2 9 16
        { // single shift
            // C
            currentC = C.get(currentRound);
            firstPos = currentC.charAt(0);
            currentC = currentC.substring(1,currentC.length()); // might error could need length-1
            aStringBuilder.append(currentC);
            aStringBuilder.append(firstPos);

            //Store data
            C.add(aStringBuilder.toString());
           // System.out.println("C: " + C.get(currentRound + 1));
            //Clean string builder
            aStringBuilder.delete(0,aStringBuilder.length());


            // D
            currentD = D.get(currentRound);
            firstPos = currentD.charAt(0);
            currentD = currentD.substring(1,currentD.length()); // might error could need length-1
            aStringBuilder.append(currentD);
            aStringBuilder.append(firstPos);

            //Store data
            D.add(aStringBuilder.toString());
            //System.out.println("D: " + D.get(currentRound + 1));
            //Clean string builder
            aStringBuilder.delete(0,aStringBuilder.length());
        }
        else    //double shift
        {
            // C
            currentC = C.get(currentRound);
            firstPos = currentC.charAt(0);
            secondPos = currentC.charAt(1);
            currentC = currentC.substring(2,currentC.length()); // might error could need length-1
            aStringBuilder.append(currentC);
            aStringBuilder.append(firstPos);
            aStringBuilder.append(secondPos);

            //Store data
            C.add(aStringBuilder.toString());
            //System.out.println("C: " + C.get(currentRound + 1));
            //Clean string builder
            aStringBuilder.delete(0,aStringBuilder.length());

            // D
            currentD = D.get(currentRound);
            firstPos = currentD.charAt(0);
            secondPos = currentD.charAt(1);
            currentD = currentD.substring(2,currentD.length()); // might error could need length-1
            aStringBuilder.append(currentD);
            aStringBuilder.append(firstPos);
            aStringBuilder.append(secondPos);

            //Store data
            D.add(aStringBuilder.toString());
           // System.out.println("D: " + D.get(currentRound + 1));
            //Clean string builder
            aStringBuilder.delete(0,aStringBuilder.length());

        }
    }

    /**
     * Method : generateKeyFromKeyPairs
     * Parameters : int currentRound ~ denotes the round we are in
     * Return type : void
     * Description : for each round we concatenate the key pairs C,D then apply the final permutation
     *  	         PC2 and store the key in the final key list
     */
    private void generateKeyFromKeyPairs(int currentRound)
    {
        //Concatenate CD for current round (CurrentRound is incremented by 1
        //because currentRound starts at 0 and we don't use C0D0 to generate a key we start with C1D1)
        int locationOfConcatenatedCDkey = currentRound;
        currentRound = currentRound + 1;

        //Concatenate C with D
        aStringBuilder.append(C.get(currentRound));
        aStringBuilder.append(D.get(currentRound));

        //Store  concatenated Key
        concatenatedCDkey.add(aStringBuilder.toString());
        //Clean string builder
        aStringBuilder.delete(0,aStringBuilder.length());

        //Apply PC2 on concatenatedCDkey and store the result in the finalKeyList
       finalKeyList.add( pc2.performPC2(concatenatedCDkey.get(locationOfConcatenatedCDkey)) );
  }

    /**
     * Method : generateKeyFromKeyPairs
     * Parameters : int currentRound ~ denotes the round we are in
     * Return type : ArrayList<String> ~ returns the entire arrylist data reference to be used in encryption
     * Description : returns the array list which is the list of the final generated keys after the 16 rounds
     */
    public ArrayList<String> getFinalKeyList() {
        return finalKeyList;
    }
}
