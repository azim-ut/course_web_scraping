package org.example;

import junit.framework.TestCase;
import org.example.parse.ParseFactory;

import java.util.concurrent.atomic.AtomicReference;

public class PatternTest
    extends TestCase
{
    public void testPattern()
    {
        var address = "http://34670.work/1/3432432";
        var patternRes = "*//34670.work";
        AtomicReference<String> out = new AtomicReference<>();

        ParseFactory.fetchPattern(address).ifPresent(out::set);

        assertEquals(patternRes, out.get());
    }
}
