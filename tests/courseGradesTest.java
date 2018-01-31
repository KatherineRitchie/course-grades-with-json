import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class courseGradesTest {

    private static final String COURSE = "{ \"CRN\": 41758, \"Subject\": \"AAS\", \"Number\": 100, \"Title\": \"Intro Asian American Studies\", \"Section\": \"AD1\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Arai, Sayuri\", \"Grades\": [6, 16, 5, 3, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.72 }";
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

    /**
     * @Test
    public void testJsonParserUtilites() {
        System.out.println(Data.getFileContentsAsString("Fall2013.json"));
    }*/

    @Test
    public void testCoursesOfSubject() {
        ArrayList<Course> unfilteredCourseList = Data.getJsonFileToList("Fall2013.json");
        ArrayList<Course> actualCourseList = ParsingUtil.coursesOfSubject("VCM", unfilteredCourseList);
        String expectedCourseString = "[{ \"CRN\": 60319, \"Subject\": \"VCM\", \"Number\": 649, \"Title\": \"Avian Medicine and Surgery\", \"Section\": \"E\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Welle, Kenneth R\", \"Grades\": [17, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.91 },\n" + "  { \"CRN\": 58864, \"Subject\": \"VCM\", \"Number\": 660, \"Title\": \"Advanced Equine Anatomy\", \"Section\": \"E\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Stewart, Matthew C\", \"Grades\": [0, 10, 0, 0, 9, 0, 0, 3, 0, 0, 3, 0, 0, 0], \"Average\": 3.04 }]";
        ArrayList<Course> expectedCourseList = Data.getStringToList(expectedCourseString);

        assertTrue(actualCourseList.equals(expectedCourseList));
    }

    @Test
    public void testCoursesOfInstructor() {
        ArrayList<Course> unfilteredCourseList = Data.getJsonFileToList("Fall2013.json");
        ArrayList<Course> actualCourseList = ParsingUtil.coursesOfInstructor("Fleck, Margaret M", unfilteredCourseList);
        String expectedCourseString = "[{ \"CRN\": 51495, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"ADA\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [1, 3, 1, 4, 3, 3, 6, 3, 2, 2, 0, 0, 3, 0], \"Average\": 2.45 },\n" + "  { \"CRN\": 51497, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"ADC\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [2, 4, 4, 3, 7, 4, 0, 4, 0, 0, 1, 1, 0, 0], \"Average\": 3 },\n" + "  { \"CRN\": 51498, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"ADD\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [5, 5, 1, 3, 1, 3, 2, 3, 3, 0, 1, 0, 0, 0], \"Average\": 3.01 },\n" + "  { \"CRN\": 51499, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"ADE\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [1, 2, 5, 6, 3, 7, 1, 2, 0, 2, 0, 1, 1, 0], \"Average\": 2.83 },\n" + "  { \"CRN\": 60278, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"ADF\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [4, 1, 4, 6, 2, 3, 3, 2, 4, 0, 1, 0, 1, 0], \"Average\": 2.82 },\n" + "  { \"CRN\": 51500, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"BDA\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [1, 3, 1, 3, 4, 2, 4, 3, 4, 2, 0, 0, 2, 0], \"Average\": 2.47 },\n" + "  { \"CRN\": 51469, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"BDB\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [0, 3, 2, 4, 4, 5, 2, 1, 1, 1, 2, 0, 1, 0], \"Average\": 2.68 },\n" + "  { \"CRN\": 58923, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"BDC\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [1, 5, 2, 3, 4, 0, 3, 4, 4, 0, 0, 1, 2, 0], \"Average\": 2.61 },\n" + "  { \"CRN\": 59602, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"BDD\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [1, 6, 1, 4, 4, 3, 0, 3, 2, 1, 1, 1, 0, 1], \"Average\": 2.86 },\n" + "  { \"CRN\": 59603, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"BDE\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [1, 4, 4, 4, 4, 1, 3, 3, 2, 0, 0, 1, 2, 2], \"Average\": 2.75 },\n" + "  { \"CRN\": 60279, \"Subject\": \"CS\", \"Number\": 173, \"Title\": \"Discrete Structures\", \"Section\": \"BDF\", \"Type\": \"DIS\", \"Term\": 120138, \"Instructor\": \"Fleck, Margaret M\", \"Grades\": [1, 4, 4, 3, 4, 1, 2, 1, 3, 0, 2, 1, 1, 0], \"Average\": 2.73 }]";
        ArrayList<Course> expectedCourseList = Data.getStringToList(expectedCourseString);

        assertTrue(actualCourseList.equals(expectedCourseList));
    }

    @Test
    public void testCoursesInRange() {
        ArrayList<Course> unfilteredCourseList = Data.getJsonFileToList("Fall2013.json");
        ArrayList<Course> actualCourseList = ParsingUtil.coursesInRange(580, 580, unfilteredCourseList);
        String expectedCoursesString = "[{ \"CRN\": 33577, \"Subject\": \"ECE\", \"Number\": 580, \"Title\": \"Optimiz by Vector Space Methds\", \"Section\": \"N\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Srikant, Rayadurgam\", \"Grades\": [2, 11, 4, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.74 },\n" + "{ \"CRN\": 30296, \"Subject\": \"EPSY\", \"Number\": 580, \"Title\": \"Statistical Inference in Educ\", \"Section\": \"A\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Jackson, James L\", \"Grades\": [25, 8, 5, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.93 },\n" + "{ \"CRN\": 50081, \"Subject\": \"FIN\", \"Number\": 580, \"Title\": \"Behavioral Finance\", \"Section\": \"BF1\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Weisbenner, Scott J\", \"Grades\": [1, 6, 9, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.7 },\n" + "{ \"CRN\": 61562, \"Subject\": \"FIN\", \"Number\": 580, \"Title\": \"Business Tax Policy\", \"Section\": \"BT1\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Fullerton, Don\", \"Grades\": [0, 6, 8, 4, 5, 3, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.45 },\n" + "{ \"CRN\": 61574, \"Subject\": \"FIN\", \"Number\": 580, \"Title\": \"Health Care Policy\", \"Section\": \"HC1\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Reif, Julian C\", \"Grades\": [1, 5, 6, 7, 2, 1, 0, 1, 0, 0, 0, 0, 0, 0], \"Average\": 3.48 },\n" + "{ \"CRN\": 61646, \"Subject\": \"FIN\", \"Number\": 580, \"Title\": \"Health Care Policy\", \"Section\": \"HC2\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Reif, Julian C\", \"Grades\": [0, 5, 5, 17, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.42 },\n" + "{ \"CRN\": 61798, \"Subject\": \"FIN\", \"Number\": 580, \"Title\": \"Market Microstructure, Trading\", \"Section\": \"MMT\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Clark-Joseph, Adam D\", \"Grades\": [5, 9, 5, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.82 },\n" + "{ \"CRN\": 61652, \"Subject\": \"FIN\", \"Number\": 580, \"Title\": \"Retirement Policy\", \"Section\": \"RP1\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Brown, Jeffrey R\", \"Grades\": [0, 3, 13, 8, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.49 },\n" + "{ \"CRN\": 60270, \"Subject\": \"FIN\", \"Number\": 580, \"Title\": \"Wealth Management\", \"Section\": \"WM\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Sinow, David M\", \"Grades\": [1, 29, 6, 2, 9, 3, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.67 },\n" + "{ \"CRN\": 60487, \"Subject\": \"LIS\", \"Number\": 580, \"Title\": \"Rare Book and Spec Colls\", \"Section\": \"A\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Stromberg, Marten N\", \"Grades\": [10, 11, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.94 },\n" + "{ \"CRN\": 30709, \"Subject\": \"PHYS\", \"Number\": 580, \"Title\": \"Quantum Mechanics I\", \"Section\": \"A\", \"Type\": \"LEC\", \"Term\": 120138, \"Instructor\": \"Stack, John D\", \"Grades\": [19, 10, 9, 1, 3, 4, 2, 0, 0, 0, 0, 0, 0, 0], \"Average\": 3.68 }]";

        ArrayList<Course> expectedCourseList = Data.getStringToList(expectedCoursesString);

        assertTrue(actualCourseList.equals(expectedCourseList));
    }

    @Test
    public void testStudentPopInRange() {
        ArrayList<Course> unfilteredCourseList = Data.getJsonFileToList("Fall2013.json");
        ArrayList<Course> actualCourseList = ParsingUtil.studentPopInRange(200, 205, unfilteredCourseList);
        String expectedCoursesString = "[{ \"CRN\": 31555, \"Subject\": \"CHBE\", \"Number\": 221, \"Title\": \"Principles of CHE\", \"Section\": \"AE1\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Henderson, Jerrod A\", \"Grades\": [0, 16, 14, 37, 17, 17, 23, 24, 19, 3, 23, 0, 10, 2], \"Average\": 2.44 },\n" + "{ \"CRN\": 32042, \"Subject\": \"MATH\", \"Number\": 125, \"Title\": \"Elementary Linear Algebra\", \"Section\": \"D1\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Arana, Rebekah R\", \"Grades\": [2, 13, 26, 29, 32, 34, 21, 16, 10, 7, 6, 4, 0, 1], \"Average\": 2.77 },\n" + "{ \"CRN\": 37746, \"Subject\": \"MUS\", \"Number\": 133, \"Title\": \"Introduction to World Music\", \"Section\": \"BL1\", \"Type\": \"LEC\", \"Term\": 120138, \"Instructor\": \"Gordon, Lillie S\", \"Grades\": [10, 47, 38, 29, 40, 21, 6, 7, 0, 0, 0, 2, 4, 1], \"Average\": 3.28 }]";

        ArrayList<Course> expectedCourseList = Data.getStringToList(expectedCoursesString);

        assertTrue(actualCourseList.equals(expectedCourseList));
    }

    @Test
    public void testGpaInRange() {
        ArrayList<Course> unfilteredCourseList = Data.getJsonFileToList("Fall2013.json");
        ArrayList<Course> actualCourseList = ParsingUtil.coursesInGpaRange(1.00, 1.99, unfilteredCourseList);
        String expectedCoursesString = "[{ \"CRN\": 55386, \"Subject\": \"LER\", \"Number\": 100, \"Title\": \"Introduction to Labor Studies\", \"Section\": \"N\", \"Type\": \"ONL\", \"Term\": 120138, \"Instructor\": \"Fauke, Clare A\", \"Grades\": [0, 7, 1, 2, 3, 2, 0, 1, 2, 2, 1, 0, 10, 0], \"Average\": 1.99 },\n" + "{ \"CRN\": 30322, \"Subject\": \"MATH\", \"Number\": 2, \"Title\": \"Introductory Algebra\", \"Section\": \"B1\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Aydin-Mullen, Yelda\", \"Grades\": [0, 0, 0, 0, 0, 2, 1, 10, 5, 3, 3, 2, 5, 3], \"Average\": 1.43 },\n" + "{ \"CRN\": 42918, \"Subject\": \"RST\", \"Number\": 110, \"Title\": \"Leisure Service Delivery\", \"Section\": \"B\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Gower, Ryan K\", \"Grades\": [0, 2, 6, 11, 9, 9, 8, 10, 6, 2, 11, 0, 17, 1], \"Average\": 1.98 }]";

        ArrayList<Course> expectedCourseList = Data.getStringToList(expectedCoursesString);

        assertTrue(actualCourseList.equals(expectedCourseList));
    }

    @Test
    public void testCourseTitleSearch() {
        ArrayList<Course> unfilteredCourseList = Data.getJsonFileToList("Fall2013.json");
        ArrayList<Course> actualCourseList = ParsingUtil.courseTitleSearch("rail", unfilteredCourseList);
        String expectedCoursesString = "[{ \"CRN\": 53742, \"Subject\": \"CEE\", \"Number\": 408, \"Title\": \"Railroad Transportation Engrg\", \"Section\": \"E3\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Barkan, Christopher P\", \"Grades\": [1, 6, 5, 3, 6, 4, 3, 4, 1, 0, 1, 0, 0, 0], \"Average\": 3.09 },\n" + "{ \"CRN\": 53743, \"Subject\": \"CEE\", \"Number\": 409, \"Title\": \"Railroad Track Engineering\", \"Section\": \"TW3\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Uzarski, Donald R\", \"Grades\": [1, 3, 13, 2, 2, 5, 2, 1, 2, 0, 0, 0, 0, 0], \"Average\": 3.24 },\n" + "{ \"CRN\": 58934, \"Subject\": \"CEE\", \"Number\": 498, \"Title\": \"High Speed Rail Engineering\", \"Section\": \"HS3\", \"Type\": \"LCD\", \"Term\": 120138, \"Instructor\": \"Kao, Tsung-Chung\", \"Grades\": [1, 3, 15, 6, 3, 4, 1, 0, 1, 0, 0, 0, 0, 0], \"Average\": 3.37 }]";

        ArrayList<Course> expectedCourseList = Data.getStringToList(expectedCoursesString);

        assertTrue(actualCourseList.equals(expectedCourseList));
    }


    /*@Test
    public void testCoursesOfSubjectEmpty() {
        Course[] expectedCourseArray = new Course[0];
        ArrayList<Course> expectedCourseList = new ArrayList<>(Arrays.asList(expectedCourseArray));
        assertTrue(expectedCourseList.equals(ParsingUtil.coursesOfSubject("ZZ", "Fall2013.json")));
        ErrorConstants.successMessage();
    }*/

    @Test
    public void testCourseToString() {
        assertEquals(COURSE, Data.getCourseToString(course));
    }

    @Test
    public void testCountStudents() {
        assertEquals(35, ParsingUtil.countStudents(course));
    }

}
