package examinador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class seGrupo {

   public ArrayList<String> gruposProf(String _id) throws SQLException{
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("call spGrupoPers("+_id+", 'profe', 0)");
        ArrayList <String> grups= new ArrayList<>();
        
        while(msj.next()){
            String str=  msj.getString("nomGru");
            grups.add(str);
        }
        return grups;
    }
    
}
