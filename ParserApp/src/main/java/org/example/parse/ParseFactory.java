package org.example.parse;

import org.example.parse.bean.WebPage;
import org.example.web_34670work.HtmlUnitParseService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class ParseFactory {

    private static final Map<String, ParseService> parseServices = Map.of(
            "http://34670.work", new HtmlUnitParseService().init()
    );

    public static WebPage parse(String path) throws MalformedURLException {
        var url = new URL(path);
        return parseServices.get(path).parse(url);
    }

}
