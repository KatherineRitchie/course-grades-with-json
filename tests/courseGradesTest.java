import com.google.gson.Gson;
import com.sun.tools.javac.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Arrays;

import static org.junit.Assert.*;

public class courseGradesTest {

    private static final String COURSE = "{ \"CRN\": 41758, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD1\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Arai, Sayuri\", \"Grades\": [6, 16, 5, 3, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.72 }";

    //private static final String COURSE_ARRAY = "[{ \"CRN\": 41758, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD1\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Arai, Sayuri\", \"Grades\": [6, 16, 5, 3, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.72 },\n" + "  { \"CRN\": 47100, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD2\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Arai, Sayuri\", \"Grades\": [6, 11, 4, 5, 6, 1, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.64 },\n" + "  { \"CRN\": 47102, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD3\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Davis, Thomas E\", \"Grades\": [2, 24, 1, 2, 4, 1, 1, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.75 },\n" + "  { \"CRN\": 51248, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD4\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Davis, Thomas E\", \"Grades\": [7, 16, 4, 4, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.71 },\n" + "  { \"CRN\": 51249, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD5\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Thomas, Merin A\", \"Grades\": [4, 12, 6, 3, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.7 },\n" + "  { \"CRN\": 51932, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD6\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Thomas, Merin A\", \"Grades\": [12, 14, 2, 2, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.83 },\n" + "  { \"CRN\": 58092, \"Subject\": \"AAS\", \"Number\": 287, \"Title\": \"Food and Asian Americans\", \"Section\": \"A\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Manalansan, Martin F\", \"Grades\": [0, 23, 2, 4, 2, 3, 2, 0, 0, 0, 0, 0, 0, 1], \"Average\": 3.65 }]";

    private static final String COURSE_ARRAY = "[{ \"CRN\": 31063, \"Subject\": \"UP\", \"Number\": 503, \"Title\": \"Physical Planning\", \"Section\": \"1G\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Allred, Dustin\", \"Grades\": [4, 18, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.92 },\n" + "  { \"CRN\": 52513, \"Subject\": \"UP\", \"Number\": 504, \"Title\": \"Urban History and Theory\", \"Section\": \"1G\", \"Type\": \"LEC\", \"Term\": 120138, \"Instructor\": \"Wilson, Beverly K\", \"Grades\": [9, 8, 3, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.76 },\n" + "  { \"CRN\": 60319, \"Subject\": \"VCM\", \"Number\": 649, \"Title\": \"Avian Medicine and Surgery\", \"Section\": \"E\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Welle, Kenneth R\", \"Grades\": [17, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.91 },\n" + "  { \"CRN\": 58864, \"Subject\": \"VCM\", \"Number\": 660, \"Title\": \"Advanced Equine Anatomy\", \"Section\": \"E\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Stewart, Matthew C\", \"Grades\": [0, 10, 0, 0, 9, 0, 0, 3, 0, 0, 3, 0, 0, 0], \"Average\": 3.04 },\n" + "  { \"CRN\": 54952, \"Subject\": \"VM\", \"Number\": 602, \"Title\": \"Structure and Function I\", \"Section\": \"AL1\", \"Type\": \"LEC\", \"Term\": 120138, \"Instructor\": \"Mahoney, Megan M\", \"Grades\": [0, 12, 0, 0, 58, 0, 0, 40, 0, 0, 13, 0, 0, 0], \"Average\": 2.56 },\n" + "  { \"CRN\": 56271, \"Subject\": \"VM\", \"Number\": 605, \"Title\": \"Pathobiology I\", \"Section\": \"AL1\", \"Type\": \"LEC\", \"Term\": 120138, \"Instructor\": \"Zuckermann, Federico A\", \"Grades\": [0, 12, 0, 0, 62, 0, 0, 40, 0, 0, 5, 0, 0, 0], \"Average\": 2.68 },]";

    private static Course course;
    private static Course[] courseArray;
    private static Course emptyCourse;
    private static Course nullCourse;


    @Before
    public void setUp() throws Exception {
        Gson courseGson = new Gson();
        course = courseGson.fromJson(COURSE, Course.class);

        Gson arrayGson = new Gson();
        courseArray = arrayGson.fromJson(COURSE_ARRAY, Course[].class);

        Gson emptyGson = new Gson();
        emptyCourse = emptyGson.fromJson("", Course.class);

        //Gson nullGson = new Gson();
        //nullCourse = nullGson.fromJson(null, Course.class);

    }

    @Test
    public void testCourseGradeBasic() {
        assertEquals(41758, course.getCrn());
    }

    @Test
    public void testCourseSubject() {
        assertEquals("AAS", course.getSubject());
    }

    @Test
    public void testArrayAverage() {
        assertEquals(2.68, courseArray[5].getAverage(), 0);

    }

    @Test
    public void testEmptyCourse() {
        try {
            emptyCourse.getAverage();
            fail(ErrorConstants.TESTING_NULL_POINTER_EXPECTED_MESSAGE);
        } catch(NullPointerException e) {
            assertTrue(true);
        }
    }

    /**@Test
    public void testJsonParserUtilites() {
        System.out.println(JsonParserUtilities.getFileContentsAsString("Fall2013.json"));
    }*/

    @Test
    public void testCoursesOfSubject() {
        String expectedCoursesListString = "[{ \"CRN\": 60319, \"Subject\": \"VCM\", \"Number\": 649, \"Title\": \"Avian Medicine and Surgery\", \"Section\": \"E\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Welle, Kenneth R\", \"Grades\": [17, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.91 },\n" + "  { \"CRN\": 58864, \"Subject\": \"VCM\", \"Number\": 660, \"Title\": \"Advanced Equine Anatomy\", \"Section\": \"E\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Stewart, Matthew C\", \"Grades\": [0, 10, 0, 0, 9, 0, 0, 3, 0, 0, 3, 0, 0, 0], \"Average\": 3.04 }]";
        Gson coursesOfSubjectGson = new Gson();
        Course[] expectedCourseArray;
        expectedCourseArray = coursesOfSubjectGson.fromJson(expectedCoursesListString, Course[].class);
        ArrayList<Course> expectedCourseList = new ArrayList<Course>(Arrays.asList(expectedCourseArray));
        assertTrue(expectedCourseList.equals(JsonParserUtilities.coursesOfSubject("VCM", "Fall2013.json")));
        ErrorConstants.successMessage();
    }


    @Test
    public void testCountStudents() {
        assertEquals(35, JsonParserUtilities.countStudents(course));
    }

    @Test
    public void testCourseToString() {
        System.out.println(course.courseToString());
        assertEquals(COURSE, course.courseToString());
    }


}
