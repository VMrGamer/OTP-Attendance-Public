package teamarv.otpattendancesystem.LambdaInvoke.createOTP;

import java.util.List;

public class createOTPRequestClass {
    String teacherID;
    String courseID;
    List<String> access;
    int multiBatch;
    String timeS;

    public createOTPRequestClass(String teacherID, String courseID, List<String> access, int multiBatch, String timeS) {
        this.teacherID = teacherID;
        this.courseID = courseID;
        this.access = access;
        this.multiBatch = multiBatch;
        this.timeS = timeS;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public List<String> getAccess() {
        return access;
    }

    public void setAccess(List<String> access) {
        this.access = access;
    }

    public int getMultiBatch() {
        return multiBatch;
    }

    public void setMultiBatch(int multiBatch) {
        this.multiBatch = multiBatch;
    }

    public String getTimeS() {
        return timeS;
    }

    public void setTimeS(String timeS) {
        this.timeS = timeS;
    }

    public createOTPRequestClass() {
    }


}
