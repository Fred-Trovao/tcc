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
import com.datastax.driver.core.Session;

public class ClienteDAOCassandra implements ClienteDAO {
	
	private Session session;
	private final String INSERT_DOC_TEL = "INSERT INTO documento_telefone "
			+ "(cliente_id, documento , tipo_documento , telefone , operadora_id ,"
			+ " nome , nascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private final String INSERT_OPERADORA = "INSERT INTO operadora (operadora_id,"
			+ " razaosocial , documentos ) VALUES (?, ?, ?)";
	
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
			
			pessoa.getDocumento().setUuid(Aleatorio.geraUuid());
			
			this.session.execute(bstm.bind(pessoa.getDocumento().getUuid(), 
					pessoa.getDocumento().getNumero(), 
					(int) pessoa.getDocumento().getTipo(),
					telefone.getNumero(),
					telefone.getOperadora().getUuid(),
					pessoa.getNome(),
					pessoa.getNascimento()));
		}
	}	
}
