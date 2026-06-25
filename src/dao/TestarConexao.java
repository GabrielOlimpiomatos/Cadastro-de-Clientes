package dao;

import java.sql.Connection;

public class TestarConexao {

	public static void main(String[] args) {
		
		Connection conexao = Conexao.conectar();
		ClienteDAO dao = new ClienteDAO();
		
		if (conexao != null) {
			System.out.println("Conectado com sucesso!");
		}else {
			System.out.println("Erro ao se conectar ao banco");
		}
		dao.excluir(2);

	}

}
