package com.example.services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.enterprise.context.ApplicationScoped;
import java.io.FileReader;
import java.io.IOException;

@ApplicationScoped
public class ReadJsonImpl implements ReadJson {

    public Object getWorkOut(String workOutDay) throws IOException, ParseException {
        JSONObject objectWorkOuts = getJsonFromResources();
        System.out.println(objectWorkOuts);

        return objectWorkOuts.get(workOutDay);
    }

    private JSONObject getJsonFromResources() throws IOException, ParseException {
        FileReader fileReader = new FileReader("src/main/resources/db.json");
        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser.parse(fileReader);
        return (JSONObject) object;
    }
}
