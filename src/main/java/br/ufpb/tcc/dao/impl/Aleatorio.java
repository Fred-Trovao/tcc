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
	
	public static Date geraData(Date min, Date max){
		return new Date(geraLong(min.getTime(), max.getTime()));
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
		
		int x = random.nextInt(max + 1);
		
		if(x< min){
			x += min;
		}
		
		return x;
	}
	
	public static long geraLong(long min, long max){
		Random random = new Random();
		
		long x = random.nextLong();
		
		if(x<0){
			x = x*-1;
		}
				
		while(x > max){
			x = x/10;
		}
		
		if(x< min){
			x += (min-x);
		}
		
		return x;
	}
	
	public static void main(String[] args){
		
		Date d = getData(1, 1, 1900);
		Date d1 = getData(31, 12, 1989);
		
		for(int i = 0; i<100; i++){			
			System.out.println(geraLong(20, 30));
		}
		
	}
}
