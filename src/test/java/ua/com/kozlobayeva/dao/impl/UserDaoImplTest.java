package ua.com.kozlobayeva.dao.impl;

import org.junit.Assert;
import org.junit.*;
import ua.com.kozlobayeva.entity.User;

import java.sql.*;
import java.util.List;

public class UserDaoImplTest {

    private UserDaoImpl userDao;
    private static final String TEST_LOGIN = "test1";
    private static final String TEST_PASSWORD = "qwert";

    private static final String TEST_DB_URL = "jdbc:mysql://localhost/test_userdb?user=root&password=root";
    private static final String REMOVE_DB = "DROP SCHEMA IF EXISTS test_userdb";
    private static final String CREATE_DB = "CREATE SCHEMA test_userdb";
    private static final String USE_DB = "USE test_userdb";
    private static final String CREATE_TABLE = "CREATE TABLE users (login VARCHAR(45) NOT NULL, password VARCHAR(45) NOT NULL, username VARCHAR(45) NOT NULL, PRIMARY KEY (login)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
    private static final String INSERT_TEST_DATA = "INSERT INTO users VALUES ('test1','qwert','test1'), ('test2','123','testPassword')";

    ;
    ;

    @Before
    public void setUp() throws Exception {
        userDao = new UserDaoImpl(TEST_DB_URL);
        Connection con = null;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection(TEST_DB_URL);
            Statement pstmt = con.createStatement();
            pstmt.executeUpdate(REMOVE_DB);
            pstmt.executeUpdate(CREATE_DB);
            pstmt.executeUpdate(USE_DB);
            pstmt.executeUpdate(CREATE_TABLE);
            pstmt.executeUpdate(INSERT_TEST_DATA);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    @Test
    public void testFindUserByLogin() throws Exception {
        Assert.assertNull(userDao.findUserByLogin("test3"));
        User user = userDao.findUserByLogin(TEST_LOGIN);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getLogin(), TEST_LOGIN);
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> userList = userDao.findAll();
        for (int i = 0; i < userList.size(); i++) {
            Assert.assertNotNull(userList.get(i));
        }

    }

    @Test
    public void testCreate() throws Exception {
        User user = new User("createTest", "createTest", "createTest");
        Assert.assertTrue(userDao.create(user));
        Assert.assertNotNull(userDao.findUserByLogin("createTest"));
    }

    @Test
    public void testUpdate() throws Exception {
        User user = userDao.findUserByLogin(TEST_LOGIN);
        userDao.update(TEST_LOGIN, "changedPassword", "changedUsername");
        Assert.assertNotEquals(user, userDao.findUserByLogin(TEST_LOGIN));

    }

    @Test
    public void testDelete() throws Exception {
        userDao.delete(TEST_LOGIN);
        Assert.assertNull(userDao.findUserByLogin(TEST_LOGIN));

    }

}