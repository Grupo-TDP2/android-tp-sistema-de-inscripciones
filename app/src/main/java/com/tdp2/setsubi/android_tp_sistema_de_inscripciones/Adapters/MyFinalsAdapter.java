package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ShapeBackgroundColorChanger;

import java.util.List;
import java.util.Locale;

public class MyFinalsAdapter extends RecyclerView.Adapter<MyFinalsAdapter.ViewHolder> {

    public interface Listener
    {
        void onUnsubscribe(int id);
    }

    private Listener listener;
    private List<Final> finals;


    public MyFinalsAdapter(List<Final> finals, Listener listener)
    {
        this.finals = finals;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_final_exam_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Final finales = finals.get(position);
        holder.setButtonLogic(finales.isCanUnsubscribe());
        holder.setCalification( finales.isFinalGiven(),
                finales.passedFinal(),
                finales.getFinalCalification());
        holder.setCatedra(finales.getCatedraName());
        holder.setDate(finales.getDate());
        holder.setTime(finales.getTime());
        holder.setSede(finales.getSede());
        holder.setAula(finales.getAula());
        holder.setCondicion(!finales.isSupportsLibre());
        Subject subject = finales.getSubject();
        holder.setSubject(subject.getDepartmentCode(), subject.getCode(), subject.getName());
    }

    @Override
    public int getItemCount() {
        return finals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private Button unsubsribe;
        private View qualificationContainer, headerContainer, dataContainer, actionContainer;
        private ImageView qualificationBackground, arrow;
        private TextView catedra,date,time,sede, subject,
                condicion, qualification, subjectCode;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            unsubsribe = itemView.findViewById(R.id.button_unsubscribe);
            catedra = itemView.findViewById(R.id.catedra_value);
            subject = itemView.findViewById(R.id.subject_value);
            subjectCode = itemView.findViewById(R.id.subject_code);
            date = itemView.findViewById(R.id.date_value);
            time = itemView.findViewById(R.id.time_value);
            sede = itemView.findViewById(R.id.sede_value);
            condicion = itemView.findViewById(R.id.condicion_value);
            arrow = itemView.findViewById(R.id.arrow);
            qualification = itemView.findViewById(R.id.qualification_note);
            actionContainer = itemView.findViewById(R.id.actions_my_final);
            qualificationBackground = itemView.findViewById(R.id.qualification_background);
            qualificationContainer = itemView.findViewById(R.id.qualification_container);
            dataContainer = itemView.findViewById(R.id.data_my_final);
            headerContainer = itemView.findViewById(R.id.header_my_final);
            unsubsribe.setOnClickListener(v -> listener.onUnsubscribe(getAdapterPosition()));
            headerContainer.setOnClickListener(v -> {
                int visible = View.VISIBLE;
                if( arrow.getRotation() == 0 )
                {
                    arrow.setRotation(180);
                } else
                {
                    visible = View.GONE;
                    arrow.setRotation(0);
                }
                dataContainer.setVisibility(visible);
            });
        }

        void setCondicion(boolean regular)
        {
            condicion.setText(regular ? R.string.regular : R.string.libre);
        }

        void setCalification(boolean finalGive, boolean finalPassed, Integer calification)
        {
            if( finalGive )
            {
                qualificationContainer.setVisibility(View.VISIBLE);
                int background = R.drawable.button_round_red;
                if( finalPassed )
                {
                    background = R.drawable.button_round_blue;
                } else
                {
                    calification = 2;
                }
                qualification.setText(String.valueOf(calification));
                qualificationBackground.setImageResource(background);
            } else {
                qualificationContainer.setVisibility(View.GONE);
            }
        }

        void setButtonLogic(boolean unsubscribeVisibility)
        {
            actionContainer.setVisibility(unsubscribeVisibility ? View.VISIBLE : View.GONE);
            unsubsribe.setEnabled(unsubscribeVisibility);
        }

        void setCatedra(String catedra)
        {
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

        void setSubject(int departmentCode, int code, String name)
        {
            this.subjectCode.setText(String.format(Locale.getDefault(), "%02d.%02d",departmentCode, code));
            this.subject.setText(name);
        }
    }
}
