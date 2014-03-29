package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class Select {

	public static void main(String[] args){
		
		String[] doc = {"14365954966", "08749003773", "83487329671", "80378098579", "81295992162", "43603896753", "45386638007"};
		String[] tel = {"6449143854", "8938694666", "0717767331", "3385189797", "9866530308", "3787357154", "7797207397"};
		
		for(int i=0;i<doc.length;i++){
		long inicio = System.currentTimeMillis();
		
		ClienteDAO cd;
		try {
			cd = DAOFactory.criarClienteDAO(Bancos.POSTGRES.ordinal());
			
			Pessoa pessoa = cd.findCliente(doc[i], tel[i]);
			
			System.out.println(pessoa.toString());
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long fim = System.currentTimeMillis();
		
		System.out.println(fim -inicio);
		}
	}
}
