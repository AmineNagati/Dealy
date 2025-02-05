package com.example.prestify.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestify.R;
import com.example.prestify.model.Message;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messages;
    private int currentUserId; // ID de l'utilisateur connecté

    public MessageAdapter(List<Message> messages, int currentUserId) {
        this.messages = messages;
        this.currentUserId = currentUserId;
    }
    public void updateMessages(List<Message> newMessages) {
        this.messages = newMessages;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Charger le layout item_message.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);

        // Afficher le contenu et l'horodatage
        holder.messageContent.setText(message.getContent());
        holder.messageTimestamp.setText(message.getTimestamp());

        // Déterminer si le message a été envoyé par l'utilisateur actuel
        if (message.getSenderId() == currentUserId) {
            holder.messageContainer.setBackgroundResource(R.drawable.message_background_sent); // Fond vert clair
            holder.messageContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.Gravity.END)); // Aligner à droite
        } else {
            holder.messageContainer.setBackgroundResource(R.drawable.message_background_received); // Fond gris clair
            holder.messageContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    android.view.Gravity.START)); // Aligner à gauche
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout messageContainer;
        TextView messageContent, messageTimestamp;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContainer = itemView.findViewById(R.id.messageContainer);
            messageContent = itemView.findViewById(R.id.messageContent);
            messageTimestamp = itemView.findViewById(R.id.messageTimestamp);
        }
    }
}