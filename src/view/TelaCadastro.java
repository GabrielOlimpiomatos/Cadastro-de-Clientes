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
				Pattern compileNome = Pattern.compile("^[A-Za-z`A-¨y ]+$");
				Pattern compileEmail = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
				Pattern compileTelefone = Pattern.compile("^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$");
				
				Matcher matchNome;
				Matcher matchEmail;
				Matcher matchTelefone;
				
				String nome = textNome.getText().toString();
				String email = textEmail.getText().toString();
				String telefone = textTelefone.getText().toString();
				String sexo = rdbtnMasculino.isSelected() ? "Masculino" : "Feminino";
				LocalDate hoje = LocalDate.now();
				

				matchNome = compileNome.matcher(nome);
				matchEmail = compileEmail.matcher(email);
				matchTelefone = compileTelefone.matcher(telefone);
				
				if (nome.isBlank() || email.isBlank() || telefone.isBlank() 
						|| sexo.isBlank()) {
					JOptionPane.showMessageDialog(TelaCadastro.this, 
							"Preencha todos os campos", "Alerta", 
							JOptionPane.WARNING_MESSAGE);
				}
				else if(!matchNome.matches()) {
					JOptionPane.showMessageDialog(TelaCadastro.this, "Formato de nome inválido", "Alerta"
							,JOptionPane.WARNING_MESSAGE);
				}else if(!matchEmail.matches()) {
					JOptionPane.showMessageDialog(TelaCadastro.this, "Formato de email inválido", "Alerta"
							,JOptionPane.WARNING_MESSAGE);
				}else if(!matchTelefone.matches()) {
					JOptionPane.showMessageDialog(TelaCadastro.this, "Formato de telefone inválido", "Alerta"
							,JOptionPane.WARNING_MESSAGE);
				}
				else {
					Cliente cliente = new Cliente(nome, telefone, email, sexo,hoje);
					modelo.addCliente(cliente);					
					dao.inserir(cliente);
					JOptionPane.showMessageDialog(TelaCadastro.this, 
							"Cliente adicionado com sucesso!", "Sucesso!", 
							JOptionPane.INFORMATION_MESSAGE);
				}
				textNome.setText("");
				textTelefone.setText("");
				textEmail.setText("");
				buttonGroup.clearSelection();
				
			}
			
		});
		btnSalvar.setBounds(23, 25, 105, 27);
		panel_1.add(btnSalvar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indice = table.getSelectedRow();
				if (indice >= 0) {
					Cliente cliente = modelo.getCliente(indice);
					System.out.println(cliente.getId());
					dao.excluir(cliente.getId());
					modelo.removerCliente(indice);
				}else {
					JOptionPane.showMessageDialog(TelaCadastro.this, 
							"Selecione um cliente antes de continuar", "Alerta", 
							JOptionPane.WARNING_MESSAGE);
				}
				
				
			}
		});
		btnExcluir.setBounds(153, 25, 105, 27);
		panel_1.add(btnExcluir);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String buscarNome = textBuscar.getText().toString();
				if (!buscarNome.isBlank()) {
					int indice = modelo.buscarCliente(buscarNome);
					table.setRowSelectionInterval(indice, indice);
				}
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
					carregarDados(file, modelo);
						
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
					salvarDados(file, modelo);
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
				if (linha < 0) {
					JOptionPane.showMessageDialog(TelaCadastro.this, "Selecione um cliente para atualizar!", 
							"Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}else {
					Cliente cliente = modelo.getCliente(linha);
					TelaAtualizar dialogo = new TelaAtualizar(TelaCadastro.this, cliente);
					dialogo.setVisible(true);
					if (dialogo.getClienteEditado() != null) {
						dao.atualizar(dialogo.getClienteEditado());
						modelo.atualizarTabela(dao.listar());
					}
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
				exportarRelatorio(modelo);
			}
		});
		mnNewMenu_2.add(mntmExportar);
		
		JMenuItem mntmCSV = new JMenuItem("Importar CSV validado");
		mntmCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				String caminhoCompleto;
		        int resultado = fileChooser.showOpenDialog(null);
		        if (resultado == JFileChooser.APPROVE_OPTION) {
		            File arquivoSelecionado = fileChooser.getSelectedFile();
		            caminhoCompleto = arquivoSelecionado.getAbsolutePath();
		            
		        }else {
		        	JOptionPane.showMessageDialog(TelaCadastro.this, "Nao foi possivel obter o endereco");
		        	return;
		        }
		        int padraoAceitado=0;
		        int padraoRejeitado=0;
		        try(BufferedReader read = new BufferedReader(new FileReader(caminhoCompleto))){
		        	read.readLine();
		        	String linha;
		        	ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		        	while((linha = read.readLine()) != null) {
		        		String partes[] = linha.split(",");
		        		if(partes.length != 4) {
		        			JOptionPane.showMessageDialog(TelaCadastro.this, "linha csv fora do padrao");
		        			padraoRejeitado++;
		        		}else {
		        			String nome = partes[0];
				        	String telefone = partes[1];
				        	String email = partes[2];
				        	String sexo = partes[3];
				        	LocalDate hoje = LocalDate.now();
				        	Cliente cliente = new Cliente(
				        			nome,
				        			telefone,
				        			email,
				        			sexo,
				        			hoje
				        	);	
				        	modelo.addCliente(cliente);
				        	dao.inserir(cliente);
				        	padraoAceitado++;
		        		}
		        	}
		        }catch(IOException file) {
		        	JOptionPane.showMessageDialog(TelaCadastro.this, "Nao foi possivel ler o arquivo");
		        }
		        System.out.println("Importado com sucesso");
		        System.out.println("Registros importados: " + padraoAceitado);
		        System.out.println("Registros rejeitados: " + padraoRejeitado);
			}
		});
		mnNewMenu_2.add(mntmCSV);
		
		JMenu mnNewMenu_3 = new JMenu("Sobre");
		menuBar.add(mnNewMenu_3);
	}
	private void exportarRelatorio(ClienteTableModel modelo) {
		String caminhoCompleto = "relatorio.txt";
		try(BufferedWriter arq = new BufferedWriter(new FileWriter(caminhoCompleto))){
			LocalDate hoje = LocalDate.now();
			DateTimeFormatter formato =
					DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String dataFormatada = hoje.format(formato);
			
			arq.write("RELATORIO DE CLIENTES");
			arq.newLine();
			arq.write("Total de clientes: "+modelo.getNumCliente());
			arq.newLine();
			arq.write("Masculino: " +modelo.getNumHomens());
			arq.newLine();
			arq.write("Feminio: "+ modelo.getNumMulheres());
			arq.newLine();
			arq.newLine();
			ArrayList<Cliente> clientes = modelo.getLista();
			
			for(Cliente cliente : clientes) {
				arq.write("------------------------------------");
				arq.write("\nNome: " + cliente.getNome()+ "\n");
				arq.write("Telefone: "+ cliente.getTelefone()+ "\n");
				arq.write("Email: " + cliente.getEmail()+ "\n");
				arq.write("Sexo: " + cliente.getSexo());
				arq.write("Data de cadastro: "+ dataFormatada + "\n");
			}
			arq.write("------------------------------------");
			JOptionPane.showMessageDialog(null, "arquivo relatorio criado com sucesso");
		}catch(IOException e) {
				e.printStackTrace();
		}
}
	
	
	private void carregarDados(File file, ClienteTableModel modelo) {
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			modelo.limparDados();
			bufferedReader.readLine();
			String linha = "";
			while((linha = bufferedReader.readLine()) != null) {
				String campos [] = linha.split(",");
				if (campos.length == 4) {
					String nome = campos[0];
					String telefone = campos[1];
					String email = campos[2];
					String sexo = campos[3];
					String data = campos[4];
					LocalDate dataLocal = LocalDate.parse(data);
					Cliente cliente = new Cliente(nome, telefone, email, sexo,dataLocal);
					modelo.addCliente(cliente);
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bufferedReader.close();
				fileReader.close();				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}		
		
	}
	
	
	
	private void salvarDados(File file, ClienteTableModel modelo) {
		try {
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("Nome,Telefone,Email,Sexo");
			bufferedWriter.newLine();
			for (int i=0; i < modelo.getRowCount(); i++) {
				String nome = (String) modelo.getValueAt(i, 0);
				String telefone = (String) modelo.getValueAt(i, 1);
				String email = (String) modelo.getValueAt(i, 2);
				String sexo = (String) modelo.getValueAt(i, 3);
				bufferedWriter.write(nome+","+telefone+","+
				","+email+","+sexo);
				bufferedWriter.newLine();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {				
				bufferedWriter.close();
				fileWriter.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}