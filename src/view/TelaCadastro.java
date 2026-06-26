package view;

import java.awt.EventQueue;
import java.awt.Image;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import dao.ClienteDAO;
import model.Arquivos;
import model.Cliente;
import model.ClienteTableModel;
import util.DadosMockados;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class TelaCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textEmail;
	private JTextField textTelefone;
	private JTextField textBuscar;
	private JTable table;
	private ClienteTableModel modelo;
	private ArrayList<Cliente> clientes;
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private ClienteDAO dao;
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastro frame = new TelaCadastro();
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
	public TelaCadastro() {
		dao = new ClienteDAO();
		clientes = new ArrayList<Cliente>();
		//DadosMockados.carregar(clientes);
		modelo = new ClienteTableModel(dao.listar());
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 737, 658);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon image = new ImageIcon(TelaCadastro.class.getResource("/img/cadastro.png"));
		Image imageScaled = image.getImage();
		Image novaImg = imageScaled.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
		lblNewLabel.setIcon(new ImageIcon(novaImg));
		lblNewLabel.setBounds(23, 35, 112, 92);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("CADASTRO DE CLIENTES");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1.setBounds(280, 63, 209, 24);
		contentPane.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(23, 127, 690, 158);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textNome = new JTextField();
		textNome.setBounds(12, 29, 339, 35);
		panel.add(textNome);
		textNome.setColumns(10);
		
		textEmail = new JTextField();
		textEmail.setBounds(12, 99, 339, 35);
		panel.add(textEmail);
		textEmail.setColumns(10);
		
		textTelefone = new JTextField();
		textTelefone.setBounds(393, 29, 285, 35);
		panel.add(textTelefone);
		textTelefone.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Nome");
		lblNewLabel_2.setBounds(12, 12, 60, 17);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("E-mail");
		lblNewLabel_3.setBounds(12, 80, 60, 17);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Telefone");
		lblNewLabel_4.setBounds(394, 12, 60, 17);
		panel.add(lblNewLabel_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(393, 99, 285, 35);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JRadioButton rdbtnMasculino = new JRadioButton("Masculino");
		rdbtnMasculino.setBackground(new Color(255, 255, 255));
		rdbtnMasculino.setBounds(0, 8, 130, 25);
		panel_2.add(rdbtnMasculino);
		
		JRadioButton rdbtnFeminino = new JRadioButton("Feminino");
		rdbtnFeminino.setBackground(new Color(255, 255, 255));
		rdbtnFeminino.setBounds(155, 8, 130, 25);
		panel_2.add(rdbtnFeminino);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnFeminino);
		buttonGroup.add(rdbtnMasculino);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(23, 299, 690, 77);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnSalvar = new JButton("Salvar");
		//ButtonAction action = new ButtonAction();
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String nome = textNome.getText().trim();
				String email = textEmail.getText().trim();
				String telefone = textTelefone.getText().trim();
				LocalDate hoje = LocalDate.now();
				
				if(!validar_campos(nome,telefone,email,rdbtnMasculino,rdbtnFeminino)) {
					return;
				}
				String sexo = rdbtnMasculino.isSelected() ? "Masculino" : "Feminino";
				
				Cliente cliente = new Cliente(nome, telefone, email, sexo,hoje);
				modelo.addCliente(cliente);					
				dao.inserir(cliente);
				
				JOptionPane.showMessageDialog(TelaCadastro.this, 
				"Cliente adicionado com sucesso!", "Sucesso!", 
				JOptionPane.INFORMATION_MESSAGE);
				
				limpar_campos(buttonGroup);
			}
			
		});
		btnSalvar.setBounds(23, 25, 105, 27);
		panel_1.add(btnSalvar);
		
		
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indice = table.getSelectedRow();
				
				if (indice < 0) { //indice nao valido
					JOptionPane.showMessageDialog(TelaCadastro.this, 
					"Selecione um cliente antes de continuar", "Alerta", 
					JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				Cliente cliente = modelo.getCliente(indice);
				System.out.println(cliente.getId());
				dao.excluir(cliente.getId());
				modelo.removerCliente(indice);
			}
		});
		
		btnExcluir.setBounds(153, 25, 105, 27);
		panel_1.add(btnExcluir);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String buscarNome = textBuscar.getText().toString();
				
				if (buscarNome.isBlank()) { //campo nao preechido
					JOptionPane.showMessageDialog(TelaCadastro.this, 
					"Preencha o campo da busca para buscar", "Alerta", 
					JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				int indice = modelo.buscarCliente(buscarNome);
				table.setRowSelectionInterval(indice, indice);
			}
		});
		btnBuscar.setBounds(278, 25, 105, 27);
		panel_1.add(btnBuscar);
		
		textBuscar = new JTextField();
		textBuscar.setBounds(409, 25, 269, 27);
		panel_1.add(textBuscar);
		textBuscar.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 395, 690, 196);
		contentPane.add(scrollPane);
		
		table = new JTable();
		
		table.setModel(modelo);
		scrollPane.setViewportView(table);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 737, 23);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Arquivo");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mnNewMenu.add(mntmAbrir);
		mntmAbrir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jFileChooser = new JFileChooser();
				if (jFileChooser.showOpenDialog(TelaCadastro.this) 
						== JFileChooser.APPROVE_OPTION) {
					
					File file = jFileChooser.getSelectedFile();
					Arquivos.carregarDados(file, modelo);
						
				}
				
			}
		});
		
		JMenuItem mntmSalvar = new JMenuItem("Salvar");
		mnNewMenu.add(mntmSalvar);
		mntmSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				if (jFileChooser.showSaveDialog(TelaCadastro.this) 
						== JFileChooser.APPROVE_OPTION) {
					File file = jFileChooser.getSelectedFile();
					Arquivos.salvarDados(file, modelo,TelaCadastro.this);
				}
				
			}
		});
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmSair);
		
		
		JMenu mnNewMenuEditar = new JMenu("Editar");
		menuBar.add(mnNewMenuEditar);
		
		JMenuItem mntmAtualizar = new JMenuItem("Atualizar");
		mntmAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int linha = table.getSelectedRow();
				if (linha < 0) { //indice nao valido
					JOptionPane.showMessageDialog(TelaCadastro.this, "Selecione um cliente para atualizar!", 
					"Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				Cliente cliente = modelo.getCliente(linha);
				TelaAtualizar dialogo = new TelaAtualizar(TelaCadastro.this, cliente);
				dialogo.setVisible(true);
				
				if (dialogo.getClienteEditado() != null) {
					dao.atualizar(dialogo.getClienteEditado());
					modelo.atualizarTabela(dao.listar());
				}
					
			}
		});
		mnNewMenuEditar.add(mntmAtualizar);
		
		JMenu mnNewMenu_2 = new JMenu("Ferramentas");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmValidar = new JMenuItem("Validar dados");
		mnNewMenu_2.add(mntmValidar);
		
		JMenuItem mntmExportar = new JMenuItem("Exportar relatório");
		mntmExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Arquivos.exportarRelatorio(modelo,TelaCadastro.this);
			}
		});
		mnNewMenu_2.add(mntmExportar);
		
		JMenuItem mntmCSV = new JMenuItem("Importar CSV validado");
		mntmCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Arquivos.importar_arquivo_csv(modelo,TelaCadastro.this,dao);
			}
		});
		mnNewMenu_2.add(mntmCSV);
		
		JMenu mnNewMenu_3 = new JMenu("Sobre");
		menuBar.add(mnNewMenu_3);
	}
	private void limpar_campos(ButtonGroup buttonGroup) {
		textNome.setText("");
		textTelefone.setText("");
		textEmail.setText("");
		buttonGroup.clearSelection();
	}
	
	private boolean validar_botao(JRadioButton F,JRadioButton M) {
		if(F.isSelected() || M.isSelected()) {
			return true;
		}
		return false;
	}
	
	private boolean validar_campos(String nome, String telefone, 
			String email,JRadioButton botao_mas,JRadioButton botao_fem) {
		
		Pattern compileNome = Pattern.compile("^[a-zA-ZÀ-ÿ ]+$");
		Pattern compileEmail = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
		Pattern compileTelefone = Pattern.compile("^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$");
		
		Matcher matchNome = compileNome.matcher(nome);
		Matcher matchEmail = compileEmail.matcher(email);
		Matcher matchTelefone = compileTelefone.matcher(telefone);
		
		if (nome.isBlank() || email.isBlank() || telefone.isBlank()
				|| !validar_botao(botao_mas,botao_fem)) {
			JOptionPane.showMessageDialog(TelaCadastro.this, 
					"Preencha todos os campos antes de salvar", "ERRO!", 
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(!matchNome.matches() || !matchEmail.matches() || !matchTelefone.matches()) {
			JOptionPane.showMessageDialog(TelaCadastro.this, 
					"Insira os campos corretamente", "ERRO!", 
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	
}