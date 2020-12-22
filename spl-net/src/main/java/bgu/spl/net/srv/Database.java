package bgu.spl.net.srv;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

    Map<String, User> registeredUsers;
    Map<Integer, Course> courses;

    private static class SingletonHolder{
        private static final Database dataBase = new Database();
    }


    //to prevent user from creating new Database
    private Database() {
        registeredUsers = new HashMap<>();
        courses = new HashMap<>();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return SingletonHolder.dataBase;
    }

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath) {
        File file = new File(coursesFilePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            return false;
        }
        return true;
    }
}