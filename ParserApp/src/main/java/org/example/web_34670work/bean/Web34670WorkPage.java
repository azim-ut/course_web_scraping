package org.example.web_34670work.bean;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.example.parse.bean.WebPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class Web34670WorkPage extends WebPage {

    private List<DataToParseItem> dataToParse = Collections.synchronizedList(new ArrayList<>());

    private List<String> youTubeLink = Collections.synchronizedList(new ArrayList<>());

    public Web34670WorkPage addDataToParseItem(DataToParseItem item){
        this.dataToParse.add(item);
        return this;
    }
}
