package com.tool.flashcard.flashcardtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizModeQuestion extends AppCompatActivity
{
    private Flashcard           m_RandomizedCards[];
    private Random              m_Generator;
    private Deck                m_CurrentDeck;

    //Information Objects
    private TextView            m_TextViewDeckName;
    private TextView            m_TextViewTimer;

    //Questions and answer objects
    private TextView            m_QuestionText;
    private Button              m_ButtonAnswer1;
    private Button              m_ButtonAnswer2;
    private Button              m_ButtonAnswer3;
    private Button              m_ButtonAnswer4;

    //Results objects
    private Button              m_ButtonCorrectAnswer;
    private TextView            m_TextViewCorrect;
    private TextView            m_TextViewSorry;

    //Statistics objects
    private Button              m_ButtonDone;
    private Button              m_ButtonTryAgain;
    private TextView            m_TextViewCorrectAnswers;
    private TextView            m_TextViewIncorrectAnswers;
    private TextView            m_TextViewResults;
    private TextView            m_TextViewTrickyGame;

    private int                 m_CurrentFlashcard;
    private int                 m_CorrectAnswer;

    private int                 m_CorrectAnswers;

    private List<Integer>       m_UsedAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        m_UsedAnswers = new ArrayList<>();

        setContentView(R.layout.activity_quiz_mode_question);

        m_QuestionText = findViewById(R.id.textViewQuestion);

        m_ButtonAnswer1 = findViewById(R.id.buttonAnswer1);
        m_ButtonAnswer1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DisplayAnswer(0);
            }
        });

        m_ButtonAnswer2 = findViewById(R.id.buttonAnswer2);
        m_ButtonAnswer2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DisplayAnswer(1);
            }
        });

        m_ButtonAnswer3 = findViewById(R.id.buttonAnswer3);
        m_ButtonAnswer3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DisplayAnswer(2);
            }
        });

        m_ButtonAnswer4 = findViewById(R.id.buttonAnswer4);
        m_ButtonAnswer4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DisplayAnswer(3);
            }
        });

        m_ButtonCorrectAnswer = findViewById(R.id.buttonRealAnswer);
        m_ButtonCorrectAnswer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                m_ButtonCorrectAnswer.setEnabled(false);
                m_ButtonCorrectAnswer.setVisibility(View.INVISIBLE);
                m_TextViewCorrect.setEnabled(false);
                m_TextViewCorrect.setVisibility(View.INVISIBLE);
                m_TextViewSorry.setEnabled(false);
                m_TextViewSorry.setVisibility(View.INVISIBLE);

                m_ButtonAnswer1.setEnabled(true);
                m_ButtonAnswer1.setVisibility(View.VISIBLE);
                m_ButtonAnswer2.setEnabled(true);
                m_ButtonAnswer2.setVisibility(View.VISIBLE);
                m_ButtonAnswer3.setEnabled(true);
                m_ButtonAnswer3.setVisibility(View.VISIBLE);
                m_ButtonAnswer4.setEnabled(true);
                m_ButtonAnswer4.setVisibility(View.VISIBLE);

                if(m_CurrentFlashcard < m_RandomizedCards.length)
                    ShowNewQuestion();
                else
                    DisplayStatistics();

            }
        });

        m_TextViewDeckName = findViewById(R.id.textViewDeckName);

        m_TextViewTimer = findViewById(R.id.textViewTimer);
        m_TextViewTimer.setEnabled(false);
        m_TextViewTimer.setVisibility(View.INVISIBLE);

        m_ButtonCorrectAnswer.setEnabled(false);
        m_ButtonCorrectAnswer.setVisibility(View.INVISIBLE);

        m_TextViewCorrect = findViewById(R.id.textViewAnswerCorrect);
        m_TextViewCorrect.setEnabled(false);
        m_TextViewCorrect.setVisibility(View.INVISIBLE);

        m_TextViewSorry = findViewById((R.id.textViewSorry));
        m_TextViewSorry.setEnabled(false);
        m_TextViewSorry.setVisibility(View.INVISIBLE);


        ConfigureStatistics();

        m_Generator = new Random();
        m_CurrentFlashcard = 0;
    }


    protected void onResume()
    {
        super.onResume();

        m_CurrentDeck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);
        m_TextViewDeckName.setText(m_CurrentDeck.GetName());

        Reset();
    }

    private  void Reset()
    {
        m_CorrectAnswers = 0;
        m_CurrentFlashcard = 0;

        RandomizeCards();
        ShowNewQuestion();
    }

    private void ShowNewQuestion()
    {
        m_UsedAnswers.clear();

        m_QuestionText.setText(m_RandomizedCards[m_CurrentFlashcard].GetCardFront());
        FillAnswers();

        m_CurrentFlashcard++;
    }

    private void DisplayAnswer(int _answer)
    {
        m_ButtonCorrectAnswer.setEnabled(true);
        m_ButtonCorrectAnswer.setVisibility(View.VISIBLE);
        m_TextViewCorrect.setEnabled(true);
        m_TextViewCorrect.setVisibility(View.VISIBLE);

        if(_answer != m_CorrectAnswer)
        {
            m_TextViewSorry.setEnabled(true);
            m_TextViewSorry.setVisibility(View.VISIBLE);

            m_TextViewCorrect.setText("Incorrect!");
        }
        else
        {
            m_TextViewCorrect.setText("CORRECT!");
            m_CorrectAnswers++;
        }


        m_ButtonAnswer1.setEnabled(false);
        m_ButtonAnswer1.setVisibility(View.INVISIBLE);
        m_ButtonAnswer2.setEnabled(false);
        m_ButtonAnswer2.setVisibility(View.INVISIBLE);
        m_ButtonAnswer3.setEnabled(false);
        m_ButtonAnswer3.setVisibility(View.INVISIBLE);
        m_ButtonAnswer4.setEnabled(false);
        m_ButtonAnswer4.setVisibility(View.INVISIBLE);
    }

    private void ConfigureStatistics()
    {
        m_ButtonDone = findViewById(R.id.buttonDone);
        m_ButtonTryAgain = findViewById(R.id.buttonAgain);
        m_TextViewCorrectAnswers = findViewById(R.id.textViewCorrectAnswers);
        m_TextViewIncorrectAnswers = findViewById(R.id.textViewAnswersWrong);
        m_TextViewResults = findViewById(R.id.textViewResults);
        m_TextViewTrickyGame = findViewById(R.id.textViewTrickyGame);

        m_ButtonTryAgain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Reset();
                HideStatistics();
            }
        });

        m_ButtonDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(QuizModeQuestion.this, DeckSelect.class);
                startActivity(intent);
            }
        });

        HideStatistics();
    }

    private void HideStatistics()
    {
        m_ButtonDone.setEnabled(false);
        m_ButtonDone.setVisibility(View.INVISIBLE);
        m_ButtonTryAgain.setEnabled(false);
        m_ButtonTryAgain.setVisibility(View.INVISIBLE);
        m_TextViewCorrectAnswers.setEnabled(false);
        m_TextViewCorrectAnswers.setVisibility(View.INVISIBLE);
        m_TextViewIncorrectAnswers.setEnabled(false);
        m_TextViewIncorrectAnswers.setVisibility(View.INVISIBLE);
        m_TextViewResults.setEnabled(false);
        m_TextViewResults.setVisibility(View.INVISIBLE);
        m_TextViewTrickyGame.setEnabled(false);
        m_TextViewTrickyGame.setVisibility(View.INVISIBLE);

        m_QuestionText.setEnabled(true);
        m_QuestionText.setVisibility(View.VISIBLE);
        m_ButtonAnswer1.setEnabled(true);
        m_ButtonAnswer1.setVisibility(View.VISIBLE);
        m_ButtonAnswer2.setEnabled(true);
        m_ButtonAnswer2.setVisibility(View.VISIBLE);
        m_ButtonAnswer3.setEnabled(true);
        m_ButtonAnswer3.setVisibility(View.VISIBLE);
        m_ButtonAnswer4.setEnabled(true);
        m_ButtonAnswer4.setVisibility(View.VISIBLE);
    }

    private void DisplayStatistics()
    {
        m_ButtonDone.setEnabled(true);
        m_ButtonDone.setVisibility(View.VISIBLE);
        m_ButtonTryAgain.setEnabled(true);
        m_ButtonTryAgain.setVisibility(View.VISIBLE);
        m_TextViewCorrectAnswers.setEnabled(true);
        m_TextViewCorrectAnswers.setVisibility(View.VISIBLE);
        m_TextViewCorrectAnswers.setText(Integer.toString(m_CorrectAnswers) + " Correct");

        m_TextViewIncorrectAnswers.setEnabled(true);
        m_TextViewIncorrectAnswers.setVisibility(View.VISIBLE);
        m_TextViewIncorrectAnswers.setText(Integer.toString(m_RandomizedCards.length - m_CorrectAnswers) + " Incorrect");

        m_TextViewResults.setEnabled(true);
        m_TextViewResults.setVisibility(View.VISIBLE);
        m_TextViewTrickyGame.setEnabled(true);
        m_TextViewTrickyGame.setVisibility(View.VISIBLE);

        m_QuestionText.setEnabled(false);
        m_QuestionText.setVisibility(View.INVISIBLE);
        m_ButtonAnswer1.setEnabled(false);
        m_ButtonAnswer1.setVisibility(View.INVISIBLE);
        m_ButtonAnswer2.setEnabled(false);
        m_ButtonAnswer2.setVisibility(View.INVISIBLE);
        m_ButtonAnswer3.setEnabled(false);
        m_ButtonAnswer3.setVisibility(View.INVISIBLE);
        m_ButtonAnswer4.setEnabled(false);
        m_ButtonAnswer4.setVisibility(View.INVISIBLE);
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

        m_ButtonCorrectAnswer.setText(m_RandomizedCards[m_CurrentFlashcard].GetCardBack());

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