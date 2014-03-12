package br.ufpb.tcc.util;

import java.sql.Connection;

public class TestaConexao {

	public static void main(String[] args) {
		System.out.println("Abrindo Conexão");
		
		Connection con = null;
		
		try {
			con = ConexaoPostgres.getConexao();
			System.out.println("Conexao aberta!");
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				ConexaoPostgres.closeConexao(con);
			} catch (TccException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}