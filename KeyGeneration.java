import java.util.ArrayList;

/**
 * @Name :           Nathan Davidson
 * @Student Number : C3187164
 * @Corse :          COMP3260
 * @Date :           13/5/18
 */
public class KeyGeneration {
    private String key = "";
    private String pc1Key = "";
    private ArrayList<String> D;
    private StringBuilder aStringBuilder = new StringBuilder();
    private ArrayList<String> C;
    private ArrayList<String> concatenatedCDkey;
    private ArrayList<String> finalKeyList;
    private PC1 pc1 = new PC1();
    private PC2 pc2 = new PC2();

    public KeyGeneration(String key_)
    {
        key = key_;
        finalKeyList = new ArrayList<String>(16);
        D = new ArrayList<String>(16);
        C = new ArrayList<String>(16);
        concatenatedCDkey = new ArrayList<String>(16);
        setup();

    }
    //step 1, setup C0,D0 28 bits each half, c0 gets 0 -> 28; d0 gets 29 -> 56;
    //step 2, after setup begin computing the 16 rounds which involves
        //setup of c1-15 and d1-15
        //generate key 1-16 using c0d0->16 with PC-2 for each pair (16 times)
    private void setup()
    {
        pc1Key = pc1.performPC1(key); //initialise the PC1 permutation on the input key and store it

        //being setup of C0 D0 (28 bits splitting PC1 key in 2 equal halves of 56)
        for (int i = 0; i < 28 ; i++)
        {
            aStringBuilder.append(pc1Key.charAt(i));
        }
        // Setup first half of 56 bits (0-28)
        C.add(aStringBuilder.toString());
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

    public void begin()
    {
        for (int i = 0; i < 16 ; i++) // c0->16 and d0->16
        {
           // System.out.println("round "+i);

            //Along with doing that each pair that is generated will be pushed to generated its key within the round
            //Setup of c1-15 and d1-15
            leftShift(i);
            //Generate Key from Key paris C1D1 -> 16
            generateKeyFromKeyPairs(i);
            //

        }
    }

    private void leftShift(int currentRound)
    {
        int previousRound = currentRound -1;
        int nextRound = currentRound + 1;
        char firstPos;
        char secondPos;
        String currentC;
        String currentD;
        /* R   Shift
           1    1
           2    1
           3    2
           4    2
           5    2
           6    2
           7    2
           8    2
           9    1
          10    2
          11    2
          12    2
          13    2
          14    2
          15    2
          16    1
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
        //System.out.println("ConcatenatedCDKey: " + concatenatedCDkey.get(locationOfConcatenatedCDkey));
        //Clean string builder
        aStringBuilder.delete(0,aStringBuilder.length());

        //Apply PC2 on concatenatedCDkey and store the result in the finalKeyList
       finalKeyList.add( pc2.performPC2(concatenatedCDkey.get(locationOfConcatenatedCDkey)) );

       // System.out.println("finalKeyList: " + finalKeyList.get(locationOfConcatenatedCDkey));
    }

    public ArrayList<String> getFinalKeyList() {
        return finalKeyList;
    }
}
