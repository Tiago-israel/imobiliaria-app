package com.br.imobiliaria.repositories;

import com.orm.SugarRecord;

import java.util.List;

public abstract class BaseRepository<T extends SugarRecord<T>> {

    private final Class<T> clazz;

    public BaseRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(T entity) {
        entity.save();
    }

    public T findById(long id) {
        return T.findById(clazz, id);
    }

    public List<T> findAll() {
        return T.listAll(clazz);
    }

    public void delete(T entity) {
        entity.delete();
    }

}
