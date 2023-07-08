package com.example.services;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface ReadJson {
    Object getWorkOut(String workOutDay) throws IOException, ParseException;
}
