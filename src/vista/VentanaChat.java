package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import cliente.Cliente;
import cliente.HiloEscucha;

public class VentanaChat extends JFrame {
    private JTextArea areaChat;
    private JTextField tfMensaje;
    private JTextField tfUsuario;
    private JTextField tfIP;
    private JTextField tfPuerto;
    private JButton btnConectar;
    private JButton btnDesconectar;
    private JButton btnEnviar;
    private JList<String> listaUsuarios;
    private DefaultListModel<String> modeloUsuarios;

    private Cliente cliente;
    private Thread hiloEscucha;
    private HiloEscucha escuchaRunnable;

    public VentanaChat() {
        setTitle("Cliente Chat - Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initComponents() {
        areaChat = new JTextArea();
        areaChat.setEditable(false);
        tfMensaje = new JTextField();
        tfUsuario = new JTextField("usuario1", 10);
        tfIP = new JTextField("127.0.0.1", 10);
        tfPuerto = new JTextField("5555", 6);
        btnConectar = new JButton("Conectar");
        btnDesconectar = new JButton("Desconectar");
        btnEnviar = new JButton("Enviar");
        modeloUsuarios = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloUsuarios);

        btnDesconectar.setEnabled(false);
        btnEnviar.setEnabled(false);
    }

    private void layoutComponents() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Usuario:"));
        top.add(tfUsuario);
        top.add(new JLabel("IP:"));
        top.add(tfIP);
        top.add(new JLabel("Puerto:"));
        top.add(tfPuerto);
        top.add(btnConectar);
        top.add(btnDesconectar);

        JPanel center = new JPanel(new BorderLayout());
        center.add(new JScrollPane(areaChat), BorderLayout.CENTER);

        JPanel right = new JPanel(new BorderLayout());
        right.setPreferredSize(new Dimension(180, 0));
        right.add(new JLabel("Usuarios conectados"), BorderLayout.NORTH);
        right.add(new JScrollPane(listaUsuarios), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(tfMensaje, BorderLayout.CENTER);
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btns.add(btnEnviar);
        bottom.add(btns, BorderLayout.EAST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(center, BorderLayout.CENTER);
        getContentPane().add(right, BorderLayout.EAST);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }

    private void addListeners() {
        btnConectar.addActionListener(e -> conectar());
        btnDesconectar.addActionListener(e -> desconectar());
        btnEnviar.addActionListener(e -> enviarMensaje());
        tfMensaje.addActionListener(e -> enviarMensaje());

        // doble clic en usuario para prefijar privado
        listaUsuarios.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String sel = listaUsuarios.getSelectedValue();
                    if (sel != null && !sel.equals(tfUsuario.getText())) {
                        tfMensaje.setText("/privado " + sel + " ");
                        tfMensaje.requestFocus();
                    }
                }
            }
        });
    }

    private void conectar() {
        String usuario = tfUsuario.getText().trim();
        String ip = tfIP.getText().trim();
        int puerto;

        try {
            puerto = Integer.parseInt(tfPuerto.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Puerto inválido");
            return;
        }

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduce un usuario");
            return;
        }

        cliente = new Cliente(ip, puerto, usuario);
        appendSistema("Intentando conectar...");

        boolean ok = cliente.conectar();
        if (!ok) {
            appendSistema("No se pudo conectar al servidor.");
            return;
        }

        appendSistema("Conectado como " + usuario);
        btnConectar.setEnabled(false);
        btnDesconectar.setEnabled(true);
        btnEnviar.setEnabled(true);
        tfUsuario.setEnabled(false);

        escuchaRunnable = new HiloEscucha(cliente, this);
        hiloEscucha = new Thread(escuchaRunnable);
        hiloEscucha.start();

        cliente.solicitarUsuarios();
    }

    private void desconectar() {
        if (cliente != null) cliente.desconectar();
        if (escuchaRunnable != null) escuchaRunnable.stop();
        if (hiloEscucha != null) hiloEscucha.interrupt();
        appendSistema("Desconectado.");
        btnConectar.setEnabled(true);
        btnDesconectar.setEnabled(false);
        btnEnviar.setEnabled(false);
        tfUsuario.setEnabled(true);
        modeloUsuarios.clear();
    }

    private void enviarMensaje() {
        String texto = tfMensaje.getText().trim();
        if (texto.isEmpty()) return;

        // Comando privado: /privado usuario mensaje
        if (texto.startsWith("/privado ")) {
            String rest = texto.substring("/privado ".length()).trim();
            int idx = rest.indexOf(' ');
            if (idx <= 0) {
                appendSistema("Formato privado: /privado <usuario> <mensaje>");
            } else {
                String destino = rest.substring(0, idx);
                String msg = rest.substring(idx + 1);
                cliente.enviarPrivado(destino, msg);
                // Los privados sí se muestran localmente
                appendPrivadoEnviado(destino, msg);
            }
        } else {
            // Mensajes públicos: el servidor lo reenvía también al emisor.
            // ❌ No lo mostramos aquí para evitar duplicado.
            cliente.enviarPublico(texto);
        }

        tfMensaje.setText("");
    }

    // --- Métodos de actualización de interfaz ---

    public void appendPublico(String emisor, String mensaje) {
        areaChat.append("[PÚBLICO] " + emisor + ": " + mensaje + "\n");
    }

    public void appendPrivadoRecibido(String emisor, String mensaje) {
        areaChat.append("[PRIVADO - RECIBIDO] " + emisor + ": " + mensaje + "\n");
    }

    public void appendPrivadoEnviado(String destino, String mensaje) {
        areaChat.append("[PRIVADO - ENVIADO] a " + destino + ": " + mensaje + "\n");
    }

    public void appendSistema(String texto) {
        areaChat.append("[SISTEMA] " + texto + "\n");
        // Si detectamos desconexión, reactivar controles
        if (texto.toLowerCase().contains("desconect")) {
            btnConectar.setEnabled(true);
            btnDesconectar.setEnabled(false);
            btnEnviar.setEnabled(false);
            tfUsuario.setEnabled(true);
        }
    }

    public void actualizarUsuarios(String commaSeparated) {
        modeloUsuarios.clear();
        if (commaSeparated == null || commaSeparated.trim().isEmpty()) return;
        String[] parts = commaSeparated.split(",");
        for (String p : parts) {
            if (!p.trim().isEmpty()) modeloUsuarios.addElement(p.trim());
        }
    }
}
