package teamarv.otpattendancesystem.LambdaInvoke.getCourses;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface getCoursesInterface {
    @LambdaFunction
    String getCourses(getCoursesRequestClass request);

    @LambdaFunction(functionName = "getCourses")
    void nogetCourses(getCoursesRequestClass requestClass);
}