package ru.job4j.concurrent.wget;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * Download specified file with specified speed.
 *
 * @author Tolstonogov Alexey
 * @version 1.0
 */
public class Wget implements Runnable {

    private final String fileSource;

    private final File fileDest;

    private long delay;

    public Wget(String fileSource) {
        this.fileSource = fileSource.trim();
        this.fileDest = new File(
                this.fileSource.substring(this.fileSource.lastIndexOf('/') + 1)
        );
    }

    public File getFileDest() {
        return fileDest;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        fileDest.delete();
        DownloadIndication downloadIndication = new DownloadIndication();
        downloadIndication.printLoading();
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileSource).openStream());
             FileOutputStream out = new FileOutputStream(fileDest)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                downloadIndication.printLoading();
                if (delay != 0) {
                    Thread.sleep(delay);
                    delay = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Wget wget = new Wget(args[0]);
        float speedLimit = Float.parseFloat(args[1]);
        long finishTime;
        long size;
        float downloadedSize;
        File downloadedFile = wget.getFileDest();
        long delay;
        Thread thread = new Thread(wget);
        thread.start();
        long startTime = System.currentTimeMillis();
        while (thread.getState() != Thread.State.TERMINATED) {
            size = downloadedFile.length();
            Thread.sleep(1000);
            downloadedSize = (downloadedFile.length() - size) >> 10;
            if (downloadedSize > speedLimit) {
                delay = (long) ((downloadedSize / speedLimit - 1) * 1000);
                wget.setDelay(delay);
            }
        }
        finishTime = System.currentTimeMillis();
        System.out.println(
                "\nDownload speed: "
                        + (downloadedFile.length() / 1024) / ((finishTime - startTime) / 1000)
        );
    }
}
