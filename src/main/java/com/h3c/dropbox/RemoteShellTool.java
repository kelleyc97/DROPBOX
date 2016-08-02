package com.h3c.dropbox;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class RemoteShellTool {

	private Connection conn;
	private String ipAddr;
	private String charset = Charset.defaultCharset().toString();
	private String userName;
	private String password;

	public RemoteShellTool() {
		this.ipAddr = "172.25.20.64";
		this.userName = "root";
		this.password = "hyg%G4#23";
		this.charset = "utf-8";

	}

	public boolean login() throws IOException {
		conn = new Connection(ipAddr);
		conn.connect(); // 连接
		return conn.authenticateWithPassword(userName, password); // 认证
	}

	public String exec(String cmds) {
		InputStream in = null;
		String result = "";
		try {
			if (this.login()) {
//				System.out.println("connected");
				Session session = conn.openSession(); // 打开一个会话
				session.execCommand(cmds);
				
				in = session.getStdout();
				result = this.processStdout(in, this.charset);
				session.close();
				conn.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	  public String processStdout(InputStream in, String charset) {  
	      
	        byte[] buf = new byte[1024];  
	        StringBuffer sb = new StringBuffer();  
	        try {  
	            while (in.read(buf) != -1) {  
//	            	System.out.println("reading it");
	                sb.append(new String(buf, charset));  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return sb.toString();  
	    }  

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		String result = tool.exec("sh /usr/test/test.sh");
//		System.out.print(result);
	}

}
