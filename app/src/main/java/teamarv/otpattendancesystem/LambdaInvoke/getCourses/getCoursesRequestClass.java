package teamarv.otpattendancesystem.LambdaInvoke.getCourses;

public class getCoursesRequestClass {
    String teacherID;
    public getCoursesRequestClass(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public getCoursesRequestClass() {
    }


}
