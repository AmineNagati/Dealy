package com.example.prestify.model;

import android.content.Context;
import com.example.prestify.database.AppDatabase;

public class ServiceInitializer {
    public static void initializeServices(Context context) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(context);

            // Vérifier si la base est vide
            if (db.serviceDao().getAllServices().isEmpty()) {
                // Services de Ménage
                db.serviceDao().insert(new Service(
                        "Ménage complet",
                        "Nettoyage complet de votre logement incluant sols, poussière, sanitaires et cuisine",
                        25.0,
                        "MENAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Repassage",
                        "Service de repassage professionnel pour tous types de vêtements",
                        20.0,
                        "MENAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Nettoyage vitres",
                        "Lavage de vitres intérieur/extérieur avec équipement professionnel",
                        22.0,
                        "MENAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Grand nettoyage saisonnier",
                        "Nettoyage approfondi de printemps ou d'automne",
                        30.0,
                        "MENAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Nettoyage après travaux",
                        "Remise en état complète après travaux ou rénovation",
                        35.0,
                        "MENAGE"
                ));

                // Services de Bricolage
                db.serviceDao().insert(new Service(
                        "Montage de meubles",
                        "Montage et installation de meubles en kit",
                        30.0,
                        "BRICOLAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Fixation murale",
                        "Installation d'étagères, tableaux, TV et autres éléments muraux",
                        35.0,
                        "BRICOLAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Petites réparations",
                        "Réparations diverses : poignées, serrures, joints, etc.",
                        32.0,
                        "BRICOLAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Peinture",
                        "Travaux de peinture intérieure et retouches",
                        28.0,
                        "BRICOLAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Installation électrique basique",
                        "Installation de luminaires, prises et petits travaux électriques",
                        40.0,
                        "BRICOLAGE"
                ));

                // Services de Jardinage
                db.serviceDao().insert(new Service(
                        "Tonte de pelouse",
                        "Tonte et entretien régulier de votre pelouse",
                        40.0,
                        "JARDINAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Taille de haies",
                        "Taille et mise en forme de vos haies et arbustes",
                        45.0,
                        "JARDINAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Désherbage",
                        "Désherbage manuel et entretien des massifs",
                        38.0,
                        "JARDINAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Plantation",
                        "Plantation de fleurs, arbustes et petits arbres",
                        42.0,
                        "JARDINAGE"
                ));
                db.serviceDao().insert(new Service(
                        "Ramassage feuilles",
                        "Nettoyage et ramassage des feuilles mortes",
                        35.0,
                        "JARDINAGE"
                ));

                // Services pour Animaux
                db.serviceDao().insert(new Service(
                        "Garde d'animaux à domicile",
                        "Garde de vos animaux chez vous pendant votre absence",
                        20.0,
                        "ANIMAUX"
                ));
                db.serviceDao().insert(new Service(
                        "Promenade de chiens",
                        "Promenade quotidienne avec exercice adapté",
                        18.0,
                        "ANIMAUX"
                ));
                db.serviceDao().insert(new Service(
                        "Visite pour chats",
                        "Visite et soin de vos chats à votre domicile",
                        15.0,
                        "ANIMAUX"
                ));
                db.serviceDao().insert(new Service(
                        "Transport animalier",
                        "Transport de vos animaux (vétérinaire, toiletteur...)",
                        25.0,
                        "ANIMAUX"
                ));
                db.serviceDao().insert(new Service(
                        "Toilettage à domicile",
                        "Service de toilettage complet à votre domicile",
                        45.0,
                        "ANIMAUX"
                ));

                // Services Informatiques
                db.serviceDao().insert(new Service(
                        "Dépannage PC",
                        "Diagnostic et résolution de problèmes informatiques",
                        35.0,
                        "INFORMATIQUE"
                ));
                db.serviceDao().insert(new Service(
                        "Installation logiciels",
                        "Installation et configuration de logiciels",
                        30.0,
                        "INFORMATIQUE"
                ));
                db.serviceDao().insert(new Service(
                        "Configuration réseau",
                        "Installation et paramétrage de votre réseau domestique",
                        40.0,
                        "INFORMATIQUE"
                ));
                db.serviceDao().insert(new Service(
                        "Formation informatique",
                        "Initiation et formation aux outils informatiques",
                        32.0,
                        "INFORMATIQUE"
                ));
                db.serviceDao().insert(new Service(
                        "Sauvegarde données",
                        "Sauvegarde et transfert de vos données importantes",
                        38.0,
                        "INFORMATIQUE"
                ));

                // Services Aide à domicile
                db.serviceDao().insert(new Service(
                        "Aide aux seniors",
                        "Accompagnement et aide quotidienne pour les seniors",
                        28.0,
                        "AIDE_DOMICILE"
                ));
                db.serviceDao().insert(new Service(
                        "Courses à domicile",
                        "Service de courses et livraison à domicile",
                        25.0,
                        "AIDE_DOMICILE"
                ));
                db.serviceDao().insert(new Service(
                        "Préparation de repas",
                        "Préparation de repas équilibrés à domicile",
                        30.0,
                        "AIDE_DOMICILE"
                ));
                db.serviceDao().insert(new Service(
                        "Aide administrative",
                        "Assistance pour les démarches administratives",
                        27.0,
                        "AIDE_DOMICILE"
                ));
                db.serviceDao().insert(new Service(
                        "Accompagnement sorties",
                        "Accompagnement pour les rendez-vous et sorties",
                        26.0,
                        "AIDE_DOMICILE"
                ));

                // Cours particuliers
                db.serviceDao().insert(new Service(
                        "Soutien scolaire",
                        "Aide aux devoirs et soutien scolaire personnalisé",
                        30.0,
                        "COURS"
                ));
                db.serviceDao().insert(new Service(
                        "Cours de langues",
                        "Cours de langues étrangères tous niveaux",
                        35.0,
                        "COURS"
                ));
                db.serviceDao().insert(new Service(
                        "Cours de musique",
                        "Initiation et perfectionnement en musique",
                        40.0,
                        "COURS"
                ));
                db.serviceDao().insert(new Service(
                        "Aide examens",
                        "Préparation intensive aux examens",
                        38.0,
                        "COURS"
                ));
                db.serviceDao().insert(new Service(
                        "Cours d'informatique",
                        "Formation personnalisée en informatique",
                        32.0,
                        "COURS"
                ));

                // Services de Déménagement
                db.serviceDao().insert(new Service(
                        "Déménagement complet",
                        "Service complet de déménagement avec transport",
                        50.0,
                        "DEMENAGEMENT"
                ));
                db.serviceDao().insert(new Service(
                        "Emballage",
                        "Service professionnel d'emballage de vos biens",
                        35.0,
                        "DEMENAGEMENT"
                ));
                db.serviceDao().insert(new Service(
                        "Démontage/Remontage",
                        "Démontage et remontage de vos meubles",
                        40.0,
                        "DEMENAGEMENT"
                ));
                db.serviceDao().insert(new Service(
                        "Transport express",
                        "Transport rapide de quelques meubles",
                        45.0,
                        "DEMENAGEMENT"
                ));
                db.serviceDao().insert(new Service(
                        "Débarras",
                        "Service de débarras et évacuation d'objets",
                        42.0,
                        "DEMENAGEMENT"
                ));

                // Services pour Enfants
                db.serviceDao().insert(new Service(
                        "Garde d'enfants",
                        "Garde d'enfants à domicile régulière",
                        25.0,
                        "ENFANTS"
                ));
                db.serviceDao().insert(new Service(
                        "Aide aux devoirs",
                        "Accompagnement scolaire personnalisé",
                        28.0,
                        "ENFANTS"
                ));
                db.serviceDao().insert(new Service(
                        "Activités créatives",
                        "Animation d'activités créatives et ludiques",
                        30.0,
                        "ENFANTS"
                ));
                db.serviceDao().insert(new Service(
                        "Sortie d'école",
                        "Accompagnement école-domicile et goûter",
                        22.0,
                        "ENFANTS"
                ));
                db.serviceDao().insert(new Service(
                        "Baby-sitting soirée",
                        "Garde d'enfants en soirée",
                        32.0,
                        "ENFANTS"
                ));
            }
        }).start();
    }
}