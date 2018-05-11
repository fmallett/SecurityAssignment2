/**
 *@Name Fiona Mallett
 *@Course Computer Science
 *@StudentNumber 3289339
*/

public class tester {

	
	public static void main (String args[]) {
		
		String key = "111100001111000011110000111100001111000011110000";
		String plain = "11111111111111111111111111111111"; 
		
		
		//Key is 56 bits
		
		String result = "";
		
		for (int i = 0; i < key.length(); i++) {
		//Padding zero bits to the left so that we XOR with 2 48bits
			while(plain.length() < key.length()) {
				plain = "0"  + plain;
			}			
			
		//XOR function
			//If the key and plaintext are the same value, the XOR result will be zero
			if(key.charAt(i) == plain.charAt(i)) {
				result = result + "0";
			}
			//When the key and plaintext values are different, the result will be one
			else  
			{
				result = result + "1";
			}
		}
		System.out.println(plain);
		System.out.println(key);
		System.out.println(result);
		
		
		
	}
}
