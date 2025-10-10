package cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class VistaCliente extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField tfIp;
	private JTextField tfPuerto;
	private JTextField tfDestinatario;
	private JTextField tfChatUsuario;
	private JTextField tfUsuario;
	private JButton btnConectar;
	private JButton btnDesconectar;
	private JLabel estadoLbl;
	private JTextArea txtaChat;
	private JCheckBox chckbxPrivado;
	private JButton btnEnviar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaCliente frame = new VistaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VistaCliente() {
		setSize(900, 600); // ancho x alto
		setLocationRelativeTo(null); // centrarlo en la pantalla
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelHearder = new JPanel();
		getContentPane().add(panelHearder, BorderLayout.NORTH);
		panelHearder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel ipLbl = new JLabel("IP:");
		ipLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelHearder.add(ipLbl);

		tfIp = new JTextField();
		panelHearder.add(tfIp);
		tfIp.setColumns(10);

		JLabel puertoLbl = new JLabel("Puerto:");
		puertoLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelHearder.add(puertoLbl);

		tfPuerto = new JTextField();
		panelHearder.add(tfPuerto);
		tfPuerto.setColumns(10);

		JLabel usuarioLbl = new JLabel("Usuario: ");
		usuarioLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelHearder.add(usuarioLbl);

		tfUsuario = new JTextField();
		panelHearder.add(tfUsuario);
		tfUsuario.setColumns(10);

		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(this);
		panelHearder.add(btnConectar);

		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.setEnabled(false);
		btnDesconectar.addActionListener(this);
		panelHearder.add(btnDesconectar);

		estadoLbl = new JLabel("No Conectado");
		estadoLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelHearder.add(estadoLbl);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		txtaChat = new JTextArea();
		scrollPane.setViewportView(txtaChat);

		JPanel panelFooter = new JPanel();
		getContentPane().add(panelFooter, BorderLayout.SOUTH);

		chckbxPrivado = new JCheckBox("Privado");
		chckbxPrivado.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelFooter.add(chckbxPrivado);

		JLabel paraLbl = new JLabel("Para: ");
		paraLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelFooter.add(paraLbl);

		tfDestinatario = new JTextField();
		panelFooter.add(tfDestinatario);
		tfDestinatario.setColumns(10);

		tfChatUsuario = new JTextField();
		panelFooter.add(tfChatUsuario);
		tfChatUsuario.setColumns(10);

		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(this);
		btnEnviar.setEnabled(false);
		panelFooter.add(btnEnviar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(btnConectar)) {

		} else if (src.equals(btnDesconectar)) {

		}else if (src.equals(btnEnviar)) {
			
		}

	}

}
