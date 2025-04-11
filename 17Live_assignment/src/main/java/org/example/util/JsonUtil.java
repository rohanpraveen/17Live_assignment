package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Section;
import org.example.model.Stream;


import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Section> readInput(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), new TypeReference<List<Section>>() {});
    }

    public static void writeOutput(Object output, String filePath) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), output);
    }
    public static void writeFlattenedOutput(List<Section> sections, String filePath) throws IOException {
        Map<String, List<String>> output = new LinkedHashMap<>();
        for (Section section : sections) {
            List<String> ids = section.getSectionData().stream()
                    .map(Stream::getStreamerID)
                    .collect(Collectors.toList());
            output.put(section.getSectionID(), ids);
        }
        writeOutput(output, filePath);
    }

    public static Map<String, List<String>> readOutput(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), new TypeReference<Map<String, List<String>>>() {});
    }



}