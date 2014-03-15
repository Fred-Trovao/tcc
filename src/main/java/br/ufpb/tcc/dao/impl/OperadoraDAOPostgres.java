package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.ufpb.tcc.dao.OperadoraDAO;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class OperadoraDAOPostgres implements OperadoraDAO {

	@Override
	public void save(Operadora entidade) throws TccException {
		if (entidade == null) {
			String mensagem = "Não foi informado a operadora a cadastrar.";
			throw new TccException(mensagem);
		}

		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConexaoPostgres.getConexao();
			String sql = "INSERT INTO operadora (razaosocial) VALUES (?)";

			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, entidade.getRazaoSocial());
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
	public void update(Operadora entity) throws TccException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Operadora entity) throws TccException {
		// TODO Auto-generated method stub

	}

	@Override
	public Operadora findOne(Operadora entity) throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Operadora> findAll() throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Operadora> findKeyValue(Operadora entity) throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

}
