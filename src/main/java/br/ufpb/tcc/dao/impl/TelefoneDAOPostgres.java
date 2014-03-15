package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.ufpb.tcc.dao.TelefoneDAO;
import br.ufpb.tcc.model.Telefone;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class TelefoneDAOPostgres implements TelefoneDAO {

	@Override
	public void save(Telefone entidade) throws TccException {
		if (entidade == null) {
			String mensagem = "Não foi informado o telefone a cadastrar.";
			throw new TccException(mensagem);
		}

		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "INSERT INTO telefone (id_pessoa, id_operadora, numero) VALUES (?, ?, ?)";

			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setInt(1, entidade.getTitular().getId());
			pstm.setInt(2, entidade.getOperadora().getId());
			pstm.setString(3, entidade.getNumero());
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
	public void update(Telefone entity) throws TccException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Telefone entity) throws TccException {
		// TODO Auto-generated method stub

	}

	@Override
	public Telefone findOne(Telefone entity) throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Telefone> findAll() throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Telefone> findKeyValue(Telefone entity)
			throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

}
