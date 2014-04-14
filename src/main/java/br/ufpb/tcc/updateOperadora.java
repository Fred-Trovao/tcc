package br.ufpb.tcc;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.dao.impl.ClienteDAOMongoDB;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class updateOperadora {

	public static void main(String[] args){
			
		ClienteDAO cd;
		try {
			
			int banco = Bancos.CASSANDRA.ordinal();
			
			cd = DAOFactory.criarClienteDAO(banco);
			
			long inicio = System.currentTimeMillis();
			
			String razaoSocial = "Gxvxej";
			
			Operadora operadora = cd.findOperadora(razaoSocial);
						
			if(operadora != null){
				operadora.setRazaoSocial("Net");
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
			
			System.out.println(fim -inicio);
			
			System.out.println(operadora.toString());
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
