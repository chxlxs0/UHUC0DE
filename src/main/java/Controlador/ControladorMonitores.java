/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Vista.VentanaMonitores;
import Vista.VentanaNewMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author nekro
 */
public class ControladorMonitores implements ActionListener {

    VentanaMonitores gMonitores;
    VentanaNewMonitor nuevoMonitor;
    Monitores monitores;
    Connection conexion;

    public ControladorMonitores(Conexion con) throws SQLException {
        gMonitores = new VentanaMonitores();

        this.monitores = new Monitores(con, gMonitores);
        this.conexion = con.getConexion();
        monitores.visualizarMonitores();

        gMonitores.setLocationRelativeTo(null);
        gMonitores.setVisible(true);

        addListeners();
    }

    private void addListeners() {
        gMonitores.newMonitor.addActionListener(this);
        gMonitores.deleteMonitor.addActionListener(this);
        gMonitores.actMonitor.addActionListener(this);
        gMonitores.Salir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae.getActionCommand());
        switch (ae.getActionCommand()) {
            case "Nuevo Monitor": {
                ControladorNewMonitor c = new ControladorNewMonitor(this.monitores);
            }
            break;

            case "Baja de monitor": {
            }
            break;

            case "Actualizar Monitor": {
            }
            case "Salir": {
                gMonitores.dispose();
            }

            break;
            default:
                throw new AssertionError();
        }

    }

}
