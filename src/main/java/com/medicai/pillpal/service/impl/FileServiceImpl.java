package com.medicai.pillpal.service.impl;

import com.medicai.pillpal.service.FileService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<String> readLinesOfFile(String filePath) throws IOException {

        BufferedReader br = Files.newBufferedReader(Paths.get(filePath));
        //br returns as stream and convert it into a List
        return br.lines().collect(Collectors.toList());
    }
}
