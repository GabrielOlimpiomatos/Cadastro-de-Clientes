package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Cliente;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class TelaAtualizar extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JTextField textFieldTelefone;
	private JTextField textFieldEmail;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFeminino;
	private Cliente clienteEditado;
	private Cliente clienteOriginal;

	public TelaAtualizar(JFrame parent, Cliente cliente) {
		super(parent, "Atualizar cliente", true);
		this.clienteOriginal = cliente;		
		setBounds(100, 100, 461, 231);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(4, 2, 5, 5));
		{
			JLabel lblNewLabel = new JLabel("Nome");
			contentPanel.add(lblNewLabel);
		}
		{
			textFieldNome = new JTextField();
			contentPanel.add(textFieldNome);
			textFieldNome.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Telefone");
			contentPanel.add(lblNewLabel_1);
		}
		{
			textFieldTelefone = new JTextField();
			contentPanel.add(textFieldTelefone);
			textFieldTelefone.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Email");
			contentPanel.add(lblNewLabel_2);
		}
		{
			textFieldEmail = new JTextField();
			contentPanel.add(textFieldEmail);
			textFieldEmail.setColumns(10);
		}
		
		rdbtnMasculino = new JRadioButton("Masculino");
		contentPanel.add(rdbtnMasculino);	
	
		rdbtnFeminino = new JRadioButton("Feminino");
		contentPanel.add(rdbtnFeminino);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnMasculino);
		buttonGroup.add(rdbtnFeminino);
		
		preencherCampos(cliente);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Salvar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						salvar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void preencherCampos(Cliente cliente) {
		textFieldNome.setText(cliente.getNome());
		textFieldTelefone.setText(cliente.getTelefone());
		textFieldEmail.setText(cliente.getEmail());
		if (cliente.getSexo().equalsIgnoreCase("Masculino")) {
			rdbtnMasculino.setSelected(true);
		}else {
			rdbtnFeminino.setSelected(true);
		}
	}
	
	private void salvar() { //salvar Cliente
		String nome = textFieldNome.getText().toString();
        String telefone = textFieldTelefone.getText().toString();
        String email = textFieldEmail.getText().toString();
        String sexo = rdbtnMasculino.isSelected() ? "Masculino" : "Feminino";
        LocalDate data = LocalDate.now();

        if (nome.isBlank() || telefone.isBlank() || email.isBlank()) {
            JOptionPane.showMessageDialog(
                this,
                "Preencha todos os campos."
            );
            return;
        }

        clienteEditado = new Cliente(
            clienteOriginal.getId(),
            nome,
            telefone,
            email,
            sexo,
            data
        );

        dispose();
	}
	
	public Cliente getClienteEditado() {
		return this.clienteEditado;
	}

}
