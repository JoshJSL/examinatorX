
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JFrame;
import sun.java2d.SunGraphicsEnvironment;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexis
 */
public class NewClass1 extends JFrame{
    
    public void Vena(){
        int pa, ph;
        GraphicsConfiguration config = getGraphicsConfiguration();
        Rectangle usableBounds = SunGraphicsEnvironment.getUsableBounds(config.getDevice());
        getSize();
        
        setMaximizedBounds(new Rectangle(0, 0, usableBounds.width, usableBounds.height));
        setExtendedState((getExtendedState() & MAXIMIZED_BOTH) == MAXIMIZED_BOTH ? NORMAL : MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(600, 400));
        pa = getSize().width / 100;
        ph = getSize().height / 100;
        
        JButton btn = new JButton();
        btn.setBounds(50*pa, 50*ph, 20*pa, 20*ph);
        add(btn);
    }
}
