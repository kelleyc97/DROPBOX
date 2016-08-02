package com.h3c.dropbox;

import java.sql.SQLException;
import java.util.TimerTask;

public class Cleansing extends TimerTask {
	public void run() {
		try {
			DBManager manage = new DBManager();
			manage.checkExpire();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

