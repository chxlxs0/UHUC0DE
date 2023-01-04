/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.MonitorDAO;
import Modelo.Monitor;
import Vista.VentanaMonitores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import org.hibernate.Session;

/**
 *
 * @author nekro
 */
public class ControladorMonitores implements ActionListener {

    private final VentanaMonitores gMonitores;
    private final MonitorDAO monitores;
    private final Session sesion;
    private ControladorVentanaNuevoMonitor c;

    public ControladorMonitores(Session ses) throws SQLException {
        gMonitores = new VentanaMonitores();

        this.monitores = new MonitorDAO(ses, gMonitores);
        this.sesion = ses;
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

    private String[] getData(int row) {
        Monitor moniiii = new Monitor();

        String[] datos = new String[7];

        datos[0] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 0);
        datos[1] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 1);
        datos[2] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 2);
        datos[3] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 3);
        datos[4] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 4);
        datos[5] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 5);
        datos[6] = (String) this.gMonitores.tablaMonitores.getValueAt(row, 6);

        moniiii.setCodmonitor(datos[0]);
        moniiii.setNombre(datos[1]);
        moniiii.setDni(datos[2]);
        moniiii.setTelefono(datos[3]);
        moniiii.setCorreo(datos[4]);
        moniiii.setFechaentrada(datos[5]);
        moniiii.setNick(datos[6]);

        return datos;
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
                int row = gMonitores.tablaMonitores.getSelectedRow();
                ControladorVentanaNuevoMonitor m = new ControladorVentanaNuevoMonitor(monitores, getData(row));
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
