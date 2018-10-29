package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.ApprovedSubject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ApprovedSubjectAdapter extends RecyclerView.Adapter<ApprovedSubjectAdapter.ViewHolder>
{
    private List<ApprovedSubject> list;

    public ApprovedSubjectAdapter(List<ApprovedSubject> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.approved_subject_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApprovedSubject subject = list.get(position);
        holder.setSubject(subject.getSubject());
        holder.setCalification(subject.getCalification());
        holder.setDate(subject.getApprovedDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView subject, date, calification;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject_value);
            date = itemView.findViewById(R.id.date_value);
            calification = itemView.findViewById(R.id.nota_value);
        }

        void setDate(Date date)
        {
            this.date.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date));
        }

        void setCalification(int calification) {
            this.calification.setText(String.valueOf(calification));
        }

        void setSubject(Subject subject) {
            this.subject.setText(String.format(Locale.getDefault(),
                    "%02d.%02d %s", subject.getDepartmentCode(), subject.getCode(), subject.getName()));
        }
    }
}
