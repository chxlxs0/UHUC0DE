/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VentanaMonitores;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

/**
 *
 * @author nekro
 */
public class MonitorDAO {

    private final Session sesion;
    private final VentanaMonitores gMonitores;
    private DefaultTableModel model;
    private PreparedStatement ps;

    int numeroMonitores;

    public MonitorDAO(Session sesion, VentanaMonitores gMonitores) {
        this.sesion = sesion;
        this.gMonitores = gMonitores;
        numeroMonitores = 0;
    }

    public void RefrescarPanelMonitores() {

        Transaction transaction = sesion.beginTransaction();
        NativeQuery consulta = sesion.createNativeQuery("SELECT * FROM MONITOR", Monitor.class);
        ArrayList<Monitor> monitores = (ArrayList<Monitor>) consulta.list();
        transaction.commit();

        model = new DefaultTableModel();
        numeroMonitores = monitores.size();
        RellenarColumnas();

        gMonitores.tablaMonitores.setModel(model);

        String[] datos = new String[7];
        for (Monitor m : monitores) {
            datos[0] = m.getCodmonitor();
            datos[1] = m.getNombre();
            datos[2] = m.getDni();
            datos[3] = m.getTelefono();
            datos[4] = m.getCorreo();
            datos[5] = m.getFechaentrada();
            datos[6] = m.getNick();
            model.addRow(datos);
        }
        
    }

    private void RellenarColumnas() {
        model.addColumn("Código");
        model.addColumn("Nombre");
        model.addColumn("DNI");
        model.addColumn("Teléfono");
        model.addColumn("Correo");
        model.addColumn("Fecha de Incorporación");
        model.addColumn("Nick");
    }

    public void InsertMonitor(Monitor nuevoMonitor) throws SQLException {
        Transaction transaction = sesion.beginTransaction();
        sesion.save(nuevoMonitor);
        transaction.commit();
        RefrescarPanelMonitores();
    }

    public void DeleteMonitor() {

        int selectedRow = this.gMonitores.tablaMonitores.getSelectedRow();

        String idMonitor = (String) this.gMonitores.tablaMonitores.getValueAt(selectedRow, 0);

        Transaction transaction = sesion.beginTransaction();
        Monitor monitor = sesion.get(Monitor.class, idMonitor);
        sesion.delete(monitor);
        transaction.commit();
    }


}
