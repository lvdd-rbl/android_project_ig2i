package com.example.final_ig2i;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.util.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


public class GlobalState extends Application {

    public String cat = "L4-SI-Logs";
    public String CAT = "L4-SI-Logs";
    private String secret;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        secret = "5906d67a67fbd6c94de29792c3b95666cc35dbd4430612ca7ac0043d56c6a0b44feb55fe4c55855d5aed28f8aff90f059226a47939046261bfc19e6b9eec8857";
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }

    public void alerter(String s) {
        Log.i(CAT, s);
        Toast t = Toast.makeText(this, s, Toast.LENGTH_LONG);
        t.show();
    }


    private String convertStreamToString(InputStream in) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String requete(String qs, String action) {
        if (qs != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String urlData = prefs.getString("urlData", "http://10.0.2.2:8081/api");

            try {
                int indexBodyQuery = -1;
                String stringBody = "";
                String query = qs;
                if (action == "POST") {
                    indexBodyQuery = qs.indexOf("?");
                    if (indexBodyQuery != -1) {
                        query = qs.substring(0, indexBodyQuery);
                        stringBody = qs.substring(indexBodyQuery + 1, qs.length());
                        Log.i(CAT, query + " " + stringBody);
                    }
                }
                URL url = new URL(urlData + "/" + query);
                Log.i(CAT, "url utilisée : " + url.toString());
                HttpURLConnection urlConnection = null;
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod(action);

                if (action == "POST" && indexBodyQuery != -1) { // construction du body de la requête
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    urlConnection.setUseCaches(false);
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    String jsonBody = "{\""; // todo algo à part "queryStringToJSON"
                    for (int i = 0; i < stringBody.length(); i++) {
                        if (stringBody.charAt(i) == '=') {
                            jsonBody += "\"" + ":" + "\"";
                        } else if (stringBody.charAt(i) == '&') {
                            jsonBody += "\"" + "," + "\"";
                        } else {
                            jsonBody += stringBody.charAt(i);
                        }
                    }
                    jsonBody += "\"}";
                    Log.i(CAT, jsonBody);

                    OutputStream wr = urlConnection.getOutputStream();
                    wr.write(jsonBody.getBytes("utf-8"));

                    wr.flush();
                    wr.close();
                }
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                Log.i(CAT, "responseCode " + responseCode);
                InputStream in = null;
                in = new BufferedInputStream(urlConnection.getInputStream());
                String txtReponse = convertStreamToString(in);
                urlConnection.disconnect();
                return txtReponse;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public boolean verifReseau() {
        // On vérifie si le réseau est disponible,
        // si oui on change le statut du bouton de connexion
        ConnectivityManager cnMngr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cnMngr.getActiveNetworkInfo();

        String sType = "Aucun réseau détecté";
        Boolean bStatut = false;
        if (netInfo != null) {
            NetworkInfo.State netState = netInfo.getState();

            if (netState.compareTo(NetworkInfo.State.CONNECTED) == 0) {
                bStatut = true;
                int netType = netInfo.getType();
                switch (netType) {
                    case ConnectivityManager.TYPE_MOBILE:
                        sType = "Réseau mobile détecté";
                        break;
                    case ConnectivityManager.TYPE_WIFI:
                        sType = "Réseau wifi détecté";
                        break;
                }

            }
        }

        this.alerter(sType);
        return bStatut;
    }


    public String createJWT(JSONObject dataToCrypt) throws UnsupportedEncodingException {

        return Jwts.builder()
                .setSubject("LE4-CHAT")
                .claim("data", dataToCrypt.toString())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes("UTF-8"))
                .compact();
    }

    public Claims  decodeJWT(String jwtToDecrypt)throws UnsupportedEncodingException {
        Jws<Claims> result = Jwts.parser()
                .setSigningKey( secret.getBytes("UTF-8"))
                .parseClaimsJws(jwtToDecrypt);

        System.out.println(result);
        return result.getBody();
    }
}
