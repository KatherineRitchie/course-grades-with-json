import com.google.gson.Gson;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ParsingUtil {

    /**
     * This function accepts a JSON file name and a subject code and returns an array list
     * of course objects that are of that subject.
     * @param subject String of subject code eg "AAS"
     * @param JSONfile String of file name eg Fall2013.json. Must be from data folder.
     * @return List<Course> returns an ArrayList of Course objects
     */
    public static ArrayList<Course> coursesOfSubject(final String subject, final List<Course> unfilteredCourseList) {
        ArrayList<Course> filteredCoursesList = new ArrayList<>();

        for (int i = 0; i < unfilteredCourseList.size(); i++) {
            if (unfilteredCourseList.get(i).getSubject().equals(subject)) {
                System.out.println("");
                filteredCoursesList.add(unfilteredCourseList.get(i));

            }
        }

        return filteredCoursesList;
    }

    /**
     * This function accepts a JSON file name and an instructor name and returns an array list
     * of course objects that are of that subject.
     * @param instructor String of instructor name eg "Arai, Sayuri"
     * @param jsonFile String of file name eg Fall2013.json. Must be from data folder.
     * @return List<Course> returns an ArrayList of Course objects
     */
    public static ArrayList<Course> coursesOfInstructor(final String instructor, final List<Course> unfilteredCourseList) {
        ArrayList<Course> filteredCoursesList = new ArrayList<>();

        for (int i = 0; i < unfilteredCourseList.size(); i++) {
            if (unfilteredCourseList.get(i).getInstructor().equals(instructor)) {
                filteredCoursesList.add(unfilteredCourseList.get(i));
            }
        }

        return filteredCoursesList;
    }

    /**
     * Accepts ArrayList of Course Objects and min/max values and returns an ArrayList
     * of Courses within that number range.
     * @param min int minimum number value (inclusive)
     * @param max int maximum number value (inclusive)
     * @param unfilteredCourseList ArrayList<Course> that need to be filtered
     * @return ArrayList<Course> within range
     */
    public static ArrayList<Course> coursesInRange(final int min, final int max, final List<Course> unfilteredCourseList) {
        ArrayList<Course> filteredCoursesList = new ArrayList<>();

        for (int i = 0; i < unfilteredCourseList.size(); i++) {
            int courseNumber = unfilteredCourseList.get(i).getNumber();
            if (courseNumber >= min && courseNumber <= max) {
                filteredCoursesList.add(unfilteredCourseList.get(i));
            }
        }

        return filteredCoursesList;
    }

    /**
     * Accepts ArrayList of Course objects and returns an arrayList of Course Objects that have
     * a student population within a specified range.
     * @param min minimum number of students (inclusive)
     * @param max maximum number of students (inclusive)
     * @param unfilteredCourseList ArrayList of courses needing to be filtered
     * @return filtered ArrayList of Courses
     */
    public static ArrayList<Course> studentPopInRange(final int min, final int max, final ArrayList<Course> unfilteredCourseList) {
        ArrayList<Course> filteredCoursesList = new ArrayList<>();

        for (int i = 0; i < unfilteredCourseList.size(); i++) {
            int coursePopulation = countStudents(unfilteredCourseList.get(i));
            if (coursePopulation >= min && coursePopulation <= max) {
                filteredCoursesList.add(unfilteredCourseList.get(i));
            }
        }

        return filteredCoursesList;
    }

    /**
     * Accepts ArrayList of Course objects and two doubles as paramets for averages and returns
     * an ArrayList of Course Objects that have a GPA in that range.
     * @param min double minimum grade average (inclusive)
     * @param max double maximum grade average (inclusive)
     * @param unfilteredCourseList ArrayList of Course Objects that are to be filtered
     * @return ArrayList of Course objects with a grade average within the specified range
     */
    public static ArrayList<Course> coursesInGpaRange(final double min, final double max, final ArrayList<Course> unfilteredCourseList) {
        ArrayList<Course> filteredCoursesList = new ArrayList<>();

        for (int i = 0; i < unfilteredCourseList.size(); i++) {
            double courseGpa = unfilteredCourseList.get(i).getAverage();
            if (courseGpa >= min && courseGpa <= max) {
                filteredCoursesList.add(unfilteredCourseList.get(i));
            }
        }

        return filteredCoursesList;
    }

    /**
     * Accepts a search word and an ArrayList of Course Objects and returns an ArrayList of all
     * Course Objects that have the searchWord in the Title.
     * @param searchWord String searchWord
     * @param unfilteredCourseList
     * @return
     */
    public static ArrayList<Course> courseTitleSearch(final String searchWord, final ArrayList<Course> unfilteredCourseList) {
        ArrayList<Course> filteredCoursesList = new ArrayList<>();
        String lowerCaseSearchTerm = searchWord.toLowerCase();

        for (int i = 0; i < unfilteredCourseList.size(); i++) {
            String lowerCaseTitle = unfilteredCourseList.get(i).getTitle().toLowerCase();
            if (lowerCaseTitle.contains(lowerCaseSearchTerm)) {
                filteredCoursesList.add(unfilteredCourseList.get(i));
            }
        }

        return filteredCoursesList;
    }

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
