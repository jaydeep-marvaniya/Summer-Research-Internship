
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GuiApp extends JFrame implements ActionListener
{
    JLabel iflbl;
    JLabel oflbl;
    JButton brw;
    JButton cb;
    JFileChooser chooser;
    File chosenFile;
     
    //constructor
    GuiApp()
    {
        iflbl = new JLabel("input file :");
        oflbl = new JLabel("output file :");
        brw = new JButton("browse");
        cb = new JButton("Convert");
        chooser= new JFileChooser();

        
        Container con=getContentPane();
        this.setSize(200,200);
        con.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        brw.addActionListener(this);
        cb.addActionListener(this);
        
        con.add(iflbl);
        con.add(brw);
        con.add(cb);
        con.add(oflbl);
       
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        JButton src=(JButton)ae.getSource();
        
        if(src == brw)
        {
           int choice = chooser.showOpenDialog(this);

            if (choice != JFileChooser.APPROVE_OPTION) return;

            chosenFile = chooser.getSelectedFile(); 
            iflbl.setText(chosenFile.getName());
        }
		
        else if(src == cb)
        {
			
			
            oflbl.setText("output file created at");
			
        }
    }
    public static void main(String[] args) 
    {
        new GuiApp();
    }
}
