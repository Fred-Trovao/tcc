package br.ufpb.tcc.dao.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.model.Telefone;
import br.ufpb.tcc.util.Aleatorio;
import br.ufpb.tcc.util.ConexaoCassandra;
import br.ufpb.tcc.util.TccException;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class ClienteDAOCassandra implements ClienteDAO {
	
	private Session session;
	private final String INSERT_DOC_TEL = "INSERT INTO documento_telefone "
			+ "(documento , tipo_documento , telefone , operadora_id ,"
			+ " nome , nascimento) VALUES (?, ?, ?, ?, ?, ?)";
	private final String INSERT_OPERADORA = "INSERT INTO operadora (operadora_id,"
			+ " razaosocial , documentos ) VALUES (?, ?, ?)";
	private final String SELECT_PESSOA = "SELECT * FROM documento_telefone "
			+ "WHERE documento = ? and telefone = ?";
	private final String SELECT_Operadora = "SELECT * FROM operadora "
			+ "WHERE operadora_id = ?";
	
	public ClienteDAOCassandra(){
		this.session = ConexaoCassandra.getInstance().getSession();
	}
	
	public void save(Pessoa pessoa) throws TccException {
		
		if(pessoa == null){
			throw new TccException("Não informado o cliente a cadastrar");
		}
		
		for(Telefone telefone : pessoa.getTelefones()){
			
			if(telefone.getOperadora().getUuid() == null){
			
				PreparedStatement pstm = this.session.prepare(INSERT_OPERADORA);
				
				BoundStatement bstm = new BoundStatement(pstm);
				
				Map<Integer, String> documentos = new HashMap<Integer, String>();
				
				for(Documento doc : telefone.getOperadora().getDocumentos()){
					documentos.put((int) doc.getTipo(), doc.getNumero());
				}
				
				telefone.getOperadora().setUuid(Aleatorio.geraUuid()); 
				
				this.session.execute(bstm.bind(telefone.getOperadora().getUuid(), 
						telefone.getOperadora().getRazaoSocial(),
						documentos));
			}
			
			PreparedStatement pstm = this.session.prepare(INSERT_DOC_TEL);
			
			BoundStatement bstm = new BoundStatement(pstm);
			
			this.session.execute(bstm.bind(pessoa.getDocumento().getNumero(), 
					(int) pessoa.getDocumento().getTipo(),
					telefone.getNumero(),
					telefone.getOperadora().getUuid(),
					pessoa.getNome(),
					pessoa.getNascimento()));
		}
	}

	public Pessoa findCliente(String documento, String telefone){
		
		PreparedStatement pstm = this.session.prepare(SELECT_PESSOA);
		
		BoundStatement bstm = new BoundStatement(pstm);
		
		ResultSet rs = this.session.execute(bstm.bind(documento, telefone));
		
		Pessoa pessoa = new Pessoa();
		
		for (Row row : rs) {
			
			Documento doc = new Documento();
			doc.setNumero(row.getString("documento"));
			doc.setTipo((byte) row.getInt("tipo_documento"));
			
			
			pessoa.setNome(row.getString("nome"));
			pessoa.setNascimento(row.getDate("nascimento"));
			pessoa.setDocumento(doc);
			
			Telefone tel = new Telefone();
			tel.setNumero(row.getString("telefone"));
			
			pessoa.addTelefone(tel);
		}
		
		return pessoa;
	}
	
	public findOperadora(UUID uuid){
		PreparedStatement pstm = this.session.prepare(SELECT_PESSOA);
		
		BoundStatement bstm = new BoundStatement(pstm);
		
		Row row = this.session.execute(bstm.bind(uuid)).one();
		
		Opera
	}
}
