package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class Delete {

	public static void main(String[] args){
		
		String[] doc = {"74570931901","19089096875","52210523997","28490786482","49309597620","37031654479","78003000007","96104006645","47467416898","60776958145"};
		
		long soma = 0;
		for(int i=0;i<doc.length;i++){
			
			
			ClienteDAO cd;
			try {
				System.out.println("Processando: " + i);
				cd = DAOFactory.criarClienteDAO(Bancos.CASSANDRA.ordinal());
				
				long inicio = System.currentTimeMillis();
				
				cd.deleteOne(doc[i]);
				
				long fim = System.currentTimeMillis();
				
				if(i>0)
					soma = soma + fim -inicio;
				
				System.out.println(fim -inicio);
				
			} catch (TccException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Media : " + (soma/(doc.length -1)));
	}
}
