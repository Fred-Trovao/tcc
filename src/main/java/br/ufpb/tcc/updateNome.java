package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.dao.impl.ClienteDAOCassandra;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class updateNome {

	public static void main(String[] args){
		
		String[] doc = {"65227311605", "77153620196", "29857597747", "10031603452","70851973650","86213959228","61825293523","79421315730","37314487821","57726608994"};
		String[] tel = {"5285373129","8330927207", "8425058065","0746864313","1696972425","2080752245","9688896872","1332028991","3245194239","5450094652"};
		
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
