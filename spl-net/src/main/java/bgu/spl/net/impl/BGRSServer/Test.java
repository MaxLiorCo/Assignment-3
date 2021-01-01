package bgu.spl.net.impl.BGRSServer;

public class Test {
    public static void main(String [] args){
        Database db = Database.getInstance();
        db.initialize("spl-net/Courses.txt");
        User user = new User("lior", "123", false);
        db.registerUser(user);
        user.registerToCourse(db.getCourses().get((short)101));
        user.registerToCourse(db.getCourses().get((short)102));
        user.registerToCourse(db.getCourses().get((short)201));
    }
}
