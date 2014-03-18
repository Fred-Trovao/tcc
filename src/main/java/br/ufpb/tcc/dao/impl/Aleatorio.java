package br.ufpb.tcc.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Aleatorio {

	private static final String ALFABETO = "abcdefghijklmnopqrstuvxwyzu";
	private static final String NUMEROS = "0123456789";
		
	public static String geraString(int tamanho, String... digitos){
				
		Random random = new Random();
		
		String digitosString = "";
		String palavra = "";
		
		for(int i=0; i<digitos.length; i++){
			digitosString += digitos[i];
		}
		
		if(digitosString.length() > 0){
			for(int i=0; i<tamanho; i++){
				int index = random.nextInt(digitosString.length());
				char letra = digitosString.charAt(index);
				if(i == 0){
					palavra += letra;
					palavra = palavra.toUpperCase();
				}else{
					palavra += letra;
				}
			}
		}
		return palavra;
	}
	
	public static String geraPalavraLetras(int tamanho){
		return geraString(tamanho, ALFABETO);
	}
	
	public static String geraPalavraNumeros(int tamanho){
		return geraString(tamanho, NUMEROS);
	}
	
	public static int sorteiaNumero(int max){
		Random random = new Random();
		return random.nextInt(max);
	}
	
	public static Date geraData(int anoMin, int anoMax){
		int d = geraInt(1, 31);
		int m = geraInt(1,12);
		int a = geraInt(anoMin, anoMax);
		
		Date date = getData(d, m, a);	
		return date;
	}
	
	public static Date getData(int dia, int mes, int ano){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, dia);
		calendar.set(Calendar.MONTH, --mes);
		calendar.set(Calendar.YEAR, ano);
		
		return calendar.getTime();
	}
	
	public static int geraInt(int min, int max){
		Random random = new Random();
		
		int x = random.nextInt(max - min + 1) + min;
				
		return x;
	}
}
