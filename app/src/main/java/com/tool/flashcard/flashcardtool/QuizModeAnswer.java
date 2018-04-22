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

public class QuizModeAnswer extends Fragment
{

    private View m_CurrentView;

    private TextView m_Question;
    private Button   m_Answer;
    private TextView m_Correct;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        m_CurrentView = inflater.inflate(R.layout.activity_quiz_mode_answer, container, false);

        m_Answer = m_CurrentView.findViewById(R.id.buttonAnswer);
        m_Answer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(QuizMode.instance.finishedQuiz)
                    QuizMode.instance.CurrentState = QuizMode.QuizModeState.Statistics;
                else
                    QuizMode.instance.CurrentState = QuizMode.QuizModeState.Question;

                QuizMode.instance.UpdateState();
                //Intent intent = new Intent(QuizModeAnswer.this, QuizModeStatistics.class);
                //startActivity(intent);
            }
        });

        m_Question = m_CurrentView.findViewById(R.id.question);
        m_Correct = m_CurrentView.findViewById(R.id.correctAnswer);

        return m_CurrentView;
    }

    public void DisplayAnswer()
    {
        if(QuizMode.instance.answeredCorrect)
        {
            m_Correct.setText("CORRECT!!!!");

            QuizMode.instance.correctAnswers++;
        }
        else
        {
            m_Correct.setText("Incorrect");
        }

        QuizMode.instance.totalQuestions++;
        m_Answer.setText(QuizMode.instance.currentCard.GetCardBack());
        m_Question.setText(QuizMode.instance.currentCard.GetCardFront());
    }

    public void UpdateDeckName(String _name)
    {
        TextView deckName = m_CurrentView.findViewById(R.id.deckName);
        deckName.setText(_name);
    }
}
