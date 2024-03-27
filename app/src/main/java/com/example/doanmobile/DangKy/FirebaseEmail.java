package com.example.doanmobile.DangKy;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseEmail implements email {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private  static final int RC_SIGN_IN = 1;

    private static final String TAG = "MyActivity";

    FirebaseEmail() {
        mAuth = FirebaseAuth.getInstance();
    }


    public void registerWithEmail(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Đăng ký thành công
                        System.out.println("Registered with email: " + email);
                    } else {
                        // Đăng ký thất bại
                        System.out.println("Registration failed: " + task.getException().getMessage());
                    }
                });
    }
}
