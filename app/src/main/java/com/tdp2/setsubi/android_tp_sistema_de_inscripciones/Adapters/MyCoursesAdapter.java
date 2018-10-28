package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CoursePeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.MyCourse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

import java.util.List;
import java.util.Locale;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.ViewHolder> {

    public interface Listener
    {
        void onUnsubscribe(int position);
        void onSeeFinals(int position);
    }

    private Listener listener;
    private List<MyCourse> courses;

    public MyCoursesAdapter(List<MyCourse> courseList, Listener listener)
    {
        this.courses = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.my_course_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyCourse course = courses.get(position);
        holder.setSubject(course.getSubject());
        holder.setPeriod(course.getYear(), course.getPeriod());
        holder.setTimes(course.getCursoTimeBands());
        holder.setCatedra(course.getCatedra());
        holder.setSede(course.getSede());
        holder.setButtons(course.isEnabledToEnroll(), course.isCanSeeFinals());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView catedra, sede, period, subject;
        private RecyclerView timeBands;
        private Button unsubscribe, seeFinals;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            catedra = itemView.findViewById(R.id.catedra_value);
            sede = itemView.findViewById(R.id.sede_value);
            period = itemView.findViewById(R.id.period_value);
            subject = itemView.findViewById(R.id.subject_value);
            timeBands = itemView.findViewById(R.id.course_time_band);
            timeBands.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            unsubscribe = itemView.findViewById(R.id.unsubscribe_button);
            seeFinals = itemView.findViewById(R.id.see_finals_button);
            unsubscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUnsubscribe(getAdapterPosition());
                }
            });
            seeFinals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSeeFinals(getAdapterPosition());
                }
            });
        }

        void setSubject(Subject subject)
        {
            this.subject.setText(String.format(Locale.getDefault(),
                    "%02d.%02d %s", subject.getDepartmentCode(), subject.getCode(), subject.getName()));
        }

        void setPeriod(int year, CoursePeriod.Period period) {
            this.period.setText(String.format(Locale.getDefault(),
                    "%04d - %s", year, getPeriodString(period)));
        }

        private String getPeriodString(CoursePeriod.Period period)
        {
            switch (period)
            {
                case FIRST:
                    return "1C";
                case SECOND:
                    return "2C";
            }
            return "Verano";
        }

        void setTimes(List<CursoTimeBand> times)
        {
            timeBands.setAdapter(new CursoTimeAdapter(times));
        }

        void setCatedra(String catedra) {
            this.catedra.setText(catedra);
        }

        void setSede(Sede sede)
        {
            int text = sede == Sede.PASEO_COLON ? R.string.sede_pc : R.string.sede_las_heras;
            this.sede.setText(text);
        }

        void setButtons(boolean enabledToEnroll, boolean canSeeFinals) {
            if( enabledToEnroll )
            {
                unsubscribe.setVisibility(View.VISIBLE);
            } else {
                unsubscribe.setVisibility(View.GONE);
            }
            if(canSeeFinals)
            {
                seeFinals.setVisibility(View.VISIBLE);
            } else
            {
                seeFinals.setVisibility(View.GONE);
            }
        }
    }
}
