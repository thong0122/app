package com.example.doanmobile.chat;

import android.text.TextUtils;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ShopChatController {

    private ShopChatActivity view;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    public ShopChatController(ShopChatActivity view) {
        this.view = view;
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public View.OnClickListener getQuayLaiTinNhanNguoiBanClickListener() {
        // Xử lý logic bấm nút quay lại
        return v -> {
            // Gọi phương thức điều hướng quay lại
            // ...
        };
    }

    public View.OnClickListener getSendMessageClickListener() {
        return v -> sendMessageToUser();
    }

    // Handle send message logic
    private void sendMessageToUser() {
        String messageText = view.getMessageText();
        // Gọi các phương thức để gửi tin nhắn bằng dữ liệu từ model/firestore
        if (!TextUtils.isEmpty(messageText)) {
            // ...
        } else {
            // Show error message
        }
    }
    // Tải thông tin cửa hàng từ Firestore
    public void loadShopInfo() {

    }
    // Tải lịch sử trò chuyện từ Firestore
    public void loadChatHistory() {

    }
    // Cập nhật các phương thức UI dựa trên việc truy xuất dữ liệu
    private void updateShopInfo(String tenDayDu) {
        view.updateTenDayDu(tenDayDu);
    }
    private void updateChatMessages(List<ChatMessage> messages) {
        view.updateChatList(messages);
    }
}

