/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Socio;
import Modelo.SocioDAO;
import Vista.VentanaNewSocio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author nekro
 */
public class ControladorVentanaNuevoSocio implements ActionListener {

    private final VentanaNewSocio ventanaNuevoSocio;
    private String nSocio, DNI, entrada, mail, name, categoria, tef, fNacimiento;
    private final SocioDAO gestorSocio;
    private Socio nuevoSocio;

    public ControladorVentanaNuevoSocio(SocioDAO gestor) {
        gestorSocio = gestor;
        ventanaNuevoSocio = new VentanaNewSocio();
        ventanaNuevoSocio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaNuevoSocio.setLocationRelativeTo(null);
        ventanaNuevoSocio.setVisible(true);
        addListeners();
    }

    public ControladorVentanaNuevoSocio(SocioDAO gestor, Socio m) {
        gestorSocio = gestor;
        ventanaNuevoSocio = new VentanaNewSocio();
        ventanaNuevoSocio.setLocationRelativeTo(null);
        ventanaNuevoSocio.setVisible(true);

        ventanaNuevoSocio.code.setEditable(false);
        ventanaNuevoSocio.code.setText(m.getCodigo());
        ventanaNuevoSocio.dni.setText(m.getDNI());
        ventanaNuevoSocio.fechaNac.setDateFormatString(m.getEntrada());
        ventanaNuevoSocio.mail.setText(m.getMail());
        ventanaNuevoSocio.name.setText(m.getName());
        ventanaNuevoSocio.categoria.setText(m.getCategoria());
        ventanaNuevoSocio.tef.setText(m.getTef());

        ventanaNuevoSocio.Insertar.setName("Hola");

        addListeners();
    }

    private void nuevoSocio() {
        this.nSocio = ventanaNuevoSocio.code.getText();
        this.DNI = ventanaNuevoSocio.dni.getText();
        SimpleDateFormat x = new SimpleDateFormat("dd/MM/yyyy");

        this.entrada = x.format(ventanaNuevoSocio.fecha.getDate());
        this.mail = ventanaNuevoSocio.mail.getText();
        this.name = ventanaNuevoSocio.name.getText();
        this.categoria = ventanaNuevoSocio.categoria.getText();
        this.tef = ventanaNuevoSocio.tef.getText();
        SimpleDateFormat y = new SimpleDateFormat("dd/MM/yyyy");

        this.fNacimiento = y.format(ventanaNuevoSocio.fechaNac.getDate());

        nuevoSocio = new Socio(this.nSocio, this.name, this.DNI, this.fNacimiento, this.tef, this.mail, this.entrada, this.categoria);
    }

    private void addListeners() {
        ventanaNuevoSocio.Insertar.addActionListener(this);
        ventanaNuevoSocio.Cancelar.addActionListener(this);
        ventanaNuevoSocio.fechaNac.setDateFormatString("dd/MM/yyyy");
        ventanaNuevoSocio.fecha.setDateFormatString("dd/MM/yyyy");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae.getActionCommand());
        switch (ae.getActionCommand()) {
            case "Insertar": {
                nuevoSocio();
                int opc;
                opc = JOptionPane.showConfirmDialog(null, "Se va a insertar el Socio introducido, ¿Está seguro?", "Confirme para añadir el Socio", JOptionPane.YES_NO_CANCEL_OPTION);
                if (opc == JOptionPane.YES_OPTION) {
                    try {
                        this.gestorSocio.InsertSocio(nuevoSocio);
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorVentanaNuevoSocio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.ventanaNuevoSocio.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha añadido ningun Socio");
                }
            }
            break;

            case "Cancelar": {
                ventanaNuevoSocio.dispose();
            }
            break;
            default:
                throw new AssertionError();
        }
    }

}
