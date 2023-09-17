package org.example.parse;

import lombok.extern.slf4j.Slf4j;
import org.example.parse.bean.WebPage;
import org.example.web_34670work.HtmlUnitParseService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ParseFactory {

    private static final Map<String, ParseService> parseServices = Map.of(
            "*//34670.work", HtmlUnitParseService.getInstance(),
            "*//34670.work/1/", HtmlUnitParseService.getInstance(),
            "*//34670.work/*/", HtmlUnitParseService.getInstance()
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
        AtomicReference<WebPage> out = new AtomicReference<>(new WebPage());
        fetchPattern(path).ifPresent(row -> {
            try {
                var res = parseServices.get(row).parse(url);
                    res.setPath(path);
                out.set(res);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });
        return out.get();
    }

}
