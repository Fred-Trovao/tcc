package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.dao.impl.ClienteDAOCassandra;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class updateNome {

	public static void main(String[] args){
		
		String[] doc = {"74570931901","19089096875","52210523997","28490786482","49309597620","37031654479","78003000007","96104006645","47467416898","60776958145"};
		String[] tel = {"9005739173","3578295097","0896091202","4856819642","2846926184","1896265384","3854285706","4671430370","0219972675","1507759034"};
		
		long soma = 0;
		
		for(int i=0;i<doc.length;i++){
		
		ClienteDAO cd;
		try {
			
			System.out.println("Processando: " + i);
			
			int banco = Bancos.CASSANDRA.ordinal();
			
			cd = DAOFactory.criarClienteDAO(banco);
			
			long inicio = System.currentTimeMillis();
			Pessoa pessoa = cd.findCliente(doc[i], tel[i]);
			
			if(banco == Bancos.CASSANDRA.ordinal()){
				pessoa = ((ClienteDAOCassandra) cd)
						.findTelefonesPorDocumento(pessoa.getDocumento().getNumero());
			}else if(banco == Bancos.POSTGRES.ordinal()){
				cd = DAOFactory.criarClienteDAO(banco);
			}
			
			if(pessoa != null){
				pessoa.setNome("Fred Augusto" + i);
			}
			
			pessoa = cd.updatePessoa(pessoa);
			long fim = System.currentTimeMillis();
			
			if(i>0)
				soma = soma + fim -inicio;
			
			System.out.println(fim -inicio);
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		System.out.println("Media : " + (soma/(doc.length-1)));
	}
}
