package com.sudocn.play.mvc.results;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import play.exceptions.UnexpectedException;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.results.Result;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;

/**
 * 200 OK with application/json
 */
public class RenderJson extends Result {

    String json;

    public RenderJson(Object o) {
        json = JSON.toJSONString(o);
    }

    public RenderJson(String jsonString) {
        json = jsonString;
    }

    public void apply(Request request, Response response) {
        try {
            String encoding = getEncoding();
            setContentTypeIfNotSet(response, "application/json; charset="+encoding);
            response.out.write(json.getBytes(encoding));
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }
    }

}