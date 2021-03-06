package br.ufpb.tcc.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import br.ufpb.tcc.conversores.DocumentoConverter;
import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.model.Operadora;
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
	private final String SELECT_PESSOA_DOCUMENTO = "SELECT telefone FROM documento_telefone "
			+ "WHERE documento = ?";
	private final String SELECT_PESSOA_OPERADORA = "SELECT documento FROM documento_telefone "
			+ "WHERE operadora_id = ?";
	private final String SELECT_OPERADORA = "SELECT * FROM operadora "
			+ "WHERE operadora_id = ?";
	private final String SELECT_OPERADORA_RS = "SELECT * FROM operadora "
			+ "WHERE razaosocial = ?";
	private final String UPDATE_NOME = "UPDATE documento_telefone "
			+ "SET nome = ? WHERE documento = ? and telefone = ?";
	private final String UPDATE_OPERADORA = "UPDATE operadora "
			+ "SET razaosocial = ? WHERE operadora_id = ?";
	private final String DELETE = "DELETE FROM documento_telefone "
			+ "WHERE documento = ?";
	private final String DELETE_OPERADORA = "DELETE FROM operadora "
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

	public Pessoa findCliente(String documento, String telefone) throws TccException{
		
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
			
			try{
				tel.setOperadora(findOperadora(row.getUUID("operadora_id")));
			}catch(TccException e){
				throw new TccException("Dados inconsistentes, não encontrado operadora");
			}
		}
		
		return pessoa;
	}
	
	public Operadora findOperadora(UUID uuid) throws TccException{
		PreparedStatement pstm = this.session.prepare(SELECT_OPERADORA);
		
		BoundStatement bstm = new BoundStatement(pstm);
		
		Row row = this.session.execute(bstm.bind(uuid)).one();
		
		Operadora operadora = new Operadora();
		operadora.setUuid(row.getUUID("operadora_id"));
		operadora.setRazaoSocial(row.getString("razaoSocial"));
		operadora.setDocumentos(new DocumentoConverter()
			.converterToSetDocumentoCassandra(new HashMap<Integer, String>(row.getMap("documentos", Integer.class, String.class))));
		
		return operadora;		
	}

	@Override
	public List<Pessoa> findTopN(String razaoSocial, int quantidade)
			throws TccException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pessoa updatePessoa(Pessoa pessoa) throws TccException {
		PreparedStatement pstm = this.session.prepare(UPDATE_NOME);
		
		BoundStatement bstm = new BoundStatement(pstm);
		
		for(Telefone t : pessoa.getTelefones()){
			this.session.execute(bstm.bind(pessoa.getNome(), 
					pessoa.getDocumento().getNumero(),
					t.getNumero()));
		}
		
		return pessoa;
	}
	
	public Pessoa findTelefonesPorDocumento(String documento) throws TccException{
		PreparedStatement pstm = this.session.prepare(SELECT_PESSOA_DOCUMENTO);
		
		BoundStatement bstm = new BoundStatement(pstm);
		
		ResultSet rs = this.session.execute(bstm.bind(documento));
		
		Pessoa pessoa = new Pessoa();
		Documento doc = new Documento();
		doc.setNumero(documento);
		pessoa.setDocumento(doc);
		
		for (Row row : rs) {
			
			Telefone telefone = new Telefone();
			telefone.setNumero(row.getString("telefone"));
			
			pessoa.addTelefone(telefone);
		}
		
		return pessoa;
	}

	@Override
	public Operadora updateOperadora(Operadora operadora) throws TccException {
		PreparedStatement pstm = this.session.prepare(UPDATE_OPERADORA);
		
		BoundStatement bstm = new BoundStatement(pstm);
		
		this.session.execute(bstm.bind(operadora.getRazaoSocial(), 
					operadora.getUuid()));
		
		return operadora;
	}

	@Override
	public Operadora findOperadora(String razaoSocial) throws TccException {
		PreparedStatement pstm = this.session.prepare(SELECT_OPERADORA_RS);
		
		BoundStatement bstm = new BoundStatement(pstm);
		
		Row row = this.session.execute(bstm.bind(razaoSocial)).one();
		
		Operadora operadora = new Operadora();
		operadora.setUuid(row.getUUID("operadora_id"));
		operadora.setRazaoSocial(row.getString("razaoSocial"));
		operadora.setDocumentos(new DocumentoConverter()
			.converterToSetDocumentoCassandra(new HashMap<Integer, String>(row.getMap("documentos", Integer.class, String.class))));
		
		return operadora;
	}

	@Override
	public void deleteOne(String documento) throws TccException {
		PreparedStatement pstm = this.session.prepare(DELETE);
		
		BoundStatement bstm = new BoundStatement(pstm);
		
		this.session.execute(bstm.bind(documento));
	}
		
	public Set<String> findDocumento(Operadora operadora){
		PreparedStatement pstm = this.session.prepare(SELECT_PESSOA_OPERADORA);
		
		BoundStatement bstm = new BoundStatement(pstm);
		
		ResultSet rs = this.session.execute(bstm.bind(operadora.getUuid()));
		
		Set<String> documentos = new HashSet<String>();
		
		for(Row row : rs){
			documentos.add(row.getString("documento"));
		}
		
		return documentos;
	}

	@Override
	public void deleteAllIdadeMenor(int anos) throws TccException {
		// TODO Auto-generated method stub
		
	}
}