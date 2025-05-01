package utils;

import java.awt.Component;

import javax.swing.*;

public class Mensajes {

	public static void mostrarAdvertencia(Component parent, String mensaje) {
		JOptionPane.showMessageDialog(parent, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
	}

	public static void mostrarError(Component parent, String mensaje) {
		JOptionPane.showMessageDialog(parent, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void mostrarConfirmacion(Component parent, String mensaje) {
		JOptionPane.showMessageDialog(parent, mensaje, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
	}

	public static int mostrarSIoNO(Component parent, String mensaje) {
		return JOptionPane.showConfirmDialog(parent, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
	}
}
