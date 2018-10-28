package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
        holder.setUnsubscribeVisibility(finales.isCanUnsubscribe());
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
        private TextView catedra,date,time,sede, subject, condicion;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            unsubsribe = itemView.findViewById(R.id.button_unsubscribe);
            catedra = itemView.findViewById(R.id.catedra_value);
            subject = itemView.findViewById(R.id.subject_value);
            date = itemView.findViewById(R.id.date_value);
            time = itemView.findViewById(R.id.time_value);
            sede = itemView.findViewById(R.id.sede_value);
            condicion = itemView.findViewById(R.id.condicion_value);
            unsubsribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUnsubscribe(getAdapterPosition());
                }
            });
        }

        void setCondicion(boolean regular)
        {
            condicion.setText(regular ? R.string.regular : R.string.libre);
        }

        void setUnsubscribeVisibility(boolean unsubscribeVisibility) {
           unsubsribe.setVisibility(unsubscribeVisibility ? View.VISIBLE : View.GONE);
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

        void setSubject(int departmentCode, int code, String name) {
            this.subject.setText(String.format(Locale.getDefault(), "%02d.%02d %s",departmentCode, code, name));
        }
    }
}
