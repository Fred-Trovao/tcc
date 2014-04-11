package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.dao.impl.ClienteDAOCassandra;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class updateNome {

	public static void main(String[] args){
		
		String[] doc = {"82833687441"};
		String[] tel = {"8212252567"};
		
		for(int i=0;i<doc.length;i++){
		
		ClienteDAO cd;
		try {
			cd = DAOFactory.criarClienteDAO(Bancos.CASSANDRA.ordinal());
			
			Pessoa pessoa = cd.findCliente(doc[i], tel[i]);
			
			if(cd instanceof ClienteDAOCassandra){
				pessoa = ((ClienteDAOCassandra) cd)
						.findTelefonesPorDocumento(pessoa.getDocumento().getNumero());
			}
			
			if(pessoa != null){
				pessoa.setNome("Fred Augusto");
			}
			
			//cd = DAOFactory.criarClienteDAO(Bancos.CASSANDRA.ordinal());
			
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
