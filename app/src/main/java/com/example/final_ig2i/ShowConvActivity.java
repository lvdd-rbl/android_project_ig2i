package com.example.final_ig2i;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowConvActivity extends RestActivity implements View.OnClickListener {

    private String idConv;
    private int idLastMessage = 0;
    private String currentUser;
    private LinearLayout msgLayout;
    private Button btnOK;
    private EditText edtMsg;
    private ArrayList<Message> messages;
    private Conversation conversation;
    public TextView conversationTitle;




    @Override
    public void traiteReponse(JSONObject o, String action) {
        if (action.contentEquals("POST")) {
            gs.alerter("retour de la requete posterMessage");
        }
        if (action.contentEquals("GET")) {
            try {
                // parcours des messages
                JSONArray messagesJSON = o.getJSONArray("messages");
                int i;
                messages = new ArrayList<Message>();
                for(i=0;i<messagesJSON.length();i++) {
                    JSONObject msgJSON = (JSONObject) messagesJSON.get(i);
                    Message msg = new Message(
                            msgJSON.getInt("id"),
                            msgJSON.getString("auteur"),
                            this.idConv,
                            msgJSON.getString("contenu"),
                            msgJSON.getString("couleur")
                    );


                    messages.add(msg);

                    LayoutInflater inflater = LayoutInflater.from(msgLayout.getContext());
                    View itemView = inflater.inflate(R.layout.message_layout, msgLayout, false);

                    MessageViewRender render = new MessageViewRender(itemView);
                    render.render(msg,msgLayout, currentUser);


                    //msgLayout.addView(tv);
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
        this.messages = new ArrayList<>();

        Bundle bdl = getIntent().getExtras();
        idConv = bdl.getString("idConversation");

        this.conversation = new Conversation(
                bdl.getString("idConversation"),
                bdl.getString("conversationTheme"),
                true
        );

        Log.i("L4-SI-Logs", bdl.getString("conversationTheme"));

        currentUser = bdl.getString("currentUser");


        requetePeriodique(10, "GET");
        msgLayout = findViewById(R.id.conversation_svLayoutMessages);
        this.conversationTitle = findViewById(R.id.conversation_titre);

         this.conversationTitle.setText(this.conversation.getTheme());
        btnOK = findViewById(R.id.conversation_btnOK);
        btnOK.setOnClickListener(this);




        edtMsg = findViewById(R.id.conversation_edtMessage);
    }

    public String urlPeriodique(String action) {
        String qs = "";
        if (action.equals("GET")) {
            qs = "conversations/" + conversation.getId() + "/" + idLastMessage;
        }

        return qs;
    }

    @Override
    public void onClick(View v) {
        // Clic sur OK : on récupère le message
        // conversation_edtMessage
        String msg = edtMsg.getText().toString();
        String qs="conversations/" + conversation.getId() +"/" + idLastMessage + "?contenu=" + msg;

        envoiRequete(qs,"POST");

        edtMsg.setText("");
    }
}
