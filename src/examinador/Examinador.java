

package examinador;

import java.io.File;
import java.io.IOException;


public class Examinador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Jpanes hoj = new Jpanes();
        //hoj.sesion();
        //calificar cc = new calificar();
        //cc.calf();
        hojaDeRespuestas hj = new hojaDeRespuestas("Si", "Yo mero3", 15, "Fisica", "Hoy");
        hj.generarHoja();
    }
    
}
//<form action subir.jsp method="post" enctype="multipart/form-data">