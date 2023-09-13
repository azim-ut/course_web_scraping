package org.example.parse;

import org.example.parse.bean.WebPage;
import org.example.web_34670work.HtmlUnitParseService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

public class ParseFactory {

    private static final Map<String, ParseService> parseServices = Map.of(
            "*//34670.work", new HtmlUnitParseService().init(),
            "*//34670.work/1/", new HtmlUnitParseService().init(),
            "*//34670.work/*/", new HtmlUnitParseService().init()
    );

    public static Optional<String> fetchPattern(String address){
        return parseServices.keySet().stream().
                sorted((a, b) -> Integer.compare(b.replace("*", "").length(), a.replace("*", "").length()))
                .filter(pattern -> {
                    if(!address.startsWith(pattern)){
                        var preparedPattern = "^" + pattern.replace("*", "([^\\/]+)");
                        var addressAfterPatternUse = address.replaceFirst(preparedPattern, "*");
                        return !address.equals(addressAfterPatternUse);
                    }
                    return true;
                })
                .findFirst();
    }

    public static WebPage parse(String path) throws MalformedURLException {
        var url = new URL(path);
        return parseServices.get(path).parse(url);
    }

}
