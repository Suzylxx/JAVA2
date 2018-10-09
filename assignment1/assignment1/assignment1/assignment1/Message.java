package assignment1;

public class Message {
	
	public String message;
	public int lengthOfMessage;

	public Message (String m){
		message = m;
		lengthOfMessage = m.length();
		this.makeValid();
	}
	
	public Message (String m, boolean b){
		message = m;
		lengthOfMessage = m.length();
	}
	
	/**
	 * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
	 */
	public void makeValid(){
		//INSERT YOUR CODE HERE
		String nMessage = this.message.toLowerCase();//convert the message to lowercase
		String newM = "";
		for(int i=0;i<nMessage.length();i++) {
			char atN = nMessage.charAt(i);
			if(atN>=97&&atN<=122) {//check if the element at "i" is a letter
				newM = newM+atN;
			}
		}
		this.message = newM;
		this.lengthOfMessage = this.message.length();
	}
	
	/**
	 * prints the string message
	 */
	public void print(){
		System.out.println(message);
	}
	
	/**
	 * tests if two Messages are equal
	 */
	public boolean equals(Message m){
		if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
			return true;
		}
		return false;
	}
	
	/**
	 * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
	 * @param key
	 */
	public void caesarCipher(int key){
		// INSERT YOUR CODE HERE
		String c = this.message;
		String cMessage = "";
		int newKey = key%26;
		for(int i=0;i<c.length();i++) {
		 	int k = c.charAt(i)+newKey;//the value of the letter after shift by "key"
			if(k<97) {//if key is negative and k less than 97, shift value plus 26
				cMessage = cMessage+(char)(k+26);
			}else if(k>122) {//if key is positive and shift value exceed 122, minus 26
				cMessage = cMessage+(char)(k-26);
			}else {
				cMessage = cMessage+(char)(k);
			}
		}
		this.message = cMessage;
		this.lengthOfMessage = cMessage.length();
	}
	
	public void caesarDecipher(int key){
		this.caesarCipher(- key);
	}
	
	/**
	 * caesarAnalysis breaks the Caesar cipher
	 * you will implement the following algorithm :
	 * - compute how often each letter appear in the message
	 * - compute a shift (key) such that the letter that happens the most was originally an 'e'
	 * - decipher the message using the key you have just computed
	 */
	public void caesarAnalysis(){
		// INSERT YOUR CODE HERE
		int[] count = new int[123];//create a new array with length 123
		String m = this.message;
		for(int i=0;i<m.length();i++) {
			count[(int)(m.charAt(i))]++;//count the times of a letter showed in the message
		}
		int max = 0;
		int commonI = 0;
		for(int j=0;j<count.length;j++) {
			if(count[j]>max) {//find the letter which happens the most
				max = count[j];
				commonI = j;
			}
		}
		char common = (char)(commonI);//get the most common letter in the message
		int key = (int)(common-'e');
		this.caesarDecipher(key);
	}
	
	/**
	 * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
	 * @param key
	 */
	public void vigenereCipher (int[] key){
		// INSERT YOUR CODE HERE
		String vMessage = "";
		String m = this.message;
		int mLength = this.message.length();
		int kLength = key.length;
		for(int i=0;i<mLength;i++) {
			int keyL = i%kLength;//get the number of key a letter should be shifted
			int newKey = key[keyL]%26;
			if(m.charAt(i)+newKey<97) {
				vMessage = vMessage+(char)(m.charAt(i)+newKey+26);
			}else if(m.charAt(i)+newKey>122){
				vMessage = vMessage+(char)(m.charAt(i)+newKey-26);
			}else {
				vMessage = vMessage+(char)(m.charAt(i)+newKey);
			}
		}
		this.message = vMessage;
		this.lengthOfMessage = vMessage.length();
	}

	/**
	 * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
	 * @param key
	 */
	public void vigenereDecipher (int[] key){
		// INSERT YOUR CODE HERE
		for(int i=0;i<key.length;i++) {
			key[i] = -key[i];
		}
		this.vigenereCipher(key);
	}
	
	/**
	 * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
	 * @param key
	 */
	public void transpositionCipher (int key){
		// INSERT YOUR CODE HERE
		String m = this.message;
		String tMessage = "";
		int row = m.length()/key+1;
		char[][] t = new char[row][key];
		for(int i=0;i<row;i++) {
			for(int j=0;j<key;j++) {
				int position = i*key+j;
				if(position>=m.length()) {//when no letter can be assigned to the position
					t[i][j] = '*';
				}else {//assign the letters in the message to exact position
					t[i][j] = m.charAt(position);
				}
			}
		}
		for(int i=0;i<key;i++) {
			for(int j=0;j<row;j++) {//get the new message
				tMessage = tMessage+t[j][i];
			}
		}
		this.message = tMessage;
		this.lengthOfMessage = tMessage.length();
	}
	
	/**
	 * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
	 * @param key
	 */
	public void transpositionDecipher (int key){
		// INSERT YOUR CODE HERE
		String m = this.message;
		String tMessage = "";
		int column = m.length()/key;
		char[][] t = new char[key][column];
		for(int i=0;i<key;i++) {//assign all the elements in the ciphered message to an array
			for(int j=0;j<column;j++) { 
				int position = i*column+j;
				t[i][j] = m.charAt(position);
			}
		}
		for(int i=0;i<column;i++) {
			for(int j=0;j<key;j++) {
				if(t[j][i]!='*') {//get the original message
					tMessage = tMessage+t[j][i];
				}
			}
		}
		this.message = tMessage;
		this.lengthOfMessage = tMessage.length();
	}
	
}
