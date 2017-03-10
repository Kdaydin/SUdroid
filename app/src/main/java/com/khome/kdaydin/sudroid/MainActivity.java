package com.khome.kdaydin.sudroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity {
    Button mLogin;
    Button mD_Prog;
    Button mD_Katalog;
    Button mFinal;
    Button mYurt;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String USRCRE = "USERCREDENTIALS";
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mLogin = (Button) findViewById(R.id.login_but);
        mD_Prog = (Button) findViewById(R.id.ders_prog_but);
        mD_Katalog = (Button) findViewById(R.id.ders_katalog_but);
        mFinal = (Button) findViewById(R.id.final_prog_but);
        mYurt = (Button) findViewById(R.id.yurt_but);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("5675801C9AE4F4C61185FE3A24AAABFF")
                .build();
        mAdView.loadAd(adRequest);
        sharedPreferences = getSharedPreferences(USRCRE,Context.MODE_PRIVATE);

        /********** Floating Action Button Code  **********************************************
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("REMOVED",1)==1){
                    Toast.makeText(MainActivity.this, "No Login info found!", Toast.LENGTH_SHORT).show();
                    editor.putInt("REMOVED",0);
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Login Info Added!", Toast.LENGTH_SHORT).show();
                }
                else if (sharedPreferences.getInt("REMOVED",1)==0){
                    Toast.makeText(MainActivity.this, "Already Have Login Info!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ***************************************************************************************/
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("HAS_ID_PASS",0)==0){
                    Toast.makeText(MainActivity.this, "No Login info found!", Toast.LENGTH_SHORT).show();
                    //Acquire Login Credentials and Attempt Login
                    Intent i = new Intent(getApplicationContext(),LoginInfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"Login Button");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);
                    startActivity(i);
                }
                else if (sharedPreferences.getInt("HAS_ID_PASS",0)==1){
                    //Toast.makeText(MainActivity.this, "Already Have Login Info!", Toast.LENGTH_SHORT).show();
                    //Attempt Login Directly
                    Intent i = new Intent(getApplicationContext(),BannerLogin.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"Login Button");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);
                    startActivity(i);
                }
            }
        });
        
        mD_Prog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Will Be Added Soon!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),D_Prog_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"Ders Programı");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);
                startActivity(i);
            }
        });

        mD_Katalog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Will Be Added Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        mFinal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Will Be Added Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        mYurt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Will Be Added Soon!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.remove_credentials) {
            sharedPreferences = getSharedPreferences(USRCRE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (sharedPreferences.getInt("HAS_ID_PASS",0)==1){
                editor.putInt("HAS_ID_PASS",0);
                editor.apply();
                Toast.makeText(this, "Login Info Deleted!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "No Login Info to Delete!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
