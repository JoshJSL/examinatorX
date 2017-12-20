/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examinador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author joshua
 */
public class examen_alumno {
    
    private int _id;
    private int id_alumno;
    private int id_examen;
    private int _aciertos;
    private int _calif;

    
    public int getId(){
        return this._id;
    }
    
    public int getIdAlumno(){
        return this.id_alumno;
    }
    
    public int getIdExamen(){
        return this.id_examen;
    }
    
    public int getAciertos(){
        return this._aciertos;
    }
    
    public int getCalif(){
        return this._calif;
    }
    
    public ArrayList<String[]> exAlum(String _id, String _idEx) throws SQLException{
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("call spVeAlumExm("+_id+", "+_idEx+")");
        ArrayList <String[]> grups= new ArrayList<>();
        while(msj.next()){
            
            
            if(msj.getString("mensaje").equals(" ")){
                String[] str= {msj.getString("idExa")+"", msj.getString("aciert")+"",msj.getString("calif")+"",msj.getString("boleta")+"",msj.getString("nomb")+"",msj.getString("grupo")+"",msj.getString("materia")+"",msj.getString("fech")+""};
                grups.add(str);
            }else{
                return grups;
            }
                
        }
        return grups;
        
        
    }
    
    public ArrayList <String[]> alumPorProf(String _IdP) throws SQLException{
        
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("call spVeAlumExmIdP("+_IdP+")");
        
        ArrayList <String[]> grups= new ArrayList<>();
        while(msj.next()){
            if(msj.getString("mensaje").equals(" ")){
                String[] str= {msj.getString("idRelacion")+"", msj.getString("idPersona")+"",msj.getString("idExa")+"",msj.getString("aciert")+"",msj.getString("calif")+"",msj.getString("boleta")+"",msj.getString("nomb")+"",msj.getString("grupo")+"",msj.getString("materia")+"",msj.getString("fech")+""};
                grups.add(str);
            }else{
                String[] str={msj.getString(1),"","","","","","","","",""};
                grups.add(str);
                return grups;
            }
                
        }
        return grups;
    }
    
    public ArrayList<String[]> califAlum(String _idA) throws SQLException{
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("call spCalfAlum("+_idA+")");
        
        ArrayList <String[]> grups= new ArrayList<>();
        while(msj.next()){
            
            
            if(msj.getString("mensaje").equals(" ")){
                String[] str= {msj.getString("examen")+"", msj.getString("fech")+"",msj.getString("puntaje")+"",msj.getString("calif")};
                grups.add(str);
            }else{
                String hola=msj.getString("mensaje").equals("no ex")?"No hay examenes":msj.getString("mensaje");
                String[] str={"Ups !",hola, "",""};
                grups.add(str);
                return grups;
            }
                
        }
        return grups;
    }
    
    public String guardaCalif(String idExam, String idalum, String aciertos, String calif) throws SQLException{
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("call  spExaAlumn("+idExam+", '"+idalum+"' ,0,"+aciertos+", "+calif+")");
        msj.next();
        return msj.getString(1);
        
        
    } 
    
    
    public void guardarBD(){
        /*
        
        aqui va el codigo chidorris que hace la magia
        
        */
    }
    
}
