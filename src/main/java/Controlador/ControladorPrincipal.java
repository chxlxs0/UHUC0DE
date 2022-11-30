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

public class ControladorPrincipal implements ActionListener {

    VentanaPrincipal ventana;
    Vista.VistaLogin vLogin;
    ControladorMonitores cMonitores;
    Conexion conexion;
    

    public ControladorPrincipal(Conexion conection) {
        this.conexion = conection;
        ventana = new VentanaPrincipal();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        vLogin = new VistaLogin();
        addListeners();
    }

    private void addListeners() {
        ventana.Cerrar.addActionListener(this);
        vLogin.Conectar.addActionListener(this);
        ventana.panelMonitor.addActionListener(this);
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

                } catch (Exception e) {
                }
            }
                break;
            default:
                throw new AssertionError();
        }
    }
}
