package com.tool.flashcard.flashcardtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class StudyModeBottom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode_bottom);

        ImageButton button = findViewById(R.id.imageButtonFlip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(StudyModeBottom.this, StudyModeTop.class);
                startActivity(intent);
            }
        });

        //((TextView) findViewById(R.id.textView2)).setText(DeckSelect.Manager.GetCurrentCardBack());
    }
}
