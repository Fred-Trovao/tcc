package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.ufpb.tcc.dao.UsuarioDAO;
import br.ufpb.tcc.model.Usuario;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class UsuarioDAOPostgres implements UsuarioDAO {

	@Override
	public void save(Usuario entidade) throws TccException {
		if (entidade == null) {
			String mensagem = "Não foi informado o usuario a cadastrar.";
			throw new TccException(mensagem);
		}

		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "INSERT INTO usuario (login, senha, tipo) VALUES (?, ?, ?)";

			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, entidade.getLogin());
			pstm.setString(2, entidade.getSenha());
			pstm.setByte(3, entidade.getTipo());
			pstm.executeUpdate();

			// recuperar o ResultSet
			ResultSet rs = pstm.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1); // <= o valor do campo está aqui

			entidade.setId(id);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConexaoPostgres.closeConexao(conn, pstm);
		}
	}

	@Override
	public void update(Usuario entity) throws TccException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Usuario entity) throws TccException {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario findOne(Usuario entity) throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll() throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findKeyValue(Usuario entity) throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

}
