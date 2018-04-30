package com.tool.flashcard.flashcardtool.FlashCardUtilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tool.flashcard.flashcardtool.R;

import java.util.HashMap;
import java.util.List;

public class StableArrayAdapter<T> extends ArrayAdapter<T>
{
    final int INVALID_ID = -1;

    HashMap<T, Integer> mIdMap = new HashMap<>();
    List<T> objects;

    public StableArrayAdapter(Context context, int textViewResourceId, List<T> objects)
    {
        super(context, textViewResourceId, objects);

        this.objects = objects;

        for (int i = 0; i < objects.size(); ++i)
        {
            mIdMap.put(objects.get(i), i);
        }
    }

    public void rebuildMap( List<T> objects)
    {
        this.objects = objects;

        mIdMap.clear();
        for (int i = 0; i < objects.size(); ++i)
        {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position)
    {
        if (position < 0 || position >= mIdMap.size())
        {
            return INVALID_ID;
        }
        T item = getItem(position);
        return mIdMap.get(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Object item = this.objects.get(position);
        if(item instanceof Flashcard)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_flashcard, parent, false);

            TextView DeckName = view.findViewById(R.id.textViewDeckName);
            TextView Desc = view.findViewById(R.id.textViewDesc);

            Flashcard card = (Flashcard)item;

            DeckName.setText(card.GetCardFront());
            Desc.setText(card.GetCardBack());

            return view;
        }

        return super.getView(position, convertView, parent);
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    public void swapItems(int index1, int index2)
    {
        T temp = objects.get(index1);
        objects.set(index1, objects.get(index2));
        objects.set(index2, temp);
    }
}
