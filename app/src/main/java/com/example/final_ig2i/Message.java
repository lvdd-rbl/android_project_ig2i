package com.example.final_ig2i;

public class Message {

    private Integer id;
    private String user;
    private String conversation;
    private String message;
    private String couleur;

    public Message() {

    }

    /**
     * Constructeur par données
     * @param id
     * @param user
     * @param conversation
     * @param message
     * @param couleur
     */
    public Message(Integer id, String user, String conversation, String message, String couleur) {
        this.id = id;
        this.user = user;
        this.conversation = conversation;
        this.message = message;
        this.couleur = couleur;
    }

    public Integer getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getConversation() {
        return conversation;
    }

    public String getMessage() {
        return message;
    }

    public String getCouleur() {
        return couleur;
    }
}
