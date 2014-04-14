package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class Delete {

	public static void main(String[] args){
		
		String[] doc = {"80047766855","41735720403", "47612979934","27538224314","98717590814","33970859406","36042508319","53526545039","06676975017","63932417152"};
		
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
