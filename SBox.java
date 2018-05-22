
/**
 *@Name : Fiona Mallett
 *@Course : COMP3260
 *@StudentNumber : 3289339
 *@Date : 8/5/2018
 *
 * Class : SBox
 * Purpose : Defines the Sbox's 1-8, applies the data manipulation for DES related to the Sbox
 */

public class SBox {

	private int [][] SBOX1= {
			{14 ,4 ,13 ,1 ,2 ,15 ,11 ,8 ,3 ,10 ,6 ,12 ,5 ,9 ,0 ,7},
			{0 ,15 ,7 ,4 ,14 ,2 ,13 ,1 ,10 ,6 ,12 ,11 ,9 ,5 ,3 ,8},
			{4 ,1 ,14 ,8 ,13 ,6 ,2 ,11 ,15 ,12 ,9 ,7 ,3 ,10 ,5 ,0},
			{15, 12, 8, 2, 4, 9, 1, 7 ,5, 11, 3, 14, 10, 0, 6, 13}
	};

	private int [][] SBOX2= {
			{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12 ,0, 5, 1},
			{3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9 ,11, 5},
			{0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
			{13 ,8 ,10 ,1 ,3 ,15 ,4 ,2 ,11 ,6 ,7 ,12 ,0 ,5 ,14 ,9}
	};

	private int [][] SBOX3={
			{10 ,0 ,9 ,14 ,6 ,3 ,15 ,5 ,1 ,13 ,12 ,7 ,11 ,4 ,2 ,8},
			{13, 7, 0, 9, 3, 4, 6, 10, 2 ,8 ,5 ,14, 12, 11, 15, 1},
			{13, 6, 4, 9, 8 ,15 ,3 ,0, 11, 1, 2, 12, 5, 10, 14, 7},
			{1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
	};

	private int [][] SBOX4={
			{7 ,13 ,14 ,3 ,0 ,6 ,9 ,10 ,1 ,2 ,8 ,5 ,11 ,12 ,4 ,15},
			{13 ,8 ,11 ,5, 6 ,15, 0 ,3 ,4 ,7 ,2 ,12 ,1 ,10 ,14 ,9},
			{10, 6, 9, 0, 12, 11, 7 ,13, 15, 1, 3, 14, 5, 2, 8, 4},
			{3, 15, 0, 6, 10, 1, 13, 8, 9, 4 ,5 ,11 ,12, 7, 2, 14}
	};

	private int [][] SBOX5={
			{2 ,12 ,4 ,1 ,7 ,10 ,11 ,6 ,8 ,5 ,3 ,15 ,13 ,0 ,14 ,9},
			{14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3 ,9 ,8 ,6},
			{4 ,2, 1, 11, 10, 13, 7 ,8 ,15, 9, 12, 5, 6, 3, 0, 14},
			{11, 8, 12 ,7, 1, 14 ,2 ,13 ,6 ,15, 0 ,9, 10, 4, 5, 3}
	};

	private int [][] SBOX6={
			{12 ,1 ,10 ,15, 9 ,2, 6 ,8 ,0 ,13 ,3 ,4 ,14 ,7 ,5 ,11},
			{10, 15, 4 ,2 ,7 ,12 ,9, 5, 6, 1, 13 ,14 ,0, 11, 3 ,8},
			{9, 14, 15, 5, 2 ,8 ,12, 3, 7 ,0 ,4 ,10, 1, 13 ,11 ,6},
			{4, 3, 2, 12, 9, 5, 15 ,10 ,11, 14, 1, 7, 6, 0, 8, 13}
	};

	private int [][] SBOX7={ 
			{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7 ,5 ,10 ,6 ,1},
			{13, 0, 11, 7, 4, 9, 1, 10, 14 ,3 ,5, 12, 2, 15, 8, 6},
			{1, 4, 11, 13, 12, 3, 7 ,14, 10, 15, 6, 8, 0, 5, 9, 2},
			{6, 11, 13, 8 ,1 ,4, 10, 7, 9, 5 ,0 ,15 ,14, 2, 3, 12}
	};

	private int [][] SBOX8={ 
			{13, 2, 8, 4, 6, 15, 11, 1 ,10, 9, 3, 14, 5, 0, 12, 7},
			{1, 15, 13, 8 ,10, 3 ,7 ,4 ,12, 5, 6 ,11, 0, 14, 9, 2},
			{7, 11, 4, 1 ,9 ,12 ,14 ,2 ,0 ,6 ,10, 13 ,15 ,3 ,5 ,8},
			{2, 1, 14, 7 ,4 ,10, 8 ,13, 15, 12, 9, 0, 3, 5, 6, 11}
	};

	/**
	 *Method : getSboxValue
	 *Parameters : int SBox ~ sbox being used
	 * 			 : String sixBits ~ reference to the selection which represents the row and column
	 *Return type: String result ~ returns the result of the sbox selection
	 *Description: using the input which define the sbox being used and the 6 bits provided to be the selection data
	 * first 2 bits represent the row while the last 4 represent the column using that data
	 * select the specified row column then calculate the result to binary
	 */
	public String getSboxValue(int SBox, String sixBits) {
		int sBoxValue;
		// Create row using the first and last bits
		String rowBinary;
		int row;
		rowBinary = sixBits.substring(0,1);
		rowBinary = rowBinary.concat(sixBits.substring(5,6));

		//Change binary values into an integer for quick lookup in the tables later
		row = Integer.parseInt(rowBinary,2);	

		// Get inside 4 bits for column 
		String columnBinary;
		int column;
		columnBinary = sixBits.substring(1,5);
		//Change binary values into an integer
		column = Integer.parseInt(columnBinary,2);

		//look up values in sbox and return the value
		sBoxValue = getSBox(SBox)[row][column];
		String result;
		//convert the integer from the s-box to a binary value
		result = Integer.toBinaryString(sBoxValue);
		//pad the binary number so it is 4 bits long
		while(result.length() < 4) {
			result = 0 + result;
		}
		return result;
	}

	/**
	 *Method : getSBox
	 *Parameters : int i ~ represents the sbox via index
	 *Return type: int[][] ~ returns an sbox
	 *Description: returns the sbox that is selected by the index i
	 */
	public int[][] getSBox(int i) {

		if (i == 1) {
			return SBOX1;
		}
		else if (i == 2) {
			return SBOX2;
		}
		else if (i == 3) {
			return SBOX3;
		}
		else if (i == 4) {
			return SBOX4;
		}
		else if (i == 5) {
			return SBOX5;
		}
		else if (i == 6) {
			return SBOX6;
		}
		else if (i == 7) {
			return SBOX7;
		}
		else if (i == 8) {
			return SBOX8;
		}
		else
			System.err.println("Invald integer for s-box, must be between 1 - 8");
		return null;
	}


}
