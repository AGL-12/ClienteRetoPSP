package main;

import javax.swing.SwingUtilities;

import cliente.Cliente;
import vista.VentanaChat;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaChat().setVisible(true);
        });
    }
}
