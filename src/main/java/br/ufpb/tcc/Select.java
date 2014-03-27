package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class Select {

	public static void main(String[] args) throws TccException {
		
		long inicio = System.currentTimeMillis();
		
		ClienteDAO cd;
		try {
			cd = DAOFactory.criarClienteDAO(Bancos.CASSANDRA.ordinal());
			
			cd.findCliente("45061528291", "6285290566");
			System.out.println("Sucesso!");
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long fim = System.currentTimeMillis();
		
		System.out.println(fim -inicio);
		
	}
}
