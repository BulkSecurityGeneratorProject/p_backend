package com.medicai.pillpal.service;

import java.io.IOException;
import java.util.List;

public interface FileService {
    List<String> readLinesOfFile(String filePath) throws IOException;
}
