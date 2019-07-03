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
        br.readLine(); // this will read the first line
        String line1 = null;
        while ((line1 = br.readLine()) != null) { //loop will run from 2nd line
            return null;
        }
        return br.lines().collect(Collectors.toList());
    }
}
