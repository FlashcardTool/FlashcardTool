package com.tool.flashcard.flashcardtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class DeckSelectSubMenu extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_select_sub_menu);

        Button buton = findViewById(R.id.buttonStudy);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DeckSelectSubMenu.this, StudyModeTop.class);
                startActivity(intent);
            }
        });

        buton = findViewById(R.id.buttonEdit);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DeckSelectSubMenu.this, EditMode.class);
                startActivity(intent);
            }
        });

        buton = findViewById(R.id.buttonQuiz);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DeckSelectSubMenu.this, QuizModeQuestion.class);
                startActivity(intent);
            }
        });

        buton = findViewById(R.id.buttonDelete);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DeckSelectSubMenu.this, DeckSelectDelete.class);
                startActivity(intent);
            }
        });

    }
}
