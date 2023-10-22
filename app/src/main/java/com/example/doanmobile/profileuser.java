package com.example.doanmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanmobile.dangkynguoiban.dangkylenguoiban;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class profileuser extends AppCompatActivity {

    TextView tendayduprofile,tengmailprofile;
    ImageView backprofile,dangxuatpf;

    View dkyngbanpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileuser);

        //Cập nhật tên người dùng gmail tenday du len user
        tendayduprofile = findViewById(R.id.tendayduprofile);
        tengmailprofile = findViewById(R.id.tengmailprofile);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        FirebaseUser user = fAuth.getCurrentUser();
        String userId = user.getUid();
        DocumentReference userRef = fStore.collection("KhachHang").document(userId);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String fullName = documentSnapshot.getString("tenDayDu");
                String gmail = documentSnapshot.getString("email");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tendayduprofile.setText(fullName);
                        tengmailprofile.setText(gmail);
                    }
                });
            }
        });
        //Chuyen sang trang chu nguoidung
        backprofile = findViewById(R.id.backprofile);
        backprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileuser.this, trangchunguoidung.class);
                startActivity(intent);
            }
        });
        //Đăng xuất tài khoản
        dangxuatpf = findViewById(R.id.dangxuatpf);
        dangxuatpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), dangnhap.class));
                finish();
            }
        });
        //Dang ky len nguoi ban
        dkyngbanpf = findViewById(R.id.dkyngbanpf);
        dkyngbanpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileuser.this, dangkylenguoiban.class);
                startActivity(intent);
            }
        });

    }
}