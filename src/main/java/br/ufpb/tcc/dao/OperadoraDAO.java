package br.ufpb.tcc.dao;

import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.util.TccException;

public interface OperadoraDAO extends IDAO<Operadora> {
	Operadora findOneComDocumento(Integer id) throws TccException;
}
