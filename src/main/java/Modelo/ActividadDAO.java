/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VentanaActividad;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

/**
 *
 * @author nekro
 */
public class ActividadDAO {

    private final Session sesion;
    private final VentanaActividad vAct;
    private DefaultTableModel model;
    private Statement ps;

    int numeroInscritos;

    public ActividadDAO(Session sesion, VentanaActividad vAct) {
        this.sesion = sesion;
        this.vAct = vAct;

    }

    private String codAct(String item) {
        Transaction transaction = sesion.beginTransaction();
        NativeQuery consulta = sesion.createNativeQuery(String.format("SELECT A.IDACTIVIDAD FROM ACTIVIDAD A WHERE A.NOMBRE = '%s'",item));
        String codUltimoMonitor = consulta.getSingleResult().toString();
        transaction.commit();
        return codUltimoMonitor;
    }

    public void listaUsuarios(String code, String SGBD) throws SQLException {

        model = new DefaultTableModel();
        RellenarColumnas();
        Transaction transaction;
        this.vAct.TablaActividades.setModel(model);
        
        code = codAct(code);
        
        ArrayList<Object[]> resultado;
        if (SGBD.equals("mariadb")) {
            transaction = sesion.beginTransaction();
            StoredProcedureQuery llamada = sesion.createStoredProcedureCall("OBTENER_DATOS")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .setParameter(1, code);
            llamada.execute();
            resultado = (ArrayList<Object[]>) llamada.getResultList();
            transaction.commit();
        } else {
            transaction = sesion.beginTransaction();
            StoredProcedureQuery llamada = sesion.createStoredProcedureCall("OBTENER_DATOS")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
                    .setParameter(1, code);
            llamada.execute();
            resultado = (ArrayList<Object[]>) llamada.getResultList();
            transaction.commit();
        }

        numeroInscritos = resultado.size();
        System.out.println("resultado: " +resultado.size());
        for (int i = 0; i < resultado.size(); i++) {
            Object[] sub = resultado.get(i);
            model.addRow(sub);
        }

    }

    private void RellenarColumnas() {
        model.addColumn("Nombre");
        model.addColumn("Correo");
    }

}
