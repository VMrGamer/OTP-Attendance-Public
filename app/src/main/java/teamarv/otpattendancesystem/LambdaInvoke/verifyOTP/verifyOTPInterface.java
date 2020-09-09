package teamarv.otpattendancesystem.LambdaInvoke.verifyOTP;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;


public interface verifyOTPInterface {
    @LambdaFunction
    String VerifyOTP(verifyOTPRequestClass request);

    @LambdaFunction(functionName = "VerifyOTP")
    void noVerifyOTP(verifyOTPRequestClass requestClass);
}
