package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class CyberStudent extends Student {

	public CyberStudent(int id, String jmeno, String prijmeni, int year, List<Integer> grades) {
		super(id, jmeno, prijmeni, year, grades);
	}

	public CyberStudent(String jmeno, String prijmeni, int year) {
		super(jmeno, prijmeni, year);
	}
	
	@Override
	public void showSkill() {
		String fullName = (jmeno + " " + prijmeni).toUpperCase();
		
		try {
			MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
			
			byte[] hashBytes = mDigest.digest(fullName.getBytes());
			
			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append(0);
				hexString.append(hex);
			}
			
	        String hash = hexString.toString();
	        System.out.println("SHA-256 Haš celé jméno: " + hash);
	        
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 Error", e);
		}
	}
	

}
