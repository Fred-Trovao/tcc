package br.ufpb.tcc;

import java.util.List;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class SelectTop50 {

	public static void main(String[] args){
		String[] ops = {"Hujbtv","Uucupy","Slnldu","Zcbbgr","Dtgior","Sfrjso","Zkgeir","Zurpkh","Lcmztl","Dqyroz"};
		
		long soma = 0;
		for(int i=0;i<ops.length;i++){
		ClienteDAO cd;
		try {
			cd = DAOFactory.criarClienteDAO(Bancos.MONGODB.ordinal());
			
			long inicio = System.currentTimeMillis();
			List<Pessoa> pessoas = cd.findTopN(ops[i], 50);
			long fim = System.currentTimeMillis();
			
			if(i>0)
				soma = soma + fim -inicio;
			
			System.out.println(fim -inicio);
			
			System.out.println(pessoas.size());
			
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		System.out.println("Media : " + (soma/(ops.length -1)));
	}
}
