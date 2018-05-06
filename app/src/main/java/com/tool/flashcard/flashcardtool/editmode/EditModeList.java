package com.tool.flashcard.flashcardtool.editmode;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import com.tool.flashcard.flashcardtool.DeckSelect;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.DynamicListView;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Flashcard;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.Deck;
import com.tool.flashcard.flashcardtool.FlashCardUtilities.StableArrayAdapter;
import com.tool.flashcard.flashcardtool.R;

public class EditModeList extends AppCompatActivity
{

    private Deck m_Deck;

    private EditText m_DeckName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mode_list);

        m_Deck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);
        final List<Flashcard> flashcards = m_Deck.getAllCards();

        DynamicListView listView = findViewById(R.id.flashcard_list);
        StableArrayAdapter<Flashcard> adapter = new StableArrayAdapter<>(this, 0, flashcards);
        listView.setAdapter(adapter);

        TextView title = findViewById(R.id.DeckTitle);
        title.setText(m_Deck.GetName());

        m_DeckName = findViewById(R.id.DeckTitle);
        m_DeckName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                m_Deck.UpdateDeckName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //m_Deck.UpdateDeckName(s.toString());
            }
        });

        FloatingActionButton fab = findViewById(R.id.NewFlashcardButton);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
            Deck deck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);
            deck.CreateNewCard("New Card Front", "New Card Back");
            DynamicListView listView =  findViewById(R.id.flashcard_list);
            StableArrayAdapter adapter = (StableArrayAdapter)listView.getAdapter();
            adapter.rebuildMap(deck.getAllCards());
            adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
            {
                Deck deck = DeckSelect.Manager.get(DeckSelect.CurrentDeckIndex);
                deck.setCurrentCard(position);
                Intent intent = new Intent(EditModeList.this, EditMode.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //Rebuild list
        DynamicListView listView =  findViewById(R.id.flashcard_list);
        StableArrayAdapter adapter = (StableArrayAdapter)listView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, DeckSelect.class);
        startActivity(intent);
    }
}
