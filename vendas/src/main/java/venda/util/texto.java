package venda.util;

public class texto {

    public static String right(String var, int tam) {
        String ret = var.substring((var.length() - tam), var.length());
        return ret;
    }
    
    public static String strZero(String var, int tam) {
        String ret = null;

        if (tam > var.length()) {
           ret = "0";
           for (int i=0; i < (tam - var.length() - 1); i++) {
               ret += "0";
           }
           ret += var;
        } else {
           ret = var;
        }
        return ret;
    }
    
}
