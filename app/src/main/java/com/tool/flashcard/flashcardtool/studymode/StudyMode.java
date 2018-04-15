package com.tool.flashcard.flashcardtool.studymode;

import java.util.ArrayList;
import java.util.Stack;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import com.tool.flashcard.flashcardtool.DeckSelect;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Flashcard;
import com.tool.flashcard.flashcardtool.R;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class StudyMode extends FragmentActivity
{
    private Deck        m_CurrentDeck;

    private TextView    m_DeckName;
    private TextView    m_CardNumber;

    private ViewPager pager;

    private FlashcardPagerAdapter deckAdapter;

    private Button remove_button;
    private Button undo_button;
    private Button reset_button;

    private ArrayList<Flashcard> mEntries = new ArrayList<Flashcard>();
    private Stack<Flashcard> mDeletions = new Stack<Flashcard>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode);

        m_CurrentDeck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);
        initEntries();

        m_DeckName = findViewById(R.id.deckName);
        m_DeckName.setText(m_CurrentDeck.GetName());

        m_CardNumber = findViewById(R.id.cardNumber);


        remove_button = (Button) findViewById(R.id.remove_card);
        undo_button = (Button) findViewById(R.id.undo_remove_card);
        reset_button = (Button) findViewById(R.id.reset_cards);
        pager = findViewById(R.id.cardViewPager);


        //deckAdapter = new FlashcardPagerAdapter(this.getSupportFragmentManager() , m_CurrentDeck);
        deckAdapter = new FlashcardPagerAdapter(this.getSupportFragmentManager(), mEntries);
        pager.setAdapter(deckAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position)
            {
                m_CardNumber.setText((position + 1) + "/" + mEntries.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        //Update the cardNumber textView for the first card
        m_CardNumber.setText("1/" + mEntries.size());

        //remove
        remove_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCurrentItem();
            }
        });

        //undo remove
        undo_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pushDeletedItem();
            }
        });

        //undo remove
        reset_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                resetAllItems();
            }
        });

    }

    private void initEntries(){
        mEntries.clear();
        for(int i = 0; i < m_CurrentDeck.getNumberOfCards(); i++){
            mEntries.add(m_CurrentDeck.getFlashcard(i));
        }
    }

    private void resetAllItems() {
        initEntries();

        //m_CardNumber = findViewById(R.id.cardNumber);
        //m_CardNumber.setText(mEntries.size());

        deckAdapter.notifyDataSetChanged();



    }

    private void pushDeletedItem() {
        if(mDeletions.empty()) return;

        mEntries.add(mDeletions.pop());

        //m_CardNumber = findViewById(R.id.cardNumber);
        //m_CardNumber.setText(mEntries.size());

        deckAdapter.notifyDataSetChanged();
    }

    private void removeCurrentItem() {
        if(mEntries.isEmpty()) return;

        int position = pager.getCurrentItem();


        mDeletions.push(mEntries.get(pager.getCurrentItem()));
        mEntries.remove(pager.getCurrentItem());

        //pager.beginFakeDrag();
         //pager.fakeDragBy(100);
        //pager.endFakeDrag();
        //m_CardNumber = findViewById(R.id.cardNumber);
        //m_CardNumber.setText(mEntries.size());

        deckAdapter.notifyChangeInPosition(1);
        deckAdapter.notifyDataSetChanged();

    }

    private class FlashcardPagerAdapter extends FragmentStatePagerAdapter
    {
        //private Deck deck;
        private ArrayList mEntries;
        private long baseId = 0;
/*
        public FlashcardPagerAdapter(FragmentManager fm, Deck deck_using)
        {
            super(fm);
            this.deck = deck_using;
        }
*/
        public FlashcardPagerAdapter(FragmentManager fm, ArrayList mEntries)
        {
            super(fm);
            this.mEntries = mEntries;
        }

        /*
        @Override
        public Fragment getItem(int position)
        {
            FlashCardFragment fragment = new FlashCardFragment();
            fragment.card = deck.getFlashcard(position);
            return fragment;
        }
        */

        @Override
        public Fragment getItem(int position)
        {
            FlashCardFragment fragment = new FlashCardFragment();
            //fragment.card = deck.getFlashcard(position);
            fragment.card = (Flashcard) mEntries.get(position);
            return fragment;
        }
/*
        @Override
        public int getCount() {
            return this.deck.getNumberOfCards();
        }
*/
        @Override
        public int getCount() {
            return this.mEntries.size();
        }

        @Override
        public int getItemPosition(Object object) {
            // refresh all fragments when data set changed
            return PagerAdapter.POSITION_NONE;
        }

        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        public void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }

    }

}