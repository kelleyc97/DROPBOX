package com.h3c.dropbox;

		import java.sql.SQLException;

		import javax.jws.WebMethod;
		import javax.jws.WebParam;
		import javax.jws.WebResult;
		import javax.jws.WebService;

@WebService
public interface BoxControllerInterface {
	@WebMethod
	@WebResult
	void updateOrCreateBox(@WebParam String accountName, @WebParam String accountPassword, @WebParam String boxPassword,
						   @WebParam String boxName, @WebParam int lifeTime, @WebParam int maxStorage, @WebParam String readWrite,
						   @WebParam String additionalInfo) throws SQLException;
}
