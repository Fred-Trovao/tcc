package br.ufpb.tcc;

import java.util.ArrayList;
import java.util.List;

import br.ufpb.tcc.dao.ClienteDAO;
import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.model.Pessoa;
import br.ufpb.tcc.model.Telefone;
import br.ufpb.tcc.util.Aleatorio;
import br.ufpb.tcc.util.Bancos;
import br.ufpb.tcc.util.DAOFactory;
import br.ufpb.tcc.util.TccException;

public class Insere {

	public static void main(String[] args) throws TccException {
		System.out.println("Processando...");
		long inicio = System.currentTimeMillis();
		createBase(1000000, 2, 20, 2, Bancos.MONGODB.ordinal());
		
		long fim = System.currentTimeMillis();
		
		System.out.println(fim -inicio);
		
	}
	
	public static void createBase(int qtdPessoas, int qtdTelefones, 
			int qtdOperadoras, int qtdDocOperadora, int banco){
		
		List<Operadora> operadoras = new ArrayList<Operadora>();
				
		for(int i=0; i<qtdOperadoras;i++){
			Operadora operadora = new Operadora();
			operadora.setRazaoSocial(Aleatorio.geraPalavraLetras(6));
			
			for(int j=0; j<qtdDocOperadora; j++){
				Documento documento = new Documento();
				documento.setNumero(Aleatorio.geraPalavraNumeros(11));
				documento.setTipo((byte) (j+2));
				
				operadora.addDocumento(documento);
			}
			
			operadoras.add(operadora);
		}
		
		for(int i=0; i<qtdPessoas;i++){
			Pessoa pessoa = new Pessoa();
			pessoa.setNome(Aleatorio.geraPalavraLetras(8)
					+" " +Aleatorio.geraPalavraLetras(6));
			pessoa.setNascimento(Aleatorio.geraData(1950, 2000));
			
			Documento documento = new Documento();
			documento.setNumero(Aleatorio.geraPalavraNumeros(11));
			documento.setTipo((byte) 1);
			
			pessoa.setDocumento(documento);
			
			for(int j=0;j<qtdTelefones;j++){
				Telefone telefone = new Telefone(
						operadoras.get(Aleatorio.geraInt(0, operadoras.size() - 1)));
				telefone.setNumero(Aleatorio.geraPalavraNumeros(10));
				pessoa.addTelefone(telefone);
			}
			
			ClienteDAO cd;
			try {
				cd = DAOFactory.criarClienteDAO(banco);
				
				cd.save(pessoa);
				
//				cd = DAOFactory.criarClienteDAO(Bancos.MONGODB.ordinal());
//				
//				cd.save(pessoa);
//				
//				cd = DAOFactory.criarClienteDAO(Bancos.CASSANDRA.ordinal());
//				
//				cd.save(pessoa);
			} catch (TccException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Sucesso!");
	}	
}
