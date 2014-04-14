package br.ufpb.tcc.dao;

import br.ufpb.tcc.model.Documento;
import br.ufpb.tcc.util.TccException;

public interface DocumentoDAO extends IDAO<Documento>{
	void delete(String documento) throws TccException;
}
