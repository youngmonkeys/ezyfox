package com.tvd12.ezyfox.testing.stream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.stream.EzyFileListInputstream;
import com.tvd12.test.assertion.Asserts;

public class EzyFileListInputstreamTest {
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void emptyFilesTest() throws IOException {
        InputStream sut = new EzyFileListInputstream(Collections.emptyList());
        sut.close();
    }
    
    @Test
    public void listFilesTest() throws IOException {
        // given
        List<File> files = Arrays.asList(new File("pom.xml"), new File("pom.xml"));
        InputStream sut = new EzyFileListInputstream(files);
        
        // when
        while(true) {
            if (sut.read() <= 0) {
                break;
            }
        }
        
        // then
        Asserts.assertEquals(sut.read(), -1);
        sut.close();
    }
    
    @Test
    public void colseWhenReadTest() throws IOException {
        // given
        List<File> files = Arrays.asList(new File("pom.xml"), new File("pom.xml"));
        InputStream sut = new EzyFileListInputstream(files);
        
        // when
        sut.read();
        
        // then
        sut.close();
        Throwable e = Asserts.assertThrows(sut::read);
        Asserts.assertEquals(e.getClass(), IOException.class);
        sut.close();
    }}