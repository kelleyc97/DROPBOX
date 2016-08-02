// This class keeps track of the information of a box. 

package com.h3c.dropbox.entity;


public class Box {
	private String accountName;
	private String accountPassword;
	private String boxName;
	private String boxPassword;
	private int maxStorage;
	private int lifeTime;
	private String readWrite;
	private String additional;

	// Creates a new box with account name, account password, box name, box password, max storage, 
	// life time and read and write configuration. 
	public Box(String accountName, String accountPassword,
			String boxName, String boxPassword, int maxStorage, int lifeTime, String readWrite, String additional) {
		this.accountName = accountName;
		this.accountPassword = accountPassword;
		this.boxName = boxName;
		this.boxPassword = boxPassword;
		this.maxStorage = maxStorage;
		this.lifeTime = lifeTime;
		this.readWrite = readWrite;
		this.additional = additional;
	}
	
	// Creates a box with only the account name and account password to authenticate
	// user identity. 
	public Box(String accountName, String accountPassword) {
		this(accountName, accountPassword, null, null, -1, -1, null, null);
	}

	// Returns the accountName
	public String getAccountName() {
		return accountName;
	}

	// Set the account name to the given account name. 
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	// Returns the accountName
	public String getAccountPassword() {
		return accountPassword;
	}

	// Set the accountPassword  to the given accountPassword. 	
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	// Returns the boxName
	public String getBoxName() {
		return boxName;
	}

	// Set the boxName to the given boxName. 
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	// Returns the boxPassword
	public String getBoxPassword() {
		return boxPassword;
	}

	// Set the boxPassword to the given boxPassword. 
	public void setBoxPassword(String boxPassword) {
		this.boxPassword = boxPassword;
	}

	// Returns the maxStorage
	public int getMaxStorage() {
		return maxStorage;
	}

	// Set the maxStorage to the given maxStorage. 
	public void setMaxStorage(int maxStorage) {
		this.maxStorage = maxStorage;
	}

	// Returns the life time
	public int getLifeTime() {
		return lifeTime;
	}

	// Set the life time to the given life time. 
	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}

	// Returns the read write configuration
	public String getReadWrite() {
		return readWrite;
	}

	// Set the read write configuration to the given read write configuration. 
	public void setReadWrite(String readWrite) {
		this.readWrite = readWrite;
	}
	
	public String getAdditional() {
		return additional;
	}
	
	public void setAdditional(String additional) {
		this.additional = additional;
	}

	
}