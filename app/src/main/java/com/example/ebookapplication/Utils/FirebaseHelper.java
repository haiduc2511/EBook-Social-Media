package com.example.ebookapplication.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseHelper {
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuth;

    // Singleton instance
    private static FirebaseHelper instance;

    // Private constructor to prevent direct instantiation
    private FirebaseHelper() {
        this.db = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }

    // Method to get the singleton instance
    public static synchronized FirebaseHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseHelper();
        }
        return instance;
    }

    // Method to get the FirebaseFireStore instance
    public FirebaseFirestore getDb() {
        return db;
    }

    // Method to get the FirebaseAuth instance
    public FirebaseAuth getAuth() {
        return mAuth;
    }
}
