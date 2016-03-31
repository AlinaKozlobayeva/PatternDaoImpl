package ua.com.kozlobayeva.entity;


/**
 * Created by Alina on 24.03.2016.
 */
public class User {
    private String name;
    private String login;
    private String password;


    /**
     * Constructor for class User insert parameters.
     * @param name user name
     * @param login user login in system
     * @param password user password in system
     */

    public User(String login, String password, String name) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
    /**
     *@return user name
     */
    public String getName() {
        return name;
    }
    /**
     *
     *@param name user name
     *
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     *@return specific user login
     */
    public String getLogin() {
        return login;
    }
    /**
     *@param login specific login for user
     *
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**@param password specific password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
