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


    /**
     * Constructeur
     * @param itemView
     */
    public MessageViewRender(@NonNull View itemView ) {
        super(itemView);
        this.view = itemView;
        this.authorView = view.findViewById(R.id.message_author);
        this.cardView = view.findViewById(R.id.message_card);
        this.messageView = view.findViewById(R.id.message_content);
    }


    public void render(@NonNull Message message, LinearLayout msgLayout, String currentUser) {
        if(message.getUser() != "null" && message.getCouleur() != "null") {

            //défini l'auteur et le message
            //défini la couleur background
            this.authorView.setText(message.getUser());
            this.messageView.setText(message.getMessage());
            this.cardView.setBackgroundColor(Color.parseColor(message.getCouleur()));

            //Si l'utilisateur est le propriétaire du message
            //on affiche le message à droite de l'écran
            if(currentUser.compareTo(message.getUser()) == 0) {

                //on récupère les LinearParams du Layout
                ViewGroup.LayoutParams oldParams = cardView.getLayoutParams();

                //On change le paramètre "gravity"
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(oldParams.width, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.RIGHT;
                cardView.setLayoutParams(params);
            }
            // On affiche le message
            msgLayout.addView(view);
        }
    }
}
