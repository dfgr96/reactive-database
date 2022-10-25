package com.example;


import com.example.data.RequestServiceImpl;
import com.example.dto.RequestEntity;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class RequestControllerTest {

    @InjectMock
    RequestServiceImpl requestService;

    @Test
    void requestTest(){
        Mockito.when(requestService.findAll(Mockito.any(MySQLPool.class))).thenReturn(getBodyRequest());
        given()
                .when()
                .get("/request")
                .then()
                .statusCode(200)
                .body("[0].id", is(1234))
                .body("[0].uid", is("234234-324234-234234-234234"));
    }

    @Test
    void getOne() {
        Mockito.when(requestService.findById(Mockito.any(MySQLPool.class), Mockito.anyLong()))
                .thenReturn(getBodyUniRequest());
        given()
                .when()
                .get("/request/1234")
                .then()
                .statusCode(200)
                .body("id", is(1234))
                .body("uid", is("234234-324234-234234-234234"));
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private Multi<RequestEntity> getBodyRequest() {
        RequestEntity request1 = new RequestEntity(1234L, "234234-324234-234234-234234");
        return Multi.createFrom().item(request1);

    }

    private Uni<RequestEntity> getBodyUniRequest() {
        RequestEntity request1 = new RequestEntity(1234L, "234234-324234-234234-234234");
        return Uni.createFrom().item(request1);

    }

}