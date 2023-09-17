package org.example.repository.jsonfiles;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.DataStorageException;
import org.example.parse.bean.WebPage;
import org.example.repository.ParsedDataRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Slf4j
public class GsonFileRepository implements ParsedDataRepository {

    private static final String ROOT_DIR = "./data";
    private final Gson gson = new Gson();

    @Getter
    private final static ParsedDataRepository instance = new GsonFileRepository();

    @Override
    public void save(WebPage webPage) throws DataStorageException{
        try {
            URL url = new URL(webPage.getPath());
            String fileName = url.getPath().replaceAll("\\W", "");
            String host = url.getHost();
            String targetDirPath = ROOT_DIR + "/" + host;
            File targetDir = new File(targetDirPath);

            if(!targetDir.exists()){
                targetDir.mkdir();
            }
            if(targetDir.exists() && !targetDir.canWrite()){
                throw new DataStorageException("Target directory is locked or can't be crated: " + targetDirPath);
            }

            AtomicInteger index = new AtomicInteger(0);
            File[] list = targetDir.listFiles();
            if(list != null){
                Stream.of(list).min((a, b) -> b.getName().compareTo(a.getName()))
                        .ifPresent(file -> {
                            try{
                                String[] parts = file.getName().split("_");
                                index.set(Integer.parseInt(parts[0]) + 1);
                            } catch (NumberFormatException e){
                                log.error(e.getMessage(), e);
                            }
                        });
            }

            String targetFileName = targetDirPath + "/" + index.get() + "_" + fileName + ".json";
            FileWriter writer = new FileWriter(targetFileName);
            gson.toJson(webPage, writer);
            writer.flush();
        } catch (IOException e) {
            throw new DataStorageException(e.getMessage(), e);
        }
    }
}
