package com.tool.flashcard.flashcardtool;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class QuizModeStatistics extends Fragment
{

    private View m_CurrentView;

    private TextView m_CorrectAnswers;
    private TextView m_IncorrectAnswers;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        m_CurrentView = inflater.inflate(R.layout.activity_quiz_mode_statistics, container, false);

        Button button = m_CurrentView.findViewById(R.id.buttonDone);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                QuizMode.instance.CurrentState = QuizMode.QuizModeState.Exit;
                QuizMode.instance.UpdateState();
            }
        });

        button = m_CurrentView.findViewById(R.id.buttonAgain);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                QuizMode.instance.CurrentState = QuizMode.QuizModeState.Reset;
                QuizMode.instance.UpdateState();
            }
        });

        m_CorrectAnswers = m_CurrentView.findViewById(R.id.correctAnswers);
        m_IncorrectAnswers = m_CurrentView.findViewById(R.id.incorrectAnswers);

        return m_CurrentView;
    }

    public void DisplayStatistics()
    {
        m_CorrectAnswers.setText(Integer.toString(QuizMode.instance.correctAnswers) + " Correct");
        m_IncorrectAnswers.setText(Integer.toString(QuizMode.instance.totalQuestions - QuizMode.instance.correctAnswers) + " Incorrect");
    }

    public void UpdateDeckName(String _name)
    {
        TextView deckName = m_CurrentView.findViewById(R.id.deckName);
        deckName.setText(_name);
    }
}
