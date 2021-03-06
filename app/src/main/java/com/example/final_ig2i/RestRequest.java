package com.example.final_ig2i;

// Il ne s'agit plus de classes filles...
// On passe donc des références à la classe mère dans le constructeur
// On crée une classe RestActivity qui implémente
// ce qu'il faut pour simplifier les requêtes


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class RestRequest extends AsyncTask<String, Void, JSONObject> {

    private RestActivity mAct;
    private GlobalState gs;
    private String action = null;
    // Une tâche ne peut être exécutée qu'une seule fois

    public RestRequest(RestActivity act) {
        mAct = act;
        gs = mAct.gs;
    }

    @Override
    protected void onPreExecute() {
        // S'exécute dans l'UI Thread
        super.onPreExecute();
        Log.i(gs.cat, "onPreExecute");
    }

    @Override
    protected JSONObject doInBackground(String... qs) {
        Log.i(gs.cat, "doInBackground");

        action = qs[1];

        String res = gs.requete(qs[0], action);
        Log.i(gs.cat, "res : " + res);

        JSONObject json;
        try {
            json = new JSONObject(res);

        } catch (JSONException e) {
            e.printStackTrace();
            json = new JSONObject();
        }

        Log.i(gs.cat, "interpretation effectuee");
        return json;
    }

    protected void onPostExecute(JSONObject result) {
        Log.i(gs.cat, "onPostExecute");
        mAct.traiteReponse(result, action);
    }
}