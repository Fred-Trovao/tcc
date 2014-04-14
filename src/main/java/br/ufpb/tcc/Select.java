package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class Select {

	public static void main(String[] args){
		
		String[] doc = {"80047766855","41735720403", "47612979934","27538224314","98717590814","33970859406","36042508319","53526545039","06676975017","63932417152"};
		String[] tel = {"8188394912","9723896053", "0116187117", "1801724462","7227752461","4490154316","0217486477","9952622536","9118082548","1544396509"};
		
		long soma = 0;
		for(int i=0;i<doc.length;i++){
			
			
			ClienteDAO cd;
			try {
				System.out.println("Processando: " + i);
				cd = DAOFactory.criarClienteDAO(Bancos.CASSANDRA.ordinal());
				
				long inicio = System.currentTimeMillis();
				
				Pessoa pessoa = cd.findCliente(doc[i], tel[i]);
				
				long fim = System.currentTimeMillis();
				
				if(i>0)
					soma = soma + fim -inicio;
				
				System.out.println(fim -inicio);
				
				System.out.println(pessoa.toString());
			} catch (TccException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Media : " + (soma/(doc.length -1)));
	}
}
