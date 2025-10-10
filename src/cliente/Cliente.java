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

public class Cliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
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
	public Cliente() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(900,600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(1, 1));
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel ipLbl = new JLabel("IP:");
		ipLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(ipLbl);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel puertoLbl = new JLabel("Puerto:");
		puertoLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(puertoLbl);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel usuarioLbl = new JLabel("Usuario: ");
		usuarioLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(usuarioLbl);
		
		textField_4 = new JTextField();
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		JButton btnNewButton = new JButton("Conectar");
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Desconectar");
		panel.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("No Conectado");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblNewLabel_2);
		
		JTextArea textArea = new JTextArea();
		getContentPane().add(textArea, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		panel_1.add(chckbxNewCheckBox);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		panel_1.add(lblNewLabel_3);
		
		textField_2 = new JTextField();
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("New button");
		panel_1.add(btnNewButton_2);
	}

}
