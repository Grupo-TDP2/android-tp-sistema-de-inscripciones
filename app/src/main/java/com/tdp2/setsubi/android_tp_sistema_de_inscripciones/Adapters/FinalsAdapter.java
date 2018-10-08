package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ShapeBackgroundColorChanger;

import java.util.ArrayList;
import java.util.List;

public class FinalsAdapter extends RecyclerView.Adapter<FinalsAdapter.ViewHolder>
{


    public interface Listener
    {
        void clickedButton(int position);
    }
    private Listener listener;
    private List<Final> finals;
    public FinalsAdapter(List<Final> finals, Listener listener)
    {
        this.finals = finals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.finals_exams_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Final exam = finals.get(position);
        holder.setCatedra(exam.getCatedraName());
        holder.setDate(exam.getFinalDate());
        holder.setTime(exam.getTime());
        holder.setSede(exam.getSede());
        holder.setAula(exam.getAula());
        holder.setButtons(exam.isSubscribed(), exam.isCanSubscribe(), exam.isCanUnsubscribe(), exam.isSupportsLibre());
    }

    @Override
    public int getItemCount() {
        return finals.size();
    }

    public boolean selectedRegular(RecyclerView.ViewHolder viewHolder)
    {
        if( viewHolder instanceof  ViewHolder )
        {
            ViewHolder viewHolder1 = (ViewHolder) viewHolder;
            return viewHolder1.selectedRegular();
        }
        return false;
    }
    class ViewHolder extends RecyclerView.ViewHolder
    {
        private int REGULAR = 0, LIBRE = 1;
        private TextView catedra, time, date, sede;
        private Spinner spinner;
        private Button subsrcibe;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            catedra = itemView.findViewById(R.id.catedra_value);
            time = itemView.findViewById(R.id.time_value);
            date = itemView.findViewById(R.id.date_value);
            sede = itemView.findViewById(R.id.sede_value);
            spinner = itemView.findViewById(R.id.spinner_subscription_type);
            subsrcibe = itemView.findViewById(R.id.subsribe_button_final);
            subsrcibe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickedButton(getAdapterPosition());
                }
            });
        }

        boolean selectedRegular()
        {
            return spinner.getSelectedItemPosition() == REGULAR;
        }

        void setCatedra(String catedra) {
            this.catedra.setText(catedra);
        }

        void setDate(String date) {
            this.date.setText(date);
        }

        void setTime(String time) {
            this.time.setText(time);
        }

        void setSede(Sede sede) {
            this.sede.setText(sede == Sede.PASEO_COLON ? R.string.sede_pc : R.string.sede_las_heras);
        }

        void setAula(int aula) {

        }

        void setButtons(boolean subscribed, boolean canSubscribe, boolean canUnsubscribe, boolean supportsLibre)
        {
            if( (subscribed && ! canUnsubscribe) || (!subscribed && !canSubscribe) )
            {
                this.subsrcibe.setVisibility(View.GONE);
                this.spinner.setVisibility(View.GONE);
            } else
            {
                this.subsrcibe.setVisibility(View.VISIBLE);
                if( subscribed ) {
                    ShapeBackgroundColorChanger.changeColor(subsrcibe, R.color.actionButtonDelete);
                    this.spinner.setVisibility(View.GONE);
                    this.subsrcibe.setText(R.string.unsubscribe);
                } else {
                    this.spinner.setVisibility(View.VISIBLE);
                    ShapeBackgroundColorChanger.changeColor(subsrcibe, R.color.actionButtonColor);
                    this.subsrcibe.setText(R.string.inscribirse_btn_text);
                    List<String> strings = new ArrayList<>();
                    strings.add(spinner.getContext().getString(R.string.regular));
                    if( supportsLibre )
                    {
                        strings.add(spinner.getContext().getString(R.string.libre));
                    }
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(spinner.getContext(), android.R.layout.simple_spinner_item, strings);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setEnabled(strings.size() > 1);
                    spinner.setAdapter(arrayAdapter);
                }
            }
        }
    }
}
