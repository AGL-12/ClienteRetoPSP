package cliente;
import vista.VentanaChat;
import java.io.*;
import javax.swing.SwingUtilities;

public class HiloEscucha implements Runnable {
    private Cliente cliente;
    private VentanaChat ventana;
    private volatile boolean running = true;

    public HiloEscucha(Cliente cliente, VentanaChat ventana) {
        this.cliente = cliente;
        this.ventana = ventana;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = cliente.getReader();
            String linea;
            while (running && (linea = in.readLine()) != null) {
                final String l = linea;
                // actualizar la GUI con SwingUtilities.invokeLater
                SwingUtilities.invokeLater(() -> processLine(l));
            }
        } catch (IOException e) {
            // conexión cerrada
            SwingUtilities.invokeLater(() -> ventana.appendSistema("Conexión perdida."));
        }
    }

    private void processLine(String linea) {
        if (linea.startsWith("PUBLIC:")) {
            // PUBLIC:emisor:mensaje
            String rest = linea.substring("PUBLIC:".length());
            int idx = rest.indexOf(':');
            if (idx > 0) {
                String emisor = rest.substring(0, idx);
                String msg = rest.substring(idx + 1);
                ventana.appendPublico(emisor, msg);
            }
        } else if (linea.startsWith("PRIVATE_FROM:")) {
            // PRIVATE_FROM:emisor:mensaje
            String rest = linea.substring("PRIVATE_FROM:".length());
            int idx = rest.indexOf(':');
            if (idx > 0) {
                String emisor = rest.substring(0, idx);
                String msg = rest.substring(idx + 1);
                ventana.appendPrivadoRecibido(emisor, msg);
            }
        } else if (linea.startsWith("PRIVATE_TO:")) {
            // PRIVATE_TO:destino:mensaje (confirmación)
            String rest = linea.substring("PRIVATE_TO:".length());
            int idx = rest.indexOf(':');
            if (idx > 0) {
                String destino = rest.substring(0, idx);
                String msg = rest.substring(idx + 1);
                ventana.appendPrivadoEnviado(destino, msg);
            }
        } else if (linea.startsWith("SISTEMA:")) {
            ventana.appendSistema(linea.substring("SISTEMA:".length()));
        } else if (linea.startsWith("USERLIST:")) {
            String rest = linea.substring("USERLIST:".length());
            ventana.actualizarUsuarios(rest);
        } else if (linea.startsWith("ERROR:")) {
            ventana.appendSistema("ERROR: " + linea.substring("ERROR:".length()));
        } else {
            ventana.appendSistema("RAW: " + linea);
        }
    }
}