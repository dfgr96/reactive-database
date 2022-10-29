package com.example.services;

import com.example.dto.RequestEntity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;

public interface RequestService {

    Multi<RequestEntity> findAll(MySQLPool client);
    Uni<RequestEntity> findById(MySQLPool client, Long id);
    Uni<Long> save(MySQLPool client, RequestEntity requestEntity);
    Uni<Integer> update(MySQLPool client, RequestEntity requestEntity);
    Uni<Boolean> delete(MySQLPool client, Long id);
}
