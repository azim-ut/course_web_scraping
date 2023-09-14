package org.example;

import junit.framework.TestCase;
import org.example.parse.ParseFactory;
import org.example.web_34670work.HtmlUnitParseService;

import java.util.concurrent.atomic.AtomicReference;

public class PatternTest
    extends TestCase
{
    public void testServiceInstances() throws InterruptedException {
        Thread thread1 = new Thread(()->{
            int epoch = 10;
            while(epoch --> 0){
                var service = HtmlUnitParseService.getInstance();
            }
        });

        Thread thread2 = new Thread(()->{
            int epoch = 10;
            while(epoch --> 0){
                var service = HtmlUnitParseService.getInstance();
            }
        });

        Thread thread3 = new Thread(()->{
            int epoch = 10;
            while(epoch --> 0){
                var service = HtmlUnitParseService.getInstance();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    public void testPattern()
    {
        var address = "http://34670.work/1/3432432";
        var patternRes = "*//34670.work/1/";
        AtomicReference<String> out = new AtomicReference<>();

        ParseFactory.fetchPattern(address).ifPresent(out::set);

        assertEquals(patternRes, out.get());
    }
}
