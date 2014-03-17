package br.ufpb.tcc.util;

import br.ufpb.tcc.dao.OperadoraDAO;
import br.ufpb.tcc.dao.PessoaDAO;
import br.ufpb.tcc.dao.TelefoneDAO;
import br.ufpb.tcc.dao.DocumentoDAO;
import br.ufpb.tcc.dao.DocumentoOperadoraDAO;
import br.ufpb.tcc.dao.impl.OperadoraDAOPostgres;
import br.ufpb.tcc.dao.impl.PessoaDAOPostgres;
import br.ufpb.tcc.dao.impl.TelefoneDAOPostgres;
import br.ufpb.tcc.dao.impl.DocumentoDAOPostgres;
import br.ufpb.tcc.dao.impl.DocumentoOperadoraDAOPostgres;

public class DAOFactory {

	public static DocumentoDAO criarDocumentoDAO(int banco) {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new DocumentoDAOPostgres();
		}
		return null;
	}

	public static PessoaDAO criarPessoaDAO(int banco) {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new PessoaDAOPostgres();
		}
		return null;
	}

	public static TelefoneDAO criarTelefoneDAO(int banco) {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new TelefoneDAOPostgres();
		}
		return null;
	}

	public static OperadoraDAO criarOperadoraDAO(int banco) {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new OperadoraDAOPostgres();
		}
		return null;
	}

	public static DocumentoOperadoraDAO criarDocumentoOperadoraDAO(int banco) {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new DocumentoOperadoraDAOPostgres();
		}
		return null;
	}

}
