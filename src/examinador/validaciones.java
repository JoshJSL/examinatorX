/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examinador;

/**
 *
 * @author joshua
 */
public class validaciones {

    private boolean val;
    
    public boolean letras(String letritas){
        if(letritas.equals("")){
            return false;
        }
        
        val=true;
        for (int i = 0; i < letritas.length(); i++) {
            if ((letritas.charAt(i)>='a'&&letritas.charAt(i)<='z')||(letritas.charAt(i)>='A'&&letritas.charAt(i)<='Z')) {
            }else{
                val=false;
            }
        }
        
        return val;
    }
    
    public boolean num(String numeros){
        if(numeros.equals("")){
            return false;
        }
        val=true;
        
        if(numeros.length()!=10)
            return false;
        if(numeros.indexOf("PM")!=-1)
            if( numeros.split("PM")[1].length()==8)
                numeros= numeros.split("PM")[1];
        
        for (int i = 0; i < numeros.length(); i++) {
            if (numeros.charAt(i)>=48&&numeros.charAt(i)<=57) {
            }else{
                val=false;
            }
        }
        
        return val;
    }
    
    public boolean correo(String correo){
        //val=true;
        if(correo.equals("")){
            return false;
        }
        String [] part=correo.split("@");
        String [] part2=part[part.length-1].split("\\.");
        
        if(part.length!=2){
            return false;
        }
        if(part2.length<=1 || part2[part2.length-1].length()==0){
            
            return false;
                   
        }
        
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < part.length; j++) {
                if ((part[i].charAt(j)>=65&&part[i].charAt(j)<=90)||(part[i].charAt(j)>=97&&part[i].charAt(j)<=122)||(part[i].charAt(j)=='.'||part[i].charAt(j)=='-'||part[i].charAt(j)=='_')) {
                }else{
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean contra(String contra){
        if(contra.equals("")){
            return false;
        }
        //val=true;
        if(contra.length()>100&&contra.length()<6){
            return false;
        }
        
            for (int j = 0; j < contra.length(); j++) {
                if ((contra.charAt(j)>=48&&contra.charAt(j)<=57)||(contra.charAt(j)>=65&&contra.charAt(j)<=90)||(contra.charAt(j)>=97&&contra.charAt(j)<=122)||(contra.charAt(j)=='.'||contra.charAt(j)=='-'||contra.charAt(j)=='_'||contra.charAt(j)=='@'||contra.charAt(j)=='*'||contra.charAt(j)=='+'||contra.charAt(j)=='&'||contra.charAt(j)=='#'||contra.charAt(j)=='$'||contra.charAt(j)=='/')) {
                }else{
                    return false;
                }
            }
        
        return true;
        
        
    }
    public boolean todo(String nombre,String  pat,String  mat,String  boleta,String  correo,String  contra,String  contra2){
           
            if(letras(nombre) && letras(pat) && letras(mat) && num(boleta) && correo(correo) && contra(contra)&& contra(contra2)){
                return true;
            }else{
            return false;
            }
        }
    
    public boolean esNum(String cadena){
        if(cadena.equals("")){
            return false;
        }
        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i)>=48&&cadena.charAt(i)<=57) {
            }else{
                return false;
            }
        }
        //48 57
        
        return true;
    }
    public boolean grupi(String grupo){
        if(grupo.equals("")){
            return false;
        }
        for (int i = 0; i < grupo.length(); i++) {
            if ((grupo.charAt(i)>=48&&grupo.charAt(i)<=57)||(grupo.charAt(i)>='a'&&grupo.charAt(i)<='z')||(grupo.charAt(i)>='A'&&grupo.charAt(i)<='Z')) {
            }else{
                return false;
            }
        }
        
        return true;
    }
    public int sigInt(float nume){

        String f= nume+"";
        String []f2= new String[2];
        
        f2=f.split("\\.");
        if(f2[1].equals("0")){
            return Integer.parseInt(f2[0]);
        }
        
        return (Integer.parseInt(f2[0])+1);
    }
}
