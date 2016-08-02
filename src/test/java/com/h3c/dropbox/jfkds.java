package com.h3c.dropbox;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class jfkds {
public static void main(String[] args) throws FileNotFoundException, IOException {
	Properties prop = new Properties();
	
	prop.load(new FileReader(jfkds.class.getResource("configuration.properties").getFile()));
	System.out.println(prop.getProperty("1"));;
} 
}
