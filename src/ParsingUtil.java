import java.util.ArrayList;
import java.util.List;


public class ParsingUtil {

    /**
     * Accepts ArrayList of course objects and subject code and returns an ArrayList of Course objects
     * that are of that subject.
     * @param subject String e.g. "AAS"
     * @param unfilteredCourseList ArrayList of unfiltered Course objects
     * @return ArrayList of Course Objects that are the specified subject
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

    /**
     * Accepts ArrayList of course objects and an instructor name and returns an ArrayList of Course objects
     * that are taught by that instructor.
     * @param instructor String e.g. "Fleck, Margaret"
     * @param unfilteredCourseList ArrayList of unfiltered Course objects
     * @return ArrayList of Course Objects that are taught by the specified instructor
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
     * @param unfilteredCourseList ArrayList of Courses to be filtered
     * @return ArrayList of Course objects with a title that matches search term
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
     * Counts all students in all courses combined and returns an int.
     * Note: may count duplicate students.
     * @param courseArrayList ArrayList of Course objects
     * @return int number of students in courses
     */
    public static int countStudents(ArrayList<Course> courseArrayList) {
        int totalStudentsInt = 0;

        for (int i = 0; i < courseArrayList.size(); i++) {
            int courseStudentCount = countStudents(courseArrayList.get(i));
            totalStudentsInt += courseStudentCount;
        }

        return totalStudentsInt;
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

    /**
     * Counts students within grade range and returns an int.
     * @param minGrade String e.g. "A+"
     * @param maxGrade String e.g. "W"
     * @param courseList ArrayList of Courses to be counted
     * @return int count of students within grade range
     */
    public static int countStudentsInRange(final String minGrade, final String maxGrade, final ArrayList<Course> courseList) {
        int totalStudentsInRange = 0;
        try {
            int minGradeIdx = gradeToIndex(minGrade);
            int maxGradeIdx = gradeToIndex(maxGrade);

            for (int listIdx = 0; listIdx < courseList.size(); listIdx++) {
                int[] gradeArray = courseList.get(listIdx).getGrades();
                for (int gradeIdx = maxGradeIdx; gradeIdx < minGradeIdx + 1; gradeIdx++) {
                    totalStudentsInRange += gradeArray[gradeIdx];
                }
            }
            return totalStudentsInRange;
        } catch (IllegalArgumentException e) {
            System.out.println(ErrorConstants.ILLEGAL_ARGUMENT_EXCEPTION);
        }
        return -1;
    }

    /**
     * converts grade string into an index for the grade array. Used primarily as a helper function.
     * throws and IllegalArgumentException if a non-grade input is passed
     * @param grade String of grade e.g. "b+"
     * @return int gradeIdx grade Index
     * @throws IllegalArgumentException in case of illegal 'grades' e.g. "hello" or "f-"
     */
    public static int gradeToIndex(final String grade) throws IllegalArgumentException {
        int gradeIdx = -1;

        switch (grade.toLowerCase()) {
            case "a+":
                gradeIdx = 0;
                break;
            case "a":
                gradeIdx = 1;
                break;
            case "a-":
                gradeIdx = 2;
                break;
            case "b+":
                gradeIdx = 3;
                break;
            case "b":
                gradeIdx = 4;
                break;
            case "b-":
                gradeIdx = 5;
                break;
            case "c+":
                gradeIdx = 6;
                break;
            case "c":
                gradeIdx = 7;
                break;
            case "c-":
                gradeIdx = 8;
                break;
            case "d+":
                gradeIdx = 9;
                break;
            case "d":
                gradeIdx = 10;
                break;
            case "d-":
                gradeIdx = 11;
                break;
            case "f":
                gradeIdx = 12;
                break;
            case "w":
                gradeIdx = 13;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return gradeIdx;
    }

    /**
     * Returns average of a collection of Courses, weighted by how many students took the Course.
     * @param courseArrayList ArrayList of Course objects
     * @return double weighted average
     */
    public static double weightedAverage(ArrayList<Course> courseArrayList) {
        double gradeSum = 0.0;

        for (int i = 0; i < courseArrayList.size(); i++) {
            double courseStudentCount = (double) countStudents(courseArrayList.get(i));
            double courseGpa = courseArrayList.get(i).getAverage();
            gradeSum += courseStudentCount * courseGpa;
        }

        double totalStudents = (double) countStudents(courseArrayList);
        return gradeSum / totalStudents;
    }

}
