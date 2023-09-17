package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.DataStorageException;
import org.example.parse.ParseFactory;
import org.example.parse.bean.WebPage;
import org.example.repository.ParsedDataRepository;
import org.example.repository.jsonfiles.GsonFileRepository;

import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

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

    private void runApp() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    parse("http://34670.work");
                } catch (MalformedURLException | DataStorageException e) {
                    log.error(e.getMessage(), e);
                    cancel();
                }
            }
        };

        Timer time = new Timer("ParseTimer");

        time.scheduleAtFixedRate(task, 0, 5000L);
    }


    public void parse(String path) throws MalformedURLException, DataStorageException {
        ParsedDataRepository repository = GsonFileRepository.getInstance();
        WebPage webPage = ParseFactory.parse(path);
        log.info("Parsed: {}", webPage);
        if(webPage.hasPath()){
            repository.save(webPage);
        }
    }
}
