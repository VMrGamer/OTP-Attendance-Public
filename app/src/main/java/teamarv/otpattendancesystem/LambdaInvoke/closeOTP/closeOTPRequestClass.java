package teamarv.otpattendancesystem.LambdaInvoke.closeOTP;

import java.util.List;

public class closeOTPRequestClass {
    String teacherID;
    String courseID;
    String otp;
    List<String> program;
    public closeOTPRequestClass(String teacherID, String courseID, String otp, List<String> program) {
        this.teacherID = teacherID;
        this.courseID = courseID;
        this.otp = otp;
        this.program = program;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public List<String> getProgram() {
        return program;
    }

    public void setProgram(List<String> program) {
        this.program = program;
    }

    public closeOTPRequestClass() {
    }


}
