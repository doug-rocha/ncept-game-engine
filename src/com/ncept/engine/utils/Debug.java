/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.utils;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Debug {

    public static void LOG(String msg) {
        System.out.println(msg);
    }

    public static void LOG_ERROR(String msg) {
        System.err.println(msg);
    }
    
    public static void LOG(Object o){
        System.out.println(o);
    }

}
