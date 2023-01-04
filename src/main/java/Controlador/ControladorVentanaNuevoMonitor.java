/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.MonitorDAO;
import Modelo.Monitor;
import Vista.VentanaNewMonitor;
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
public class ControladorVentanaNuevoMonitor implements ActionListener {

    private final VentanaNewMonitor ventanaNuevoMonitor;
    private String codigo, DNI, entrada, mail, name, nick, tef;
    private final MonitorDAO gestorMonitor;
    private Monitor nuevoMonitor;

    public ControladorVentanaNuevoMonitor(MonitorDAO gestor) {
        gestorMonitor = gestor;
        ventanaNuevoMonitor = new VentanaNewMonitor();
        ventanaNuevoMonitor.setLocationRelativeTo(null);
        ventanaNuevoMonitor.setVisible(true);
        ventanaNuevoMonitor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addListeners();
    }

    public ControladorVentanaNuevoMonitor(MonitorDAO gestor, String[] m) {
        gestorMonitor = gestor;
        ventanaNuevoMonitor = new VentanaNewMonitor();
        ventanaNuevoMonitor.setLocationRelativeTo(null);
        ventanaNuevoMonitor.setVisible(true);
        ventanaNuevoMonitor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ventanaNuevoMonitor.code.setEditable(false);
        ventanaNuevoMonitor.code.setText(m[0]);
        ventanaNuevoMonitor.dni.setText(m[1]);
        ventanaNuevoMonitor.fecha.setDateFormatString(m[2]);
        ventanaNuevoMonitor.mail.setText(m[3]);
        ventanaNuevoMonitor.name.setText(m[4]);
        ventanaNuevoMonitor.nick.setText(m[5]);
        ventanaNuevoMonitor.tef.setText(m[6]);

        addListeners();
    }

    private void nuevoMonitor() {
        this.codigo = ventanaNuevoMonitor.code.getText();
        this.DNI = ventanaNuevoMonitor.dni.getText();
        SimpleDateFormat x = new SimpleDateFormat("dd/MM/yyyy");
        this.entrada = x.format(ventanaNuevoMonitor.fecha.getDate());
        this.mail = ventanaNuevoMonitor.mail.getText();
        this.name = ventanaNuevoMonitor.name.getText();
        this.nick = ventanaNuevoMonitor.nick.getText();
        this.tef = ventanaNuevoMonitor.tef.getText();
        nuevoMonitor = new Monitor(this.codigo, this.name, this.DNI, this.entrada);

        nuevoMonitor.setCorreo(mail);
        nuevoMonitor.setNick(nick);
        nuevoMonitor.setTelefono(tef);
    }

    private void addListeners() {
        ventanaNuevoMonitor.Insertar.addActionListener(this);
        ventanaNuevoMonitor.Cancelar.addActionListener(this);
        ventanaNuevoMonitor.fecha.setDateFormatString("dd/MM/yyyy");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae.getActionCommand());
        switch (ae.getActionCommand()) {
            case "Insertar": {
                try {
                    nuevoMonitor();
                    int opc;
                    opc = JOptionPane.showConfirmDialog(null, "Se va a insertar el monitor introducido, ¿Está seguro?", "Confirme para añadir el monitor", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (opc == JOptionPane.YES_OPTION) {
                        this.gestorMonitor.InsertMonitor(nuevoMonitor);
                        this.ventanaNuevoMonitor.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha añadido ningun monitor");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ControladorVentanaNuevoMonitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "Cancelar": {
                ventanaNuevoMonitor.dispose();
            }
            break;
            default:
                throw new AssertionError();
        }
    }

}
