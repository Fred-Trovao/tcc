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
			
			int banco = Bancos.MONGODB.ordinal();
			
			cd = DAOFactory.criarClienteDAO(banco);
			
			String razaoSocial = "Tymphc";
			
			Operadora operadora = cd.findOperadora(razaoSocial);
						
			if(operadora != null){
				operadora.setRazaoSocial("Oi");
			}
			
			if(banco == Bancos.POSTGRES.ordinal()){
				cd = DAOFactory.criarClienteDAO(Bancos.POSTGRES.ordinal());
			}
			
			long inicio = System.currentTimeMillis();
			
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
