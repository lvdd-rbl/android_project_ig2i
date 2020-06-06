package com.example.final_ig2i;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewRender extends RecyclerView.ViewHolder {

    public final View view;
    public final View cardView;
    public final TextView authorView;
    public final TextView messageView;


    public MessageViewRender(@NonNull View itemView ) {
        super(itemView);
        this.view = itemView;
        this.authorView = view.findViewById(R.id.message_author);
        this.cardView = view.findViewById(R.id.message_card);
        this.messageView = view.findViewById(R.id.message_content);
    }


    public void render(@NonNull Message message, LinearLayout msgLayout, String currentUser) {
        this.authorView.setText(message.getUser());
        this.messageView.setText(message.getMessage());
        this.cardView.setBackgroundColor(Color.parseColor(message.getCouleur()));
        Log.i("L4-SI-Logs", "CURRENT USER IS : " + currentUser);
        Log.i("L4-SI-Logs", "AUTHOR IS : " + message.getUser());
        Log.i("L4-SI-Logs", "AUTHOR IS : " + currentUser.compareTo(message.getUser()));

        if(currentUser.compareTo(message.getUser()) == 0) {
            Log.i("L4-SI-Logs", "GO RIGHT " + message.getUser());
            ViewGroup.LayoutParams oldParams = cardView.getLayoutParams();

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(oldParams.width, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;

            cardView.setLayoutParams(params);
        }
        msgLayout.addView(view);
    }
}
