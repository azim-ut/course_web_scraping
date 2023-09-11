package org.example.web_34670work;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.example.parse.ParseService;
import org.example.parse.bean.WebPage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Slf4j
public class HtmlUnitParseService implements ParseService {

    private WebClient webClient;

    @Override
    public ParseService init() {
        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        return this;
    }

    @Override
    public WebPage parse(URL url) {
        WebPage out = new WebPage();

        try {
            HtmlPage page = webClient.getPage(url);
            List<HtmlElement> titlesList = page.getByXPath("//title");
            titlesList.forEach(htmlElement -> {
                out.setTitle(htmlElement.getTextContent());
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return out;
    }
}
