package ua.com.kozlobayeva.DAO;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for creating DAO for every entity.
 * @param <K>
 * @param <T>
 * Created by Alina on 24.03.2016.
 */
public interface GenericDao<K extends Serializable, T> {
    /**
     * Method for finding all T objects in database.
     * @return list of T objects
     */
    List<T> findAll();

    /**
     * Method for creating new user.
     * @param value key
     * @return true if object was created and false if not
     */
    boolean create(T value);

    /**
     * Method for updating user.
     * @param value key
     * @return true if update successful and false otherwise
     */
    boolean update(T value);

    /**
     * Method for deleting user with K key.
     * @param value key for user
     * @return true if delete successful and false otherwise
     */
    boolean delete(K value);


}
