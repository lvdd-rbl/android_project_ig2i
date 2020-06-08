package com.example.final_ig2i;

import java.util.ArrayList;

public class ListeConversations {

    private ArrayList<Conversation> list;

    /**
     * Constructeur par défaut
     */
    public ListeConversations() {
        this.list = new ArrayList<Conversation>();
    }

    public ArrayList<Conversation> getList() {
        return list;
    }

    /**
     * Ajoture une conversation à la liste
     * @param c conversation
     */
    public void addConversation(Conversation c) {
        list.add(c);
    }

    @Override
    public String toString() {
        return "ListeConversations{" +
                "list=" + list +
                '}';
    }
}
