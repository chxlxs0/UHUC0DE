/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VentanaSocio;
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
public class SocioDAO {

    private final Connection conexion;
    private final VentanaSocio gSocio;
    private DefaultTableModel model;
    private PreparedStatement ps;

    int numeroSocio;

    public SocioDAO(Conexion con, VentanaSocio gMonitores) {
        this.conexion = con.getConexion();
        this.gSocio = gMonitores;
    }

    public void RefrescarPanelSocios() {
        model = new DefaultTableModel();
        numeroSocio = 0;

        RellenarColumnas();

        String selectAllProducts = "SELECT * FROM SOCIO";

        Statement st;

        gSocio.tablaMonitores.setModel(model);

        String[] datos = new String[8];
        try {
            st = this.conexion.createStatement();

            ResultSet resultado = st.executeQuery(selectAllProducts);

            while (resultado.next()) {

                for (int i = 0; i < 8; i++) {
                    datos[i] = resultado.getString(i + 1);
                    System.out.println(datos[i]);
                }
                model.addRow(datos);
                numeroSocio++;
                System.out.println("EL NUEVO NUMERO DE SOCIO ES: " + numeroSocio);
            }

        } catch (SQLException e) {
        }
    }

    private void RellenarColumnas() {
        model.addColumn("Nº Socio");
        model.addColumn("Nombre");
        model.addColumn("DNI");
        model.addColumn("Nacimiento");
        model.addColumn("Telefono");
        model.addColumn("Correo");
        model.addColumn("Entrada");
        model.addColumn("Categoria");

    }

    public void InsertSocio(Socio nuevoSocio) throws SQLException {
        System.out.println(numeroSocio);
        numeroSocio++;
        String codigo = numeroSocio < 100 ? "S0" + numeroSocio : "S" + numeroSocio;
        String nie = nuevoSocio.getDNI();

        try {
            String consulta;
            consulta = String.format(
                    "INSERT INTO SOCIO VALUES ('%s','%s','%s','%s','%s','%s','%s','%s')",
                    nuevoSocio.getCodigo().equals("") ? codigo : nuevoSocio.getCodigo(),
                    nuevoSocio.getName(),
                    nuevoSocio.getDNI(),
                    nuevoSocio.getNac(),
                    nuevoSocio.getTef(),
                    nuevoSocio.getMail(),
                    nuevoSocio.getEntrada(),
                    nuevoSocio.getCategoria()
            );

            System.out.println(consulta);
            ps = this.conexion.prepareStatement(consulta);

            ps.execute();
        } catch (SQLException ex) {
            UpdateSocio(nuevoSocio, nuevoSocio.getCodigo());
        }
        RefrescarPanelSocios();
    }

    public void DeleteSocio() {
        try {
            int selectedRow = this.gSocio.tablaMonitores.getSelectedRow();

            String idSocio = (String) this.gSocio.tablaMonitores.getValueAt(selectedRow, 0); //tablaMonitores en gSocio == tablaSocios

            String consulta = String.format(
                    "DELETE FROM SOCIO WHERE SOCIO.numeroSocio = '%s'",
                    idSocio
            );

            numeroSocio--;
            ps = this.conexion.prepareStatement(consulta);
            ps.execute();
            JOptionPane.showMessageDialog(null, "El socio se ha borrado correctamente");
            RefrescarPanelSocios();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(gSocio, "No se ha seleccionador ningun socio para dar de baja");
        }
    }

    public void UpdateSocio(Socio nuevoSocio, String codigo) throws SQLException {
        String consulta;
        consulta = String.format(
                "UPDATE SOCIO "
                + "SET SOCIO.nombre = '%s', "
                + "SOCIO.dni = '%s', "
                + "SOCIO.fechaNacimiento = '%s', "
                + "SOCIO.telefono = '%s', "
                + "SOCIO.correo = '%s', "
                + "SOCIO.fechaEntrada = '%s', "
                + "SOCIO.categoria = '%s' "
                + "WHERE SOCIO.numeroSocio = '%s'",
                nuevoSocio.getName(),
                nuevoSocio.getDNI(),
                nuevoSocio.getNac(),
                nuevoSocio.getTef(),
                nuevoSocio.getMail(),
                nuevoSocio.getEntrada(),
                nuevoSocio.getCategoria(),
                codigo
        );

        System.out.println(consulta);
        ps = this.conexion.prepareStatement(consulta);
        ps.execute();
        RefrescarPanelSocios();
    }

}
