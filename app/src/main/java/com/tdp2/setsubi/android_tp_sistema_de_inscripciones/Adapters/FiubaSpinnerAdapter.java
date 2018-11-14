package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

import java.util.List;

public class FiubaSpinnerAdapter extends BaseAdapter
{
    private List<String> items;

    public FiubaSpinnerAdapter( List<String> items)
    {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View rowView = convertView;
        if (rowView == null)
        {

            rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_list_item, parent, false);
        }

        ((TextView)rowView.findViewById(R.id.textView)).setText(items.get(position));

        return rowView;
    }
}
