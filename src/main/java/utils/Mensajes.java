package utils;

import java.awt.Component;

import javax.swing.*;

public class MensajeError {

    public static void mostrarAdvertencia(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(
            parent,
            mensaje,
            "Advertencia",
            JOptionPane.WARNING_MESSAGE
        );
    }

    public static void mostrarError(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(
            parent,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    public static void mostrarConfirmacion(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(
            parent,
            mensaje,
            "Confirmación",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
