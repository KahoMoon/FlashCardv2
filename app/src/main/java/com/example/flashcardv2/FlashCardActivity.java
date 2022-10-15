package com.example.flashcardv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FlashCardActivity extends Activity {

    static String EXTRA_NEW_TERM = "cardNewTerm";
    static String EXTRA_OLD_TERM = "cardOldTerm";
    static String EXTRA_NEW_DEFINITION = "cardNewDefinition";
    static String EXTRA_OLD_DEFINITION = "cardOldDefinition";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        Intent fromMainActivity = getIntent();
        String term = fromMainActivity.getStringExtra(MainActivity.EXTRA_TERM);
        String definition = fromMainActivity.getStringExtra(MainActivity.EXTRA_DEFINITION);

        EditText termText = findViewById(R.id.termView);
        EditText definitionText = findViewById(R.id.definitionView);
        termText.setText(term);
        definitionText.setText(definition);

        FloatingActionButton returnToMain = findViewById(R.id.finishAddingTerm);
        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(FlashCardActivity.this, MainActivity.class);

                if (termText.getText() != null && definitionText.getText() != null) {
                    Log.d("termsAndDefinition", "term: " + term);

                    backIntent.putExtra(EXTRA_NEW_TERM, termText.getText().toString());
                    backIntent.putExtra(EXTRA_OLD_TERM, term);

                    backIntent.putExtra(EXTRA_NEW_DEFINITION, definitionText.getText().toString());
                    backIntent.putExtra(EXTRA_OLD_DEFINITION, definition);

                    //Log.d("termsAndDefinition", "if statement ran");
                }

                startActivity(backIntent);
            }
        });
    }
}
