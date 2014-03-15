package br.ufpb.tcc;

import br.ufpb.tcc.dao.OperadoraDAO;
import br.ufpb.tcc.dao.PessoaDAO;
import br.ufpb.tcc.dao.TelefoneDAO;
import br.ufpb.tcc.dao.UsuarioDAO;
import br.ufpb.tcc.dao.UsuarioOperadoraDAO;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.model.Telefone;
import br.ufpb.tcc.model.Usuario;
import br.ufpb.tcc.model.UsuarioOperadora;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class Insere {

	public static void main(String[] args) throws TccException {
		
		Usuario u1 = new Usuario();
		u1.setLogin("fred");
		u1.setSenha("1234");
		u1.setTipo((byte) 1);
		
		UsuarioDAO ud = DAOFactory.criarUsuarioDAO(Bancos.POSTGRES.ordinal());
		ud.save(u1);
		
		Operadora o1 = new Operadora();
		o1.setRazaoSocial("oi");
		
		OperadoraDAO od = DAOFactory.criarOperadoraDAO(Bancos.POSTGRES.ordinal());
		od.save(o1);
		
		UsuarioOperadora uo1 = new UsuarioOperadora(u1, o1);
		
		UsuarioOperadoraDAO uod = DAOFactory.criarUsuarioOperadoraDAO(Bancos.POSTGRES.ordinal());
		uod.save(uo1);
		
		Pessoa p1 = new Pessoa();
		p1.setNome("fred");
		p1.setCpf("0087597409");
		p1.setUsuario(u1);
		
		PessoaDAO pd = DAOFactory.criarPessoaDAO(Bancos.POSTGRES.ordinal());
		pd.save(p1);
		
		Telefone t1 = new Telefone(p1, o1);
		TelefoneDAO td = DAOFactory.criarTelefoneDAO(Bancos.POSTGRES.ordinal());
		td.save(t1);	
	}
}
