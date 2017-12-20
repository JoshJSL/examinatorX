package examinador;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
public class constructor implements Serializable{
    
    private String Titulo, 
            Fecha, 
            Periodo, 
            Maestro, 
            Materia,
            idExa;

  
    
    private int numPreg, 
            numCol;
    private String pregunta, 
            opc[] = new String[4], 
            opcCol[] = new String[numCol],
            relCol[] = new String[numCol], 
            respuesta;

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String[] getOpc() {
        return opc;
    }

    public void setOpc(String[] opc) {
        this.opc = opc;
    }

    public String[] getOpcCol() {
        return opcCol;
    }

    public void setOpcCol(String[] opcCol) {
        this.opcCol = opcCol;
    }

    public String[] getRelCol() {
        return relCol;
    }

    public void setRelCol(String[] relCol) {
        this.relCol = relCol;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }

    public int getNumPreg() {
        return numPreg;
    }

    public void setNumPreg(int numPreg) {
        this.numPreg = numPreg;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getPeriodo() {
        return Periodo;
    }

    public void setPeriodo(String Periodo) {
        this.Periodo = Periodo;
    }

    public String getMaestro() {
        return Maestro;
    }

    public void setMaestro(String Maestro) {
        this.Maestro = Maestro;
    }

    public String getMateria() {
        return Materia;
    }

    public void setMateria(String Materia) {
        this.Materia = Materia;
    }
    
      public String getIdExa() {
        return idExa;
    }

    public void setIdExa(String idExa) {
        this.idExa = idExa;
    }
    
    public String[] guardaEx(String _idP) throws SQLException{
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("call spGuardaExam('"+Fecha+"', "+numPreg+", '"+Periodo+"', '"+Materia+"',"+_idP+")");
        msj.next();
        String[] mensa={msj.getString("idExam"),msj.getString("mensaje")};
        String hola=mensa[1];
        idExa=mensa[0];
        return mensa;
    }
    
    public void guardaExaGrup(String Grup,String idEx) throws SQLException{
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("call spExaGrup("+idEx+", '"+Grup+"')");
        msj.next();        
    }
    
    
    
}
