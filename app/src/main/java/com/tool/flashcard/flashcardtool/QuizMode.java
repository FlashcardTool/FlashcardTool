package com.tool.flashcard.flashcardtool;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.Chronometer;
import android.widget.Switch;

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Flashcard;

import java.sql.Struct;

/**
 * Created by j on 4/22/18.
 */

public class QuizMode extends AppCompatActivity
{
    public static QuizMode instance;

    public enum QuizModeState {Question, Answer, Statistics, Reset, Exit};

    private FragmentManager     m_FragMan;
    private QuizModeStatistics  m_Statistics;
    private QuizModeQuestion    m_Question;
    private QuizModeAnswer      m_Answer;

    public QuizModeState        CurrentState;

    //QuizModeSharedData This should be moved into a struct
    public Flashcard            currentCard;
    public int                  correctAnswers;
    public int                  totalQuestions;
    public boolean              answeredCorrect;
    public boolean              finishedQuiz;

    private Chronometer         m_Timer;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz_mode);

        instance = this;

        m_FragMan = getFragmentManager();

        m_Statistics = new QuizModeStatistics();
        m_Question = new QuizModeQuestion();
        m_Answer = new QuizModeAnswer();
        LoadFragment(m_Statistics);
        LoadFragment(m_Question);
        LoadFragment(m_Answer);

        m_Timer = findViewById(R.id.timer);
    }

    protected void onResume()
    {
        super.onResume();

        CurrentState = QuizModeState.Question;
        UpdateState();

        UpdateDeckName();

        m_Timer.start();
    }

    private void UpdateDeckName()
    {
        String currentDeckName = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex).GetName();
        m_Question.UpdateDeckName(currentDeckName);
        m_Statistics.UpdateDeckName(currentDeckName);
        m_Answer.UpdateDeckName(currentDeckName);
    }

    private void LoadFragment(Fragment _fragment)
    {
        FragmentTransaction fragManTrans = m_FragMan.beginTransaction();
        fragManTrans.add(R.id.fragmentLayout, _fragment);
        fragManTrans.commit();
    }

    private void ShowFragment(Fragment _fragment)
    {
        FragmentTransaction fragManTrans = m_FragMan.beginTransaction();
        fragManTrans.show(_fragment);
        fragManTrans.commit();
    }

    private void HideFragment(Fragment _fragment)
    {
        FragmentTransaction fragManTrans = m_FragMan.beginTransaction();
        fragManTrans.hide(_fragment);
        fragManTrans.commit();
    }

    public void UpdateState()
    {

        switch (CurrentState)
        {
            case Question:
                HideFragment(m_Statistics);
                HideFragment(m_Answer);
                ShowFragment(m_Question);

                m_Question.ShowNewQuestion();
                break;
            case Answer:
                HideFragment(m_Statistics);
                ShowFragment(m_Answer);
                HideFragment(m_Question);

                m_Answer.DisplayAnswer();
                break;
            case Statistics:
                ShowFragment(m_Statistics);
                HideFragment(m_Answer);
                HideFragment(m_Question);
                m_Timer.stop();

                m_Statistics.DisplayStatistics();
                break;
            case Reset:
                m_Question.Reset();
                CurrentState = QuizModeState.Question;

                currentCard = null;
                correctAnswers = 0;
                totalQuestions = 0;
                answeredCorrect = false;
                finishedQuiz = false;

                m_Timer.setBase(SystemClock.elapsedRealtime());
                m_Timer.start();
                UpdateState();
                break;
            case Exit:
                Intent intent = new Intent(QuizMode.this, DeckSelect.class);
                startActivity(intent);
                break;
        }
    }
}
