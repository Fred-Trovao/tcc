package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.dao.impl.ClienteDAOCassandra;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class updateNome {

	public static void main(String[] args){
		
		String[] doc = {"80047766855","41735720403", "47612979934","27538224314","98717590814","33970859406","36042508319","53526545039","06676975017","63932417152"};
		String[] tel = {"8188394912","9723896053", "0116187117", "1801724462","7227752461","4490154316","0217486477","9952622536","9118082548","1544396509"};
		
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
