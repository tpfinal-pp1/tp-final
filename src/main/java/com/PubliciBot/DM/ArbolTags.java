package com.PubliciBot.DM;

import java.util.ArrayList;

/**
 * Created by Hugo on 21/05/2017.
 */
public class ArbolTags {
    private ArrayList<Tag> tags;

    public ArbolTags() {
        this.tags = new ArrayList<>();
    }

    public ArbolTags(ArrayList<Tag> nodos) {
        this.tags = nodos;
    }

    public void AgregarTag(Tag tag)
    {
        this.tags.add(tag);
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }


}
