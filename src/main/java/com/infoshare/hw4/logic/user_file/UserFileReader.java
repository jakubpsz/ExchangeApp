package com.infoshare.hw4.logic.user_file;

import java.io.IOException;
import java.util.List;

public interface UserFileReader {
    void processingUsersFile(String fileName) throws IOException;

    List<Record> getData();
}
