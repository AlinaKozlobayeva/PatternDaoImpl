
package ua.com.kozlobayeva.dao.impl;

import ua.com.kozlobayeva.dao.UserDao;
import ua.com.kozlobayeva.entity.User;
import ua.com.kozlobayeva.entity.UserFields;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.mysql.jdbc.Driver;


/**
 * Implementation of UserDao.
 * Created by Alina on 24.03.2016.
 */
public class UserDaoImpl implements UserDao {


    private final String url;
    private static final String DB_PROPERTIES = "db_prop.txt";

    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE " + UserFields.LOGIN + "=?";
    private static final String SQL_CREATE_USER = "INSERT INTO users VALUES (DEFAULT, ?,?,?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET " + UserFields.NAME + "=?," + UserFields.LOGIN
            + "=?," + UserFields.PASSWORD + "=?,";
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE " + UserFields.LOGIN + "=?";

    /**
     * Class Constructor.
     */
    public UserDaoImpl() {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();

        // this way we obtain db.properties file from the CLASSPATH
        InputStream in;
        try {
            in = getClass().getClassLoader().getResourceAsStream(DB_PROPERTIES);
            if (in == null) {
                throw new IllegalStateException(
                        "File must be resides in classpath: " + DB_PROPERTIES);
            }
            props.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        url = props.getProperty("connection.url");
        if (url == null) {
            throw new IllegalStateException(
                    "You have to define connection.url property in " + DB_PROPERTIES);
        }
    }


    @Override
    public User findUserByLogin(String login) {
        Connection con = null;
        User res = null;
        try {
            con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                res = extractUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                close(con);
            }
        }
        return res;

    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ALL_USERS);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                close(con);
            }
        }
        return users;
    }

    @Override
    public boolean create(User user) {
        Connection con = null;
        int k = 0;
        try {
            con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getName());
            pstmt.setString(k++, user.getPassword());
            int count = pstmt.executeUpdate();
            if (count > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                close(con);
            }
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        Connection con = null;
        int k = 0;
        try {
            con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USER);
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getName());
            pstmt.setString(k++, user.getPassword());
            int count = pstmt.executeUpdate();
            if (count > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                close(con);
            }
        }
        return false;
    }

    @Override
    public boolean delete(String login) {
        Connection con = null;
        int k = 0;
        try {
            con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_USER);
            pstmt.setString(k++, login);
            int count = pstmt.executeUpdate();
            if (count > 0) {
               return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                close(con);
            }
        }
        return false;
    }

    private Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Connection con = DriverManager.getConnection(url);
        return con;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User(rs.getString(UserFields.NAME), rs.getString(UserFields.LOGIN), rs.getString(UserFields.PASSWORD));
        return user;
    }

    private void close(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Can't close connection");
            e.printStackTrace();
        }

    }


}