package com.chaloa.android.app;

import Adapter.MockDataAdapter;
import Model.MockData;
import Model.StopWiseData;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<MockData> fullData;
    private MockDataAdapter mockDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

            progressBar.setVisibility(View.VISIBLE);
            new GetDataForContent(getParent(), progressBar, recyclerView).execute();

    }

    private class GetDataForContent extends AsyncTask<String, Void, String> {

        private final int[] progr  = {45,95};
        private int index;

        private final Activity parent;
        private final ProgressBar progressBar;
        private final RecyclerView recyclerView;

        public GetDataForContent(Activity parent, ProgressBar progressBar, RecyclerView recyclerView) {
            this.parent = parent;
            this.progressBar = progressBar;
            this.recyclerView = recyclerView;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            progressBar.incrementProgressBy(progr[index]);
            ++index;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://mock.chalo.com:8080/metadata");
                Log.v("TEST","Hello");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000  /*milliseconds*/ );
                conn.setConnectTimeout(15000  /*milliseconds */);
                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//                conn.setRequestProperty("Content-Type", "application/json");
                Log.v("MAIN", conn.getResponseCode() + " ");

                int responseCode=conn.getResponseCode();
                Log.v("RESPONSE CODE", responseCode + " ");


                final StringBuilder output = new StringBuilder("Request URL " + url);
                Log.v("OUTPUTURL", url+ "");
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type "+ "GET");

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    Log.v("progressStart", progressBar.getProgress()  + " ");
                    publishProgress();
                    fullData = convertStreamToString(conn.getInputStream());
                    publishProgress();
                    Log.v("progressEnd", progressBar.getProgress()  + " ");
                    return  output.toString();

                }
                else {
                    Log.v("False2" , "false : " + responseCode);
                    return "false : " + responseCode;
                }
            }
            catch(Exception e){
                Log.v("Exception-try/catch2" , "false : " + e.getMessage());
                return "try/catch : " + e.getMessage();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            int max = 0;
            for (final int p : progr) {
                max += p;
            }
            progressBar.setMax(max);
            index = 0;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setProgress(100);
            progressBar.setVisibility(GONE);
            Log.v("OUTPUT",result.toString());
            if(result.contains("200")){
                setUpData(fullData);
                mockDataAdapter.notifyDataSetChanged();
            }
            Log.v("onPostExecute2", result);
        }


    }

    private ArrayList<MockData> convertStreamToString(InputStream inputStream) {
        ArrayList<MockData> res = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));


        String line = null;
        JSONObject ja;
        StringBuilder sb2 = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb2.append(line+ "\n");


            }

        } catch (IOException e) {
            Log.v("StreamtoString2", e.getMessage());
        }

        line = sb2.toString();
        try{
            JSONArray jsonObject = new JSONArray(line);
            for(int i=0;i<jsonObject.length();i++){
                JSONObject jb = jsonObject.getJSONObject(i);
                JSONArray item = (JSONArray)(jb.getJSONArray("stopDataList"));
                ArrayList<StopWiseData> swdList = new ArrayList<>();
                for(int j=0;j<item.length();j++){
                    JSONObject itemJb = item.getJSONObject(j);
                    StopWiseData swd = new StopWiseData(
                            itemJb.getInt("stopId"),
                            itemJb.getString("stopName"),
                            itemJb.getInt("sequence"),
                            Float.parseFloat(itemJb.getString("latitute")),
                            Float.parseFloat(itemJb.getString("longitude")));
                    swdList.add(swd);
                }
                MockData md = new MockData(jb.getInt("routeId"), jb.getString("routeName"), swdList);
                res.add(md);
            }
        }
        catch (JSONException e) {
            Log.v("JsonError2",  e.getMessage());
        }

        return res;
    }


    private void setUpData(ArrayList<MockData> fullData) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mockDataAdapter = new MockDataAdapter(getApplicationContext(), fullData);
        recyclerView.setAdapter(mockDataAdapter);
        Log.v("setUpData", fullData.toString());
    }
    }
