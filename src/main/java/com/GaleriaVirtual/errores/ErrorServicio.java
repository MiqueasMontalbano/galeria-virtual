/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GaleriaVirtual.errores;

/**
 *
 * @author Guille
 */
public class ErrorServicio extends Exception{
    
    public ErrorServicio(String msj){
        super(msj);
    }       
}
