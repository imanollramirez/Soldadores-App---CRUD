package Controller;

import Model.tbSoldadores;
import View.frmSoldadores;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

//3. Class inheritance that detects actions
public class ctrlVisitas implements MouseListener,KeyListener{
    //1. Call the other layers
    private tbSoldadores Model;
    private frmSoldadores View;
    
    //2. Create the constructor
    public ctrlVisitas(tbSoldadores Model, frmSoldadores View){
        this.Model = Model;
        this.View = View;
        
        View.btnAgregar.addMouseListener(this);
        View.btnLimpiar.addMouseListener(this);
        View.btnEliminar.addMouseListener(this);
        View.btnEditar.addMouseListener(this);
        
        //Se hará al escribir, osea letra por letra. Entonces, el boton no es necesario
        View.jtbSoldador.addMouseListener(this);
        View.txtBuscar.addKeyListener(this);
        Model.Mostrar(View.jtbSoldador);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == View.btnAgregar)
        {          
            if(View.txtNombre.getText().isEmpty() || View.txtPeso.getText().isEmpty() || View.txtEdad.getText().isEmpty() || View.txtCorreo.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Complete los campos.");
                return;
            }                                                                                                                                                                  
            else{
            Model.setNombre(View.txtNombre.getText());
            Model.setEdad(Integer.parseInt(View.txtEdad.getText()));
            Model.setPeso(Integer.parseInt(View.txtPeso.getText()));
            Model.setCorreo(View.txtCorreo.getText());
            Model.Guardar();
            
            JOptionPane.showMessageDialog(null, "Se registro el soldador correctamente.");
            Model.Mostrar(View.jtbSoldador);
            Clean();
            }
        }
        
        if(e.getSource() == View.btnLimpiar)
        {
            Clean();
            Model.Mostrar(View.jtbSoldador);
            JOptionPane.showMessageDialog(null, "Se limpiaron los campos.");
        }
        
        if(e.getSource() == View.btnEliminar)
        {   
            Model.Eliminar(View.jtbSoldador);
            JOptionPane.showMessageDialog(null, "Se eliminó el registro con éxito.");
            Clean();
            Model.Mostrar(View.jtbSoldador);
        }
        
        if(e.getSource() == View.jtbSoldador)
        {
            Model.cargarDatosTabla(View);
            View.btnAgregar.setEnabled(false);
            View.btnAgregar.removeMouseListener(this);
        }
        
        if(e.getSource() == View.btnEditar)
        {
            Model.setNombre(View.txtNombre.getText());
            Model.setEdad(Integer.parseInt(View.txtEdad.getText()));
            Model.setPeso(Integer.parseInt(View.txtPeso.getText()));
            Model.setCorreo(View.txtCorreo.getText());
            Model.Actualizar(View.jtbSoldador);
            Clean();
            JOptionPane.showMessageDialog(null,"Se actualizó el dato correctamente.");
            Model.Mostrar(View.jtbSoldador);
        }
    }
    
    public void Clean()
    {
   
       View.txtNombre.setText("");
       View.txtPeso.setText("");
       View.txtEdad.setText("");
       View.txtCorreo.setText("");
       View.btnAgregar.setEnabled(true);
       View.btnAgregar.addMouseListener(this);
       View.txtBuscar.setText("");
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource() == View.txtBuscar)
        {
            Model.Buscar(View.jtbSoldador, View.txtBuscar);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
