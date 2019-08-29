package com.example.zdroa.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.aid.DataHandler;
import com.example.zdroa.myapplication.models.MovieModel;
import com.example.zdroa.myapplication.models.RESTRICTED_GENRES_ENUM;

import java.util.List;

public class PopulateListsActivity extends AppCompatActivity {

    private String[] restrictedGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populate_lists);


        DataHandler dataHandler = new DataHandler();
        List<MovieModel> movieModelList = dataHandler.getMovies(100);



















        EditText numberOfResultsEditText = findViewById(R.id.etNumOfRes);

        Button generateListsButton = findViewById(R.id.admin_gen_list);
        generateListsButton.setEnabled(false);

        ((RadioGroup) findViewById(R.id.rgPersonTYPE)).setOnCheckedChangeListener((radioGroup1, i) -> {
            int checkedRadioButtonId = radioGroup1.getCheckedRadioButtonId();
            String radioButtonText = ((RadioButton) findViewById(checkedRadioButtonId)).getText().toString();
            restrictedGenres = RESTRICTED_GENRES_ENUM.valueOf(radioButtonText.toUpperCase()).getStrings();

            //set generateListsButton to true
        });

        // TODO: 29/08/2019 make the generate lists button work
    }

}