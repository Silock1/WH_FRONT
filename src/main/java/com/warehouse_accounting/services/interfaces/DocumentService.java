package com.warehouse_accounting.services.interfaces;

import java.util.List;

public interface DocumentService<T> {

    List<T> getAll();

    T getById(Long id);

    void create(T dto);

    void update(T dto);

    void deleteById(Long id);
}
