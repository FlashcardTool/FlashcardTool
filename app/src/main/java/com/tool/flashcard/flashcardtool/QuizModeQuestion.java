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

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizModeQuestion extends Fragment
{
    private Flashcard           m_RandomizedCards[];
    private Random              m_Generator;
    private Deck                m_CurrentDeck;

    //Questions and answer objects
    private TextView            m_QuestionText;
    private Button              m_ButtonAnswer1;
    private Button              m_ButtonAnswer2;
    private Button              m_ButtonAnswer3;
    private Button              m_ButtonAnswer4;

    private int                 m_CurrentFlashcard;
    private int                 m_CorrectAnswer;

    private List<Integer>       m_UsedAnswers;


    private View m_CurrentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);


        m_CurrentView = inflater.inflate(R.layout.activity_quiz_mode_question, container, false);

        m_UsedAnswers = new ArrayList<>();

        m_QuestionText = m_CurrentView.findViewById(R.id.textViewQuestion);

        m_ButtonAnswer1 = m_CurrentView.findViewById(R.id.buttonAnswer1);
        m_ButtonAnswer1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                QuizMode.instance.CurrentState = QuizMode.QuizModeState.Answer;
                QuizMode.instance.answeredCorrect = (m_CorrectAnswer == 0);
                QuizMode.instance.UpdateState();
            }
        });

        m_ButtonAnswer2 = m_CurrentView.findViewById(R.id.buttonAnswer2);
        m_ButtonAnswer2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                QuizMode.instance.CurrentState = QuizMode.QuizModeState.Answer;
                QuizMode.instance.answeredCorrect = (m_CorrectAnswer == 1);
                QuizMode.instance.UpdateState();
            }
        });

        m_ButtonAnswer3 = m_CurrentView.findViewById(R.id.buttonAnswer3);
        m_ButtonAnswer3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                QuizMode.instance.CurrentState = QuizMode.QuizModeState.Answer;
                QuizMode.instance.answeredCorrect = (m_CorrectAnswer == 2);
                QuizMode.instance.UpdateState();
            }
        });

        m_ButtonAnswer4 = m_CurrentView.findViewById(R.id.buttonAnswer4);
        m_ButtonAnswer4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                QuizMode.instance.CurrentState = QuizMode.QuizModeState.Answer;
                QuizMode.instance.answeredCorrect = (m_CorrectAnswer == 3);
                QuizMode.instance.UpdateState();
            }
        });


        m_Generator = new Random();
        m_CurrentFlashcard = 0;

        return m_CurrentView;
    }

    public void onStart()
    {
        super.onStart();

        m_CurrentDeck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);
        Reset();
    }

    public void UpdateDeckName(String _name)
    {
        TextView deckName = m_CurrentView.findViewById(R.id.deckName);
        deckName.setText(_name);
    }

    public void Reset()
    {
        m_CurrentFlashcard = 0;

        RandomizeCards();
    }

    public void ShowNewQuestion()
    {
        m_UsedAnswers.clear();

        m_QuestionText.setText(m_RandomizedCards[m_CurrentFlashcard].GetCardFront());
        QuizMode.instance.currentCard = m_RandomizedCards[m_CurrentFlashcard];
        FillAnswers();

        m_CurrentFlashcard++;

        QuizMode.instance.finishedQuiz = (m_CurrentFlashcard >= m_RandomizedCards.length);
    }

    private void RandomizeCards()
    {
        List<Flashcard> cards = m_CurrentDeck.getAllCards();
        m_RandomizedCards = new Flashcard[cards.size()];

        int randomNum = Math.abs(m_Generator.nextInt() % (cards.size() - 1));
        for (int index = 0; index < cards.size(); index++)
        {
            if (m_RandomizedCards[randomNum] == null)
            {
                m_RandomizedCards[randomNum] = cards.get(index);
            }
            else
            {
                for (int ind = randomNum + 1; ind != randomNum; ind++)
                {
                    if (m_RandomizedCards[ind] == null)
                    {
                        m_RandomizedCards[ind] = cards.get(index);
                        break;
                    }
                    else
                    {
                        if (ind >= (cards.size() - 1))
                            ind = -1;
                    }
                }
            }

            randomNum = Math.abs(m_Generator.nextInt() % (cards.size() - 1));
        }
    }

    private void FillAnswers()
    {
        m_CorrectAnswer = Math.abs(m_Generator.nextInt() % (3));

        switch (m_CorrectAnswer)
        {
            case 0:
                m_ButtonAnswer1.setText(m_RandomizedCards[m_CurrentFlashcard].GetCardBack());
                m_ButtonAnswer2.setText(GetRandomAnswer());
                m_ButtonAnswer3.setText(GetRandomAnswer());
                m_ButtonAnswer4.setText(GetRandomAnswer());
                break;
            case 1:
                m_ButtonAnswer1.setText(GetRandomAnswer());
                m_ButtonAnswer2.setText(m_RandomizedCards[m_CurrentFlashcard].GetCardBack());
                m_ButtonAnswer3.setText(GetRandomAnswer());
                m_ButtonAnswer4.setText(GetRandomAnswer());
                break;
            case 2:
                m_ButtonAnswer1.setText(GetRandomAnswer());
                m_ButtonAnswer2.setText(GetRandomAnswer());
                m_ButtonAnswer3.setText(m_RandomizedCards[m_CurrentFlashcard].GetCardBack());
                m_ButtonAnswer4.setText(GetRandomAnswer());
                break;
            case 3:
                m_ButtonAnswer1.setText(GetRandomAnswer());
                m_ButtonAnswer2.setText(GetRandomAnswer());
                m_ButtonAnswer3.setText(GetRandomAnswer());
                m_ButtonAnswer4.setText(m_RandomizedCards[m_CurrentFlashcard].GetCardBack());
                break;
        }

    }

    private String GetRandomAnswer()
    {
        m_UsedAnswers.add(m_CurrentFlashcard);

        int random = Math.abs(m_Generator.nextInt() % (m_RandomizedCards.length - 1));
        int holding = random;

        while (m_UsedAnswers.contains(random) && (holding - 1) != random)
        {
            random++;

            if(random >= m_RandomizedCards.length)
                random = 0;
        }

        m_UsedAnswers.add(random);

        return m_RandomizedCards[random].GetCardBack();
    }

}