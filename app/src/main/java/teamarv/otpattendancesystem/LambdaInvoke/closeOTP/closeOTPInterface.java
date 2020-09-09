package teamarv.otpattendancesystem.LambdaInvoke.closeOTP;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface closeOTPInterface {
    @LambdaFunction
    String closeOTP(closeOTPRequestClass request);

    @LambdaFunction(functionName = "closeOTP")
    void nocloseOTP(closeOTPRequestClass requestClass);
}