package com.example.final_ig2i;

public class Conversation {

    /*
    * {"connecte":true,
    * "action":"getConversations",
    * "feedback":"entrez action: logout, setPasse(passe),setPseudo(pseudo), setCouleur(couleur),getConversations, getMessages(idConv,[idLastMessage]), setMessage(idConv,contenu), ...",
    * "conversations":[ {"id":"12","active":"1","theme":"Les cours en IAM"},
    *                   {"id":"2","active":"1","theme":"Ballon d'Or"}]}
    * */

    private String _id;
    private String theme;
    private Boolean active;

    // Raccourci : Alt+Ins pour générer getters, setters et constructeurs


    /**
     * Constructeur par données
     * @param _id
     * @param theme
     * @param active
     */
    public Conversation(String _id, String theme, Boolean active) {
        this._id = _id;
        this.theme = theme;
        this.active = active;
    }

    /*
    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", active=" + active +
                '}';
    }*/

    public String toString() {
        return theme;
    }


    public String getId() {
        return _id;
    }

    public String getTheme() {
        return theme;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(String id) {
        this._id = id;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
