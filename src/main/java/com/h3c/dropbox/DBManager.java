// This class connects to database, where our account datas are being stored. It can update 
// information, add data as well as read data. 
package com.h3c.dropbox;

import com.h3c.dropbox.entity.Box;

import java.sql.*;
import java.util.Date;

public class DBManager {

    // make these variables static and final, they will be only initialized once when program starting up, and their value can not be changed;
    private static final String DRIVE_NAME = "net.sourceforge.jtds.jdbc.Driver";
    private static final String DB_URL = "jdbc:jtds:sqlserver://10.8.1.151:1433/Uhs_Dropbox_ITG";
    private static final String USERNAME = "DropboxDBAdmin";
    private static final String USER_PASSWD = "5dRKt01i0r";
    Box box;

    // get db connection
    // to solve Exception: java.sql.SQLException: No suitable driver found for jdbc:jtds:sqlserver://10.8.1.151:1433/Uhs_Dropbox_ITG
    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVE_NAME);
            connection = DriverManager.getConnection(DB_URL, USERNAME, USER_PASSWD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Creates a new manager with the given box with associated information.
    public DBManager(Box box) throws SQLException {
        this.box = box;
    }

    public DBManager() throws SQLException {
        this(null);
    }


    // Connects to the database and then checks if the box exists, if it exists, it updates the
    // information, if it does not exist then it will add the given box as a new data entry.
    public void save() {
        try {
            // Class.forName(driverName);
            int findId = checkBox();
            if (findId > 0 && !isActivate()) {
                update(findId);
            } else if (!isActivate()) {
                insert();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isActivate() throws SQLException {
        // get db connection
        Connection conn = this.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("select * from Box");
        ResultSet result = pstmt.executeQuery();
        String target = box.getBoxName();
        String current = "";
        int boxStatus = -1;
        while (result.next() && current != target) {
            String name = result.getString("Name");
            int status = result.getInt("Status");
            if (name.equals(target)) {
                current = target;
                boxStatus = status;
            }
        }
        // clean resources
        this.closeResource(conn, pstmt, result);
        return boxStatus == 1;
    }


    // Inserts a new line of data into the database with the given information from box.
    private void insert() throws SQLException {
        String command = "insert into dbo.Box (AccountId, Name, Password, RWConfig, "
                + "EndDate, MaxStorage, Additional) values (?, ?, ? , ? , (GetDate() + ?) , ?, ?)";
        Connection conn = this.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(command);
        pstmt.setInt(1, checkAccount());
        pstmt.setString(2, box.getBoxName());
        pstmt.setString(3, box.getBoxPassword());
        pstmt.setString(4, String.valueOf(box.getReadWrite()));
        pstmt.setInt(5, box.getLifeTime());
        pstmt.setInt(6, box.getMaxStorage());
        pstmt.setString(7, box.getAdditional());
        pstmt.executeUpdate();
        this.closeResource(conn, pstmt, null);

    }

    // Updates the date and the status of the line of data of the given Id findId to
    // current date and active status.
    private void update(int findId) throws SQLException {
        String command = "update Box set Status = 1, StartDate = GetDate(), "
                + "EndDate = (GetDate() + ?) where Id = ?";
        Connection conn = this.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(command);
        pstmt.setInt(1, box.getLifeTime());
        pstmt.setInt(2, findId);
        pstmt.executeUpdate();
        this.closeResource(conn, pstmt, null);
    }

    // Goes through the database and searches for the existence of the given box
    // information.
    private int checkBox() throws SQLException {
        Connection conn = this.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("select * from Box");
        ResultSet result = pstmt.executeQuery();
        String target = box.getBoxName();
        String current = "";
        int id = -1;
        while (result.next() && current != target) {
            String name = result.getString("Name");
            int accountId = result.getInt("Id");
            if (name.equals(target)) {
                current = name;
                id = accountId;
            }
        }
        this.closeResource(conn, pstmt, result);

        return id;
    }

    public String getRole() throws SQLException {
        Connection dbConn = this.getConnection();
        PreparedStatement pstmt = dbConn.prepareStatement("select * from Account");
        ResultSet result = pstmt.executeQuery();
        String name = box.getAccountName();
        String current = null;
        while (result.next() && current == null) {
            String names = result.getString("Name");
            String status = result.getString("Role");
            if (names.equals(name)) {
                current = status;
            }
        }
        this.closeResource(dbConn, pstmt, result);
        return current;

    }

    // Checks if the given account information in the box exists in the database.
    public int checkAccount() throws SQLException {
        Connection dbConn = this.getConnection();
        PreparedStatement pstmt = dbConn.prepareStatement("select * from Account");
        ResultSet result = pstmt.executeQuery();
        String target = box.getAccountName();
        String targetPassword = box.getAccountPassword();
        String current = "";
        int id = -1;
        while (result.next() && current != targetPassword) {
            String name = result.getString("Name");
            String pw = result.getString("Pwd");
            int accountId = result.getInt("Id");
            if (name.equals(target)) {
                current = pw;
                id = accountId;
            }
        }
        this.closeResource(dbConn, pstmt, result);
        return id;
    }

    public void checkExpire() throws SQLException {
        Connection dbConn = this.getConnection();
        PreparedStatement pstmt = dbConn.prepareStatement("select * from Box");
        ResultSet result = pstmt.executeQuery();
        while (result.next()) {
            Date date = result.getTimestamp("EndDate");
            if (System.currentTimeMillis() >= date.getTime()) {
                //call shell to delete
                int findId = result.getInt("Id");
                String command = "update Box set Status = 0 where Id = ?";
                // if the PreparedStatement object has not closed, it can be re-used;
                pstmt = dbConn.prepareStatement(command);
                pstmt.setInt(1, findId);
                pstmt.executeUpdate();
                pstmt.close();
            }
        }
        this.closeResource(dbConn, pstmt, result);
    }

    /**
     * close database connection, PreparedStatement , ResultSet
     *
     * @param conn
     * @param pstmt
     * @param rs
     */
    private void closeResource(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
