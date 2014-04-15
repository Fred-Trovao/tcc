package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class DeleteMenor18 {

	public static void main(String[] args){
			
		ClienteDAO cd;
		try {
			
			int banco = Bancos.MONGODB.ordinal();
			
			cd = DAOFactory.criarClienteDAO(banco);
			
			long inicio = System.currentTimeMillis();
			
			cd.deleteAllIdadeMenor(18);
						
			long fim = System.currentTimeMillis();
			
			System.out.println(fim -inicio);
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
