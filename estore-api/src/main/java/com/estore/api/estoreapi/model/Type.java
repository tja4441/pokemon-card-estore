package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration Representing a type in the Pokemon TCG
 * 
 * @author Jensen DeRosier
 */
public enum Type {
    GRASS ("Grass"),
    FIRE ("Fire"),
    WATER ("Water"),
    ELECTRIC ("Electric"),
    FIGHTING ("Fighting"),
    PSYCHIC ("Psychic"),
    NORMAL ("Normal"),
    DARK ("Dark"),
    STEEL ("Steel"),
    DRAGON ("Dragon"),
    FAIRY ("Fairy"),
    TRAINER ("Trainer");

    @JsonProperty("string") private String name;

    private Type (@JsonProperty("type")String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
