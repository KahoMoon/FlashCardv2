package com.example.flashcardv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends Activity {

    static HashMap<String, String> termsAndDefinitions = new HashMap<>();

    //key for Intent
    static String EXTRA_TERM = "cardTerm";
    static String EXTRA_DEFINITION = "cardDefinition";

    //xml elements
    ListView listView;  //view to list terms
    TextView termView;  //view for single term

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        termsAndDefinitions.put("Term1", "Definition1");
        termsAndDefinitions.put("Term2", "Definition2");
        termsAndDefinitions.put("Term3", "Definition3");
        termsAndDefinitions.put("Term4", "Definition4");

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();

        //get intent and extras from FlashCardActivity if returning from FlashCardActivity
        Intent returnFromFlashCard = getIntent();
        String newTerm = null;
        String oldTerm = null;
        String newDefinition = null;
        String oldDefinition = null;
        if (returnFromFlashCard != null) {
            newTerm = returnFromFlashCard.getStringExtra(FlashCardActivity.EXTRA_NEW_TERM);
            oldTerm = returnFromFlashCard.getStringExtra(FlashCardActivity.EXTRA_OLD_TERM);
            newDefinition = returnFromFlashCard.getStringExtra(FlashCardActivity.EXTRA_NEW_DEFINITION);
            oldDefinition = returnFromFlashCard.getStringExtra(FlashCardActivity.EXTRA_OLD_DEFINITION);
        }

        //checks if the term and definition has changed and if so, updates termsAndDefinitions
        if (Objects.equals(newTerm, oldTerm) && Objects.equals(newDefinition, oldDefinition)) {
            Log.d("termsAndDefinitions", "same term same definition");
        }
        else if (Objects.equals(newTerm, oldTerm) && !Objects.equals(newDefinition, oldDefinition)) {
            Log.d("termsAndDefinitions", "same term diff definition");
            termsAndDefinitions.put(oldTerm, newDefinition);
        }
        else if (!Objects.equals(newTerm, oldTerm) && Objects.equals(newDefinition, oldDefinition)) {
            Log.d("termsAndDefinitions", "diff term same definition");
            termsAndDefinitions.put(newTerm, oldDefinition);
            termsAndDefinitions.remove(oldTerm);
        }
        else if (!Objects.equals(newTerm, oldTerm) && !Objects.equals(newDefinition, oldDefinition)) {
            Log.d("termsAndDefinitions", "diff term diff definition");
            termsAndDefinitions.put(newTerm, newDefinition);
            termsAndDefinitions.remove(oldTerm, oldDefinition);
        }

        //find the corresponding elements
        listView = findViewById(R.id.listView);
        termView = findViewById(R.id.termView);

        //create an adapter for the terms and set listView to it
        ArrayAdapter termsAndDefinitionsAdapter = new ArrayAdapter(this, R.layout.activity_list, new ArrayList(termsAndDefinitions.keySet()));
        listView.setAdapter(termsAndDefinitionsAdapter);
        listView.setOnItemClickListener((adapterView, view, itemPosition, itemId) -> {
            Intent toFlashCard = new Intent(MainActivity.this, FlashCardActivity.class);
            String term = (String) termsAndDefinitionsAdapter.getItem(itemPosition);
            String definition = termsAndDefinitions.get(term);
            toFlashCard.putExtra(EXTRA_TERM, term);
            toFlashCard.putExtra(EXTRA_DEFINITION, definition);
            startActivity(toFlashCard);
        });

    }

    public void addTerm(View view) {
        Intent intent = new Intent(MainActivity.this, FlashCardActivity.class);
        startActivity(intent);
    }
}