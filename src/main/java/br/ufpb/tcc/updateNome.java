package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class updateNome {

	public static void main(String[] args){
		
		String[] doc = {"45931735580"};
		String[] tel = {"1226764123"};
		
		for(int i=0;i<doc.length;i++){
		
		ClienteDAO cd;
		try {
			cd = DAOFactory.criarClienteDAO(Bancos.MONGODB.ordinal());
			
			Pessoa pessoa = cd.findCliente(doc[i], tel[i]);
			
			if(pessoa != null){
				pessoa.setNome("Fred Augusto");
			}
			
			cd = DAOFactory.criarClienteDAO(Bancos.MONGODB.ordinal());
			
			long inicio = System.currentTimeMillis();
			pessoa = cd.updatePessoa(pessoa);
			long fim = System.currentTimeMillis();
			
			System.out.println(fim -inicio);
			
			System.out.println(pessoa.toString());
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
