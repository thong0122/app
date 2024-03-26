package com.example.doanmobile.yeuthichsanpham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import androidx.annotation.NonNull;

import java.util.List;
public class FavoritesRepository {

    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;

    public FavoritesRepository(FirebaseFirestore fStore, FirebaseAuth fAuth) {
        this.fStore = fStore;
        this.fAuth = fAuth;
    }

    public void getFavorites(List<Favorites> favoriteList, OnDataLoadedCallback callback) {
        FirebaseUser user = fAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            fStore.collection("favorites")
                    .whereEqualTo("userID", userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                favoriteList.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Favorites favorite = document.toObject(Favorites.class);
                                    favoriteList.add(favorite);
                                }
                                callback.onDataLoaded();
                            } else {
                                callback.onError(task.getException());
                            }
                        }
                    });
        } else {
            callback.onError(new Exception("User not logged in"));
        }
    }

    public interface OnDataLoadedCallback {
        void onDataLoaded();
        void onError(Exception e);
    }
}

