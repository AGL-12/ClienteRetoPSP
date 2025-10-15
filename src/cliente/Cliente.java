package cliente;

import java.io.*;
import java.net.*;

public class Cliente {

	private String ip;
	private int puerto;
	private String usuario;
	private Socket socket;
	private ObjectOutputStream salida;
	private ObjectInputStream entrada;
	private VistaCliente vista;

	public Cliente(String ip, int puerto, String usuario, VistaCliente vista) {
		this.ip = ip;
		this.puerto = puerto;
		this.usuario = usuario;
		this.vista = vista;
	}

	public String getIp() {
		return ip;
	}

	public int getPuerto() {
		return puerto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

	public void setSalida(ObjectOutputStream salida) {
		this.salida = salida;
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(ObjectInputStream entrada) {
		this.entrada = entrada;
	}

	public void conectar() {
		try {
			socket = new Socket(ip, puerto);
			entrada = new ObjectInputStream(socket.getInputStream());
			salida = new ObjectOutputStream(socket.getOutputStream());

			// Enviar nombre de usuario
			salida.writeObject(usuario);

			// Esperar respuesta del servidor
			String respuesta = (String) entrada.readObject();
			if (respuesta.contains("ya está en uso")) {
				vista.actualizarEstado("El nombre ya está en uso.");
				return;
			}

			// Si todo está bien
			vista.actualizarEstado("Conectado como: " + usuario);
			new Thread(new RecibirMensajes()).start(); // Iniciar hilo de recepción de mensajes

		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
			vista.actualizarEstado("Error de conexión.");
		}
	}

	public void desconectar() {
		try {
			salida.writeObject("salir");
			socket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void enviarMensaje(String mensaje, String destinatario) {
		try {
			Mensaje msg = new Mensaje(mensaje, usuario, destinatario);
			salida.writeObject(msg);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private class RecibirMensajes implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					Object obj = entrada.readObject();
					if (obj instanceof String) {
						vista.actualizarChat((String) obj);
					} else if (obj instanceof Mensaje) {
						Mensaje mensaje = (Mensaje) obj;
						vista.actualizarChat(mensaje.getEmisor() + ": " + mensaje.getContenido());
					}
				}
			} catch (IOException | ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
	}
}
