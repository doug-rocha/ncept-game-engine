/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.physicsEngine.objects.etc;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public enum ObjectType {
    PLAYER("player"),
    BLOCK("block"),
    ITEM("item"),
    AIR("air");

    private String value;

    private ObjectType(String value) {
        this.value = value;
    }

    public static ObjectType fromValue(String value) {
        for (ObjectType ot : values()) {
            if (ot.value.equals(value.toLowerCase())) {
                return ot;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
