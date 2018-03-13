package com.tool.flashcard.flashcardtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizModeQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_mode_question);

        Button button = findViewById(R.id.buttonCorrect);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(QuizModeQuestion.this, QuizModeAnswer.class);
                startActivity(intent);
            }
        });

        Button button1 = findViewById(R.id.buttonIncorrect1);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(QuizModeQuestion.this, QuizModeAnswerIncorrect.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.buttonIncorrect2);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(QuizModeQuestion.this, QuizModeAnswerIncorrect.class);
                startActivity(intent);
            }
        });

        Button button3 = findViewById(R.id.buttonIncorrect3);
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(QuizModeQuestion.this, QuizModeAnswerIncorrect.class);
                startActivity(intent);
            }
        });
    }
}
