package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.Arrays;
import java.util.List;

public class FinalsAdapter extends RecyclerView.Adapter<FinalsAdapter.ViewHolder>
{
    public interface Listener
    {
        void clickedButton(int position, boolean regular);
    }

    private static final int REGULAR = 0, FREE = 1;
    private Listener listener;
    private List<Final> finals;
    private boolean interactionEnabled = true;

    public FinalsAdapter(List<Final> finals, Listener listener)
    {
        this.finals = finals;
        this.listener = listener;
    }

    public void setInteractionEnabled(boolean enabled)
    {
        this.interactionEnabled = enabled;
        notifyDataSetChanged();
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
        holder.setDate(exam.getDate());
        holder.setTime(exam.getTime());
        holder.setSede(exam.getSede());
        holder.setAula(exam.getAula());
        holder.setCondicion(exam.isSubscribed(),
                exam.isApprovedCourseOfFinal(), exam.isSupportsLibre(),
                exam.isFreeSubscription());
        holder.setButtons(exam.isSubscribed(),
                exam.isCanSubscribe(),
                exam.isCanUnsubscribe());
    }

    @Override
    public int getItemCount() {
        return finals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView catedra, time, date, sede, condicion;
        private Button subsrcibe;
        private Spinner conditionSpinner;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            catedra = itemView.findViewById(R.id.catedra_value);
            time = itemView.findViewById(R.id.time_value);
            date = itemView.findViewById(R.id.date_value);
            sede = itemView.findViewById(R.id.sede_value);
            condicion = itemView.findViewById(R.id.condicion_value);
            subsrcibe = itemView.findViewById(R.id.subsribe_button_final);
            conditionSpinner = itemView.findViewById(R.id.condition_spinner);
            List<String> options = Arrays.asList(itemView.getContext().getString(R.string.regular),
                    itemView.getContext().getString(R.string.libre));
            conditionSpinner.setAdapter(new ArrayAdapter<>(itemView.getContext(),
                    android.support.design.R.layout.support_simple_spinner_dropdown_item,
                    options));
            conditionSpinner.setSelection(REGULAR);
            subsrcibe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickedButton(getAdapterPosition(), conditionSpinner.getSelectedItemPosition() == REGULAR);
                }
            });
        }

        void setCondicion(boolean isSubscribed,
                          boolean canGiveRegular, boolean canGiveFree,
                          boolean subscribedFree)
        {
            if( !isSubscribed && canGiveRegular && canGiveFree )
            {
                condicion.setVisibility(View.GONE);
                conditionSpinner.setEnabled(true);
                conditionSpinner.setVisibility(View.VISIBLE);
            } else if( isSubscribed )
            {
                if( subscribedFree ) conditionSpinner.setSelection(FREE);
                conditionSpinner.setEnabled(false);
                condicion.setText(!subscribedFree ? R.string.regular : R.string.libre);
                conditionSpinner.setVisibility(View.GONE);
                condicion.setVisibility(View.VISIBLE);
            } else if( !canGiveRegular && !canGiveFree )
            {
                condicion.setText(R.string.cant_subscribe_final);
                conditionSpinner.setVisibility(View.GONE);
                condicion.setVisibility(View.VISIBLE);
            } else
            {
                if( canGiveFree ) conditionSpinner.setSelection(FREE);
                condicion.setText(canGiveRegular ? R.string.regular : R.string.libre);
                conditionSpinner.setVisibility(View.GONE);
                condicion.setVisibility(View.VISIBLE);
            }

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

        void setButtons(boolean subscribed, boolean canSubscribe, boolean canUnsubscribe)
        {
            if( (subscribed && !canUnsubscribe) || (!subscribed && !canSubscribe) )
            {
                this.subsrcibe.setVisibility(View.GONE);
            } else
            {
                this.subsrcibe.setVisibility(View.VISIBLE);
                int background, text;
                if( subscribed )
                {
                    background = R.drawable.button_round_red;
                   text = R.string.unsubscribe;
                } else
                {
                    background = R.drawable.button_round_blue;
                    text = R.string.inscribirse_btn_text;
                }
                if( !interactionEnabled ) background = R.drawable.button_round_grey;
                ShapeBackgroundColorChanger.changeBackground(subsrcibe,background);
                this.subsrcibe.setText(text);
                this.subsrcibe.setEnabled(interactionEnabled);
            }
        }
    }
}
