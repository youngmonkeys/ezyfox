package com.tvd12.ezyfox.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EzyFileListInputStream extends InputStream {

    private final List<File> files;
    private int currentFileIndex;
    private InputStream currentStream;
    private volatile boolean closed;
    private volatile boolean done;

    public EzyFileListInputStream(List<File> files) {
        if (files.isEmpty()) {
            throw new IllegalArgumentException("file list is empty");
        }
        this.files = files;
    }

    @Override
    public int read() throws IOException {
        while (true) {
            synchronized (this) {
                if (closed) {
                    throw new IOException("stream closed");
                }
                if (done) {
                    return -1;
                }
                if (currentStream == null) {
                    currentStream = new FileInputStream(files.get(currentFileIndex));
                }
                int b = currentStream.read();
                if (b > 0) {
                    return b;
                }
                currentStream.close();
                currentStream = null;

                if ((++currentFileIndex) >= files.size()) {
                    done = true;
                    return -1;
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            this.closed = true;
            if (currentStream != null) {
                currentStream.close();
                currentStream = null;
            }
        }
    }
}
