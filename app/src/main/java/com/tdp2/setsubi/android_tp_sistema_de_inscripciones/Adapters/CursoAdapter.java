package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Curso;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.StringTransform;

import org.w3c.dom.Text;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoViewHolder>
{
    private List<Curso> cursos;
    private boolean canSubscribe;
    private SubscribeListener listener;

    public interface SubscribeListener
    {
        void onSubscribe(int cursoId);
    }

    public CursoAdapter(List<Curso> cursos, boolean canSubscribe, SubscribeListener listener)
    {
        this.cursos = cursos;
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
        Curso curso = cursos.get(position);
        holder.setCatedra(curso.getCatedra());
        holder.setCupo(curso.getCupos());
        holder.setTimes(curso.getCursoTimeBands());
        holder.setSede(curso.getSede());
        holder.enableSubscription(canSubscribe);
        holder.setSubscribeListener(listener, curso.getId());
    }

    @Override
    public int getItemCount()
    {
        return cursos.size();
    }

    public static class CursoViewHolder extends RecyclerView.ViewHolder
    {
        private TextView catedra;
        private TextView sede;
        private TextView cupo;
        private Button subscribe;
        private RecyclerView recyclerView;

        public CursoViewHolder(@NonNull View itemView)
        {
            super(itemView);
            catedra = itemView.findViewById(R.id.caterda_name);
            sede = itemView.findViewById(R.id.sede_name);
            cupo = itemView.findViewById(R.id.cupo_size);
            subscribe = itemView.findViewById(R.id.subscribe_button);
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
            if( cupo == 0 )
            {
                subscribe.setEnabled(false);
            }
        }

        public void setTimes(List<CursoTimeBand> times)
        {
            recyclerView.setAdapter(new CursoTimeAdapter(times));
        }

        public void enableSubscription(boolean canSubscribe) {
            if( canSubscribe )
            {
                subscribe.setVisibility(canSubscribe ? View.VISIBLE : View.INVISIBLE);
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
