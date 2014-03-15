package br.ufpb.tcc.util;

import br.ufpb.tcc.dao.OperadoraDAO;
import br.ufpb.tcc.dao.PessoaDAO;
import br.ufpb.tcc.dao.TelefoneDAO;
import br.ufpb.tcc.dao.UsuarioDAO;
import br.ufpb.tcc.dao.UsuarioOperadoraDAO;
import br.ufpb.tcc.dao.impl.OperadoraDAOPostgres;
import br.ufpb.tcc.dao.impl.PessoaDAOPostgres;
import br.ufpb.tcc.dao.impl.TelefoneDAOPostgres;
import br.ufpb.tcc.dao.impl.UsuarioDAOPostgres;
import br.ufpb.tcc.dao.impl.UsuarioOperadoraDAOPostgres;

public class DAOFactory {

	public static UsuarioDAO criarUsuarioDAO(byte banco) {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new UsuarioDAOPostgres();
		}
		return null;
	}

	public static PessoaDAO criarPessoaDAO(byte banco) {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new PessoaDAOPostgres();
		}
		return null;
	}

	public static TelefoneDAO criarTelefoneDAO(byte banco) {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new TelefoneDAOPostgres();
		}
		return null;
	}

	public static OperadoraDAO criarOperadoraDAO(byte banco) {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new OperadoraDAOPostgres();
		}
		return null;
	}

	public static UsuarioOperadoraDAO criarUsuarioOperadoraDAO(byte banco) {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new UsuarioOperadoraDAOPostgres();
		}
		return null;
	}

}
