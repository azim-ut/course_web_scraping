package org.example.web_34670work;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.example.parse.ParseService;
import org.example.parse.bean.WebPage;
import org.example.web_34670work.bean.DataToParseItem;
import org.example.web_34670work.bean.Web34670WorkPage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Slf4j
public class HtmlUnitParseService implements ParseService {

    private static final HtmlUnitParseService instance = new HtmlUnitParseService();

    private WebClient webClient;

    public HtmlUnitParseService(){
        this.init();
    }

    public static ParseService getInstance(){
        return instance;
    }

    @Override
    public ParseService init() {
        webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        return this;
    }

    @Override
    public WebPage parse(URL url) {
        Web34670WorkPage out = new Web34670WorkPage();

        try {
            HtmlPage page = webClient.getPage(url);
            List<HtmlElement> titlesList = page.getByXPath("//title");
            titlesList.forEach(htmlElement -> {
                out.setTitle(htmlElement.getTextContent());
            });
            parseYouTubeLink(page, out);
            parseDataToParse(page, out);


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return out;
    }

    private void parseYouTubeLink(HtmlPage page, Web34670WorkPage out){
        List<HtmlElement> linksList = page.getByXPath("//a[contains(@href, 'youtube.com')]");
        linksList.forEach(htmlElement -> {
            out.getYouTubeLink().add(htmlElement.getAttribute("href"));
        });
    }

    private void parseDataToParse(HtmlPage page, Web34670WorkPage out){
        List<HtmlElement> dataToParseBlocks = page.getByXPath("""
                    //h2[@data-aid="FAQ_SECTION_TITLE_RENDERED"]
                    /../..
                    /div[@data-ux="Grid"]
                    /div/div/div
                    """);
        dataToParseBlocks.forEach(htmlElement -> {
            DataToParseItem item = new DataToParseItem();
            htmlElement.getByXPath("""
                        ./button/span
                        """).stream().findFirst().ifPresent(el -> {
                item.setTitle(((HtmlElement) el).getTextContent());
            });
            htmlElement.getByXPath("""
                        ./div//p
                        """).stream().findFirst().ifPresent(el -> {
                item.setDetails(((HtmlElement) el).getTextContent());
            });
            out.addDataToParseItem(item);
        });
    }
}
