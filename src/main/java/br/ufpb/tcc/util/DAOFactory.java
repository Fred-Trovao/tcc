package br.ufpb.tcc.util;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.dao.impl.ClienteDAOCassandra;
import br.ufpb.tcc.dao.impl.ClienteDAOMongoDB;
import br.ufpb.tcc.dao.impl.ClienteDAOPostgres;

public class DAOFactory {
	
	public static ClienteDAO criarClienteDAO(int banco) throws TccException {

		if (banco == Bancos.POSTGRES.ordinal()) {
			return new ClienteDAOPostgres(ConexaoPostgres.getConexao());
		}else if(banco == Bancos.MONGODB.ordinal()){
			return new ClienteDAOMongoDB();
		}else if(banco == Bancos.CASSANDRA.ordinal()){
			return new ClienteDAOCassandra();
		}
		return null;
	}

}
