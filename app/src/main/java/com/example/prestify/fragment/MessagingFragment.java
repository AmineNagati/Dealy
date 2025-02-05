package com.example.prestify.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestify.R;
import com.example.prestify.activity.ServiceRequestActivity;
import com.example.prestify.adapter.ConversationAdapter;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.Message;
import com.example.prestify.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MessagingFragment extends Fragment {
    private View emptyStateContainer;
    private RecyclerView conversationsRecyclerView;
    private TextView tabEnCours, tabArchivees;
    private Button requestServiceButton;
    private ConversationAdapter conversationAdapter;
    private SessionManager sessionManager;
    private boolean isShowingArchived = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging, container, false);
        initializeViews(view);
        setupClickListeners();
        loadConversations();
        return view;
    }

    private void initializeViews(View view) {
        sessionManager = new SessionManager(requireContext());
        emptyStateContainer = view.findViewById(R.id.emptyStateContainer);
        conversationsRecyclerView = view.findViewById(R.id.conversationsRecyclerView);
        tabEnCours = view.findViewById(R.id.tabEnCours);
        tabArchivees = view.findViewById(R.id.tabArchivees);
        requestServiceButton = view.findViewById(R.id.requestServiceButton);

        conversationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        conversationAdapter = new ConversationAdapter(new ArrayList<>());
        conversationsRecyclerView.setAdapter(conversationAdapter);
    }

    private void setupClickListeners() {
        tabEnCours.setOnClickListener(v -> {
            if (isShowingArchived) {
                isShowingArchived = false;
                updateTabsAppearance();
                loadConversations();
            }
        });

        tabArchivees.setOnClickListener(v -> {
            if (!isShowingArchived) {
                isShowingArchived = true;
                updateTabsAppearance();
                loadConversations();
            }
        });

        requestServiceButton.setOnClickListener(v -> {
            // Redirection vers l'activité de demande de service
            Intent intent = new Intent(getActivity(), ServiceRequestActivity.class);
            startActivity(intent);
        });
    }

    private void updateTabsAppearance() {
        if (isShowingArchived) {
            tabEnCours.setBackgroundResource(R.drawable.tab_unselected_background);
            tabEnCours.setTextColor(getResources().getColor(R.color.gray));
            tabEnCours.setTypeface(null, Typeface.NORMAL);

            tabArchivees.setBackgroundResource(R.drawable.tab_selected_background);
            tabArchivees.setTextColor(getResources().getColor(R.color.black));
            tabArchivees.setTypeface(null, Typeface.BOLD);
        } else {
            tabEnCours.setBackgroundResource(R.drawable.tab_selected_background);
            tabEnCours.setTextColor(getResources().getColor(R.color.black));
            tabEnCours.setTypeface(null, Typeface.BOLD);

            tabArchivees.setBackgroundResource(R.drawable.tab_unselected_background);
            tabArchivees.setTextColor(getResources().getColor(R.color.gray));
            tabArchivees.setTypeface(null, Typeface.NORMAL);
        }
    }

    private void loadConversations() {
        int userId = sessionManager.getUserId();

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            List<Message> messages = db.messageDao().getMessagesByUser(userId);

            requireActivity().runOnUiThread(() -> {
                if (messages.isEmpty()) {
                    showEmptyState();
                } else {
                    showConversations(messages);
                }
            });
        }).start();
    }

    private void showEmptyState() {
        emptyStateContainer.setVisibility(View.VISIBLE);
        conversationsRecyclerView.setVisibility(View.GONE);
    }

    private void showConversations(List<Message> messages) {
        emptyStateContainer.setVisibility(View.GONE);
        conversationsRecyclerView.setVisibility(View.VISIBLE);
        conversationAdapter.updateMessages(messages);
    }
}