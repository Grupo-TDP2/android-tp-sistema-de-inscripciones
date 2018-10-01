package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoViewHolder>
{
    private List<Course> courses;
    private boolean canSubscribe;
    private SubscribeListener listener;

    public interface SubscribeListener
    {
        void onSubscribe(int cursoId);
    }

    public CursoAdapter(List<Course> courses, boolean canSubscribe, SubscribeListener listener)
    {
        this.courses = courses;
        this.canSubscribe = canSubscribe;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.curso_item_layout, parent, false);
        return new CursoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.setCatedra(course.getCatedra());
        holder.setCupo(course.getCupos());
        holder.setTimes(course.getCursoTimeBands());
        holder.setSede(course.getSede());
        holder.enableSubscription(canSubscribe);
        holder.setButtonEnabled(!course.isSubscribed());
        holder.setSubscribeListener(listener, course.getId());
    }

    @Override
    public int getItemCount()
    {
        return courses.size();
    }

    static class CursoViewHolder extends RecyclerView.ViewHolder
    {
        private TextView catedra;
        private TextView sede;
        private TextView cupo;
        private TextView noTimesText;
        private Button subscribe;
        private RecyclerView recyclerView;

        public CursoViewHolder(@NonNull View itemView)
        {
            super(itemView);
            catedra = itemView.findViewById(R.id.caterda_name);
            sede = itemView.findViewById(R.id.sede_name);
            cupo = itemView.findViewById(R.id.cupo_size);
            subscribe = itemView.findViewById(R.id.subscribe_button);
            noTimesText = itemView.findViewById(R.id.noHayHorarios);
            recyclerView = itemView.findViewById(R.id.horarios_container);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL));
            recyclerView.setNestedScrollingEnabled(false);
        }

        public void setCatedra(String catedra)
        {
            this.catedra.setText(catedra);
        }

        public void setSede(Sede sede)
        {
            int text = sede == Sede.PASEO_COLON ? R.string.sede_pc : R.string.sede_las_heras;
            this.sede.setText(text);
        }

        public void setCupo(int cupo)
        {
            this.cupo.setText(String.valueOf(cupo));
        }

        public void setTimes(List<CursoTimeBand> times)
        {
            recyclerView.setAdapter(new CursoTimeAdapter(times));
            int noTimesVisibility = times.size() == 0 ? View.VISIBLE : View.GONE;
            noTimesText.setVisibility(noTimesVisibility);
        }

        public void enableSubscription(boolean canSubscribe) {
            if( canSubscribe )
            {
                subscribe.setVisibility(canSubscribe ? View.VISIBLE : View.INVISIBLE);
            }
        }

        public void setButtonEnabled(boolean enabled)
        {
            if( enabled )
            {
                subscribe.setBackgroundColor(ContextCompat.getColor(subscribe.getContext(), R.color.actionButtonColor));
            } else
            {
                subscribe.setBackgroundColor(ContextCompat.getColor(subscribe.getContext(), R.color.disbaledBackground));
            }
        }

        public void setSubscribeListener(final SubscribeListener subscribeListener,final int id)
        {
            this.subscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subscribeListener.onSubscribe(id);
                }
            });
        }
    }
}
