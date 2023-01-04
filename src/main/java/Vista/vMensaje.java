/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author nekro
 */
import java.awt.TrayIcon;
import javax.swing.JOptionPane;

public class vMensaje {

    public void show(int type, String msg) {
        switch (type) {
            case -1:
                JOptionPane.showMessageDialog(null, msg, "ERROR", TrayIcon.MessageType.ERROR.ordinal());
                break;
            case 1: {
                JOptionPane.showMessageDialog(null, msg, "Información", TrayIcon.MessageType.INFO.ordinal());

            }
            break;
            default:
                JOptionPane.showMessageDialog(null, msg, "WARNING", TrayIcon.MessageType.INFO.ordinal());
        }

    }

    public int confirmWarning(String msg) {
        return JOptionPane.showConfirmDialog(null, msg, "WARNING", 0, 2);
    }
}
