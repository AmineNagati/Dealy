package com.example.prestify.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestify.R;
import com.example.prestify.adapter.MessageAdapter;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.Message;
import com.example.prestify.util.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConversationActivity extends AppCompatActivity {
    private RecyclerView messagesRecyclerView;
    private MessageAdapter messageAdapter;
    private EditText messageInput;
    private ImageButton sendButton;
    private TextView contactName;
    private TextView contactInitial;
    private int receiverId;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        receiverId = getIntent().getIntExtra("receiverId", -1);
        sessionManager = new SessionManager(this);

        initializeViews();
        setupMessagesList();
        loadMessages();

        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void initializeViews() {
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        contactName = findViewById(R.id.contactName);
        contactInitial = findViewById(R.id.contactInitial);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // Configuration du nom du contact
        contactName.setText("Prestataire");
        contactInitial.setText("P");
    }

    private void setupMessagesList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        messagesRecyclerView.setLayoutManager(layoutManager);
        messageAdapter = new MessageAdapter(new ArrayList<>(), sessionManager.getUserId());
        messagesRecyclerView.setAdapter(messageAdapter);
    }

    private void loadMessages() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<Message> messages = db.messageDao().getMessagesBetweenUsers(
                    sessionManager.getUserId(), receiverId);
            runOnUiThread(() -> messageAdapter.updateMessages(messages));
        }).start();
    }

    private void sendMessage() {
        String content = messageInput.getText().toString().trim();
        if (content.isEmpty()) return;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Message message = new Message(0, sessionManager.getUserId(), receiverId,
                content, sdf.format(new Date()));

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            db.messageDao().insert(message);
            runOnUiThread(() -> {
                messageInput.setText("");
                loadMessages();
            });
        }).start();
    }
}