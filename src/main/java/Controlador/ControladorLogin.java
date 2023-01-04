package Controlador;

import Modelo.Hibernate.HibernateUtilOracle;
import Modelo.Hibernate.HibernateUtilMariaDB;
import Vista.VentanaPrincipal;
import Modelo.*;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.hibernate.Session;

public class ControladorLogin implements ActionListener {

    private Session sesion;
    private boolean status;
    private final VistaLogin vLogin;
    private VentanaPrincipal ventana;
    private vMensaje mensaje;
    private String SGBD;

    public ControladorLogin() throws SQLException, ClassNotFoundException {

        vLogin = new VistaLogin();
        vLogin.setLocationRelativeTo(null);
        vLogin.setVisible(true);
        vLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addListener();
    }

    private void addListener() {
        vLogin.Conectar.addActionListener(this);
        vLogin.Salir.addActionListener(this);
        vLogin.Password_fied.addActionListener(this);
    }

    public Session conectar() throws SQLException, ClassNotFoundException {

        String server = (String) (vLogin.Servidores.getSelectedItem());
        if ("MariaDB".equals(server)) {
            server = "mariadb";
        } else if ("Oracle".equals(server)) {
            server = "oracle";
        }
        if ("oracle".equals(server)) {
            sesion = HibernateUtilOracle.getSessionFactory().openSession();
        } else if ("mariadb".equals(server)) {
            sesion = HibernateUtilMariaDB.getSessionFactory().openSession();
        }
        return (sesion);
    }

    public void desconectar() throws SQLException, ClassNotFoundException {
        sesion.close();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        status = false;
        System.out.println(ae.getActionCommand());
        switch (ae.getActionCommand()) {
            case "Conectar":
            {
                try {
                    sesion = conectar();
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                if (sesion != null) {
                    //mensaje.show(1, "Conexión correcta con Hibernate");
                    vLogin.dispose();
                    ControladorPrincipal controlador = new ControladorPrincipal(sesion, vLogin.Servidores.getSelectedItem().toString());
                } else {
                    //mensaje.show(-1, "Error en la conexión. No se ha podido crear una sesión\n");
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
                    this.mensaje.show(-1, "Error al intentar desconectarse");

                }

            }
            break;

            case "Cerrar":
                System.exit(0);
                break;
        }

    }

}
