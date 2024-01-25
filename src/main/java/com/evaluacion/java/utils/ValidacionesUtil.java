package com.evaluacion.java.utils;

import com.evaluacion.java.model.SesionUsuario;

public class ValidacionesUtil {
    public static boolean validaSesion(SesionUsuario respuesta){
        if(respuesta != null && !respuesta.getSesion()){
            return false;
        }
        return true;
    }

}
