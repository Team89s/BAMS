package com.igeek.bams.dao;

/**
 * CRUD操作  增删改查
 */
public interface IDao<T,E> {

    //查
    T selectOne(Long id,String password);

    T selectOne(Long id);

    E selectAll();

    //增
    void insert(T acc);

    //删
    void delete(Long id);

    //改
    void update(T acc);

    //创建id
    Long createId();
}
