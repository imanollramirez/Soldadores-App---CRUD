package Model;

import View.frmSoldadores;
import java.sql.*;
import java.util.UUID;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class tbSoldadores {

    public String getIdPacientes() {
        return idPacientes;
    }

    public void setIdPacientes(String idPacientes) {
        this.idPacientes = idPacientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    private String idPacientes;
    private String nombre;
    private int edad;
    private int peso;
    private String correo;
    
    
    public void Guardar() {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClassConnection.getConexion();
        try {
            //Creamos el PreparedStatement que ejecutará la Query
            PreparedStatement addSoldador = conexion.prepareStatement("INSERT INTO tbSoldador (ID_Soldador, Nombre,Edad,Peso,Correo) VALUES(?,?,?,?,?)");
            //Establecer valores de la consulta SQL
            addSoldador.setString(1,UUID.randomUUID().toString());
            addSoldador.setString(2, getNombre());
            addSoldador.setInt(3, getEdad());
            addSoldador.setInt(4, getPeso());
            addSoldador.setString(5, getCorreo());
            
            //Ejecutar la consulta
            addSoldador.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    public void Mostrar(JTable tabla) {
        //Creamos una variable de la clase de conexion
        Connection conexion = ClassConnection.getConexion();
        //Definimos el modelo de la tabla
        DefaultTableModel modeloDeDatos = new DefaultTableModel();
        
        modeloDeDatos.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Edad", "Peso","Correo"});
        try {
            //Creamos un Statement
            Statement statement = conexion.createStatement();
            //Ejecutamos el Statement con la consulta y lo asignamos a una variable de tipo ResultSet
            ResultSet rs = statement.executeQuery("SELECT * FROM tbSoldador");
            //Recorremos el ResultSet
            while (rs.next()) {
                //Llenamos el modelo por cada vez que recorremos el resultSet
                modeloDeDatos.addRow(new Object[]{rs.getString("ID_Soldador"), 
                    rs.getString("NOMBRE"), 
                    rs.getInt("EDAD"), 
                    rs.getString("PESO"),
                    rs.getString("CORREO")});
            }
            //Asignamos el nuevo modelo lleno a la tabla
            tabla.setModel(modeloDeDatos);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
    public void Eliminar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClassConnection.getConexion();
 
        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();
        //Obtenemos el id de la fila seleccionada
        String idSoldador = tabla.getValueAt(filaSeleccionada, 0).toString();
        //borramos 
        try {
            PreparedStatement deleteSoldador = conexion.prepareStatement("DELETE FROM tbSoldador WHERE idSoldador = ?");
            deleteSoldador.setString(1, idSoldador);
            deleteSoldador.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
    public void cargarDatosTabla(frmSoldadores View) {
        // Obtén la fila seleccionada 
        int filaSeleccionada = View.jtbSoldador.getSelectedRow();
 
        // Debemos asegurarnos que haya una fila seleccionada antes de acceder a sus valores
        if (filaSeleccionada != -1) {
            String UUIDDeTb = View.jtbSoldador.getValueAt(filaSeleccionada, 0).toString();
            String NombreDeTB = View.jtbSoldador.getValueAt(filaSeleccionada, 1).toString();
            String EdadDeTb = View.jtbSoldador.getValueAt(filaSeleccionada, 2).toString();
            String pesoDeTB = View.jtbSoldador.getValueAt(filaSeleccionada, 3).toString();
            String correoDeTB = View.jtbSoldador.getValueAt(filaSeleccionada, 4).toString();
 
            // Establece los valores en los campos de texto
            View.txtNombre.setText(NombreDeTB);
            View.txtEdad.setText(EdadDeTb);
            View.txtPeso.setText(pesoDeTB);
            View.txtCorreo.setText(correoDeTB);
        }
    }
    
    public void Actualizar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClassConnection.getConexion();
 
        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            //Obtenemos el id de la fila seleccionada
            String IdSoldador = tabla.getValueAt(filaSeleccionada, 0).toString();
            try { 
                //Ejecutamos la Query
                PreparedStatement updateSoldador = conexion.prepareStatement("UPDATE tbSoldador SET Nombre = ?, Edad = ?, Peso = ?, Correo = ? WHERE ID_Soldador = ?");
                updateSoldador.setString(1, getNombre());
                updateSoldador.setInt(2, getEdad());
                updateSoldador.setInt(3, getPeso());
                updateSoldador.setString(4, getCorreo());
                updateSoldador.setString(5, IdSoldador);
                updateSoldador.executeUpdate();
                
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        } else {
            System.out.println("Error");
        }
    }
    
    public void Buscar(JTable tabla, JTextField txtBuscar) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClassConnection.getConexion();
        //Definimos el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Edad", "Peso","Correo"});
        try {
            PreparedStatement deleteSoldador = conexion.prepareStatement("SELECT * FROM tbSoldador WHERE Nombre LIKE ? || '%'");
            deleteSoldador.setString(1, txtBuscar.getText());
            ResultSet rs = deleteSoldador.executeQuery();
 
             while (rs.next()) {
                //Llenamos el modelo por cada vez que recorremos el resultSet
                modelo.addRow(new Object[]{rs.getString("ID_Soldador"), 
                    rs.getString("NOMBRE"), 
                    rs.getInt("EDAD"), 
                    rs.getString("PESO"),
                    rs.getString("CORREO")});
            }
            //Asignamos el nuevo modelo lleno a la tabla
            tabla.setModel(modelo);
 
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}