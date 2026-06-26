package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Cliente;

public class ClienteDAO {

	public void inserir(Cliente cliente) {
		String sql = "INSERT INTO clientes "
				+ "(nome, telefone, email, sexo, data) VALUES (?,?,?,?,?)";	
		
		try {
			Connection conexao = Conexao.conectar();
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getTelefone());
			stmt.setString(3, cliente.getEmail());
			stmt.setString(4, cliente.getSexo());
			stmt.setString(5, cliente.getData().toString());
			stmt.execute();
			
			stmt.close();
			conexao.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(int id) {
		
		String sql = "DELETE FROM clientes WHERE id=?";
		try {
			Connection conexao = Conexao.conectar();
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			
			stmt.close();
			conexao.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel comunicar com banco de dados","ERROR",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public ArrayList<Cliente> listar(){
		String sql = "SELECT * FROM clientes";
		
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try {
			Connection conexao = Conexao.conectar();
			PreparedStatement stmt = conexao.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String nome = resultSet.getString("nome");
				String telefone = resultSet.getString("telefone");
				String email = resultSet.getString("email");
				String sexo = resultSet.getString("sexo");
				String data = resultSet.getString("data");
				LocalDate localData = LocalDate.parse(data);
				Cliente cliente = new Cliente(id, nome, telefone, email, sexo,localData);
				clientes.add(cliente);
			}
			return clientes;
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel comunicar com banco de dados","ERROR",JOptionPane.ERROR_MESSAGE);
		}
		return clientes;
	}
	
	public void atualizar(Cliente cliente) {
		String sql = "UPDATE clientes SET nome=?, "
				+ "telefone=?, email=?, sexo=?, data=? WHERE id=?";
		try {
			Connection conexao = Conexao.conectar();
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getTelefone());
			stmt.setString(3, cliente.getEmail());
			stmt.setString(4, cliente.getSexo());
			stmt.setString(5, cliente.getData().toString());
			stmt.setInt(6, cliente.getId());
			stmt.executeUpdate();
			stmt.close();
			conexao.close();
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel comunicar com banco de dados","ERROR",JOptionPane.ERROR_MESSAGE);
		
		}
	}

}
