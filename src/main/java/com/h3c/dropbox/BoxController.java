// This class controls creating and updating box information. 


package com.h3c.dropbox;

import com.h3c.dropbox.entity.Box;
import com.h3c.dropbox.model.Message;

import java.sql.SQLException;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class BoxController  {
	public static final int MAXLIFE = 30;
	public static final int MAXSTORAGE = 10;
	
	// Taking in information from the user such as accountname, accountpassword, boxname ,boxpassword, lifetime, 
	// max storage, read write configuration, and additional info, creates a ftp box and saves the inforamtion 
	// in the database. If created successful, then the ftp link will be returned along with warning messages if 
	// applicable. If not created successfully, the error message will be given. 
	public void updateOrCreateBox(String accountName, String accountPassword, String boxName,
			String boxPassword, int lifeTime, int maxStorage, String readWrite, String additionalInfo) throws SQLException{
		Box box = new Box(accountName, accountPassword, boxName, boxPassword, maxStorage, lifeTime, readWrite, additionalInfo);
		DBManager manager = new DBManager(box);
		if (manager.getRole() != null && !manager.getRole().equalsIgnoreCase("system")) {
			if (lifeTime > MAXLIFE) {
				lifeTime = MAXLIFE;
			}
			if (maxStorage > MAXSTORAGE) {
				maxStorage = MAXSTORAGE;
			}
		} else {
			lifeTime = MAXLIFE;
			maxStorage = MAXSTORAGE;
		}
		
		if (readWrite == null) {
			readWrite = "w";
		}
		
		Message message;
		if (authenticate(accountName, accountPassword)) {
			if (boxPassword == null) {
				boxPassword = generatePassword();
			}
			boolean createdBox = shellScriptCreate(boxName, boxPassword, readWrite);
			if (createdBox) {
				if (!saveData(accountName, accountPassword, boxName, boxPassword, maxStorage, lifeTime, readWrite, additionalInfo, box)) {
					message = new Message("Box still active", false, box);
				} else {
					message = new Message("", createdBox, box);
				}
			} else {
				message = new Message("", createdBox, null);
			}  
		} else {
			message = new Message("Illegal Account", false, null);
		}
		returnMessage(message);
	}
	
	// Converts the messages into XML format. 
	private void returnMessage(Message message) {
        try {  
            JAXBContext context = JAXBContext.newInstance(Message.class);  
            Marshaller marshaller = context.createMarshaller();  
            marshaller.marshal(message, System.out);  
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }
		
	}


	// Takes the box information accountname, accountpassword, boxname, boxpassword, maxstorage, lifetime and config, 
	// saves the data into the database. 
	private boolean saveData(String accountName, String accountPassword, String boxName, String boxPassword, int maxStorage,
			int lifeTime, String readWrite, String additionalInfo, Box box) throws SQLException {
		DBManager data = new DBManager(box);
		boolean activate = data.isActivate();
		data.save();
		return !activate;
	}

	// Taking boxname, boxPassword, read and write configuration, creates a ftp account. Returns the ftp link if 
	// created successful and nothing if not. 
	private boolean shellScriptCreate(String boxName, String boxPassword, String readWrite) {
		return true;
	}

	// Authenticates the account information with the given name and password. Returns the ID if account exists
	// and -1 if account does not exist. 
	private boolean authenticate(String accountName, String accountPassword) throws SQLException {
		Box box = new Box(accountName, accountPassword);
		DBManager login = new DBManager(box);
		return login.checkAccount() >= 1;
	}
	
	// Generates a sequence of password if box password was not given and returns the sequence. 
	private String generatePassword() {
		String values = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890~!@#$%^&*?";
		Random rand = new Random();
		String result = "";
		for (int i = 0; i < 8; i++) {
			int value = rand.nextInt(values.length());
			result += values.charAt(value);
		}
		return result;
	}
}