package teamarv.otpattendancesystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import teamarv.otpattendancesystem.Cognito.AppHelper;
import teamarv.otpattendancesystem.Cognito.UserAttributesAdapter;
import teamarv.otpattendancesystem.Fragments.createOTPFragment;
import teamarv.otpattendancesystem.Fragments.verifyOTPFragment;
import teamarv.otpattendancesystem.LambdaInvoke.closeOTP.closeOTPInterface;
import teamarv.otpattendancesystem.LambdaInvoke.closeOTP.closeOTPRequestClass;
import teamarv.otpattendancesystem.LambdaInvoke.createOTP.createOTPInterface;
import teamarv.otpattendancesystem.LambdaInvoke.createOTP.createOTPRequestClass;
import teamarv.otpattendancesystem.LambdaInvoke.verifyOTP.verifyOTPInterface;
import teamarv.otpattendancesystem.LambdaInvoke.verifyOTP.verifyOTPRequestClass;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG="MainActivity";
    private CognitoUserDetails cognitoUserDetails;
    private CognitoUserPool cognitoUserPool;
    private Fragment recyclerFragment = null;
    private static Fragment createOTPMainFragment = null;
    private static Fragment enterOTPMainFragment = null;
    private static FragmentManager fragmentManager;

    // Cognito user objects
    private CognitoUser user;
    private CognitoUserSession session;
    private CognitoUserDetails details;

    // User details
    private String username;

    // To track changes to user details
    private final List<String> attributesToDelete = new ArrayList<>();

    //OTP
    private static String otp = null;

    private AlertDialog userDialog;
    private ProgressDialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        username = AppHelper.getCurrUser();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_bar_username);
        navUsername.setText(username);

        if (recyclerFragment != null)
            recyclerFragment = null;
        recyclerFragment = RecyclerViewFragment.newInstance();
        if (fragmentManager != null)
            fragmentManager = null;
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, recyclerFragment).commit();
        cognitoUserDetails = AppHelper.getUserDetails();
        cognitoUserPool = AppHelper.getPool();
        init();
    }

    private void init() {
        // Get the user name
        Bundle extras = getIntent().getExtras();
        username = AppHelper.getCurrUser();
        user = AppHelper.getPool().getUser(username);
        getDetails();
    }

    GetDetailsHandler detailsHandler = new GetDetailsHandler() {
        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            closeWaitDialog();
            // Store details in the AppHandler
            AppHelper.setUserDetails(cognitoUserDetails);
        }

        @Override
        public void onFailure(Exception exception) {
            closeWaitDialog();
            showDialogMessage("Could not fetch user details!", AppHelper.formatException(exception), true);
        }
    };

    private void signOut() {
        user.signOut();
        exit();
    }
    private void getDetails() {
        AppHelper.getPool().getUser(username).getDetailsInBackground(detailsHandler);
    }

    public static void openFragment(char c) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (c){
            case '0':
                if (enterOTPMainFragment != null)
                    enterOTPMainFragment = null;
                enterOTPMainFragment = verifyOTPFragment.newInstance("enter", "OTP");
                fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
                fragmentTransaction.replace(R.id.otp_frame, enterOTPMainFragment).addToBackStack(null).commit();
                break;
            case '1':
                if (createOTPMainFragment != null)
                    createOTPMainFragment = null;
                createOTPMainFragment = createOTPFragment.newInstance("create", "OTP");
                fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
                fragmentManager.beginTransaction().replace(R.id.otp_frame, createOTPMainFragment).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int count = fragmentManager.getBackStackEntryCount();
        String msg = "" + count;
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(count == 0){
            exit();
            super.onBackPressed();
        }
        else{
            fragmentManager.popBackStack();
        }
    }

    private void showWaitDialog(String message) {
        closeWaitDialog();
        waitDialog = new ProgressDialog(this);
        waitDialog.setTitle(message);
        waitDialog.show();
    }

    private void showDialogMessage(String title, String body, final boolean exit) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(body).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                    if(exit) {
                        exit();
                    }
                } catch (Exception e) {
                    // Log failure
                    Log.e(TAG," -- Dialog dismiss failed");
                    if(exit) {
                        exit();
                    }
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickCreateOTP(View view){
        TextView textView = ((TextView)findViewById(R.id.textView4));
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(getApplicationContext(), "us-east-2:cc1d241f-618f-4f77-a480-3257d3abe59b", Regions.US_EAST_2);
        LambdaInvokerFactory factory = new LambdaInvokerFactory(getApplicationContext(), Regions.US_EAST_2, credentialsProvider);
        if(textView.getText().equals("Create")) {
            final createOTPInterface myInterface = factory.build(createOTPInterface.class);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            df.setTimeZone(TimeZone.getTimeZone("gmt"));
            String gmtTime = df.format(new Date());
            List<String> access = new ArrayList<>();
            access.add("307");
            access.add("btech");
            access.add("cse");
            access.add("2016");
            createOTPRequestClass request = new createOTPRequestClass(username, "CS307", access, 0, gmtTime);
            new AsyncTask<createOTPRequestClass, Void, String>() {
                @Override
                protected String doInBackground(createOTPRequestClass... params) {
                    try {
                        return myInterface.createOTP(params[0]);
                    } catch (LambdaFunctionException lfe) {
                        Log.e("UserActivity", "Failed to invoke createOTP", lfe);
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    if (result == null) {
                        return;
                    }
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    otp = result;
                    ((EditText)findViewById(R.id.otpCreate)).setText(result);
                }
            }.execute(request);
            textView.setText("Close");
        }
        else{
            final closeOTPInterface myInterface = factory.build(closeOTPInterface.class);
            List<String> program = new ArrayList<>();
            program.add("2016");
            program.add("btech");
            program.add("cse");
            closeOTPRequestClass request = new closeOTPRequestClass(username, "CS307", ((EditText)findViewById(R.id.otpCreate)).getText().toString(),program);
            new AsyncTask<closeOTPRequestClass, Void, String>() {
                @Override
                protected String doInBackground(closeOTPRequestClass... params) {
                    try {
                        return myInterface.closeOTP(params[0]);
                    } catch (LambdaFunctionException lfe) {
                        Log.e("UserActivity", "Failed to invoke createOTP", lfe);
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    if (result == null) {
                        return;
                    }
                    fragmentManager.popBackStack();
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }
            }.execute(request);
        }
    }

    public void onClickVerifyOTP(View view) {
        final TextView textView = ((TextView)findViewById(R.id.textView5));
        if(textView.getText().toString().equals("Confirm")) {
            EditText editText = (EditText) findViewById(R.id.otpEdit);
            String otp = editText.getText().toString();
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(getApplicationContext(), "us-east-2:cc1d241f-618f-4f77-a480-3257d3abe59b", Regions.US_EAST_2);
            LambdaInvokerFactory factory = new LambdaInvokerFactory(getApplicationContext(), Regions.US_EAST_2, credentialsProvider);
            final verifyOTPInterface myInterface = factory.build(verifyOTPInterface.class);
            List<String> program = new ArrayList<>();
            program.add("2016");
            program.add("btech");
            program.add("cse");
            textView.setText("Please Wait...");
            verifyOTPRequestClass request = new verifyOTPRequestClass("2016", "CS307", program, username, otp);
            new AsyncTask<verifyOTPRequestClass, Void, String>() {
                @Override
                protected String doInBackground(verifyOTPRequestClass... params) {
                    try {
                        return myInterface.VerifyOTP(params[0]);
                    } catch (LambdaFunctionException lfe) {
                        Log.e("UserActivity", "Failed to invoke createOTP", lfe);
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    if (result == null) {
                        return;
                    }
                    if (result.equals("success") || result.equals("verified"))
                        textView.setText("Close");
                    else
                        textView.setText(result);
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }
            }.execute(request);
        }
        else{
            fragmentManager.popBackStack();
        }
    }

    private void exit () {
        Intent intent = new Intent();
        if(username == null)
            username = "";
        intent.putExtra("name",username);
        setResult(RESULT_OK, intent);
        finish();
    }
}
