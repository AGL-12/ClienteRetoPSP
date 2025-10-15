package cliente;

import java.io.*;
import java.net.*;

public class Cliente {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String usuario;
    private String servidorIP;
    private int puerto;

    public Cliente(String servidorIP, int puerto, String usuario) {
        this.servidorIP = servidorIP;
        this.puerto = puerto;
        this.usuario = usuario;
    }

    public boolean conectar() {
        try {
            socket = new Socket(servidorIP, puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // enviar CONNECT
            out.println("CONNECT:" + usuario);
            String respuesta = in.readLine();
            if (respuesta != null && respuesta.startsWith("OK:")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    public void desconectar() {
        try {
            if (out != null) out.println("DISCONNECT");
            if (socket != null) socket.close();
        } catch (IOException e) {
            // ignorar
        }
    }

    public void enviarPublico(String mensaje) {
        if (out != null) out.println("MSG_PUBLIC:" + mensaje);
    }

    public void enviarPrivado(String destino, String mensaje) {
        if (out != null) out.println("MSG_PRIVATE:" + destino + ":" + mensaje);
    }

    public BufferedReader getReader() {
        return in;
    }

    public String getUsuario() {
        return usuario;
    }

    public void solicitarUsuarios() {
        if (out != null) out.println("GET_USERS");
    }
}