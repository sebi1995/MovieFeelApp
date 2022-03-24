package com.example.zdroa.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.aid.HttpHandler;
import com.example.zdroa.myapplication.aid.PersonTypesArrays;
import com.example.zdroa.myapplication.handlers.HttpRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class PopulateListsActivity extends AppCompatActivity {

    private static String API_KEY = "b692b9da86f1cf0c1b623ea6e2770101";
    private static StringBuilder movieIdsAsStringList = new StringBuilder();

    private static String pType;
    PersonTypesArrays personTypesArrays = new PersonTypesArrays();
    private int num_of_results = 0;

    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populate_lists);

        Button genLists = findViewById(R.id.admin_gen_list);
        EditText resnum = findViewById(R.id.etNumOfRes);
        RadioGroup rgPTYPE = findViewById(R.id.rgPersonTYPE);

        rgPTYPE.setOnCheckedChangeListener((group, checkedId) -> {
            pType = map.get(checkedId);
            genLists.setEnabled(true);
        });
        genLists.setEnabled(false);
        genLists.setOnClickListener(v -> {
            num_of_results = Integer.parseInt(resnum.getText().toString());
            new getWorkingIdsFromAPI(new ArrayList<>(personTypesArrays.PersonTypesArrays(pType))).execute();
        });
    }

    private static final Map<Integer, String> map = Map.of(
            R.id.Anxious, "anxious",
            R.id.Paranoid, "paranoid",
            R.id.Histrionic, "histrionic",
            R.id.Obsessive, "obsessive",
            R.id.Narcissist, "narcissist",
            R.id.Schizoid, "schizoid",
            R.id.Depressive, "depressive",
            R.id.Dependent, "dependent"
    );


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
                                    movieIdsAsStringList.append("0000");
                                    break;
                                case 2:
                                    movieIdsAsStringList.append("000");
                                    break;
                                case 3:
                                    movieIdsAsStringList.append("00");
                                    break;
                                case 4:
                                    movieIdsAsStringList.append("0");
                                    break;
                            }
                            movieIdsAsStringList.append(id);
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

            Volley.newRequestQueue(PopulateListsActivity.this).add(HttpRequestHandler.addMovieIdsToDbByPersonType(responseListener, pType, movieIdsAsStringList.toString()));
            progressDoalog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        PopulateListsActivity.this.finish();
    }
}