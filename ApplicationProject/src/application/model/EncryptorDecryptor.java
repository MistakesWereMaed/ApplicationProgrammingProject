package application.model;

import java.util.Base64;

/**
 * @deprecated
 * EncryptorDecryptor is a class containing methods to encrypt and decrypt data files
 * @author Daniel Dang (danield2141)
 */
public class EncryptorDecryptor {

	/**
	 * Takes the passed line of input and returns the decrypted String
	 * @param inputLine the encrypted line of input
	 * @return the decrypted line of input (String)
	 */
	public static String decryptLine(String inputLine) {
		
		String decryptedLine = "";
		String[] tokens = inputLine.split(",");
		
		for(int i = 0; i < tokens.length; i++) {
	    	tokens[i] = new String(Base64.getDecoder().decode(tokens[i]));
	    	decryptedLine = decryptedLine.concat(tokens[i]);
	    	if (i != tokens.length - 1) {
	    		decryptedLine = decryptedLine.concat(",");
	    	}
	    }
		
		return decryptedLine;
		
	}
	/**
	 * Takes the passed line of output and returns the encryted byte array
	 * @param outputLine the String that will be encrypted
	 * @return the encrypted line (byte[])
	 */
	public static byte[] encryptLine(String outputLine) {
		
		String encryptedLine = "";
		String[] tokens = outputLine.split(",");
		
	    for(int i = 0; i < tokens.length; i++) {
	    	tokens[i] = Base64.getEncoder().encodeToString(tokens[i].getBytes());
	    	encryptedLine = encryptedLine.concat(tokens[i]);
	    	if (i != tokens.length - 1) {
	    		encryptedLine = encryptedLine.concat(",");
	    	}
	    }
	    
	    encryptedLine = encryptedLine.concat("\n");
		return encryptedLine.getBytes();
		
	}
	
}
