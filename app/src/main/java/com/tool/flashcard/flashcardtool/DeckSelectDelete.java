package com.tool.flashcard.flashcardtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class DeckSelectDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_select_delete);

        Button buton = findViewById(R.id.buttonYes);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DeckSelectDelete.this, DeckSelect.class);
                startActivity(intent);
            }
        });

        buton = findViewById(R.id.buttonNo);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(DeckSelectDelete.this, DeckSelect.class);
                startActivity(intent);
            }
        });
    }
}
