package com.h3c.dropbox;

import java.sql.SQLException;


public class DropboxImpl implements BoxControllerInterface{
	  public void updateOrCreateBox(String accountName, String accountPassword, String boxPassword,
				String boxName, int lifeTime, int maxStorage, String readWrite, String additionalInfo)
				throws SQLException {
		 
		  BoxController boxControl = new BoxController();
		   boxControl.updateOrCreateBox(accountName, accountPassword, boxPassword, boxName, 
				  lifeTime, maxStorage, readWrite, additionalInfo);
	  }
}
