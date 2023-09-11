package org.example.parse;

import org.example.parse.bean.WebPage;

import java.net.URL;

public interface ParseService {
    ParseService init();

    WebPage parse(URL url);
}
