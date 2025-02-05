package com.example.prestify.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestify.R;
import com.example.prestify.activity.ConversationActivity;
import com.example.prestify.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {
    private List<Message> messages;
    private Map<Integer, List<Message>> conversationGroups;

    public ConversationAdapter(List<Message> messages) {
        updateMessages(messages);
    }

    public void updateMessages(List<Message> newMessages) {
        this.messages = newMessages;
        // Grouper les messages par conversation
        this.conversationGroups = messages.stream()
                .collect(Collectors.groupingBy(msg -> msg.getReceiverId()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversation, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        List<Message> conversationMessages = new ArrayList<>(conversationGroups.values()).get(position);
        Message lastMessage = conversationMessages.get(conversationMessages.size() - 1);

        // Configurer la vue
        holder.contactInitial.setText("P"); // P pour Prestataire
        holder.contactName.setText("Prestataire"); // À remplacer par le vrai nom
        holder.lastMessage.setText(lastMessage.getContent());
        holder.timestamp.setText(lastMessage.getTimestamp());

        // Gérer le clic sur la conversation
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ConversationActivity.class);
            intent.putExtra("receiverId", lastMessage.getReceiverId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return conversationGroups.size();
    }

    static class ConversationViewHolder extends RecyclerView.ViewHolder {
        TextView contactInitial, contactName, lastMessage, timestamp;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            contactInitial = itemView.findViewById(R.id.contactInitial);
            contactName = itemView.findViewById(R.id.contactName);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }
}