package br.ufpb.tcc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.ufpb.tcc.dao.OperadoraDAO;
import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.model.DocumentoOperadora;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.util.ConexaoPostgres;
import br.ufpb.tcc.util.TccException;

public class OperadoraDAOPostgres implements OperadoraDAO {

	private Connection conn;
	
	public OperadoraDAOPostgres(Connection conn){
		this.conn = conn;
	}
	
	public void save(Operadora entidade) throws TccException {
		if (entidade == null) {
			String mensagem = "Não foi informado a operadora a cadastrar.";
			throw new TccException(mensagem);
		}
		
		if(entidade.getDocumentos() == null || entidade.getDocumentos().isEmpty()){
			String mensagem = "Não foi informado o(s) documento(s) da operadora a cadastrar.";
			throw new TccException(mensagem);
		}
		
		PreparedStatement pstm = null;
		try {
			String sql = "INSERT INTO operadora (razaosocial) VALUES (?)";

			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, entidade.getRazaoSocial());
			pstm.executeUpdate();

			// recuperar o ResultSet
			ResultSet rs = pstm.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1); // <= o valor do campo está aqui

			entidade.setId(id);
			
			for(Documento doc : entidade.getDocumentos()){
				
				if(doc.getId() == null){
					DocumentoDAOPostgres ddp = new DocumentoDAOPostgres(conn);				
					ddp.save(doc);
				}
				
				DocumentoOperadoraDAOPostgres dodp = new DocumentoOperadoraDAOPostgres(conn);
				DocumentoOperadora docOp = new DocumentoOperadora(doc, entidade);
				
				dodp.save(docOp);
			}
			
		} catch (SQLException e) {
			throw new TccException(e);
		} finally {
			ConexaoPostgres.closeConexao(pstm);
		}
	}

	public void update(Operadora entidade) throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "UPDATE operadora SET razaosocial = ? WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, entidade.getRazaoSocial());
            pstm.setInt(2, entidade.getId());
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
            ConexaoPostgres.closeConexao(pstm);
        }
	}

	public void delete(Operadora entidade) throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "DELETE FROM operadora WHERE id = ?";
        
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, entidade.getId());
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
            ConexaoPostgres.closeConexao(pstm);
        }

	}

	public Operadora findOne(Integer id) throws TccException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		Operadora operadora = null;
		
		try {
			String sql = "SELECT * FROM operadora WHERE id = ?";
			            
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
            
			rs = pstm.executeQuery();
            if(rs.next()) {
                operadora = new Operadora();
                operadora.setId(rs.getInt("id"));
                operadora.setRazaoSocial(rs.getString("razaosocial"));
            }
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
        	ConexaoPostgres.closeConexao(pstm, rs);
        }
		
		return operadora;
	}

	public List<Operadora> findAll() throws TccException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Operadora> operadoras = new ArrayList<Operadora>();
		
		try {
			String sql = "SELECT * FROM operadora";
			Operadora operadora = null;
            
			pstm = conn.prepareStatement(sql);
            
			rs = pstm.executeQuery();
            while (rs.next()) {
                operadora = new Operadora();
                operadora.setId(rs.getInt("id"));
                operadora.setRazaoSocial(rs.getString("razaosocial"));
                 
                operadoras.add(operadora);
            }
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
        	ConexaoPostgres.closeConexao(pstm, rs);
        }
        return operadoras;
	}

	public void deleteAll() throws TccException {
		PreparedStatement pstm = null;
		try {
			String sql = "DELETE FROM operadora";
        
            pstm = conn.prepareStatement(sql);
            
            pstm.executeUpdate();            
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
            ConexaoPostgres.closeConexao(pstm);
        }		
	}

	@Override
	public Operadora findOneComDocumento(Integer id) throws TccException {
		Operadora operadora = findOne(id);
		
		if(operadora != null){
			DocumentoOperadoraDAOPostgres dodp = new DocumentoOperadoraDAOPostgres(conn);
			Set<DocumentoOperadora> documentoOperadoras = dodp.findByOperadora(operadora);
			
			for(DocumentoOperadora dop : documentoOperadoras){
				operadora.addDocumento(dop.getDocumento());
			}
		}
		return operadora;
	}

	public Operadora findOneComDocumento(String razaoSocial)
			throws TccException {
		Operadora operadora = findOne(razaoSocial);
		
		if(operadora != null){
			operadora = findOneComDocumento(operadora.getId());
		}
		
		return operadora;
	}

	public Operadora findOne(String razaoSocial) throws TccException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		Operadora operadora = null;
		
		try {
			String sql = "SELECT * FROM operadora WHERE razaoSocial = ?";
			            
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, razaoSocial);
            
			rs = pstm.executeQuery();
            if(rs.next()) {
                operadora = new Operadora();
                operadora.setId(rs.getInt("id"));
                operadora.setRazaoSocial(rs.getString("razaosocial"));
            }
        } catch (SQLException e) {
        	throw new TccException(e);
        } finally {
        	ConexaoPostgres.closeConexao(pstm, rs);
        }
		
		return operadora;
	} 
}
