package org.ciberfarma.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.ciberfarma.modelo.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class FrmCrudUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextArea txtSalida;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmCrudUsuario frame = new FrmCrudUsuario();
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
	public FrmCrudUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btmRegistrar = new JButton("Registrar");
		btmRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		btmRegistrar.setBounds(367, 11, 89, 23);
		contentPane.add(btmRegistrar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(367, 45, 89, 23);
		contentPane.add(btnBuscar);
		
		JLabel lblNewLabel = new JLabel("C\u00F3digo:");
		lblNewLabel.setBounds(10, 15, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(66, 12, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(10, 49, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(66, 46, 86, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Apellido:");
		lblNewLabel_2.setBounds(10, 84, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(66, 81, 86, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 205, 444, 147);
		contentPane.add(scrollPane);
		
		txtSalida = new JTextArea();
		scrollPane.setViewportView(txtSalida);
		
		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(367, 170, 89, 23);
		contentPane.add(btnListado);
	}
	
	void registrar() {
		String nombre = leerNombre();
	}

	void listado() {
		//Obtener un listado del contenido
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("dawi_sesion04");
		EntityManager em = fabrica.createEntityManager();
		
		TypedQuery<Usuario> consulta = em.createNamedQuery("Usuario.findAllWithType", Usuario.class);
		consulta.setParameter("xtipo", 1);
		List<Usuario> lstUsuarios = consulta.getResultList();
		
		em.close();
		
		//Pasar el listado a txt, etc.
		for(Usuario u : lstUsuarios){
			txtSalida.append(u.getCodigo() + "\t" + u.getNombre() + "\t" + u.getApellido() + "\n");
		}
	}

	void buscar() {
		//Leer codigo
		int codigo = leerCodigo();
		
		//Buscar en la tabla para obtener un usuario
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("dawi_sesion04");
		EntityManager em = fabrica.createEntityManager();
		
		/*Se le pasa al metodo find la Entity Class Usuario, la cual ya tiene determinada la tabla "tb_usuarios"
		(Pasar cursor)
		La segunda parte del método funciona como un "select * from", el cual buscará mediante la PK con el valor de
		"codigo"*/
		Usuario u = em.find(Usuario.class, codigo);
		em.close();
		
		//Si existe muestra la info en los campos, sino manda msg error
		if(u == null) {
			aviso("No existe un usuario con el código dado.");
		}
		else {
			txtNombre.setText(u.getNombre());
			txtApellido.setText(u.getApellido());
			
		}
		
	}

	private void aviso(String msg) {
		// JOptionPane.showMessageDialog(Ventana a mandar msg, msg, Titulo de msg, Tipo de msg);
		JOptionPane.showMessageDialog(this, msg, "Aviso del sistema", JOptionPane.ERROR_MESSAGE);
		
	}

	private int leerCodigo() {
		return Integer.parseInt(txtCodigo.getText());
	}
	
	private String leerNombre() {
		return txtNombre.getText();
	}
}
