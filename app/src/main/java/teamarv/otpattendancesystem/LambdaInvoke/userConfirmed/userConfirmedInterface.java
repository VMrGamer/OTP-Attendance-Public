package teamarv.otpattendancesystem.LambdaInvoke.userConfirmed;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface userConfirmedInterface {
    @LambdaFunction
    String userConfirmed(userConfirmedRequestClass request);

    @LambdaFunction(functionName = "userConfirmed")
    void nouserConfirmed(userConfirmedRequestClass requestClass);
}
