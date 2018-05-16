package com.tool.flashcard.flashcardtool;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class QuizModeError extends Fragment
{

    private View m_CurrentView;

    private TextView m_Error;
    private Button   m_Answer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        m_CurrentView = inflater.inflate(R.layout.activity_quiz_mode_error, container, false);

        m_Answer = m_CurrentView.findViewById(R.id.buttonError);
        m_Answer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                QuizMode.instance.CurrentState = QuizMode.QuizModeState.Exit;
                QuizMode.instance.UpdateState();
                //Intent intent = new Intent(QuizModeAnswer.this, QuizModeStatistics.class);
                //startActivity(intent);
            }
        });

        m_Error = m_CurrentView.findViewById(R.id.textViewError);

        return m_CurrentView;
    }

    public void onResume()
    {
        super.onResume();

        m_Error.setText("Error, Quiz Mode requires that the deck contain more than 4 cards, the current deck contains " + DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex).getNumberOfCards() + " cards");
    }

}
