package com.example.prestify.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.prestify.R;
import com.example.prestify.activity.LoginActivity;
import com.example.prestify.activity.SignupActivity;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.User;
import com.example.prestify.util.SessionManager;

public class AccountFragment extends Fragment {
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sessionManager = new SessionManager(requireContext());
        Log.d("AccountFragment", "isLoggedIn: " + sessionManager.isLoggedIn());

        if (sessionManager.isLoggedIn()) {
            return inflater.inflate(R.layout.fragment_account_logged_in, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_account, container, false);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (sessionManager.isLoggedIn()) {
            setupLoggedInView(view);
        } else {
            setupLoggedOutView(view);
        }
    }

    private void setupLoggedInView(View view) {
        // Récupérer les vues principales
        TextView usernameTextView = view.findViewById(R.id.usernameTextView);
        TextView userInitial = view.findViewById(R.id.userInitial);
        Button logoutButton = view.findViewById(R.id.logoutButton);

        // Récupérer l'utilisateur connecté
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            User user = db.userDao().getUserById(sessionManager.getUserId());

            requireActivity().runOnUiThread(() -> {
                if (user != null) {
                    String fullName = user.getFirstName() + " " + user.getLastName();
                    usernameTextView.setText(fullName);
                    userInitial.setText(String.valueOf(user.getFirstName().charAt(0)).toUpperCase());
                }
            });
        }).start();

        // Configurer tous les clics sur les options
        setupOptionClickListeners(view);

        // Gestion de la déconnexion
        logoutButton.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Déconnexion")
                    .setMessage("Êtes-vous sûr de vouloir vous déconnecter ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        sessionManager.logout();
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainer, new AccountFragment())
                                .commit();
                    })
                    .setNegativeButton("Non", null)
                    .show();
        });
    }

    private void setupLoggedOutView(View view) {
        Button signupButton = view.findViewById(R.id.signupButton);
        TextView loginLink = view.findViewById(R.id.loginLink);

        signupButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SignupActivity.class));
        });

        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        // Configurer les clics pour les options du layout déconnecté
        setupOptionClickListeners(view);
    }

    private void setupOptionClickListeners(View view) {
        // Section Gérer mon compte
        setClickWithToast(view, R.id.personalInfoOption, "Informations personnelles");
        setClickWithToast(view, R.id.balanceOption, "Mon solde");
        setClickWithToast(view, R.id.taxCertificateOption, "Attestations fiscales");
        setClickWithToast(view, R.id.notificationsOption, "Gestion des notifications");

        // Section Produits
        setClickWithToast(view, R.id.taxCreditOption, "Crédit d'impôt");
        setClickWithToast(view, R.id.inviteFriendsOption, "Invitation d'amis");
        setClickWithToast(view, R.id.vipStatusOption, "Statut VIP");
        setClickWithToast(view, R.id.giftCardsOption, "Cartes cadeaux");
        setClickWithToast(view, R.id.prestifyDirectOption, "Prestify Direct");

        // Section Informations utiles
        setClickWithToast(view, R.id.helpCenterOption, "Centre d'aide");
        setClickWithToast(view, R.id.prestifyCoverOption, "Prestify Cover");
        setClickWithToast(view, R.id.securityOption, "Confiance et sécurité");
        setClickWithToast(view, R.id.taxInfoOption, "Crédit d'impôts");
        setClickWithToast(view, R.id.termsOption, "Conditions générales");
        setClickWithToast(view, R.id.becomeProviderOption, "Devenir Prestataire");

        // Gestion du clic sur le statut VIP
        View vipStatusLayout = view.findViewById(R.id.vipStatusLayout);
        if (vipStatusLayout != null) {
            vipStatusLayout.setOnClickListener(v -> {
                Toast.makeText(requireContext(), "Statut VIP - Bientôt disponible", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void setClickWithToast(View rootView, int viewId, String message) {
        View view = rootView.findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(v -> {
                Toast.makeText(requireContext(), message + " - Bientôt disponible", Toast.LENGTH_SHORT).show();
            });
        }
    }
}