package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.parse.ParseFactory;
import org.example.parse.ParseService;
import org.example.parse.bean.WebPage;
import org.example.web_34670work.HtmlUnitParseService;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Hello world!
 *
 */
@Slf4j
public class App 
{
    public static void main( String[] args )
    {
        log.info("Hello world!");
        log.error("Hello world!");
        log.warn("Hello world!");

        var app = new App();
        app.runApp();
    }

    public void runApp(){
        try {
            WebPage webPage = ParseFactory.parse("http://34670.work");
            log.info("Parsed: {}", webPage);
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
