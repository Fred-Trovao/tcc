package br.ufpb.tcc.util;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class ConexaoCassandra {
	private Session session;
	private static ConexaoCassandra conexaoCassandra;
	private static final String URL = "127.0.0.1";
	private final String KEYSPACE = "tcc";
	private Cluster cluster;
	
	private ConexaoCassandra(){
		
	}
	
	public static synchronized ConexaoCassandra getInstance(){
		if (conexaoCassandra == null){
			conexaoCassandra = new ConexaoCassandra();
		}
		return conexaoCassandra;
	}
	
	private Cluster getCluster(){
		if(this.cluster == null){
			this.cluster = Cluster.builder().addContactPoint(URL)
			.build();
		}
		return this.cluster;
	}
	
	public Session getSession() {
		if(this.session == null){
			this.session = getCluster().connect();
			this.session.execute("USE " + KEYSPACE);
		}
		return this.session;
	}

	public void close() {
		session.close();
		session.getCluster().close();
	}
}
