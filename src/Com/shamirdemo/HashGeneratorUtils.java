package Com.shamirdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hash functions utility class.
 * @author www.codejava.net
 *
 */
public class HashGeneratorUtils {
	public HashGeneratorUtils() {
		
	}
	
	
	
	public static String generateSHA256(String message) throws HashGenerationException {
		return hashString(message, "SHA-256");
	}
	
	
public static void main(String args[]) throws HashGenerationException {
		HashGeneratorUtils generatorUtils=new HashGeneratorUtils();
	
		String abc1=generatorUtils.generateSHA256("hello");
		System.out.println(" abc is "+abc1);
	}
	
	
	private static String hashString(String message, String algorithm)
			throws HashGenerationException {
		
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
			
			return convertByteArrayToHexString(hashedBytes);
		} catch (Exception ex) {
			throw new HashGenerationException(
					"Could not generate hash from String", ex);
		}
	}
	
	private static String hashFile(File file, String algorithm)
			throws HashGenerationException, FileNotFoundException, NoSuchAlgorithmException, IOException {
		 FileInputStream inputStream = new FileInputStream(file); 
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			
			byte[] bytesBuffer = new byte[1024];
			int bytesRead = -1;
			
			while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
				digest.update(bytesBuffer, 0, bytesRead);
			}
			
			byte[] hashedBytes = digest.digest();
			
			return convertByteArrayToHexString(hashedBytes);
			
	}
	
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}		
		return stringBuffer.toString();
	}
}
