package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CourseTime;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.DayOfWeek;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.StringTransform;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CursoTimeAdapter extends RecyclerView.Adapter<CursoTimeAdapter.ViewHolder> implements Comparator<CursoTimeBand> {

    private List<CursoTimeBand> cursosSchedules;

    public CursoTimeAdapter(List<CursoTimeBand> cursosSchedules)
    {
        this.cursosSchedules = cursosSchedules;
        Collections.sort(cursosSchedules, this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.curso_horario_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CursoTimeBand timeBand = cursosSchedules.get(position);
        holder.setAula(timeBand.getAula());
        holder.setDay(timeBand.getDayOfWeek());
        holder.setTime(timeBand.getStarTime(), timeBand.getEndTime());
        holder.setType(timeBand.getType(), timeBand.isObligatory());
    }

    @Override
    public int getItemCount() {
        return cursosSchedules.size();
    }

    @Override
    public int compare(CursoTimeBand o1, CursoTimeBand o2)
    {
        int comparison = o1.getDayOfWeek().getValue() - o2.getDayOfWeek().getValue();
        if( comparison == 0 )
        {
            return o1.getStarTime().getHours() - o2.getStarTime().getHours();
        } if( comparison < 0 )
        {
            return -1;
        }
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView cursoTime;
        private TextView cursoDay;
        private TextView cursoType;
        private TextView cursoAula;

        public ViewHolder(@NonNull View view)
        {
            super(view);
            cursoTime = view.findViewById(R.id.time_curso);
            cursoDay = view.findViewById(R.id.day_curso);
            cursoType = view.findViewById(R.id.curso_type);
            cursoAula = view.findViewById(R.id.curso_aula);
        }

        public void setTime(CourseTime startTime, CourseTime endTime)
        {
            Context context = cursoTime.getContext();
            cursoTime.setText( context.getString(R.string.time_curso,
                    startTime.getHours(), startTime.getMinutes(),
                    endTime.getHours(),endTime.getMinutes()));
        }

        public void setDay(DayOfWeek day)
        {
            cursoDay.setText(StringTransform.getDayOfWeek(day));
        }

        public void setAula(int aula)
        {
            String value = aula == -1 ? "-" : String.valueOf(aula);
            cursoAula.setText(value);
        }

        public void setType(CursoTimeBand.CursoTimeType type, boolean obligatory)
        {
            cursoType.setText(getType(type, obligatory));
        }

        private int getType(CursoTimeBand.CursoTimeType type, boolean obligatory)
        {
            switch (type)
            {
                case PRACTIO:
                    if( obligatory )
                    {
                        return R.string.practio_obligatory;
                    }
                    return R.string.practio;
                case TEORICO:
                    if( obligatory )
                    {
                        return R.string.teorico_obligatory;
                    }
                    return R.string.teorico;
            }
            if( obligatory )
            {
                return R.string.teorico_practio_obligatory;
            }
            return R.string.teorico_practio;
        }
    }
}
