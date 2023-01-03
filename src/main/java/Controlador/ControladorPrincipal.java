/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author nekro
 */
import Modelo.Conexion;
import Vista.VentanaPrincipal;
import Vista.VistaLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ControladorPrincipal implements ActionListener {

    private final VentanaPrincipal ventana;
    private final Vista.VistaLogin vLogin;
    private ControladorMonitores cMonitores;
    private ControladorActividad cActividad;
    private ControladorSocios cSocio;

    private final Conexion conexion;
    private final String SBD;

    public ControladorPrincipal(Conexion conection, String SGBD) {
        this.conexion = conection;
        ventana = new VentanaPrincipal();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        vLogin = new VistaLogin();
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.SBD = SGBD;
        addListeners();
    }

    private void addListeners() {
        ventana.Cerrar.addActionListener(this);
        vLogin.Conectar.addActionListener(this);
        ventana.panelMonitor.addActionListener(this);
        ventana.Actividad.addActionListener(this);
        ventana.Socios.addActionListener(this);
        ventana.Salir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae.getActionCommand());
        switch (ae.getActionCommand()) {
            case "Cerrar": {
                try {
                    int opc;
                    opc = JOptionPane.showConfirmDialog(ventana, "Esto cerrará el programa por completo, ¿Está seguro de que quiere continuar?", "Confirme para cerrar esta ventana", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (opc == JOptionPane.YES_OPTION) {
                        ventana.dispose();
                        this.conexion.desconexion();
                        System.exit(0);

                    } else {
                        JOptionPane.showMessageDialog(ventana, "Operación cancelada");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(ventana, "Se ha producido un error en la desconexion");
                }
            }
            break;

            case "Salir": {
                int opc;
                opc = JOptionPane.showConfirmDialog(ventana, "Esto finalizará la conexión, ¿Está seguro de que quiere continuar?", "Confirme para cerrar esta ventana", JOptionPane.YES_NO_CANCEL_OPTION);
                if (opc == JOptionPane.YES_OPTION) {
                    ventana.dispose();
                } else {
                    JOptionPane.showMessageDialog(ventana, "Operación cancelada");
                }
            }

            break;

            case "Abrir panel de monitores": {
                try {
                    this.cMonitores = new ControladorMonitores(this.conexion);

                } catch (SQLException e) {
                }
            }

            break;
            case "Actividad": {
                this.cActividad = new ControladorActividad(this.conexion, this.SBD);
            }
            break;
            case "Socios": {
                try {
                    this.cSocio = new ControladorSocios(this.conexion);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            default:
                throw new AssertionError();
        }
    }
}
