package com.example.doanmobile.chat;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.doanmobile.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//View
public class ShopChatActivity extends AppCompatActivity {

    private TextView tenDayduTextView;
    private RecyclerView recyclerView;
    private EditText messageEditText;
    private ImageView sendMessageImageView, quaylaitinnhannguoibannha;

    private ShopChatController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_chat);

        tenDayduTextView = findViewById(R.id.tenDayduTextView);
        recyclerView = findViewById(R.id.recycle_shopchat);
        messageEditText = findViewById(R.id.edtinputtext);
        sendMessageImageView = findViewById(R.id.imagechat);
        quaylaitinnhannguoibannha = findViewById(R.id.quaylaitinnhannguoibannha);

        controller = new ShopChatController(this);

        // Thiết lập listeners và logic liên quan đến view khác tại đây
        quaylaitinnhannguoibannha.setOnClickListener(controller.getQuayLaiTinNhanNguoiBanClickListener());
        sendMessageImageView.setOnClickListener(controller.getSendMessageClickListener());

        // Gọi các phương thức điều khiển để lấy dữ liệu và điền vào view
        controller.loadShopInfo();
        controller.loadChatHistory();
    }

    // Cập nhật các phương thức giao diện người dùng dựa trên controller updates
    public void updateTenDayDu(String tenDayDu) {
        tenDayduTextView.setText(tenDayDu);
    }

    public void updateChatList(List<ChatMessage> messages) {
        // Update recycler view adapter with new messages
    }

    public String getMessageText() {
        return messageEditText.getText().toString().trim();
    }
}

