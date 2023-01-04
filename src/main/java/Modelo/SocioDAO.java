/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VentanaSocio;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author nekro
 */
public class SocioDAO {

    private final Session sesion;
    private final VentanaSocio gSocio;
    private DefaultTableModel model;
    private PreparedStatement ps;

    int numeroSocio;

    public SocioDAO(Session con, VentanaSocio gSocio) {
        this.sesion = con;
        this.gSocio = gSocio;
    }

    public void RefrescarPanelSocios() {
        ArrayList<Socio> listaSocios;
        Transaction transaction = sesion.beginTransaction();
        Query consulta = sesion.createQuery("SELECT s FROM Socio s", Socio.class);

        listaSocios = (ArrayList<Socio>) consulta.getResultList();

        transaction.commit();

        model = new DefaultTableModel();
        numeroSocio = listaSocios.size();
        RellenarColumnas();

        gSocio.tablaMonitores.setModel(model);

        String[] datos = new String[8];
        for (Socio s : listaSocios) {
            if (s != null) {
                datos[0] = s.getNumerosocio();
                datos[1] = s.getNombre();
                datos[2] = s.getDni();
                datos[3] = s.getFechanacimiento();
                datos[4] = s.getTelefono();
                datos[5] = s.getCorreo();
                datos[6] = s.getFechaentrada();
                datos[7] = String.valueOf(s.getCategoria());

                System.out.println(datos[0]);
                System.out.println(datos[1]);
                System.out.println(datos[2]);
                System.out.println(datos[3]);
                System.out.println(datos[4]);
                System.out.println(datos[5]);
                System.out.println(datos[6]);
                System.out.println(datos[7]);

                model.addRow(datos);
            }
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
        Transaction transaction = sesion.beginTransaction();
        sesion.save(nuevoSocio);
        transaction.commit();
        RefrescarPanelSocios();
    }

    public void DeleteSocio() {

        int selectedRow = this.gSocio.tablaMonitores.getSelectedRow();

        String idSocio = (String) this.gSocio.tablaMonitores.getValueAt(selectedRow, 0);

        Transaction transaction = sesion.beginTransaction();
        Socio monitor = sesion.get(Socio.class, idSocio);
        sesion.delete(monitor);
        transaction.commit();
    }

    public void UpdateSocio(Socio nuevoSocio, String codigo) throws SQLException {
        Transaction transaction = sesion.beginTransaction();
        sesion.save(nuevoSocio);
        transaction.commit();
        RefrescarPanelSocios();
        RefrescarPanelSocios();
    }

}
