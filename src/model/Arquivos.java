package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dao.ClienteDAO;
import view.TelaCadastro;

import java.io.BufferedReader;


public class Arquivos {
	private static boolean arquivoVazio(String linha) {
		return (linha == null) ? true : false;
	}
	
	
	public static void carregarDados(File file, ClienteTableModel modelo) {
		 try(BufferedReader read = new BufferedReader(new FileReader(file))){
			modelo.limparDados();
			
			if(arquivoVazio(read.readLine())) {
				JOptionPane.showMessageDialog(null, 
						"Arquivo vazio, selecione outro arquivo","Aviso",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			String linha = "";
			while((linha = read.readLine()) != null) {
				String campos [] = linha.split(",");
				
				if (campos.length != 4)//campo nao valido, passe para proximo ciclo  
					continue; 
				
				String nome = campos[0];
				String telefone = campos[1];
				String email = campos[2];
				String sexo = campos[3];
				String data = campos[4];
				LocalDate dataLocal = LocalDate.parse(data);
				Cliente cliente = new Cliente(nome, telefone, email, sexo,dataLocal);
				modelo.addCliente(cliente);
				
			}
		}catch(IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					"Não foi possivel abrir o arquivo, selecione outro","Aviso",JOptionPane.WARNING_MESSAGE);
		}
	}

	
	public static String formatar_data(LocalDate data) {
		DateTimeFormatter formato =
				DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return data.format(formato);
	}
	
	public static void exportarRelatorio(ClienteTableModel modelo, JFrame TelaCadastro) {
		String caminhoCompleto = "relatorio.txt";
		
		try(BufferedWriter arq = new BufferedWriter(new FileWriter(caminhoCompleto))){
			
			String dataFormatada = formatar_data(LocalDate.now());
			
			arq.write("RELATORIO DE CLIENTES"+ "\n");
			arq.write("Total de clientes: "+modelo.getNumCliente()+ "\n");
			arq.write("Masculino: " +modelo.getNumHomens()+ "\n");
			arq.write("Feminio: "+ modelo.getNumMulheres() + "\n\n");
			
			ArrayList<Cliente> clientes = modelo.getLista();
			
			for(Cliente cliente : clientes) {
				arq.write("------------------------------------");
				arq.write("\nNome: " + cliente.getNome()+ "\n");
				arq.write("Telefone: "+ cliente.getTelefone()+ "\n");
				arq.write("Email: " + cliente.getEmail()+ "\n");
				arq.write("Sexo: " + cliente.getSexo()+ "\n");
				arq.write("Data de cadastro: "+ dataFormatada + "\n");
			}
			arq.write("------------------------------------");
			
			JOptionPane.showMessageDialog(TelaCadastro, 
					"arquivo relatorio criado com sucesso");
			
		}catch(IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(TelaCadastro, 
			"Não foi possivel criar o arquivo", "ERRO", 
			JOptionPane.WARNING_MESSAGE);
		}
	}

	public static void importar_arquivo_csv(ClienteTableModel modelo, JFrame TelaCadastro, ClienteDAO dao) {
		
		JFileChooser fileChooser = new JFileChooser();
		String caminhoCompleto;
		
	    int resultado = fileChooser.showOpenDialog(null);
	    if (resultado == JFileChooser.APPROVE_OPTION) {
	        File arquivoSelecionado = fileChooser.getSelectedFile();
	        caminhoCompleto = arquivoSelecionado.getAbsolutePath();
	    }else {
	    	JOptionPane.showMessageDialog(TelaCadastro, "Nao foi possivel obter o endereco");
	    	return;
	    }
	    
	    int padraoAceitado=0;
	    int padraoRejeitado=0;
	    
	    try(BufferedReader read = new BufferedReader(new FileReader(caminhoCompleto))){
	    	if(arquivoVazio(read.readLine())) { //encerra caso o arquivo esteja vazio
				JOptionPane.showMessageDialog(null, 
						"O arquivo selecionado está vazio.","Aviso",JOptionPane.WARNING_MESSAGE);
				return;
			}
	    	
	    	String linha;
	    	while((linha = read.readLine()) != null) {
	    		String partes[] = linha.split(",");
	    		
	    		if(partes.length != 4) { //linhas fora do padrao
	    			padraoRejeitado++;
	    			continue;
	    		}
	    			String nome = partes[0];
		        	String telefone = partes[1];
		        	String email = partes[2];
		        	String sexo = partes[3];
		        	LocalDate hoje = LocalDate.now();
		        	Cliente cliente = new Cliente(nome,telefone,email,sexo,hoje);	
		        	modelo.addCliente(cliente);
		        	dao.inserir(cliente);
		        	padraoAceitado++;	
	    	}
	    	
	    	if(padraoRejeitado > 0) { //houve linhas fora do padrao
	    		JOptionPane.showMessageDialog(null, 
						"O arquivo contém linhas em formato inválido.","Aviso",JOptionPane.WARNING_MESSAGE);
	    	}
	    		
	    }catch(IOException file) {
	    	file.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					"Não foi possivel ler o arquivo, selecione outro","Aviso",JOptionPane.WARNING_MESSAGE);
	    }
	    System.out.println("Importado com sucesso");
	    System.out.println("Registros importados: " + padraoAceitado);
	    System.out.println("Registros rejeitados: " + padraoRejeitado);
	}

	public static void salvarDados(File file, ClienteTableModel modelo, JFrame TelaCadastro) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
			
			writer.write("Nome,Telefone,Email,Sexo");
			writer.newLine();
			for (int i=0; i < modelo.getRowCount(); i++) {
				String nome = 		(String) modelo.getValueAt(i, 0);
				String telefone = 	(String) modelo.getValueAt(i, 1);
				String email = 		(String) modelo.getValueAt(i, 2);
				String sexo = 		(String) modelo.getValueAt(i, 3);
				writer.write(nome+","+telefone+","+
				","+email+","+sexo);
				writer.newLine();
			}
		}catch(IOException e) {
			JOptionPane.showMessageDialog(TelaCadastro, "Nao foi possivel ler o arquivo");
		}
		
	}
	
	
}
