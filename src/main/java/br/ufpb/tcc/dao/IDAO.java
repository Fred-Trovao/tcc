package br.ufpb.tcc.dao;

import java.util.List;

import br.ufpb.tcc.util.TccException;

public interface IDAO<T> {
	void save(T entity) throws TccException;
	 
    void update(T entity) throws TccException;
     
    void delete(T entity) throws TccException;
    
    void deleteAll() throws TccException;
 
    T findOne(T entity) throws TccException;
 
    List<T> findAll() throws TccException ;
}
