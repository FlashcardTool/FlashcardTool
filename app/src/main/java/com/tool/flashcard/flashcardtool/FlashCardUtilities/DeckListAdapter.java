package com.tool.flashcard.flashcardtool.FlashCardUtilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tool.flashcard.flashcardtool.DeckSelect;
import com.tool.flashcard.flashcardtool.R;

/**
 * Created by joshua.masci082 on 3/28/18.
 */

public class DeckListAdapter extends RecyclerView.Adapter<DeckListAdapter.ViewHolder>
{
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Deck deck = DeckSelect.Manager.get(position);

        holder.DeckName.setText(deck.GetName());
        holder.Desc.setText("Stuff that I haven't decided what to do with");
    }

    @Override
    public int getItemCount()
    {
        return DeckSelect.Manager.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView DeckName;
        public TextView Desc;

        public ViewHolder(View itemView)
        {
            super(itemView);

            this.DeckName = itemView.findViewById(R.id.textViewDeckName);
            this.Desc = itemView.findViewById(R.id.textViewDesc);
        }
    }
}
