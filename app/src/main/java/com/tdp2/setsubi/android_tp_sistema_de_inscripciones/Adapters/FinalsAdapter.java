package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
        private TextView catedra, time, date, sede;
        private Button subsrcibe;
        private Spinner conditionSpinner;
        private View finalTitleView, sedeContainer, catedraContainer;
        private ImageView arrow;
        private boolean opened = false;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            finalTitleView = itemView.findViewById(R.id.header_container);
            catedra = itemView.findViewById(R.id.catedra_value);
            time = itemView.findViewById(R.id.time_value);
            date = itemView.findViewById(R.id.date_value);
            sede = itemView.findViewById(R.id.sede_value);
            subsrcibe = itemView.findViewById(R.id.subsribe_button_final);
            conditionSpinner = itemView.findViewById(R.id.condition_spinner);
            sedeContainer = itemView.findViewById(R.id.sede_container);
            catedraContainer = itemView.findViewById(R.id.catedra_container);
            arrow = itemView.findViewById(R.id.final_arrow);
            finalTitleView.setOnClickListener(v -> {
                int visibilit = View.VISIBLE;
                if( opened )
                {
                    visibilit = View.GONE;
                    arrow.setRotation(0);
                    opened = false;
                } else
                {
                    arrow.setRotation(180);
                    opened = true;
                }
                sedeContainer.setVisibility(visibilit);
                catedraContainer.setVisibility(visibilit);
            });
            List<String> options = Arrays.asList(itemView.getContext().getString(R.string.regular),
                    itemView.getContext().getString(R.string.libre));
            conditionSpinner.setAdapter(new ArrayAdapter<>(itemView.getContext(),
                    android.support.design.R.layout.support_simple_spinner_dropdown_item,
                    options));
            conditionSpinner.setSelection(REGULAR);
            subsrcibe.setOnClickListener(v -> listener.clickedButton(getAdapterPosition(), conditionSpinner.getSelectedItemPosition() == REGULAR));
        }

        void setCondicion(boolean isSubscribed,
                          boolean canGiveRegular, boolean canGiveFree,
                          boolean subscribedFree)
        {
            int backgroundColor = R.color.colorAccent;
            boolean showSubscribeButton = true;
            if( !isSubscribed && canGiveRegular && canGiveFree )
            {
                subsrcibe.setText(R.string.inscribirse_btn_text);
                conditionSpinner.setEnabled(true);
                conditionSpinner.setVisibility(View.VISIBLE);
            } else if( isSubscribed )
            {
                if( subscribedFree ) conditionSpinner.setSelection(FREE);
                conditionSpinner.setEnabled(false);
                subsrcibe.setText(!subscribedFree ? R.string.unsubscribe_regular : R.string.unsubscribe_libre);
                conditionSpinner.setVisibility(View.GONE);
            } else if( !canGiveRegular && !canGiveFree )
            {
                backgroundColor = R.color.disbaledBackground;
                showSubscribeButton = false;
                conditionSpinner.setVisibility(View.GONE);
            } else
            {
                if( canGiveFree ) conditionSpinner.setSelection(FREE);
                subsrcibe.setText(canGiveRegular ? R.string.subscribe_regular : R.string.subscribe_libre);
                conditionSpinner.setVisibility(View.GONE);
            }
            subsrcibe.setVisibility(showSubscribeButton ? View.VISIBLE : View.GONE);
            finalTitleView.setBackgroundColor(ContextCompat.getColor(subsrcibe.getContext(), backgroundColor));
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
            }
        }
    }
}
