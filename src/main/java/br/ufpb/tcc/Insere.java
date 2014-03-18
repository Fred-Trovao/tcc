package br.ufpb.tcc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.model.Telefone;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class Insere {

	public static void main(String[] args) throws TccException {
		
		Documento d1 = new Documento();
		d1.setNumero("00875976409");
		d1.setTipo((byte) 1);
		
		Documento d2 = new Documento();
		d2.setNumero("123456789");
		d2.setTipo((byte) 2);
		
		Documento d3 = new Documento();
		d3.setNumero("987654321");
		d3.setTipo((byte) 2);
						
		Operadora o1 = new Operadora();
		o1.setRazaoSocial("oi");
		o1.addDocumento(d2);
		o1.addDocumento(d3);
						
		Pessoa p1 = new Pessoa();
		p1.setNome("fred");
		p1.setNascimento(new Date());
		p1.setDocumento(d1);
		
		Telefone t1 = new Telefone(o1);
		t1.setNumero("88269825");
		p1.addTelefone(t1);
		
		Telefone t2 = new Telefone(o1);
		t2.setNumero("32310681");
		p1.addTelefone(t2);
		
		ClienteDAO cd = DAOFactory.criarClienteDAO(Bancos.MONGODB.ordinal());
		cd.save(p1);
	}
	
	public static void createBase(long pessoas, long operadoras){
		
		List<Integer> idOperadoras = new ArrayList<Integer>();
				
		for(long i=0; i<operadoras;i++){
			
		}
		
	}
	
}
