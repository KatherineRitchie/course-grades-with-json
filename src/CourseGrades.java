
public class CourseGrades {

    /**
     * {
     "CRN":41758,
     "Subject":"AAS",
     "Number":100,
     "Title":"Intro Asian American Studies",
     "Section":"AD1",
     "Type":"DIS",
     "Term":120138,
     "Instructor":"Arai, Sayuri",
     "Grades":[
     6,
     16,
     5,
     3,
     2,
     3,
     0,
     0,
     0,
     0,
     0,
     0,
     0,
     0
     ],
     "Average":3.72
     }
     */

    private String exampleCourse = "";

    private int CRN;
    private String Subject;
    private int Number;
    private String Title;
    private String Section;
    private String Type;
    private int Term;
    private String Instructor;
    private int[] Grades;
    private double Average;

    public int getCRN() {
        return CRN;
    }

    public String getSubject() {
        return Subject;
    }

    public int getNumber() {
        return Number;
    }

    public String getTitle() {
        return Title;
    }

    public String getSection() {
        return Section;
    }

    public String getType() {
        return Type;
    }

    public int getTerm() {
        return Term;
    }

    public String getInstructor() {
        return Instructor;
    }

    public int[] getGrades() {
        return Grades;
    }

    public double getAverage() {
        return Average;
    }

    public CourseGrades(int CRN) {
    }


}
