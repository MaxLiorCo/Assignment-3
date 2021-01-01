package bgu.spl.net.impl.BGRSServer;


import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

    private Map<String, User> registeredUsers;
    private Map<Short, Course> courses;


    private static class SingletonHolder {
        private static final Database dataBase = new Database();
    }


    //to prevent user from creating new Database
    private Database() {
        registeredUsers = new ConcurrentHashMap<>();
        courses = new ConcurrentHashMap<>();
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
      public boolean initialize(String coursesFilePath) {
        File file = new File(coursesFilePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            User.initializeCourseArray(coursesFilePath); //the weird sorted by file, array of courses user has registered to
            String courseLine;
            while ((courseLine = br.readLine()) != null) {
                short courseNum = Short.decode(parser(courseLine)); // course ID
                courseLine = courseLine.substring(courseLine.indexOf('|') + 1);

                String courseName = parser(courseLine); // course name
                courseLine = courseLine.substring(courseLine.indexOf('|') + 1);

                List<Integer> kdamCourses = parseKdam(courseLine); // kdamCourses of the course
                courseLine = courseLine.substring(courseLine.indexOf('|') + 1);

                int max = Integer.decode(parser(courseLine)); // max students allowed to study this course concurrently

                Course toAdd = new Course(courseNum,courseName, kdamCourses, max); // Creating the course that will be added to the map
                courses.put(courseNum, toAdd);
            }
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }

        return true;
    }

    /**
     * @param courseLine
     * @return List of kdamCourses of a specific course
     */
    private List<Integer> parseKdam(String courseLine) {
        List<Integer> kdamCourses = new LinkedList<>();
        String helper = "";
        for (int i = 0; i < courseLine.length(); i++) {
            char temp = courseLine.charAt(i);
            if (temp == '[' | temp == ']') {
                if (!helper.isEmpty()) // will happen in situations like this: "[]", or "[.....,111]". In the first we don't add any course of course, but in the second we do.
                    kdamCourses.add(Integer.decode(helper));
                continue;
            }
            if (temp == '|') { // reached to the end of the kdamCourses sections, finished here
                break;
            }
            if (temp == ',') { // moving to next kdamCouse, current kdamCourse is translated and may be added to the list
                kdamCourses.add(Integer.decode(helper));
                helper = "";
            } else { // if none of the above happens, we still didn't finish to read the current kdamCourse
                helper = helper + temp;
            }
        }
        return kdamCourses;
    }

    /**
     * Returns next number/string in the courseLine
     * @param courseLine
     * @return String that is one of the following: courseNum, courseName, numMaxOfStudents
     */
    private String parser(String courseLine) {
        String accumulator = "";
        for (int i = 0; i < courseLine.length(); i++) {
            char temp = courseLine.charAt(i);
            if (temp == '|') {
                break;
            }
            accumulator = accumulator + temp;
        }
        return accumulator;
    }

    /**
     *
     * @param user we want to register
     * @return True iff the specific user wasn't already taken, thus user was registered successfully to the database.
     */
    public synchronized boolean registerUser(User user){
        if (registeredUsers.containsKey(user.getUserName()))
            return false;
        registeredUsers.put(user.getUserName(), user);
        return true;
    }
    public Map<String, User> getRegisteredUsers() {
        return registeredUsers;
    }

    public Map<Short, Course> getCourses() {
        return courses;
    }
}