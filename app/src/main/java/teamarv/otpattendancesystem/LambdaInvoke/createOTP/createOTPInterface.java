package teamarv.otpattendancesystem.LambdaInvoke.createOTP;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface createOTPInterface {
    @LambdaFunction
    String createOTP(createOTPRequestClass request);

    @LambdaFunction(functionName = "createOTP")
    void nocreateOTP(createOTPRequestClass requestClass);
}