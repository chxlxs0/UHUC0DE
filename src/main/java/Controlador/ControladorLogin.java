package Controlador;

import Vista.VentanaPrincipal;
import Modelo.*;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ControladorLogin implements ActionListener {

    private Conexion conexion;
    private boolean status;
    private final VistaLogin vLogin;
    private VentanaPrincipal ventana;
    private VistaMensajes mensaje;
    private String SGBD;
    

    public ControladorLogin() throws SQLException, ClassNotFoundException {

        vLogin = new VistaLogin();

        vLogin.setLocationRelativeTo(null);

        vLogin.setVisible(true);

        addListener();
    }

    private void addListener() {
        vLogin.Conectar.addActionListener(this);
        vLogin.Salir.addActionListener(this);
        vLogin.Password_fied.addActionListener(this);
    }
    
    public boolean conectar() throws SQLException, ClassNotFoundException {

        String sgbd, ip, servicio_bd, usuario, password;

        sgbd = (String) (vLogin.Servidores.getSelectedItem());
        servicio_bd = vLogin.Services_field.getText();
        usuario = vLogin.User_field.getText();
        password = new String(vLogin.Password_fied.getPassword());
        ip = vLogin.Ip_field.getText();
        
        this.SGBD = sgbd;
        
        try {
            conexion = new Conexion(sgbd, ip, servicio_bd, usuario, password);
            return !conexion.getConexion().isClosed();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No se ha podido conectar de manera correcta a la Base de Datos");
        }

        return false;
    }

    public void desconectar() throws SQLException, ClassNotFoundException {
        if (status == true) {
            conexion.getConexion().close();
        }
    }

    public boolean getStatus() {
        return status;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        status = false;
        System.out.println(ae.getActionCommand());
        switch (ae.getActionCommand()) {
            case "Conectar": {
                System.out.println("\n\n\n\n\n\n\nConectar\n\n\n\n\n\n\n");
                try {
                    status = conectar();
                    if (status) {
                        ControladorPrincipal c = new ControladorPrincipal(conexion, this.SGBD);
                    } else {
                        Thread.sleep(1000*4);
                        throw new SQLException();
                    }

                } catch (SQLException | ClassNotFoundException ex) {
                    this.mensaje = new VistaMensajes("ERROR", "Error al intentar conectarse");
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "Salir": {
                try {
                    desconectar();
                    vLogin.dispose();
                    System.exit(0);
                } catch (ClassNotFoundException | SQLException e) {

                    JOptionPane.showMessageDialog(null, "Operación no realizada");
                    this.mensaje = new VistaMensajes("ERROR", "Error al intentar desconectarse");

                }

            }
            break;

            case "Cerrar":
                System.exit(0);
                break;
        }

    }

}