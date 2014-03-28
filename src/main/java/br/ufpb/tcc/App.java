package br.ufpb.tcc;

import br.ufpb.tcc.util.TccException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        try {
			new App().b();
			System.out.println("acabou");
		} catch (TccException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void a() throws TccException{
    	Integer x;
    	
    	try{
    		x = Integer.parseInt("d");
    		System.out.println(x);
    	}catch(Exception e){
    		throw new TccException(e);
    	}
    }
    
    public void b() throws TccException{
    	
    	try{
    		System.out.println("chamdno a");
        	a();
        	System.out.println("chamei a");
    	}catch(Exception e){
    		throw new TccException(e);
    	}
    }
}
