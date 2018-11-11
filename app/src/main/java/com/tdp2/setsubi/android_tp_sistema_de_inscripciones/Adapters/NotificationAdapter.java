package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>
{
    public interface  Listener
    {
        void onClickedActionAtIndex(int index);
    }
    private List<Notification> notifications;
    private Listener listener;

    public NotificationAdapter(List<Notification> notifications, Listener listener)
    {
        this.notifications = notifications;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.setTitle(notification.getTitle());
        holder.setBody(notification.getBody());
        holder.setDate(notification.getReceivedDate());
        holder.setAction(notification.getType());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title, body, date;
        private Button action;
        private ImageView imageView;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.notification_image);
            title = itemView.findViewById(R.id.notification_title);
            body = itemView.findViewById(R.id.notification_body);
            date = itemView.findViewById(R.id.notification_date);
            action = itemView.findViewById(R.id.notification_action);
            action.setOnClickListener(v -> listener.onClickedActionAtIndex(getAdapterPosition()));
        }

        void setTitle(String title)
        {
            this.title.setText(title);
        }

        void setBody(String body) {
            this.body.setText(body);
        }

        void setDate(Date date) {
            this.date.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date));
        }

        void setAction(Notification.Type action)
        {
            if( action == null ) this.action.setVisibility(View.GONE);
            else
            {
                this.action.setVisibility(View.VISIBLE);
                int text;
                int icon;
                switch (action)
                {
                    case UNSUBSCRIBE_EXAM:
                        text = R.string.my_finals;
                        icon = R.drawable.ic_exam;
                        break;
                    default:
                        text = R.string.my_courses;
                        icon = R.drawable.ic_date_range_black_24dp;
                }
                this.action.setText(text);
                this.imageView.setImageResource(icon);
            }
        }
    }

}
