package org.example.repository;

import org.example.exception.DataStorageException;
import org.example.parse.bean.WebPage;

public interface ParsedDataRepository {
    void save(WebPage webPage) throws DataStorageException;
}
