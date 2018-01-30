import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JsonParserUtilities {
    public static final String[] JSON_FILES = {"Fall2013.json", "Fall2014.json", "Spring2013.json",
            "Spring2014.json", "Summer2013.json", "Summer2014.json"};

    /**
     * Return the names of the data files as an ArrayList
     *
     * @return an ArrayList of Strings containing the names of the JSON files
     */
    public static List<String> getJsonFilesAsList() {
        // Arrays is a Java library containing utility functions for working with Java arrays.  We're
        // using the 'asList' method which returns a List interface for an existing array.  We use
        // this to construct an actual ArrayList.  Observant readers might note that this function
        // returns an ArrayList, but the return type of the function is a List.  List is an interface
        // that ArrayList implements; we'll talk about interfaces on Tuesday.
        return new ArrayList<String>(Arrays.asList(JSON_FILES));
    }

    /**
     * This function reads the contents of a file located in the project's 'data' directory into a String
     *
     * @param filename contains the name of file
     * @return a String containing the file's contents
     */
    public static String getFileContentsAsString(String filename) {

        // Java uses Paths as an operating system-independent specification of the location of files.
        // In this case, we're looking for files that are in a directory called 'data' located in the
        // root directory of the project, which is the 'current working directory'.
        final Path path = FileSystems.getDefault().getPath("data", filename);

        try {
            // Read all of the bytes out of the file specified by 'path' and then convert those bytes
            // into a Java String.  Because this operation can fail if the file doesn't exist, we
            // include this in a try/catch block
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            // Since we couldn't find the file, there is no point in trying to continue.  Let the
            // user know what happened and exit the run of the program.  Note: we're only exiting
            // in this way because we haven't talked about exceptions and throwing them in CS 126 yet.
            System.out.println("Couldn't find file: " + filename);
            System.exit(-1);
            return null;  // note that this return will never execute, but Java wants it there.
        }
    }

    /**
     * This function accepts a JSON file name and a subject code and returns an array list
     * of course objects that are of that subject.
     * @param subject String of subject code eg "AAS"
     * @param JSONfile String of file name eg Fall2013.json. Must be from data folder.
     * @return List<Course> returns an ArrayList of Course objects
     */
    public static List<Course> coursesOfSubject(final String subject, final String JSONfile) {
        Gson arrayGson = new Gson();
        String JSONFilesString = getFileContentsAsString(JSONfile);
        Course[] courseArray = arrayGson.fromJson(JSONFilesString, Course[].class);

        ArrayList<Course> matchingCoursesList = new ArrayList<>();

        for (int i = 0; i < courseArray.length; i++) {
            if (courseArray[i].getSubject().equals(subject)) {
                System.out.println("");
                matchingCoursesList.add(courseArray[i]);

            }
        }

        return matchingCoursesList;

    }

    /**
     * This function accepts a JSON file name and an instructor name and returns an array list
     * of course objects that are of that subject.
     * @param instructor String of instructor name eg "Arai, Sayuri"
     * @param JSONfile String of file name eg Fall2013.json. Must be from data folder.
     * @return List<Course> returns an ArrayList of Course objects
     */
    public static List<Course> coursesOfInstructor(final String instructor, final String JSONfile) {
        Gson arrayGson = new Gson();
        String JSONFilesString = getFileContentsAsString(JSONfile);
        Course[] courseArray = arrayGson.fromJson(JSONFilesString, Course[].class);

        ArrayList<Course> matchingCoursesList = null;

        for (int i = 0; i < courseArray.length; i++) {
            if (courseArray[i].getInstructor().equals(instructor)) {
                matchingCoursesList.add(courseArray[i]);

            }
        }

        return matchingCoursesList;

    }

    //public static List<Course> coursesInRange(final int min, final int max, final String JSONfile) { }

    /**
     * Accepts course object and returns number of students enrolled by summing grade array.
     * @param course A course object
     * @return int the number of students
     */
    public static int countStudents(final Course course) {
        int[] gradeArray = course.getGrades();
        int noOfStudents = 0;
        for (int i = 0; i < gradeArray.length; i++) {
            noOfStudents += gradeArray[i];
        }
        return noOfStudents;
    }

}
