
package examinador;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class getMate implements Serializable {
    String materia;

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) throws SQLException {
        this.materia = materia;
        guardaMAt(materia);
    }
    
    public void guardaMAt(String mate) throws SQLException{
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("call  spMateria('"+mate+"')");
        msj.next();
        System.out.println(msj.getString(1));
        
        
    } 
    
    
    public ArrayList<String> mates() throws SQLException{
        ArrayList<String> mats= new ArrayList<>();
        
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("select * from vwMateri");
        while(msj.next()){
        String mat= msj.getString("nom");
        mats.add(mat);
    }
        
        return mats;
    }
    
}
