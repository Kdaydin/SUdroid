package com.khome.kdaydin.sudroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;

public class D_Prog_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner mSpinner;
    ListView mListView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String TERM = "TERM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d__prog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSpinner = (Spinner) findViewById(R.id.d_prog_term_spinner);
        mListView = (ListView) findViewById(R.id.d_prog_courselist);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        new TermAccess().execute(); //Getting Terms
        mSpinner.setOnItemSelectedListener(this);



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sharedPreferences = getSharedPreferences(TERM, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.putString(TERM,parent.getItemAtPosition(position).toString());
        editor.apply();
        Snackbar.make(view,sharedPreferences.getString(TERM,""),Snackbar.LENGTH_LONG)
                .setAction("Action",null)
                .show();
        if(sharedPreferences.getString(TERM,"").equalsIgnoreCase("")){
            mListView.setVisibility(View.GONE);
        }
        else{
            mListView.setVisibility(View.VISIBLE);
            new CourseAccess().execute();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Do Nothing?
    }

    private class TermAccess extends AsyncTask<Void, Void, Void> {
        ProgressDialog mProgressDialog;
        LinkedHashMap<String,String> termarray = new LinkedHashMap<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(D_Prog_Activity.this);
            mProgressDialog.setMessage("Getting Terms...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect("http://suis.sabanciuniv.edu/prod/bwckschd.p_disp_dyn_sched").get();
                Element element =  document.getElementById("term_input_id");
                Elements terms = element.getElementsByTag("OPTION");
                for (Element term : terms) {
                    termarray.put(term.text(),term.attr("VALUE"));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            final HashMapAdapter hashMapAdapter = new HashMapAdapter(termarray);

            Runnable runnable = new Runnable() {
                public void run() {
                    mSpinner.setAdapter(hashMapAdapter);
                }
            };
            runOnUiThread(runnable);
            mProgressDialog.dismiss();
        }
    }
    private class CourseAccess extends AsyncTask<Void, Void, Void> {
        ProgressDialog mProgressDialog;
        LinkedHashMap<String,String> coursearray = new LinkedHashMap<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(D_Prog_Activity.this);
            mProgressDialog.setMessage("Getting Courses...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            sharedPreferences = getSharedPreferences(TERM, Context.MODE_PRIVATE);

            try {
                Connection.Response termresponse = Jsoup.connect("http://suis.sabanciuniv.edu/prod/bwckgens.p_proc_term_date")
                        .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0")
                        .referrer("http://suis.sabanciuniv.edu/prod/bwckschd.p_disp_dyn_sched")
                        .data("p_calling_proc","bwckschd.p_disp_dyn_sched")
                        .data("p_term",sharedPreferences.getString(TERM,""))
                        .method(Connection.Method.POST).execute();

                Element element =  termresponse.parse().getElementById("subj_id");
                Elements terms = element.getElementsByTag("option");
                for (Element term : terms) {
                    coursearray.put(term.text(),term.attr("VALUE"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            final HashMapAdapter hashMapAdapter = new HashMapAdapter(coursearray);

            Runnable runnable = new Runnable() {
                public void run() {
                    mListView.setAdapter(hashMapAdapter);
                }
            };
            runOnUiThread(runnable);
            mProgressDialog.dismiss();
        }
    }

}



