package com.example.zdroa.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.aid.HttpHandler;
import com.example.zdroa.myapplication.aid.PersonTypesArrays;
import com.example.zdroa.myapplication.requests.InsertToDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PopulateListsActivity extends AppCompatActivity {

    private static String API_KEY = "b692b9da86f1cf0c1b623ea6e2770101";
    private static String STRING_LIST_OF_IDS = "";

    private static String pType;
    PersonTypesArrays personTypesArrays = new PersonTypesArrays();
    private int num_of_results = 0;

    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populate_lists);

        final Button genLists = (Button) findViewById(R.id.admin_gen_list);
        final EditText resnum = (EditText) findViewById(R.id.etNumOfRes);
        RadioGroup rgPTYPE = (RadioGroup) findViewById(R.id.rgPersonTYPE);

        rgPTYPE.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Anxious:
                        pType = "anxious";
                        genLists.setEnabled(true);
                        break;
                    case R.id.Paranoid:
                        pType = "paranoid";
                        genLists.setEnabled(true);
                        break;
                    case R.id.Histrionic:
                        pType = "histrionic";
                        genLists.setEnabled(true);
                        break;
                    case R.id.Obsessive:
                        pType = "obsessive";
                        genLists.setEnabled(true);
                        break;
                    case R.id.Narcissist:
                        pType = "narcissist";
                        genLists.setEnabled(true);
                        break;
                    case R.id.Schizoid:
                        pType = "schizoid";
                        genLists.setEnabled(true);
                        break;
                    case R.id.Depressive:
                        pType = "depressive";
                        genLists.setEnabled(true);
                        break;
                    case R.id.Dependent:
                        pType = "dependent";
                        genLists.setEnabled(true);
                        break;
                }
            }
        });

        genLists.setEnabled(false);


        genLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_of_results = Integer.parseInt(resnum.getText().toString());
                new getWorkingIdsFromAPI(new ArrayList<>(personTypesArrays.PersonTypesArrays(pType))).execute();
            }
        });
    }


    class getWorkingIdsFromAPI extends AsyncTask<Void, Void, Void> {

        ArrayList<String> alPersonType;

        public getWorkingIdsFromAPI(ArrayList<String> arrayList) {
            alPersonType = new ArrayList<>(arrayList);
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler httpHandler = new HttpHandler();

            for (int i = 1; i < num_of_results; i++) { //20000
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String JSONString = httpHandler.makeServiceCall("https://api.themoviedb.org/3/movie/" + i + "?api_key=" + API_KEY);

                if (JSONString != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(JSONString);
                        JSONArray jsonArray = jsonObject.getJSONArray("genres");

                        boolean cont = true;

                        for (int a = 0; a < jsonArray.length(); a++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(a);
                            String string = jsonObject1.getString("name");

                            for (int b = 0; b < alPersonType.size(); b++) {
                                if (string.equals(alPersonType.get(b))) {
                                    cont = false;
                                }
                            }
                        }

                        if (cont) {
                            String id = String.valueOf(jsonObject.getInt("id"));

                            switch (id.length()) {
                                case 1:
                                    STRING_LIST_OF_IDS += "0000" + id;
                                    break;
                                case 2:
                                    STRING_LIST_OF_IDS += "000" + id;
                                    break;
                                case 3:
                                    STRING_LIST_OF_IDS += "00" + id;
                                    break;
                                case 4:
                                    STRING_LIST_OF_IDS += "0" + id;
                                    break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                progressDoalog.setProgress(i);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDoalog = new ProgressDialog(PopulateListsActivity.this);
            progressDoalog.setMax(num_of_results);
            progressDoalog.setMessage("Loading....");
            progressDoalog.setTitle("Setting IDS");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDoalog.setCancelable(false);
            progressDoalog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            System.out.println("Success");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            InsertToDB loginRequest = new InsertToDB(pType, STRING_LIST_OF_IDS, responseListener);
            RequestQueue queue = Volley.newRequestQueue(PopulateListsActivity.this);
            queue.add(loginRequest);
            progressDoalog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        PopulateListsActivity.this.finish();
    }
}