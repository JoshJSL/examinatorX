package examinador;

import static com.itextpdf.text.pdf.PdfName.URI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;
import sun.java2d.SunGraphicsEnvironment;

public class Jpanes extends JFrame implements ActionListener  {
    private String[] exam;
    private persona p= new persona();
    private validaciones val= new validaciones();
    private String _idExa="0";
    private static String _idProfe; 
    private JPanel partCen, elegir, pregNormal, relCol, Pa, verEx,
            jpaneExa, elegirHoja, compa, ses, lista, hoja, men, grup,
            parteSup, parteInfe, MainCont;
    private JButton Imprimir, Editar, Eliminar, Siguiente, Examenes, HacerExamenes, Subir,
            Atras, Salir, Aceptar, ExamHoj, SoloHoj, Normal, OpcionMultiple, Sigui, Atra, PregSig,
            PregSigCol, PregAtraCol, PregAtr, Finalizar, ok, Sigui2,
            Regresar, Cargar, Sal, Escaneo, matAgrega, matAc, ini, reg;
    private JLabel Examen, Instrucciones;
    private Rectangle barraSeleccion;
    private Color azul = new Color(29, 51, 75), gris = new Color(226, 226, 226), blanco = new Color(255, 255, 255);
    private Font Enca = new Font(null, 16, 18), Vacios = new Font(null, 45, 45), Ins = new Font(null, 20, 27),
            Letras = new Font(null, 25, 25), otro = new Font(null, 21, 21);
    private JComboBox selec;
    private JXComboBox dia, mes, año, peri, mate;
    private JXTextField nPreguntas, tTitulo, tMate, tPeriodo, tMateria, tProfesor, numP;
    private JXTextField tOpcionA, tOpcionB, tOpcionC, tOpcionD, codi;
    private JXTextArea tEnunciado;
    private JPasswordField contras;
    private JLabel lEcunciado, lOpcionA, lOpcionB, lOpcionC, lOpcionD,
            ImgCont, nPregu;
    private JRadioButton rOpcionA, rOpcionB, rOpcionC, rOpcionD;
    private int numeroRel, numPreg = 0, error, error2, error3;
    private Ventana ven;
    private String nombreArchivo;
    ArrayList<constructor> datExa = new ArrayList<>();
    ArrayList<getMate> materias = new ArrayList();
    ArrayList<setImg> imagenes = new ArrayList();
    
    hojaRespuestasPDF hoj = new hojaRespuestasPDF();
    examenPDF exa = new examenPDF();
    String Direc = "dat/";
    File f = new File(Direc);
    File ficheros[] = f.listFiles();
    private JButton ex[] = new JButton[ficheros.length], ver[] = new JButton[ficheros.length],
            elimi[] = new JButton[ficheros.length], edit[] = new JButton[ficheros.length];
        
    public void AbrirArchivo() {
        try {
            FileInputStream fis = new FileInputStream("Examinador/dat/" + nombreArchivo + ".dat");
            ObjectInputStream entrada = new ObjectInputStream(fis);
            datExa = (ArrayList<constructor>) entrada.readObject();
            entrada.close();
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    public void AbrirArchivo2() {
        try {
            FileInputStream fis = new FileInputStream("dat/materias.dat");
            ObjectInputStream entrada = new ObjectInputStream(fis);
            materias = (ArrayList<getMate>) entrada.readObject();
            entrada.close();
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    public void CerrarArchivo2() {
        try {
            FileOutputStream fos = new FileOutputStream("dat/materias.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(materias);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CerrarArchivo() {
        try {
            FileOutputStream fos = new FileOutputStream("dat/" + nombreArchivo + ".dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(datExa);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sesion() {
        ses = new JPanel();
        ses.setBackground(blanco);
        ses.setLayout(new GridLayout(6, 1, 0, 20));
        JLabel Tit = new JLabel("Inicia Sesión");
        Tit.setForeground(azul);
        Tit.setFont(Letras);
        JLabel Codigo = new JLabel("Codigo:");
        Codigo.setForeground(azul);
        Codigo.setFont(otro);
        codi = new JXTextField();
        codi.setPrompt("Ingrese su codigo");
        codi.setForeground(azul);
        JLabel Contra = new JLabel("Contraseña:");
        Contra.setForeground(azul);
        Contra.setFont(otro);
        
        contras = new JPasswordField();
        //contras.setPrompt();
        contras.setForeground(azul);
        
        JPanel bot = new JPanel();
        bot.setBackground(blanco);
        bot.setLayout(new GridLayout(1, 2, 40, 0));
        ini = new JButton("Inicia");
        ini.setBackground(azul);
        ini.setForeground(blanco);
        ini.setFont(otro);
        ini.addActionListener(this);
        reg = new JButton("Registrarse");
        reg.setForeground(azul);
        reg.setBackground(blanco);
        reg.setBorder(null);
        reg.setFont(Enca);
        reg.addActionListener(this);
        bot.add(ini);
        bot.add(reg);
        ses.add(Tit);
        ses.add(Codigo);
        ses.add(codi);
        ses.add(Contra);
        ses.add(contras);
        ses.add(bot);
        ven = new Ventana();
        ven.Ventanaa2(ses);
    }

    public void menu(){
        men = new JPanel();
        men.setBackground(blanco);
        men.setLayout(new GridLayout(3,5));
        
        JLabel meni = new JLabel("MENU");
        meni.setForeground(azul);
        meni.setFont(Vacios);
        meni.setAlignmentX(CENTER_ALIGNMENT);
        
        Examenes = new JButton("Examenes");
        Examenes.setBackground(azul);
        Examenes.setForeground(blanco);
        Examenes.setFont(otro);
        Examenes.addActionListener(this);
        
        
        //Boton HacerExamenes
        HacerExamenes = new JButton("Hacer Examenes");
        HacerExamenes.setBackground(azul);
        HacerExamenes.setForeground(blanco);
        HacerExamenes.setFont(otro);
        HacerExamenes.addActionListener(this);
        
        //Boton Subir
        Subir = new JButton("Calificar");
        Subir.setBackground(azul);
        Subir.setForeground(blanco);
        Subir.setFont(otro);
        Subir.addActionListener(this);
        
        JLabel Bv[] = new JLabel[12];
        for (int i = 0; i < Bv.length; i++) {
            Bv[i] = new JLabel("NEL");
            Bv[i].setForeground(blanco);
        }
        for (int i = 0; i < 2; i++) {
            men.add(Bv[i]);
        }
        men.add(meni);
        for (int i = 3; i < 5; i++) {
            men.add(Bv[i]);
        }
        men.add(Examenes);
        men.add(Bv[6]);
        men.add(HacerExamenes);
        men.add(Bv[7]);
        men.add(Subir);
        for (int i = 8; i < Bv.length; i++) {
            men.add(Bv[i]);
        }
        
        ven = new Ventana();
        ven.Ventanaa(men);
    }
    
    public void VerExamenes() {
        verEx = new JPanel();
        verEx.setBorder(new EmptyBorder(5, 5, 5, 5));
        verEx.setLayout(new BorderLayout(0, 0));
        jpaneExa = new JPanel();
        jpaneExa.setBackground(blanco);

        JScrollPane jsp = new JScrollPane(jpaneExa);
        jsp.setBackground(blanco);

        if (f.exists()) {
            JLabel vv[] = new JLabel[ficheros.length * 2];
            jpaneExa.setLayout(new GridLayout(3, ficheros.length, 30, 1));
            for (int i = 0; i < vv.length; i++) {
                vv[i] = new JLabel("NO TOY");
                vv[i].setForeground(blanco);
                vv[i].setBackground(blanco);
            }
            for (int i = 0; i < ficheros.length; i++) {
                jpaneExa.add(vv[i]);
            }
            for (int i = 0; i < ficheros.length; i++) {
                String name = ficheros[i].getName();
                name = name.replace(".dat", "");
                ex[i] = new JButton(name);
                ex[i].setBackground(azul);
                ex[i].setForeground(blanco);
                ex[i].addActionListener(this);
                jpaneExa.add(ex[i]);
            }
            for (int i = 0; i < ficheros.length; i++) {
                jpaneExa.add(vv[i + ficheros.length]);
            }
        } else {

        }
        verEx.add(jsp, BorderLayout.CENTER);
        
        ven = new Ventana();
        ven.Ventanaa(verEx);
    }

    public void DatosExamen() {
        partCen = new JPanel();
        partCen.setLayout(new GridLayout(6, 4, 10, 10));
        partCen.setBackground(blanco);

        //Labels
        JLabel LTitulo = new JLabel("Titulo");
        LTitulo.setFont(Enca);
        LTitulo.setForeground(azul);
        JLabel LFecha = new JLabel("Fecha");
        LFecha.setFont(Enca);
        LFecha.setForeground(azul);
        JLabel LPeriodo = new JLabel("Periodo");
        LPeriodo.setFont(Enca);
        LPeriodo.setForeground(azul);
        JLabel LMateria = new JLabel("Materia");
        LMateria.setFont(Enca);
        LMateria.setForeground(azul);
        JLabel LProfesor = new JLabel("Profesor");
        LProfesor.setFont(Enca);
        LProfesor.setForeground(azul);

        //Combo
        dia = new JXComboBox();
        dia.setBackground(blanco);
        dia.setForeground(azul);

        for (int i = 0; i < 31; i++) {
            dia.addItem(i + 1);
        }
        mes = new JXComboBox();
        mes.setBackground(blanco);
        mes.setForeground(azul);
        mes.addItem("Enero");
        mes.addItem("Febrero");
        mes.addItem("Marzo");
        mes.addItem("Abril");
        mes.addItem("Mayo");
        mes.addItem("Junio");
        mes.addItem("Julio");
        mes.addItem("Agosto");
        mes.addItem("Septiembre");
        mes.addItem("Octubre");
        mes.addItem("Noviembre");
        mes.addItem("Diciembre");

        año = new JXComboBox();
        for (int i = 2017; i < 2035; i++) {
            año.addItem(i);
        }

        JPanel fecha = new JPanel();
        fecha.setLayout(new GridLayout(3, 3, 30, 0));
        fecha.setBackground(blanco);
        JLabel Vac1[] = new JLabel[6];
        for (int i = 0; i < Vac1.length; i++) {
            Vac1[i] = new JLabel();
            Vac1[i].setForeground(blanco);
        }
        for (int i = 0; i < 3; i++) {
            fecha.add(Vac1[i]);
        }
        fecha.add(dia);
        fecha.add(mes);
        fecha.add(año);
        for (int i = 3; i < Vac1.length; i++) {
            fecha.add(Vac1[i]);
        }

        JPanel per = new JPanel();
        per.setBackground(blanco);
        per.setLayout(new GridLayout(3, 1, 0, 2));
        peri = new JXComboBox();
        peri.setBackground(blanco);
        peri.setForeground(azul);
        peri.addItem("1er Parcial");
        peri.addItem("2do Paracial");
        peri.addItem("3er Parcial");
        peri.addItem("Extraordinario");
        peri.addItem("ETS");
        peri.addItem("ETS especial");
        peri.addItem("ESPA");
        peri.addItem("Interpolitecnico");
        peri.addItem("Otro");
        JLabel Vac3[] = new JLabel[2];
        for (int i = 0; i < Vac3.length; i++) {
            Vac3[i] = new JLabel();
            Vac3[i].setForeground(blanco);
        }

        per.add(Vac3[0]);

        per.add(peri);
        per.add(Vac3[1]);
        JPanel jmate = new JPanel();
        jmate.setBackground(blanco);
        jmate.setLayout(new GridLayout(3, 1));
        mate = new JXComboBox();
        mate.addItem("Selecciona una materia");
        AbrirArchivo2();
        getMate nuevo = new getMate();
        ArrayList<String> mats= new ArrayList<>();
        try {
            mats = nuevo.mates();
        } catch (SQLException ex) {
            Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i = 0; i<mats.size(); i++){
            mate.addItem(mats.get(i));
        }
        
        JPanel agregar = new JPanel();
        agregar.setBackground(blanco);
        agregar.setLayout(new GridLayout(1, 3));
        matAgrega = new JButton("Agrega");
        matAgrega.setBackground(azul);
        matAgrega.setForeground(blanco);
        matAgrega.addActionListener(this);
        tMate = new JXTextField();
        tMate.setPrompt("Ingrese la nueva materia");
        tMate.setForeground(azul);
        tMate.setVisible(false);
        matAc = new JButton("Aceptar");
        matAc.setBackground(azul);
        matAc.setForeground(blanco);
        matAc.setVisible(false);
        matAc.addActionListener(this);
        agregar.add(matAgrega);
        agregar.add(tMate);
        agregar.add(matAc);
        JLabel ba = new JLabel("NEL");
        ba.setForeground(blanco);
        jmate.add(ba);
        jmate.add(mate);
        jmate.add(agregar);
        //Textos
        tTitulo = new JXTextField();
        tTitulo.setPrompt("Ingrese el titulo del Examen");
        tTitulo.setBackground(gris);
        tTitulo.setForeground(azul);
        tPeriodo = new JXTextField();
        tPeriodo.setPrompt("Ingrese el periodo a evaluar");
        tPeriodo.setBackground(gris);
        tPeriodo.setForeground(azul);
        tProfesor = new JXTextField();
        tProfesor.setPrompt("Ingrese el nombre del profesor (puede quedar vacio)");
        tProfesor.setBackground(gris);
        tProfesor.setForeground(azul);

        //Botones
        Sigui = new JButton("Siguiente");
        Sigui.setBackground(azul);
        Sigui.setForeground(blanco);
        Sigui.setFont(Letras);
        Sigui.addActionListener(this);
        Atra = new JButton("Atras");
        Atra.setBackground(azul);
        Atra.setForeground(blanco);
        Atra.setFont(Letras);
        Atra.addActionListener(this);

        //Vacios
        JLabel Vac[] = new JLabel[13];
        for (int i = 0; i < Vac.length; i++) {
            Vac[i] = new JLabel();
            Vac[i].setForeground(blanco);
        }

        //Agregar los elementos
        partCen.add(Vac[1]);
        partCen.add(LTitulo);
        partCen.add(tTitulo);
        partCen.add(Vac[2]);
        partCen.add(Vac[3]);
        partCen.add(LFecha);
        partCen.add(fecha);
        partCen.add(Vac[4]);
        partCen.add(Vac[5]);
        partCen.add(LPeriodo);
        partCen.add(per);
        partCen.add(Vac[6]);
        partCen.add(Vac[7]);
        partCen.add(LMateria);
        partCen.add(jmate);
        partCen.add(Vac[8]);
        partCen.add(Vac[9]);
        partCen.add(LProfesor);
        partCen.add(tProfesor);
        partCen.add(Vac[10]);
        partCen.add(Atra);
        partCen.add(Vac[11]);
        partCen.add(Vac[12]);
        partCen.add(Sigui);
        ven = new Ventana();
        ven.Ventanaa(partCen);

    }

    public void DatosExamen2() {
        partCen = new JPanel();
        partCen.setLayout(new GridLayout(7, 4, 10, 10));
        partCen.setBackground(blanco);

        //Labels
        JLabel LTitulo = new JLabel("Titulo");
        LTitulo.setFont(Enca);
        LTitulo.setForeground(azul);
        JLabel LFecha = new JLabel("Fecha");
        LFecha.setFont(Enca);
        LFecha.setForeground(azul);
        JLabel LPeriodo = new JLabel("Periodo");
        LPeriodo.setFont(Enca);
        LPeriodo.setForeground(azul);
        JLabel LMateria = new JLabel("Materia");
        LMateria.setFont(Enca);
        LMateria.setForeground(azul);
        JLabel LProfesor = new JLabel("Profesor");
        LProfesor.setFont(Enca);
        LProfesor.setForeground(azul);
        JLabel LnumPreg = new JLabel("Numero de preguntas");
        LnumPreg.setFont(Enca);
        LnumPreg.setForeground(azul);

        //Combo
        dia = new JXComboBox();
        dia.setBackground(blanco);
        dia.setForeground(azul);

        for (int i = 0; i < 31; i++) {
            dia.addItem(i + 1);
        }
        mes = new JXComboBox();
        mes.setBackground(blanco);
        mes.setForeground(azul);
        mes.addItem("Enero");
        mes.addItem("Febrero");
        mes.addItem("Marzo");
        mes.addItem("Abril");
        mes.addItem("Mayo");
        mes.addItem("Junio");
        mes.addItem("Julio");
        mes.addItem("Agosto");
        mes.addItem("Septiembre");
        mes.addItem("Octubre");
        mes.addItem("Noviembre");
        mes.addItem("Diciembre");

        año = new JXComboBox();
        for (int i = 2017; i < 2035; i++) {
            año.addItem(i);
        }

        JPanel fecha = new JPanel();
        fecha.setLayout(new GridLayout(3, 3, 30, 0));
        fecha.setBackground(blanco);
        JLabel Vac1[] = new JLabel[6];
        for (int i = 0; i < Vac1.length; i++) {
            Vac1[i] = new JLabel();
            Vac1[i].setForeground(blanco);
        }
        for (int i = 0; i < 3; i++) {
            fecha.add(Vac1[i]);
        }
        fecha.add(dia);
        fecha.add(mes);
        fecha.add(año);
        for (int i = 3; i < Vac1.length; i++) {
            fecha.add(Vac1[i]);
        }

        JPanel per = new JPanel();
        per.setBackground(blanco);
        per.setLayout(new GridLayout(3, 1, 0, 2));
        peri = new JXComboBox();
        peri.setBackground(blanco);
        peri.setForeground(azul);
        peri.addItem("1er Parcial");
        peri.addItem("2do Paracial");
        peri.addItem("3er Parcial");
        peri.addItem("Extraordinario");
        peri.addItem("ETS");
        peri.addItem("ETS especial");
        peri.addItem("ESPA");
        peri.addItem("Interpolitecnico");
        peri.addItem("Otro");
        JLabel Vac3[] = new JLabel[2];
        for (int i = 0; i < Vac3.length; i++) {
            Vac3[i] = new JLabel();
            Vac3[i].setForeground(blanco);
        }

        per.add(Vac3[0]);

        per.add(peri);
        per.add(Vac3[1]);
        JPanel jmate = new JPanel();
        jmate.setBackground(blanco);
        jmate.setLayout(new GridLayout(3, 1));
        mate = new JXComboBox();
        mate.addItem("Selecciona una materia");
        getMate nuevo = new getMate();
        ArrayList<String> mats= new ArrayList<>();
        try {
            mats = nuevo.mates();
        } catch (SQLException ex) {
            Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i = 0; i<mats.size(); i++){
            mate.addItem(mats.get(i));
        }
        
        JPanel agregar = new JPanel();
        agregar.setBackground(blanco);
        agregar.setLayout(new GridLayout(1, 3));
        matAgrega = new JButton("Agrega");
        matAgrega.setBackground(azul);
        matAgrega.setForeground(blanco);
        matAgrega.addActionListener(this);
        tMate = new JXTextField();
        tMate.setPrompt("Ingrese la nueva materia");
        tMate.setForeground(azul);
        tMate.setVisible(false);
        matAc = new JButton("Aceptar");
        matAc.setBackground(azul);
        matAc.setForeground(blanco);
        matAc.setVisible(false);
        matAc.addActionListener(this);
        agregar.add(matAgrega);
        agregar.add(tMate);
        agregar.add(matAc);
        JLabel ba = new JLabel("NEL");
        ba.setForeground(blanco);
        jmate.add(ba);
        jmate.add(mate);
        jmate.add(agregar);
        //Textos
        tTitulo = new JXTextField();
        tTitulo.setPrompt("Ingrese el titulo del Examen");
        tTitulo.setBackground(gris);
        tTitulo.setForeground(azul);
        tPeriodo = new JXTextField();
        tPeriodo.setPrompt("Ingrese el periodo a evaluar");
        tPeriodo.setBackground(gris);
        tPeriodo.setForeground(azul);
        tProfesor = new JXTextField();
        tProfesor.setPrompt("Ingrese el nombre del profesor (puede quedar vacio)");
        tProfesor.setBackground(gris);
        tProfesor.setForeground(azul);
        numP = new JXTextField();
        numP.setPrompt("Ingrese el numero de preguntas del examen (max 52)");
        numP.setBackground(gris);
        numP.setForeground(azul);

        //Botones
        Sigui2 = new JButton("Siguiente");
        Sigui2.setBackground(azul);
        Sigui2.setForeground(blanco);
        Sigui2.setFont(Letras);
        Sigui2.addActionListener(this);
        Atra = new JButton("Atras");
        Atra.setBackground(azul);
        Atra.setForeground(blanco);
        Atra.setFont(Letras);
        Atra.addActionListener(this);

        //Vacios
        JLabel Vac[] = new JLabel[15];
        for (int i = 0; i < Vac.length; i++) {
            Vac[i] = new JLabel();
            Vac[i].setForeground(blanco);
        }

        //Agregar los elementos
        partCen.add(Vac[1]);
        partCen.add(LTitulo);
        partCen.add(tTitulo);
        partCen.add(Vac[2]);
        partCen.add(Vac[3]);
        partCen.add(LFecha);
        partCen.add(fecha);
        partCen.add(Vac[4]);
        partCen.add(Vac[5]);
        partCen.add(LPeriodo);
        partCen.add(per);
        partCen.add(Vac[6]);
        partCen.add(Vac[7]);
        partCen.add(LMateria);
        partCen.add(jmate);
        partCen.add(Vac[8]);
        partCen.add(Vac[9]);
        partCen.add(LProfesor);
        partCen.add(tProfesor);
        partCen.add(Vac[10]);
        partCen.add(Vac[11]);
        partCen.add(LnumPreg);
        partCen.add(numP);
        partCen.add(Vac[12]);
        partCen.add(Atra);
        partCen.add(Vac[13]);
        partCen.add(Vac[14]);
        partCen.add(Sigui2);
        ven = new Ventana();
        ven.Ventanaa(partCen);

    }	
    
    public void grupos() throws SQLException{
        seGrupo setG = new seGrupo();
        ArrayList<String> gro = setG.gruposProf(_idProfe);
        int cu = gro.size();
        float ct = cu/5f;
        
        
        int row = (int) Math.ceil(ct);
        grup = new JPanel();
        grup.setBackground(blanco);
        grup.setLayout(new GridLayout(row + 1, 5, 20, 30));
        JButton Avanza = new JButton("Siguiente");
        Avanza.setForeground(blanco);
        Avanza.setBackground(azul);
        Avanza.setFont(Enca);
        JButton Reg = new JButton("Regresar");
        Reg.setForeground(blanco);
        Reg.setBackground(azul);
        Reg.setFont(Enca);
        JCheckBox grop[] = new JCheckBox[cu];
        for (int i = 0; i < cu; i++) {
            grop[i] = new JCheckBox(gro.get(i));
            grop[i].setForeground(azul);
            grup.add(grop[i]);
        }
        int vaciooos = (row*5) - cu;
        vaciooos +=3;
        JLabel Vab[] = new JLabel[vaciooos];
        for (int i = 0; i < vaciooos; i++) {
            Vab[i] = new JLabel();
            Vab[i].setForeground(blanco);
        }
        
        for (int i = 0; i < vaciooos-3; i++) {
            grup.add(Vab[i]);
        }
        grup.add(Reg);
        for (int i = vaciooos-3; i < vaciooos; i++) {
            grup.add(Vab[i]);
        }
        Avanza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(Avanza)){
                    for (int i = 0; i < cu; i++) {
                        if(grop[i].isSelected()){
                            constructor con = new constructor();
                            try {
                                con.guardaExaGrup(gro.get(i), _idExa);
                            } catch (SQLException ex) {
                                Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    ven.dispose();
                    soloHoja();
                }
            }
        }
        );
        grup.add(Avanza);
        
        
        ven = new Ventana();
        ven.Ventanaa(grup);
    }
    
    public void grupos2() throws SQLException{
        seGrupo setG = new seGrupo();
        ArrayList<String> gro = setG.gruposProf(_idProfe);
        int cu = gro.size();
        float ct = cu/5f;
        
        
        int row = (int) Math.ceil(ct);
        grup = new JPanel();
        grup.setBackground(blanco);
        grup.setLayout(new GridLayout(row + 1, 5, 20, 30));
        JButton Avanza = new JButton("Siguiente");
        Avanza.setForeground(blanco);
        Avanza.setBackground(azul);
        Avanza.setFont(Enca);
        JButton Reg = new JButton("Regresar");
        Reg.setForeground(blanco);
        Reg.setBackground(azul);
        Reg.setFont(Enca);
        JCheckBox grop[] = new JCheckBox[cu];
        for (int i = 0; i < cu; i++) {
            grop[i] = new JCheckBox(gro.get(i));
            grop[i].setForeground(azul);
            grup.add(grop[i]);
        }
        int vaciooos = (row*5) - cu;
        vaciooos +=3;
        JLabel Vab[] = new JLabel[vaciooos];
        for (int i = 0; i < vaciooos; i++) {
            Vab[i] = new JLabel();
            Vab[i].setForeground(blanco);
        }
        
        for (int i = 0; i < vaciooos-3; i++) {
            grup.add(Vab[i]);
        }
        grup.add(Reg);
        for (int i = vaciooos-3; i < vaciooos; i++) {
            grup.add(Vab[i]);
        }
        Avanza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(Avanza)){
                    for (int i = 0; i < cu; i++) {
                        if(grop[i].isSelected()){
                            constructor con = new constructor();
                            try {
                                con.guardaExaGrup(gro.get(i), _idExa);
                            } catch (SQLException ex) {
                                Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    ven.dispose();
                    ElegirPregunta();
                }
            }
        });
        grup.add(Avanza);
        
        
        ven = new Ventana();
        ven.Ventanaa(grup);
    }
    
    public String messs(String mees){
        String mee = "01";
        switch(mees){
            case "Enero":
                mee = "01";
                break;
            case "Febrero":
                mee = "02";
                break;
            case "Marzo":
                mee = "03";
                break;
            case "Abril":
                mee = "04";
                break;
            case "Mayo" :
                mee = "05";
                break;
            case "Junio":
                mee = "06";
                break;
            case "Julio":
                mee = "07";
                break;
            case "Agosto":
                mee = "08";
                break;
            case "Septiembre":
                mee = "09";
                break;
            case "Octubre":
                mee = "10";
                break;
            case "Noviembre":
                mee = "11";
                break;
            case "Diciembre":
                mee = "12";
                break;
            default:
                mee = "01";
                break;
        }
        return mee;
    }
	
    public void ElegirHo() {
        //----------------------Seleccion del profesor--------------------------
        elegirHoja = new JPanel();
        elegirHoja.setBackground(blanco);
        elegirHoja.setLayout(new GridLayout(5, 5));

        ExamHoj = new JButton("Examen y Hoja");
        ExamHoj.setBackground(azul);
        ExamHoj.setForeground(blanco);
        ExamHoj.setFont(Enca);
        ExamHoj.setToolTipText("Crear un Examen con su hoja de respuestas");
        ExamHoj.addActionListener(this);

        SoloHoj = new JButton("Hoja de respuestas");
        SoloHoj.setBackground(azul);
        SoloHoj.setForeground(blanco);
        SoloHoj.setFont(Enca);
        SoloHoj.setToolTipText("Crear solo la hoja de respuestas");
        SoloHoj.addActionListener(this);

        Instrucciones = new JLabel("¿Qué quiere hacer?");
        Instrucciones.setFont(Ins);
        Instrucciones.setForeground(azul);

        JLabel Vacio[] = new JLabel[22];
        for (int i = 0; i < Vacio.length; i++) {
            Vacio[i] = new JLabel();
            Vacio[i].setForeground(blanco);
        }

        for (int i = 0; i < 2; i++) {
            elegirHoja.add(Vacio[i]);
        }
        elegirHoja.add(Instrucciones);
        for (int i = 2; i < 10; i++) {
            elegirHoja.add(Vacio[i]);
        }
        elegirHoja.add(ExamHoj);
        elegirHoja.add(Vacio[10]);
        elegirHoja.add(SoloHoj);
        for (int i = 11; i < Vacio.length; i++) {
            elegirHoja.add(Vacio[i]);
        }
        ven = new Ventana();
        ven.Ventanaa(elegirHoja);
    }

    public void soloHoja() {
        int nuPe = numPreg;
        hoja = new JPanel();
        hoja.setBackground(blanco);
        hoja.setBorder(new EmptyBorder(5, 5, 5, 5));
        hoja.setLayout(new BorderLayout(0, 0));
        JPanel hoje = new JPanel();
        hoje.setBackground(blanco);
        JScrollPane jsp = new JScrollPane(hoje);
        jsp.setBackground(blanco);
        hoje.setLayout(new GridLayout(nuPe + 1, 3, 20, 20));
        JButton sig = new JButton("Siguiente");
        sig.setBackground(azul);
        sig.setForeground(blanco);
        sig.setFont(Enca);
        JButton atras = new JButton("Atras");
        atras.setBackground(azul);
        atras.setForeground(blanco);
        atras.setFont(Enca);
        JPanel ho[] = new JPanel[nuPe];
        JLabel vb[] = new JLabel[nuPe * 2];
        JRadioButton inA[] = new JRadioButton[nuPe];
        JRadioButton inB[] = new JRadioButton[nuPe];
        JRadioButton inC[] = new JRadioButton[nuPe];
        JRadioButton inD[] = new JRadioButton[nuPe];
        ButtonGroup grupos[] = new ButtonGroup[nuPe];
        JLabel numero[] = new JLabel[nuPe];
        for (int i = 0; i < nuPe; i++) {
            vb[i] = new JLabel("No estoy");
            vb[i].setBackground(blanco);
            vb[i].setForeground(blanco);
            vb[i + nuPe] = new JLabel("No estoy");
            vb[i + nuPe].setBackground(blanco);
            vb[i + nuPe].setForeground(blanco);
            int wa = i + 1;
            numero[i] = new JLabel(wa + "");
            numero[i].setForeground(azul);
            inA[i] = new JRadioButton("A");
            inA[i].setForeground(azul);
            inB[i] = new JRadioButton("B");
            inB[i].setForeground(azul);
            inC[i] = new JRadioButton("C");
            inC[i].setForeground(azul);
            inD[i] = new JRadioButton("D");
            inD[i].setForeground(azul);
            ho[i] = new JPanel();
            ho[i].setBackground(blanco);
            ho[i].setLayout(new GridLayout(1, 5));
            ho[i].add(numero[i]);
            ho[i].add(inA[i]);
            ho[i].add(inB[i]);
            ho[i].add(inC[i]);
            ho[i].add(inD[i]);
            grupos[i] = new ButtonGroup();
            grupos[i].add(inA[i]);
            grupos[i].add(inB[i]);
            grupos[i].add(inC[i]);
            grupos[i].add(inD[i]);
            hoje.add(vb[i]);
            hoje.add(ho[i]);
            hoje.add(vb[i + nuPe]);
        }
        hoje.add(atras);
        JLabel vab = new JLabel("Hola");
        vab.setForeground(blanco);
        hoje.add(vab);
        hoje.add(sig);
        sig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                constructor co = new constructor();
                String res[] = new String[nuPe];
                boolean val[] = new boolean[nuPe];
                for (int i = 0; i < nuPe; i++) {

                    int don = i + 1;
                    if (inA[i].isSelected()) {
                        res[i] = "A";
                        val[i] = true;
                    } else if (inB[i].isSelected()) {
                        res[i] = "B";
                        val[i] = true;
                    } else if (inC[i].isSelected()) {
                        res[i] = "C";
                        val[i] = true;
                    } else if (inD[i].isSelected()) {
                        res[i] = "D";
                        val[i] = true;

                    } else {
                        JOptionPane.showMessageDialog(ven, "Falto seleccionar la pregunta: " + don);
                        val[i] = false;
                    }

                }

                boolean seqir = allTrue(val);
                if (seqir == true) {
                    for (int i = 0; i < nuPe; i++) {
                        co.setRespuesta(res[i]);
                        datExa.add(co);
                    }
                    CerrarArchivo();
                    ven.dispose();
                    ElegirHo();
                    try {
                        hoj.Hoja(new File("pdf/" + nombreArchivo + "Exa.pdf"));
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            }
        });
        hoja.add(jsp);
        ven = new Ventana();
        ven.Ventanaa(hoja);// auqi que onda para guardar los datos

    }
	
    public void ElegirPregunta() {
        AbrirArchivo();
        elegir = new JPanel();
        elegir.setBackground(blanco);
        elegir.setLayout(new GridLayout(5, 7));
        nPreguntas = new JXTextField();
        nPreguntas.setPrompt("Ingrese el numero de columnas");
        nPreguntas.setBackground(gris);
        nPreguntas.setForeground(azul);
        nPreguntas.setVisible(false);
        nPreguntas.setFont(Letras);
        ok = new JButton("OK");
        ok.setBackground(azul);
        ok.setForeground(blanco);
        ok.setVisible(false);
        ok.addActionListener(this);
        ok.setFont(Letras);
        Normal = new JButton("Opcion Multiple");
        Normal.setBackground(azul);
        Normal.setForeground(blanco);
        Normal.addActionListener(this);
        Normal.setFont(Enca);
        OpcionMultiple = new JButton("Relacion Columnas");
        OpcionMultiple.setBackground(azul);
        OpcionMultiple.setForeground(blanco);
        OpcionMultiple.setFont(Enca);
        Finalizar = new JButton("Finalizar");
        Finalizar.setBackground(azul);
        Finalizar.setForeground(blanco);
        Finalizar.addActionListener(this);
        Finalizar.setFont(Letras);
        Regresar = new JButton("Regresar");
        Regresar.setBackground(azul);
        Regresar.setForeground(blanco);
        Regresar.addActionListener(this);
        Regresar.setFont(Letras);
        int w = datExa.size() - 1;

        nPregu = new JLabel("Pregunta: " + w);
        nPregu.setForeground(azul);
        nPregu.setFont(Letras);

        JLabel Vacio[] = new JLabel[29];
        for (int i = 0; i < Vacio.length; i++) {
            Vacio[i] = new JLabel();
            Vacio[i].setForeground(blanco);
        }
        //Agregar los elementos

        for (int i = 0; i < 3; i++) {
            elegir.add(Vacio[i]);
        }
        elegir.add(nPregu);
        for (int i = 4; i < 16; i++) {
            elegir.add(Vacio[i]);
        }
        elegir.add(Normal);
        elegir.add(Vacio[16]);
        elegir.add(OpcionMultiple);
        elegir.add(nPreguntas);
        elegir.add(ok);

        for (int i = 17; i < 24; i++) {
            elegir.add(Vacio[i]);
        }
        elegir.add(Regresar);
        for (int i = 24; i < 29; i++) {
            elegir.add(Vacio[i]);
        }
        if (w == 52) {
            Normal.setEnabled(false);
            OpcionMultiple.setEnabled(false);
        }
        elegir.add(Finalizar);
        OpcionMultiple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok.setVisible(true);
                nPreguntas.setVisible(true);
            }
        });

        elegir.setVisible(true);
        ven = new Ventana();
        ven.Ventanaa(elegir);
    }

    public boolean allTrue(boolean[] array) {
        for (boolean b : array) {
            if (!b) {
                return false;
            }
        }
        return true;
    }
    
    public void Normal() {
        pregNormal = new JPanel();
        pregNormal.setBackground(blanco);
        pregNormal.setLayout(new BorderLayout());

        JPanel Separacion = new JPanel();
        Separacion.setLayout(new GridLayout(2, 1));
        Separacion.setBackground(blanco);

        lEcunciado = new JLabel("Pregunta:");
        lEcunciado.setForeground(azul);
        lEcunciado.setFont(Letras);
        tEnunciado = new JXTextArea();
        tEnunciado.setPrompt("Ingrese la pregunta planteada");
        tEnunciado.setPromptFontStyle(30);
        tEnunciado.setBackground(gris);
        tEnunciado.setForeground(azul);
        tEnunciado.setFont(Enca);
        tEnunciado.setPreferredSize(new Dimension(MAXIMIZED_HORIZ, 60));

        Separacion.add(lEcunciado);
        Separacion.add(tEnunciado);

        JPanel opcion = new JPanel();
        opcion.setLayout(new GridLayout(5, 3, 20, 15));
        opcion.setBackground(blanco);

        lOpcionA = new JLabel("Opcion A:");
        lOpcionA.setForeground(azul);
        lOpcionA.setFont(Letras);
        tOpcionA = new JXTextField();
        tOpcionA.setPrompt("Ingrese la opcion A");
        tOpcionA.setBackground(gris);
        tOpcionA.setForeground(azul);
        tOpcionA.setFont(Letras);
        rOpcionA = new JRadioButton("A");
        rOpcionA.setBackground(blanco);
        rOpcionA.setForeground(azul);
        rOpcionA.setFont(Letras);

        lOpcionB = new JLabel("Opcion B:");
        lOpcionB.setForeground(azul);
        lOpcionB.setFont(Letras);
        tOpcionB = new JXTextField();
        tOpcionB.setPrompt("Ingrese la opcion B");
        tOpcionB.setBackground(gris);
        tOpcionB.setForeground(azul);
        tOpcionB.setFont(Letras);
        rOpcionB = new JRadioButton("B");
        rOpcionB.setBackground(blanco);
        rOpcionB.setForeground(azul);
        rOpcionB.setFont(Letras);

        lOpcionC = new JLabel("Opcion C:");
        lOpcionC.setForeground(azul);
        lOpcionC.setFont(Letras);
        tOpcionC = new JXTextField();
        tOpcionC.setPrompt("Ingrese la opcion C");
        tOpcionC.setBackground(gris);
        tOpcionC.setForeground(azul);
        tOpcionC.setFont(Letras);
        rOpcionC = new JRadioButton("C");
        rOpcionC.setBackground(blanco);
        rOpcionC.setForeground(azul);
        rOpcionC.setFont(Letras);

        lOpcionD = new JLabel("Opcion D:");
        lOpcionD.setForeground(azul);
        lOpcionD.setFont(Letras);
        tOpcionD = new JXTextField();
        tOpcionD.setPrompt("Ingrese la opcion D");
        tOpcionD.setBackground(gris);
        tOpcionD.setForeground(azul);
        tOpcionD.setFont(Letras);
        rOpcionD = new JRadioButton("D");
        rOpcionD.setBackground(blanco);
        rOpcionD.setForeground(azul);
        rOpcionD.setFont(Letras);

        PregSig = new JButton("Siguiente");
        PregSig.setBackground(azul);
        PregSig.setForeground(blanco);
        PregSig.setFont(Letras);
        PregSig.addActionListener(this);
        PregAtr = new JButton("Atras");
        PregAtr.setBackground(azul);
        PregAtr.setForeground(blanco);
        PregAtr.setFont(Letras);
        PregAtr.addActionListener(this);
        Finalizar = new JButton("Finalizar");
        Finalizar.setBackground(azul);
        Finalizar.setForeground(blanco);
        Finalizar.setFont(Letras);

        opcion.add(lOpcionA);
        opcion.add(tOpcionA);
        opcion.add(rOpcionA);
        opcion.add(lOpcionB);
        opcion.add(tOpcionB);
        opcion.add(rOpcionB);
        opcion.add(lOpcionC);
        opcion.add(tOpcionC);
        opcion.add(rOpcionC);
        opcion.add(lOpcionD);
        opcion.add(tOpcionD);
        opcion.add(rOpcionD);
        opcion.add(PregAtr);
        opcion.add(Finalizar);
        opcion.add(PregSig);

        ButtonGroup Gru1 = new ButtonGroup();
        Gru1.add(rOpcionA);
        Gru1.add(rOpcionB);
        Gru1.add(rOpcionC);
        Gru1.add(rOpcionD);

        pregNormal.add(Separacion, BorderLayout.NORTH);
        pregNormal.add(opcion, BorderLayout.CENTER);

        ven = new Ventana();
        ven.Ventanaa(pregNormal);

    }

    public void RelacionColumnas() {
        constructor con = new constructor();
        Pa = new JPanel();
        Pa.setBorder(new EmptyBorder(5, 5, 5, 5));
        Pa.setLayout(new BorderLayout(0, 0));
        relCol = new JPanel();
        JScrollPane jsp = new JScrollPane(relCol);
        GraphicsConfiguration config = getGraphicsConfiguration();
        Rectangle usableBounds = SunGraphicsEnvironment.getUsableBounds(config.getDevice());
        relCol.setPreferredSize(new Dimension(usableBounds.width - 700, (60 * numeroRel)));
        relCol.setLayout(new GridLayout(numeroRel + 6, 2, 25, 10));
        relCol.setBackground(blanco);

        //Textos
        tEnunciado = new JXTextArea();
        tEnunciado.setPrompt("Ingrese la instruccion");
        tEnunciado.setPromptFontStyle(30);
        tEnunciado.setBackground(gris);
        tEnunciado.setForeground(azul);
        tEnunciado.setFont(Enca);
        tEnunciado.setPreferredSize(new Dimension(MAXIMIZED_HORIZ, 60));
        JXTextField columna1[] = new JXTextField[numeroRel];
        JXTextField columna2[] = new JXTextField[numeroRel];
        for (int i = 0; i < numeroRel; i++) {
            String numero = i + "";
            columna1[i] = new JXTextField(numero);
            columna1[i].setBackground(gris);
            columna1[i].setForeground(azul);
            columna1[i].setFont(Enca);
            columna2[i] = new JXTextField();
            columna2[i].setBackground(gris);
            columna2[i].setForeground(azul);
            columna2[i].setFont(Enca);
        }
        JLabel Va[] = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            Va[i] = new JLabel("Nel Mijo");
            Va[i].setForeground(blanco);
        }

        tOpcionA = new JXTextField();
        tOpcionA.setPrompt("Ingrese la opcion A");
        tOpcionA.setBackground(gris);
        tOpcionA.setForeground(azul);
        tOpcionA.setFont(Letras);
        rOpcionA = new JRadioButton("A");
        rOpcionA.setBackground(blanco);
        rOpcionA.setForeground(azul);
        rOpcionA.setFont(Letras);

        tOpcionB = new JXTextField();
        tOpcionB.setPrompt("Ingrese la opcion B");
        tOpcionB.setBackground(gris);
        tOpcionB.setForeground(azul);
        tOpcionB.setFont(Letras);
        rOpcionB = new JRadioButton("B");
        rOpcionB.setBackground(blanco);
        rOpcionB.setForeground(azul);
        rOpcionB.setFont(Letras);

        tOpcionC = new JXTextField();
        tOpcionC.setPrompt("Ingrese la opcion C");
        tOpcionC.setBackground(gris);
        tOpcionC.setForeground(azul);
        tOpcionC.setFont(Letras);
        rOpcionC = new JRadioButton("C");
        rOpcionC.setBackground(blanco);
        rOpcionC.setForeground(azul);
        rOpcionC.setFont(Letras);

        tOpcionD = new JXTextField();
        tOpcionD.setPrompt("Ingrese la opcion D");
        tOpcionD.setBackground(gris);
        tOpcionD.setForeground(azul);
        tOpcionD.setFont(Letras);
        rOpcionD = new JRadioButton("D");
        rOpcionD.setBackground(blanco);
        rOpcionD.setForeground(azul);
        rOpcionD.setFont(Letras);

        PregAtr = new JButton("Atras");
        PregAtr.setBackground(azul);
        PregAtr.setForeground(blanco);
        PregAtr.addActionListener(this);

        PregSigCol = new JButton("Siguiente");
        PregSigCol.setBackground(azul);
        PregSigCol.setForeground(blanco);
        PregSigCol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(PregSigCol)) {
                    AbrirArchivo();
                    constructor con = new constructor();
                    con.setNumCol(numeroRel);
                    String col1[] = new String[numeroRel];
                    String col2[] = new String[numeroRel];
                    String opciones[] = new String[4];
                    String correcta = null;
                    opciones[0] = "A)" + tOpcionA.getText();
                    opciones[1] = "B)" + tOpcionB.getText();
                    opciones[2] = "C)" + tOpcionC.getText();
                    opciones[3] = "D)" + tOpcionD.getText();
                    if (rOpcionA.isSelected() == true) {
                        correcta = "A";
                    } else if (rOpcionB.isSelected() == true) {
                        correcta = "B";
                    } else if (rOpcionC.isSelected() == true) {
                        correcta = "C";
                    } else if (rOpcionD.isSelected() == true) {
                        correcta = "D";
                    } else {

                    }

                    for (int i = 0; i < numeroRel; i++) {
                        col1[i] = columna1[i].getText();
                        col2[i] = columna2[i].getText();
                    }
                    con.setOpcCol(col1);
                    con.setRelCol(col2);
                    con.setOpc(opciones);
                    con.setRespuesta(correcta);
                    con.setPregunta(tEnunciado.getText());
                    datExa.add(con);
                    CerrarArchivo();

                    ven.dispose();
                    ElegirPregunta();
                }
            }
        });

        Finalizar = new JButton("Finalizar");
        Finalizar.setBackground(azul);
        Finalizar.setForeground(blanco);
        JLabel Vaci = new JLabel("No toy");
        Vaci.setBackground(blanco);
        Vaci.setForeground(blanco);
        relCol.add(tEnunciado);
        relCol.add(Vaci);
        for (int i = 0; i < numeroRel; i++) {
            relCol.add(columna1[i]);
            relCol.add(columna2[i]);
        }
        for (int i = 0; i < 2; i++) {
            relCol.add(Va[i]);
        }
        JPanel jtext1 = new JPanel();
        jtext1.setLayout(new GridLayout(1, 2, 10, 0));
        jtext1.setBackground(blanco);
        jtext1.add(tOpcionA);
        jtext1.add(tOpcionB);

        JPanel jtext2 = new JPanel();
        jtext2.setLayout(new GridLayout(1, 2, 10, 0));
        jtext2.setBackground(blanco);
        jtext2.add(tOpcionC);
        jtext2.add(tOpcionD);

        JPanel jradio1 = new JPanel();
        jradio1.setLayout(new GridLayout(1, 2, 5, 0));
        jradio1.setBackground(blanco);
        jradio1.add(rOpcionA);
        jradio1.add(rOpcionB);

        JPanel jradio2 = new JPanel();
        jradio2.setLayout(new GridLayout(1, 2, 5, 0));
        jradio2.setBackground(blanco);
        jradio2.add(rOpcionC);
        jradio2.add(rOpcionD);

        relCol.add(jtext1);
        relCol.add(jtext2);
        relCol.add(jradio1);
        relCol.add(jradio2);

        ButtonGroup Gru1 = new ButtonGroup();
        Gru1.add(rOpcionA);
        Gru1.add(rOpcionB);
        Gru1.add(rOpcionC);
        Gru1.add(rOpcionD);

        relCol.add(PregAtr);
        relCol.add(PregSigCol);
        relCol.add(Va[2]);
        relCol.add(Finalizar);

        relCol.setVisible(true);
        Pa.add(jsp);

        ven = new Ventana();
        ven.Ventanaa(Pa);
    }

    public void Comparar() {
        compa = new JPanel();
        compa.setLayout(new GridLayout(1, 2));
        compa.setBackground(blanco);
        JPanel l = base();

        JPanel Botones = new JPanel();
        Botones.setLayout(new GridLayout(4, 1, 30, 30));
        Botones.setBackground(blanco);
        Cargar = new JButton("Cargar imagen");
        Cargar.addActionListener(this);
        Cargar.setBackground(azul);
        Cargar.setForeground(blanco);
        Sal = new JButton("Salir");
        Sal.setBackground(azul);
        Sal.setForeground(blanco);
        Sal.addActionListener(this);
        Escaneo = new JButton("Comenzar Escaneo");
        Escaneo.setBackground(azul);
        Escaneo.setForeground(blanco);
        Escaneo.addActionListener(this);
        selec = new JComboBox();
        selec.setBackground(azul);
        selec.setForeground(blanco);
        selec.addItem("Selecciona un Archivo de Respuestas");
        if (f.exists()) {
            for (int i = 0; i < ficheros.length; i++) {
                selec.addItem(ficheros[i].getName());
            }
        }
        selec.addActionListener(this);
        Botones.add(Cargar);
        Botones.add(selec);
        Botones.add(Escaneo);
        Botones.add(Sal);
        compa.add(l);
        compa.add(Botones);
        ven = new Ventana();
        ven.Ventanaa(compa);
    }

    public void valida() {
        String titulo = tTitulo.getText();
        String mate2 = (String) mate.getSelectedItem();
        String prof = tProfesor.getText();

        for (int i = 0; i < titulo.length(); i++) {
            if (titulo.codePointAt(i) == 34 || titulo.codePointAt(i) == 47 || titulo.codePointAt(i) == 92 || titulo.codePointAt(i) == 63 || titulo.codePointAt(i) == 168 || titulo.codePointAt(i) == 42 || titulo.codePointAt(i) == 58 || titulo.codePointAt(i) == 60 || titulo.codePointAt(i) == 62 || titulo.codePointAt(i) == 124) {
                error = 1;
            } else {
                error = 0;
            }
        }
        for (int j = 0; j < mate2.length(); j++) {
            if (mate2.codePointAt(j) >= 48 && mate2.codePointAt(j) <= 57) {
                error2 = 1;
            } else {
                error2 = 0;
            }
        }
        for (int k = 0; k < prof.length(); k++) {
            if (prof.codePointAt(k) >= 48 && prof.codePointAt(k) <= 57) {
                error3 = 1;
            } else {
                error3 = 0;
            }
        }

    }

    private JPanel base() {
        lista = new JPanel();
        lista.setBorder(new EmptyBorder(5, 5, 5, 5));
        lista.setLayout(new BorderLayout(0, 0));
        JPanel lis = new JPanel();
        JScrollPane jsp = new JScrollPane(lis);
        String[] titulo = {"Nombre archivo"};
        JList listita = new JList(titulo);

        DefaultListModel modelito = new DefaultListModel();
        modelito.addElement("Nombre archivo");
        for (int i = 0; i < imagenes.size(); i++) {
            modelito.addElement(imagenes.get(i).getName());
        }
        listita.setModel(modelito);
        lis.add(listita);
        lista.add(jsp);
        return lista;
    }

    @Override
    public void actionPerformed(ActionEvent e)  {
        //Menu
        if(e.getSource().equals(Examenes)){
            ven.dispose();
            VerExamenes();
        }
        if(e.getSource().equals(HacerExamenes)){
            ven.dispose();
            ElegirHo();
        }
        
        if(e.getSource().equals(Subir)){
            ven.dispose();
            Comparar();
        }
        //Sesion
        if (e.getSource().equals(ini)) {
            //System.out.println("hola");
            //Parametros de la base
            
            String entra="";
            try {
                entra=p.entra(codi.getText(), contras.getPassword(), "profe");
            } catch (SQLException ex) {
                Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (val.esNum(entra)) {
                this._idProfe=entra;
                
                try {
                    p.obten(_idProfe, "");
                } catch (SQLException ex) {
                    Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                }
                ven.dispose();
                menu();

            } else {
                JOptionPane.showMessageDialog(this, "Datos incorrectos");
            }
        }
        if (e.getSource().equals("Registrarse")) {
             if(java.awt.Desktop.isDesktopSupported()){
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
          
            if(desktop.isSupported(java.awt.Desktop.Action.BROWSE)){
                try{
                    java.net.URI uri= new java.net.URI("http://www.tututorial.com/2016/06/tutorial-como-abrir-enlaces-web-desde-java.html");
                    desktop.browse(uri);
                }catch(URISyntaxException | IOException ex){}
            }
        }
            
            
             
        }
        //Comparar------------------------------------------------------------------------------
        if (e.getSource().equals(Cargar)) {
            
            JFileChooser selector = new JFileChooser();
            selector.setDialogTitle("Seleccione una imagen");
            FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & PNG & BMP", "jpg", "png", "bmp");
            selector.setFileFilter(filtroImagen);
            selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
            selector.setMultiSelectionEnabled(true);

            int flag = selector.showOpenDialog(null);
            if (flag == JFileChooser.APPROVE_OPTION) {

                File[] files = selector.getSelectedFiles();
                for (int i = 0; i < files.length; i++) {
                    setImg SG = new setImg();
                    String url = "";
                    try {
                        url = files[i].getAbsolutePath();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    String img1 = files[i].getName();
                    SG.setUrl(url);
                    SG.setName(img1);
                    imagenes.add(SG);

                }
                ven.dispose();
                Comparar();

            }
        }
        if (e.getSource().equals(Escaneo)) {
            if (imagenes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese primero la imagen");
            } else {
                if(selec.getSelectedItem().toString().equals("Selecciona un Archivo de Respuestas")){
                    JOptionPane.showMessageDialog(this, "Seleccione un archivo de respuestas");
                }else{
                    Main mai = new Main();
                    String nomar = (String) selec.getSelectedItem().toString();
                    mai.setNombreArchivo(nomar);
                try {
                    
                    mai.califica(imagenes);
                } catch (IOException ex) {
                    Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                }   catch (SQLException ex) {
                        Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   
            }
        }
        //Datos Examen
        if (e.getSource().equals(matAgrega)) {
            matAc.setVisible(true);
            tMate.setVisible(true);
        }
        if (e.getSource().equals(matAc)) {
            if (tMate.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese la materia");
            } else {
                getMate nuevo = new getMate();
                try {
                    nuevo.guardaMAt(tMate.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(this, "Se agregó");
                AbrirArchivo2();
                try {
                    nuevo.setMateria(tMate.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                }
                materias.add(nuevo);
                CerrarArchivo2();
                mate.addItem(tMate.getText());
            }
        }
        if (e.getSource().equals(Sigui2)) {
            
            if (tTitulo.getText().isEmpty() || peri.getSelectedItem().toString().equals("Selecciona un periodo")
                    || numP.getText().isEmpty() || mate.getSelectedItem().toString().equals("Selecciona una materia")) {
                JOptionPane.showMessageDialog(this, "Teclee todos los datos obligatorios");
            } else {
                valida();
                if (error != 0 || error2 != 0 || error3 != 0) {
                    JOptionPane.showMessageDialog(this, "Teclee sólo carácteres válidos");
                } else if (numP.getText().matches("[0-9]*")) {
                    if (Integer.parseInt(numP.getText()) < 53 && Integer.parseInt(numP.getText())>0) {
                        constructor co = new constructor();
                        String fechaExa;
                        String mens= messs((String) mes.getSelectedItem().toString());
                        fechaExa = (String) año.getSelectedItem().toString()+ "-"
                                + mens
                                + "-" + (String) dia.getSelectedItem().toString() ;
                        
                        co.setTitulo(tTitulo.getText());

                        co.setFecha(fechaExa);
                        co.setPeriodo((String) peri.getSelectedItem());
                        co.setMateria((String) mate.getSelectedItem());
                        co.setMaestro(p.getNomCompleto());
                        numPreg = Integer.parseInt(numP.getText());
                        nombreArchivo = tTitulo.getText();
                        co.setNumPreg(numPreg);
                        try {
                            exam=co.guardaEx(_idProfe);
                        } catch (SQLException ex) {
                            Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        _idExa=exam[0];
                        co.setIdExa(_idExa);
                        datExa.add(co);
                        hoj.setNombreArchivo(nombreArchivo);
                        CerrarArchivo();
                        ven.dispose();
                        try {
                            grupos();
                        } catch (SQLException ex) {
                            Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(this, "Numero maximo de preguntas: 52");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Teclee solo numeros");
                }
            }

        }
        if (e.getSource().equals(Sigui)) {
            if (tTitulo.getText().isEmpty() || peri.getSelectedItem().toString().equals("Selecciona un periodo")) {
                JOptionPane.showMessageDialog(this, "Teclee todos los datos obligatorios");
            } else {
                valida();
                if (error != 0 || error2 != 0 || error3 != 0) {
                    
                    JOptionPane.showMessageDialog(this, "Teclee sólo carácteres válidos");
                } else {
                    constructor con = new constructor();
                    
                    String fechaExa;
                    String mens= messs((String) mes.getSelectedItem().toString());
                    fechaExa = (String) año.getSelectedItem().toString() + "-" 
                            + mens + "-"
                            + (String) dia.getSelectedItem().toString();
                            
                            
                    con.setTitulo(tTitulo.getText());

                    con.setFecha(fechaExa);
                    con.setPeriodo((String) peri.getSelectedItem());
                    con.setMateria((String) mate.getSelectedItem());
                    con.setMaestro(tProfesor.getText());
                    nombreArchivo = tTitulo.getText();
                    exa.setNombreArchivo(nombreArchivo);
                    hoj.setNombreArchivo(nombreArchivo);
                    
                    try {
                        exam=con.guardaEx(_idProfe);
                    } catch (SQLException ex) {
                        Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    _idExa=exam[0];
                    con.setIdExa(_idExa);
                    datExa.add(con);
                    CerrarArchivo();
                    ven.dispose();
                    try {
                        grupos2();
                    } catch (SQLException ex) {
                        Logger.getLogger(Jpanes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }

        }
        if (e.getSource().equals(Atra)) {
            ven.dispose();
            ElegirHo();
        }
        //ElegirHoja
        if (e.getSource().equals(ExamHoj)) {
            ven.dispose();
            DatosExamen();
            
        }
        if (e.getSource().equals(SoloHoj)) {
            ven.dispose();
            DatosExamen2();
        }
        //Elegir pregunta-------------------------------------------------------
        if (e.getSource().equals(Normal)) {
            ven.dispose();
            Normal();
        }
        if (e.getSource().equals(ok)) {
            ven.dispose();
            numeroRel = Integer.parseInt(nPreguntas.getText());
            RelacionColumnas();
        }
        //Pregunta Normal-------------------------------------------------------
        if (e.getSource().equals(PregSig)) {
            AbrirArchivo();
            String opciones[] = new String[4];
            String correcta = null;
            opciones[0] = "A)" + tOpcionA.getText();
            opciones[1] = "B)" + tOpcionB.getText();
            opciones[2] = "C)" + tOpcionC.getText();
            opciones[3] = "D)" + tOpcionD.getText();
            if(rOpcionA.isSelected() == false && rOpcionB.isSelected() == false
                    && rOpcionC.isSelected() == false && rOpcionD.isSelected() == false ){
                JOptionPane.showMessageDialog(this, "Seleccione alguno de las respuestas");
            }
             else{
            if(tOpcionA.getText().isEmpty() || tOpcionB.getText().isEmpty() ||
                     tOpcionC.getText().isEmpty() || tOpcionD.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Teclee todos los campos");
            }
           
                else{
                    if (rOpcionA.isSelected() == true) {
                    correcta = "A";
                } else if (rOpcionB.isSelected() == true) {
                    correcta = "B";
                } else if (rOpcionC.isSelected() == true) {
                    correcta = "C";
                } else if (rOpcionD.isSelected() == true) {
                    correcta = "D";
                } else {

                }
                constructor con = new constructor();
                
                con.setOpc(opciones);
                con.setRespuesta(correcta);
                con.setPregunta(tEnunciado.getText());
                datExa.add(con);
                CerrarArchivo();
                ven.dispose();
                ElegirPregunta();
            }
            }
        }
        if (e.getSource().equals(PregAtr)) {
            ven.dispose();
            ElegirPregunta();
        }
        //Finalizar
        if (e.getSource().equals(Finalizar)) {

            try {
                exa.crearPDF(new File("pdf/" + nombreArchivo + "Exa.pdf"));

            } catch (IOException ex) {

                System.out.println(ex);
            }
        }
        //Ver examenes
        for (int i = 0; i < ficheros.length; i++) {
            if (e.getActionCommand().equals(ficheros[i].getName())) {
                String pdf = "pdf/" + ficheros[i].getName();
                try {
                    File path = new File(pdf + "Exa.pdf");
                    Desktop.getDesktop().open(path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

