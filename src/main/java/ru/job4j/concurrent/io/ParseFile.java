package ru.job4j.concurrent.io;

import java.io.*;

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
        StringBuilder output = new StringBuilder();
        synchronized (this) {
            try (InputStream i = new FileInputStream(file)) {
                int data;
                while ((data = i.read()) > 0) {
                    output.append((char) data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

    public String getContentWithoutUnicode() {
        StringBuilder output = new StringBuilder();
        synchronized (this) {
            try (InputStream i = new FileInputStream(file)) {
                int data;
                while ((data = i.read()) > 0 && data < 0x80) {
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
