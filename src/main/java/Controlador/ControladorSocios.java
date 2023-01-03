/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.MonitorDAO;
import Modelo.Conexion;
import Modelo.Monitor;
import Vista.VentanaMonitores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author nekro
 */
public class ControladorMonitores implements ActionListener {

    private final VentanaMonitores gMonitores;
    private final MonitorDAO monitores;
    private final Connection conexion;
    private ControladorVentanaNuevoMonitor c;

    public ControladorMonitores(Conexion con) throws SQLException {
        gMonitores = new VentanaMonitores();

        this.monitores = new MonitorDAO(con, gMonitores);
        this.conexion = con.getConexion();
        monitores.RefrescarPanelMonitores();

        gMonitores.setLocationRelativeTo(null);
        gMonitores.setVisible(true);
        gMonitores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addListeners();
    }

    private void addListeners() {
        gMonitores.newMonitor.addActionListener(this);
        gMonitores.deleteMonitor.addActionListener(this);
        gMonitores.actMonitor.addActionListener(this);
        gMonitores.Salir.addActionListener(this);
    }

    private Monitor getData(int row) {
        Monitor moniiii;

        String[] datos = new String[7];

        datos[0] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 0);
        datos[1] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 1);
        datos[2] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 2);
        datos[3] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 3);
        datos[4] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 4);
        datos[5] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 5);
        datos[6] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 6);
        moniiii = new Monitor(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6]);
        return moniiii;
    }
    @Override
    public void actionPerformed(ActionEvent ae
    ) {
        System.out.println(ae.getActionCommand());
        switch (ae.getActionCommand()) {
            case "Nuevo Monitor": {
                this.c = new ControladorVentanaNuevoMonitor(this.monitores);
            }
            break;

            case "Baja de monitor": {
                    monitores.DeleteMonitor();
                    monitores.RefrescarPanelMonitores();                
            }
            break;

            case "Actualizar Monitor": {

                try {
                    int row = gMonitores.tablaMonitores.getSelectedRow(); 
                    Monitor monit = getData(row);
                    monitores.UpdateMonitor(monit, monit.getCodigo());
                    ControladorVentanaNuevoMonitor m = new ControladorVentanaNuevoMonitor(monitores, monit);

                } catch (SQLException ex) {
                    Logger.getLogger(ControladorMonitores.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case "Salir": {
                gMonitores.dispose();
            }
            break;

            default:
                throw new AssertionError();
        }

    }

}
