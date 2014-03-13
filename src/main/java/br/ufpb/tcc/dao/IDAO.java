package br.ufpb.tcc.dao;

import java.util.List;

public interface IDAO<T> {
	void save(T entity);
	 
    void update(T entity);
 
    void delete(T entity);
 
    T findOne(T entity);
 
    List<T> findAll();
 
    List<T> findKeyValue(T entity);
}
