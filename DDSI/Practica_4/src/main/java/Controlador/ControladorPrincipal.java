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

public class ControladorPrincipal implements ActionListener {

    private final VentanaPrincipal ventana;
    private final Vista.VistaLogin vLogin;
    private ControladorMonitores cMonitores;
    private ControladorActividad cActividad;
    private final Conexion conexion;
    private final String SBD;
    

    public ControladorPrincipal(Conexion conection, String SGBD) {
        this.conexion = conection;
        ventana = new VentanaPrincipal();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        vLogin = new VistaLogin();
        this.SBD = SGBD;
        addListeners();
    }

    private void addListeners() {
        ventana.Cerrar.addActionListener(this);
        vLogin.Conectar.addActionListener(this);
        ventana.panelMonitor.addActionListener(this);
        ventana.Actividad.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae.getActionCommand());
        switch (ae.getActionCommand()) {
            case "Cerrar":
                System.exit(0);

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
            default:
                throw new AssertionError();
        }
    }
}
