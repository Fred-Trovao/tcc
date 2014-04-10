package br.ufpb.tcc;

import java.util.List;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class SelectTop50 {

	public static void main(String[] args){
		
		ClienteDAO cd;
		try {
			cd = DAOFactory.criarClienteDAO(Bancos.POSTGRES.ordinal());
			
			long inicio = System.currentTimeMillis();
			List<Pessoa> pessoas = cd.findTopN("Zxrggh", 50);
			long fim = System.currentTimeMillis();
			
			System.out.println(fim -inicio);
			
			System.out.println(pessoas.size());
			
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
