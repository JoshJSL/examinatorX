package examinador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import sun.java2d.SunGraphicsEnvironment;

public class Ventana extends JFrame implements ActionListener {
    
    //ArrayList<Object>
    
    private JPanel parteSup, parteInfe;
    private JButton Examenes, HacerExamenes, Subir;
    private Color azul = new Color(29,51,95), gris = new Color(226,226,226), blanco = new Color(255,255,255);
    private Font Enca = new Font(null, 16, 18), Vacios = new Font(null, 45, 45), Ins = new Font(null, 20, 27),
            Letras = new Font(null, 25, 25);
    
    
    public void Ventanaa(JPanel contenedor){
        
        GraphicsConfiguration config = getGraphicsConfiguration();
        Rectangle usableBounds = SunGraphicsEnvironment.getUsableBounds(config.getDevice());;
        setMaximizedBounds(new Rectangle(0, 0, usableBounds.width, usableBounds.height));
        setExtendedState((getExtendedState() & MAXIMIZED_BOTH) == MAXIMIZED_BOTH ? NORMAL : MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        BorderLayout lay = new BorderLayout();
        setLayout(lay);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        setMinimumSize(new Dimension(600, 400));
        getContentPane().setBackground(blanco);
        GridLayout layo = new GridLayout(2, 7);
        parteSup = new JPanel();
        parteSup.setLayout(layo);
        parteSup.setBackground(gris);
        parteSup.setPreferredSize(new Dimension(usableBounds.width, 60));
        
        //Vacio para los espacios del layout que no se usen
        JLabel Vacio[] = new JLabel[11];
        for (int i = 0; i<Vacio.length; i++) {
            Vacio[i] = new JLabel();
            Vacio[i].setForeground(gris);
            
        }
        
        //Boton Examenes
        Examenes = new JButton("Examenes");
        Examenes.setBorder(null);
        Examenes.setBackground(gris);
        Examenes.setForeground(azul);
        Examenes.setFont(Enca);
        Examenes.addActionListener(this);
        
        
        //Boton HacerExamenes
        HacerExamenes = new JButton("Hacer Examenes");
        HacerExamenes.setBorder(null);
        HacerExamenes.setBackground(gris);
        HacerExamenes.setForeground(azul);
        HacerExamenes.setFont(Enca);
        HacerExamenes.addActionListener(this);
        
        //Boton Subir
        Subir = new JButton("Calificar");
        Subir.setBorder(null);
        Subir.setBackground(gris);
        Subir.setForeground(azul);
        Subir.setFont(Enca);
        Subir.addActionListener(this);
        
        
        //Agregar los componentes
        for (int i = 0; i < Vacio.length-4; i++){
            parteSup.add(Vacio[i]);
            
        }
        parteSup.add(Examenes);
        parteSup.add(HacerExamenes);
        parteSup.add(Subir);
        for (int i = 7; i < Vacio.length; i++){
            parteSup.add(Vacio[i]);
            
        }
        
        add(parteSup, BorderLayout.NORTH);
        
        
        
        parteInfe = new JPanel();
        parteInfe.setBackground(blanco);
        parteInfe.setLayout(new BorderLayout());
        
        JLabel Vacio1 = new JLabel("No estoy Aqui");
        Vacio1.setForeground(blanco);
        Vacio1.setFont(Vacios);
        JLabel Vacio2 = new JLabel("No estoy Aqui");
        Vacio2.setForeground(blanco);
        Vacio2.setFont(Vacios);
        JLabel Vacio3 = new JLabel("nel");
        Vacio3.setForeground(blanco);
        Vacio3.setFont(Vacios);
        JLabel Vacio4 = new JLabel("nel");
        Vacio4.setForeground(blanco);
        Vacio4.setFont(Vacios);
        
        Jpanes panes = new Jpanes();
        
        parteInfe.add(Vacio1, BorderLayout.NORTH);
        parteInfe.add(contenedor, BorderLayout.CENTER);   
        parteInfe.add(Vacio2, BorderLayout.SOUTH);
        parteInfe.add(Vacio3, BorderLayout.WEST);
        parteInfe.add(Vacio4, BorderLayout.EAST);
        
        add(parteInfe, BorderLayout.CENTER);
        
        
    }
    public void Ventanaa2(JPanel contenedor){
        
        
        setLocationRelativeTo(null);
        BorderLayout lay = new BorderLayout();
        setLayout(lay);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));
        getContentPane().setBackground(blanco);
        GridLayout layo = new GridLayout(2, 7);
        parteSup = new JPanel();
        parteSup.setLayout(layo);
        parteSup.setBackground(gris);
        parteSup.setPreferredSize(new Dimension(700, 60));
        
        //Vacio para los espacios del layout que no se usen
        JLabel Vacio[] = new JLabel[14];
        for (int i = 0; i<Vacio.length; i++) {
            Vacio[i] = new JLabel();
            Vacio[i].setForeground(gris);
            
        }
        
        
        
        //Agregar los componentes
        
        for (int i = 0; i < Vacio.length; i++){
            parteSup.add(Vacio[i]);
            
        }
        
        add(parteSup, BorderLayout.NORTH);
        
        
        
        parteInfe = new JPanel();
        parteInfe.setBackground(blanco);
        parteInfe.setLayout(new BorderLayout());
        
        JLabel Vacio1 = new JLabel("No estoy Aqui");
        Vacio1.setForeground(blanco);
        Vacio1.setFont(Vacios);
        JLabel Vacio2 = new JLabel("No estoy Aqui");
        Vacio2.setForeground(blanco);
        Vacio2.setFont(Vacios);
        JLabel Vacio3 = new JLabel("nel");
        Vacio3.setForeground(blanco);
        Vacio3.setFont(Vacios);
        JLabel Vacio4 = new JLabel("nel");
        Vacio4.setForeground(blanco);
        Vacio4.setFont(Vacios);
        
        Jpanes panes = new Jpanes();
        
        parteInfe.add(Vacio1, BorderLayout.NORTH);
        parteInfe.add(contenedor, BorderLayout.CENTER);   
        parteInfe.add(Vacio2, BorderLayout.SOUTH);
        parteInfe.add(Vacio3, BorderLayout.WEST);
        parteInfe.add(Vacio4, BorderLayout.EAST);
        
        add(parteInfe, BorderLayout.CENTER);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Jpanes j = new Jpanes();
        if(e.getSource().equals(Examenes)){
            j.VerExamenes();
            this.dispose();
        }
        if(e.getSource().equals(HacerExamenes)){
            j.ElegirHo();
            this.dispose();
        }
        if(e.getSource().equals(Subir)){
            j.Comparar();
            this.dispose();
        }
    
    }
}
