package br.ufpb.tcc.dao;

import java.util.Set;

import br.ufpb.tcc.model.DocumentoOperadora;
import br.ufpb.tcc.model.Operadora;
import br.ufpb.tcc.util.TccException;

public interface DocumentoOperadoraDAO extends IDAO<DocumentoOperadora> {
	Set<DocumentoOperadora> findByOperadora(Operadora operadora) throws TccException;
}
