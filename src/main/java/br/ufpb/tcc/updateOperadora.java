package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.dao.impl.ClienteDAOMongoDB;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class updateOperadora {

	public static void main(String[] args){
		String[] ops = {"Hujbtv","Uucupy","Slnldu","Zcbbgr","Dtgior","Sfrjso","Zkgeir","Zurpkh","Lcmztl","Dqyroz"};
		
		long soma = 0;
		for(int i=0;i<ops.length;i++){
		ClienteDAO cd;
		try {
			
			int banco = Bancos.CASSANDRA.ordinal();
			
			cd = DAOFactory.criarClienteDAO(banco);
			
			long inicio = System.currentTimeMillis();
			
			String razaoSocial = ops[i];
			
			Operadora operadora = cd.findOperadora(razaoSocial);
						
			if(operadora != null){
				operadora.setRazaoSocial(razaoSocial.toUpperCase());
			}
			
			if(banco == Bancos.POSTGRES.ordinal()){
				cd = DAOFactory.criarClienteDAO(Bancos.POSTGRES.ordinal());
			}
			
			if(banco == Bancos.MONGODB.ordinal()){
				((ClienteDAOMongoDB) cd).updateOperadora(razaoSocial, operadora.getRazaoSocial());
			}else{
				operadora = cd.updateOperadora(operadora);
			}
						
			long fim = System.currentTimeMillis();
			
			if(i>0)
				soma = soma + fim -inicio;
			
			System.out.println(fim -inicio);
			
			System.out.println(operadora.toString());
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		System.out.println("Media : " + (soma/(ops.length -1)));
	}
}
