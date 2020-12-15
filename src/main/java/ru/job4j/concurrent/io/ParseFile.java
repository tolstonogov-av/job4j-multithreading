package ru.job4j.concurrent.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * Parses file.
 *
 * @version 2.0
 */
public class ParseFile {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }
    
    public synchronized File getFile() {
        return file;
    }

    public String getContent() {
        return getContentByCondition(data -> true);
    }

    public String getContentWithoutUnicode() {
        return getContentByCondition(data -> data < 0x80);
    }

    public String getContentByCondition(Predicate<Integer> predicate) {
        StringBuilder output = new StringBuilder();
        synchronized (this) {
            try (InputStream i = new FileInputStream(file)) {
                int data;
                while ((data = i.read()) > 0 && predicate.test(data)) {
                    output.append((char) data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

    public synchronized void saveContent(String content) {
        try (OutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}
