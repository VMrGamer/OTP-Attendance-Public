package teamarv.otpattendancesystem.LambdaInvoke.verifyOTP;

import java.util.List;

public class verifyOTPRequestClass {
    String batch;
    String courseID;
    List<String> program;
    String enrollmentID;
    String otp;

    public verifyOTPRequestClass(String batch, String courseID, List<String> program, String enrollmentID, String otp) {
        this.batch = batch;
        this.courseID = courseID;
        this.program = program;
        this.enrollmentID = enrollmentID;
        this.otp = otp;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public List<String> getProgram() {
        return program;
    }

    public void setProgram(List<String> program) {
        this.program = program;
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(String enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public verifyOTPRequestClass() {
    }
}
