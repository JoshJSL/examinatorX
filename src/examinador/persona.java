package examinador;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author joshua
 */
public class persona {

    private String _id;
    private String _clave;
    private String _contra;
    private String _nombre;
    private String _apPat;
    private String _apMat;
    private String _correo;
    private String _foto;
    private String _tipo;
    
    public persona(){
        
    }
    

     public persona(String _tipo, String _id, String _clave, String _contra, String _nombre, String _apPat, String _apMat, String _correo, String _foto) {
        this._id = _id;
        this._clave = _clave;
        this._contra = _contra;
        this._nombre = _nombre;
        this._apPat = _apPat;
        this._apMat = _apMat;
        this._correo = _correo;
        this._foto=_foto;
        this._tipo=_tipo;
    }
    
     
        public void obten(String _id, String _clave) throws SQLException{
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj=conec.ejecuta("call spEntraID( "+_id+", '"+_clave+"')");
        msj.next();
        
        this._clave = msj.getNString("boleta");        
        this._nombre = msj.getNString("nomb");
        this._apPat = msj.getNString("pate");
        this._apMat = msj.getNString("mate");
        this._correo = msj.getNString("email");
        this._foto=msj.getNString("imagen");
        this._id = msj.getString("personaid");
         
     }
     

    public String getNombre() {

        return this._nombre;
    }

    public String getApeMater() {

        return this._apMat;
    }

    public String getApePater() {

        return this._apPat;
    }

    public String getNomCompleto() {
        String nomComp = getNombre() + " " + getApePater() + " " + getApeMater();

        return nomComp;
    }

    public String getClave() {

        return this._clave;
    }

    public String getId() {

        return this._id;
    }

    public String getCorreo() {

        return this._correo;
    }

    public String getContra() {

        return this._contra;
    }
    
    public String getFoto(){
        
        return "../foto/"+this._foto; 
    }
    
    public String entra(String _clave, char[] contri, String _tipo) throws SQLException{
        _contra="";
        for (int i = 0; i < contri.length; i++) {
            _contra+=contri[i];
        }
        
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj=conec.ejecuta("call spEntra('"+_clave+"', '"+_contra+"', '"+_tipo+"')");
        
        msj.next();
        return ""+msj.getNString(1);
    }
    
    
    
    public String guardarBD() throws SQLException{
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("call spGuardaPer("+
                                    this._id + ", '"+this._nombre+"', '"+this._apPat+"', '"+
                                    this._apMat+"', '"+ this._clave+"', '"+this._contra+"', '"
                                    +this._correo+"', '"+this._tipo
                +"', '"+this._foto+"')");
        
        
        msj.next();
        return ""+msj.getNString("mensaje");
        
    }
    
    public boolean confirma(String codigo,String id, int vez) throws SQLException{
        cDatos conec=new cDatos();
        conec.conectar();
        ResultSet msj =conec.ejecuta("call spRevisa("+id+", "+vez+", '"+codigo+"')");
        msj.next();
        String mensa=msj.getNString("mensaje");
        return mensa.equals("si");
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setClave(String _clave) {
        this._clave = _clave;
    }

    public void setContra(String _contra) {
        this._contra = _contra;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    public void setApPat(String _apPat) {
        this._apPat = _apPat;
    }

    public void setApMat(String _apMat) {
        this._apMat = _apMat;
    }

    public void setCorreo(String _correo) {
        this._correo = _correo;
    }

    public void setFoto(String _foto) {
        this._foto = _foto;
    }

    public void setTipo(String _tipo) {
        this._tipo = _tipo;
    }
    
    

}