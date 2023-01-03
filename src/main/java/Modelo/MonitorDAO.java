/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VentanaMonitores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nekro
 */
public class MonitorDAO {

    private final Connection conexion;
    private final VentanaMonitores gMonitores;
    private DefaultTableModel model;
    private PreparedStatement ps;

    int numeroMonitores;

    public MonitorDAO(Conexion con, VentanaMonitores gMonitores) {
        this.conexion = con.getConexion();
        this.gMonitores = gMonitores;
    }

    public void RefrescarPanelMonitores() {
        model = new DefaultTableModel();
        numeroMonitores = 0;

        RellenarColumnas();

        String selectAllProducts = "SELECT * FROM MONITOR";

        Statement st;

        gMonitores.tablaMonitores.setModel(model);

        String[] datos = new String[7];
        try {
            st = this.conexion.createStatement();

            ResultSet resultado = st.executeQuery(selectAllProducts);

            while (resultado.next()) {

                for (int i = 0; i < 7; i++) {
                    datos[i] = resultado.getString(i + 1);
                    System.out.println(datos[i]);
                }
                model.addRow(datos);
                numeroMonitores++;
                System.out.println("EL NUEVO NUMERO DE MONITORES ES: " + numeroMonitores);
            }

        } catch (SQLException e) {
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
        System.out.println(numeroMonitores);
        numeroMonitores++;
        String codigo = numeroMonitores < 100 ? "M0" + numeroMonitores : "M" + numeroMonitores;
        String nie = nuevoMonitor.getDNI();

        try {
            String consulta;
            consulta = String.format(
                    "INSERT INTO MONITOR VALUES ('%s','%s','%s','%s','%s','%s','%s')",
                    nuevoMonitor.getCodigo().equals("") ? codigo : nuevoMonitor.getCodigo(),
                    nuevoMonitor.getName(),
                    nuevoMonitor.getDNI(),
                    nuevoMonitor.getTef(),
                    nuevoMonitor.getMail(),
                    nuevoMonitor.getEntrada(),
                    nuevoMonitor.getNick()
            );

            System.out.println(consulta);
            ps = this.conexion.prepareStatement(consulta);

            ps.execute();
        } catch (SQLException ex) {
                UpdateMonitor(nuevoMonitor, nuevoMonitor.getCodigo());
        }
        RefrescarPanelMonitores();
    }

    public void DeleteMonitor() {
        try {
            int selectedRow = this.gMonitores.tablaMonitores.getSelectedRow();

            String idMonitor = (String) this.gMonitores.tablaMonitores.getValueAt(selectedRow, 0);
            
            String consulta = String.format(
                    "DELETE FROM MONITOR WHERE MONITOR.CODMONITOR = '%s'",
                    idMonitor
            );
            
            numeroMonitores--;
            ps = this.conexion.prepareStatement(consulta);
            ps.execute();
            JOptionPane.showMessageDialog(null, "El monitor se ha borrado correctamente");
            RefrescarPanelMonitores();
        } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(gMonitores, "No se ha seleccionador ningun monitor para dar de ");
        }
    }

    public void UpdateMonitor(Monitor m, String codigo) throws SQLException {
        String consulta;
        consulta = String.format(
                "UPDATE MONITOR "
                        + "SET MONITOR.nombre = '%s', "
                        + "MONITOR.dni = '%s', "
                        + "MONITOR.telefono = '%s', "
                        + "MONITOR.correo = '%s', "
                        + "MONITOR.fechaEntrada = '%s', "
                        + "MONITOR.nick = '%s' "
                        + "WHERE MONITOR.codMonitor = '%s' ",
                m.getName(), 
                m.getDNI(),
                m.getTef(),
                m.getMail(), 
                m.getEntrada(),
                m.getNick(),
                codigo
        );

        System.out.println(consulta);
        ps = this.conexion.prepareStatement(consulta);
        ps.execute();
        RefrescarPanelMonitores();
    }

}
