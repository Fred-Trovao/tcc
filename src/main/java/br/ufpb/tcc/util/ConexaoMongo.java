package br.ufpb.tcc.util;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class ConexaoMongo {
	
	private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DB_NAME = "tcc-mongodb";
    
    private static ConexaoMongo uniqInstance;
    private static int mongoInstance = 1;
 
    private MongoClient mongo;
    private DB db;
 
    private ConexaoMongo() {
        //construtor privado
    }
 
    //garante sempre uma unica instancia
    public static synchronized ConexaoMongo getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new ConexaoMongo();
        }
        return uniqInstance;
    }
 
    //garante um unico objeto mongo
    public DB getDB() {
        if (mongo == null) {
            try {
                mongo = new MongoClient(HOST, PORT);
                db = mongo.getDB(DB_NAME);
                System.out.println("Mongo instance equals :> " + mongoInstance++);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return db;
    }
}
