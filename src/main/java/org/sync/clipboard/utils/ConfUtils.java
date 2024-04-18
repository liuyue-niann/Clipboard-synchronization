package org.sync.clipboard.utils;


import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ConfUtils {

    public static Set<String> getConfIpList() {
        try (InputStream inputStream = new FileInputStream("./address.conf")) {
            HashSet<String> set = new HashSet<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                set.add(line);
            }
            return set;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
