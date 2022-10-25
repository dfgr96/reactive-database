package com.example.data;

import com.example.dto.RequestEntity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RequestServiceImpl implements RequestService{

    @Override
    public Multi<RequestEntity> findAll(MySQLPool mySQLPool){
        return mySQLPool.query("select id, uid from test_database.request limit 10").execute()
                .onItem().transformToMulti(set ->Multi.createFrom().iterable(set))
                .onItem().transform(RequestServiceImpl::from);
    }

    @Override
    public Uni<RequestEntity> findById(MySQLPool client, Long id) {
        return client.preparedQuery("select id, uid from test_database.request where id = ?")
                .execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(m -> m.hasNext() ? from(m.next()) : null);
    }

    @Override
    public Uni<Long> save(MySQLPool client, RequestEntity requestEntity) {
        return client.preparedQuery("INSERT INTO request (uid,request_type_id,participant_id,channel_id,recovery_method_id,dtdate) VALUES (?,1,2,1,1, now());")
                .execute(Tuple.of(requestEntity.getUid()))
                .flatMap(m -> client.query("SELECT LAST_INSERT_ID() as id").execute())
                .onItem().transform(m -> m.iterator().next().getLong("id"));
    }

    @Override
    public Uni<Integer> update(MySQLPool client, RequestEntity requestEntity) {
        return client.preparedQuery("UPDATE request SET uid = ? where id = ?;")
                .execute(Tuple.of(requestEntity.getUid(),requestEntity.getId()))
                .onItem().transform(m -> m.rowCount());
    }

    @Override
    public Uni<Boolean> delete(MySQLPool client, Long id) {
        return client.preparedQuery("DELETE FROM request WHERE id = ?").execute(Tuple.of(id))
                .onItem().transform(mysqlRowSet -> mysqlRowSet.rowCount() == 1);
    }

    private static RequestEntity from(Row row){
        return  new RequestEntity(row.getLong("id"),row.getString("uid"));
    }
}
