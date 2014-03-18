package br.ufpb.tcc;

import java.util.Date;

import br.ufpb.tcc.dao.OperadoraDAO;
import br.ufpb.tcc.dao.PessoaDAO;
import br.ufpb.tcc.dao.TelefoneDAO;
import br.ufpb.tcc.dao.DocumentoDAO;
import br.ufpb.tcc.dao.DocumentoOperadoraDAO;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.model.Telefone;
import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.model.DocumentoOperadora;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class Insere {

	public static void main(String[] args) throws TccException {
		
		Documento u1 = new Documento();
		u1.setNumero("fred");
		u1.setTipo((byte) 1);
		
		DocumentoDAO ud = DAOFactory.criarDocumentoDAO(Bancos.POSTGRES.ordinal());
		ud.save(u1);
		
		Operadora o1 = new Operadora();
		o1.setRazaoSocial("oi");
		
		OperadoraDAO od = DAOFactory.criarOperadoraDAO(Bancos.POSTGRES.ordinal());
		od.save(o1);
		
		DocumentoOperadora uo1 = new DocumentoOperadora(u1, o1);
		
		DocumentoOperadoraDAO uod = DAOFactory.criarDocumentoOperadoraDAO(Bancos.POSTGRES.ordinal());
		uod.save(uo1);
		
		Pessoa p1 = new Pessoa();
		p1.setNome("fred");
		p1.setNascimento(new Date());
		p1.setDocumento(u1);
		
		PessoaDAO pd = DAOFactory.criarPessoaDAO(Bancos.POSTGRES.ordinal());
		pd.save(p1);
		
		Telefone t1 = new Telefone(p1, o1);
		TelefoneDAO td = DAOFactory.criarTelefoneDAO(Bancos.POSTGRES.ordinal());
		td.save(t1);	
	}
}
