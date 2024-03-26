package com.example.doanmobile.chat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatRepository {

    private final FirebaseFirestore db;

    public ChatRepository(FirebaseFirestore db) {
        this.db = db;
    }

    public Task<DocumentReference> sendMessage(ChatMessage chatMessage, int userID) {
        chatMessage.setUserID(userID);
        return db.collection("chat")
                .add(chatMessage);
    }
}
