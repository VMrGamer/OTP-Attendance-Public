package teamarv.otpattendancesystem.LambdaInvoke.userConfirmed;

public class userConfirmedRequestClass {
    String username;
    String enrollmentID;
    String teacherID;
    String batch;
    String programID;
    String school;
    String multiBatch;

    public userConfirmedRequestClass() {
    }

    public userConfirmedRequestClass(String username, String enrollmentID, String teacherID, String batch, String programID, String school, String multiBatch) {
        this.username = username;
        this.enrollmentID = enrollmentID;
        this.teacherID = teacherID;
        this.batch = batch;
        this.programID = programID;
        this.school = school;
        this.multiBatch = multiBatch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(String enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMultiBatch() {
        return multiBatch;
    }

    public void setMultiBatch(String multiBatch) {
        this.multiBatch = multiBatch;
    }

}
