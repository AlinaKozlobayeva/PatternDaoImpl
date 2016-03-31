
package ua.com.kozlobayeva.dao.impl;

import com.mysql.jdbc.Driver;
import ua.com.kozlobayeva.dao.UserDao;
import ua.com.kozlobayeva.entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Implementation of UserDao.
 * Created by Alina on 24.03.2016.
 */
public class UserDaoImpl implements UserDao {


    private final String url;
    private static final String DB_PROPERTIES = "db_prop.txt";
    private static final String NAME = "username";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE " + LOGIN + "=?";
    private static final String SQL_CREATE_USER = "INSERT INTO users VALUES (?,?,?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET " + NAME + "=?," + PASSWORD + "=? WHERE " + LOGIN  + "=?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE " + LOGIN + "=?";



    /**
     * Class Constructor. Getting connection url from property.
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
    }

    /**
     * Create DAO with specified connection url.
     * @param url database connection url.
     */
    public UserDaoImpl(String url) {
        this.url = url;
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
            close(con);
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
        int k = 1;
        try {
            con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getName());
            int count = pstmt.executeUpdate();
            if (count > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();

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
    public boolean update(String login, String changedPassword, String changedUsername) {
        Connection con = null;
        int k = 1;
        try {
            con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USER);
            pstmt.setString(k++, login);
            pstmt.setString(k++, changedPassword);
            pstmt.setString(k++, changedUsername);
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
        int k = 1;
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
        return DriverManager.getConnection(url);
    }

    private User extractUser(ResultSet rs) throws SQLException {
     return new User(rs.getString(LOGIN), rs.getString(PASSWORD), rs.getString(NAME));
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
