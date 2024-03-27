package com.example.doanmobile.DangKy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doanmobile.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GmailDecorator extends AppCompatActivity {

    private email Email;
    EditText Tendaydugmail,Sodienthoaigmail;
    ImageButton BtnDangKiTaiKhoangmail;
    ImageButton BtnDangKigmail;
    private GoogleSignInClient mGoogleSignInClient;
    private  static final int RC_SIGN_IN = 1;
    FirebaseAuth mAuth;
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail_decorator);
        Tendaydugmail = findViewById(R.id.Tendaydugmail);
        Sodienthoaigmail = findViewById(R.id.Sodienthoaigmail);
        BtnDangKiTaiKhoangmail = findViewById(R.id.BtnDangKiTaiKhoangmail);
        BtnDangKigmail = findViewById(R.id.loginwithgoogle);
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        BtnDangKigmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerWithGmail();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        BtnDangKiTaiKhoangmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = Tendaydugmail.getText().toString();
                String pass = Sodienthoaigmail.getText().toString();

                if (!TextUtils.isEmpty(name)  && !TextUtils.isEmpty(pass))
                {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    //if (currentUser != null)
                    // {
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("tenDayDu", name);
                    userInfo.put("soDienThoai", pass);
                    Email.registerWithEmail(name, pass);
                    fStore.collection("KhachHang").document(currentUser.getUid())
                            .set(userInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(GmailDecorator.this, "Thông tin đã được lưu vào Firestore", Toast.LENGTH_SHORT).show();
                                    // (Optional) Nếu cần, thực hiện các bước tiếp theo sau khi lưu thông tin
                                    Intent intent = new Intent(GmailDecorator.this, dangnhap.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(GmailDecorator.this, "Lỗi khi lưu thông tin vào Firestore", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                else {
                    Toast.makeText(GmailDecorator.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void registerWithGmail() {
        // Gọi intent để thực hiện đăng ký bằng Gmail
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng nhập thành công
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Tiếp tục xử lý tại đây (nếu cần)
                            /*Intent intent = new Intent(dangky.this, GmailDecorator.class);
                            startActivity(intent);*/
                        } else {
                            // Đăng nhập thất bại
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            /*Toast.makeText(dangky.this, "Đăng nhập thất bại.",
                                    Toast.LENGTH_SHORT).show();*/
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Đăng ký bằng Gmail thành công, thực hiện các bước tiếp theo
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Đăng ký bằng Gmail thất bại
                Log.w(TAG, "Đăng nhập thất bại" + e.getStatusCode());
            }
        }
    }
}