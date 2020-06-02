package com.example.final_ig2i;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowConvActivity extends RestActivity implements View.OnClickListener {

    private String idConv;
    private int idLastMessage = 0;

    private LinearLayout msgLayout;
    private Button btnOK;
    private EditText edtMsg;

    @Override
    public void traiteReponse(JSONObject o, String action) {
        if (action.contentEquals("POST")) {
            gs.alerter("retour de la requete posterMessage");
        }
        if (action.contentEquals("GET")) {
            try {
                // parcours des messages
                JSONArray messages = o.getJSONArray("messages");
                int i;
                for(i=0;i<messages.length();i++) {
                    JSONObject msg = (JSONObject) messages.get(i);
                    String contenu =  msg.getString("contenu");
                    String auteur =  msg.getString("auteur");
                    String couleur =  msg.getString("couleur");

                    TextView tv = new TextView(this);
                    tv.setText("[" + auteur + "] " + contenu);
                    tv.setTextColor(Color.parseColor(couleur));

                    msgLayout.addView(tv);
                }

                // mise à jour du numéro du dernier message
                idLastMessage = Integer.parseInt(o.getString("idLastMessage"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_conversation);

        Bundle bdl = getIntent().getExtras();
        idConv = bdl.getString("idConversation");

        gs.alerter(idConv);

        requetePeriodique(10, "GET");
        msgLayout = findViewById(R.id.conversation_svLayoutMessages);

        btnOK = findViewById(R.id.conversation_btnOK);
        btnOK.setOnClickListener(this);

        edtMsg = findViewById(R.id.conversation_edtMessage);
    }

    public String urlPeriodique(String action) {
        String qs = "";
        if (action.equals("GET")) {
            qs = "conversations/" + idConv + "/" + idLastMessage;
        }

        return qs;
    }

    @Override
    public void onClick(View v) {
        // Clic sur OK : on récupère le message
        // conversation_edtMessage
        String msg = edtMsg.getText().toString();
        String qs="conversations/" + idConv +"/" + idLastMessage + "?contenu=" + msg;

        envoiRequete(qs,"POST");

        edtMsg.setText("");
    }
}
