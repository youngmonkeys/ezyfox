package com.tvd12.ezyfox.testing.stream;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.stream.EzyFileListInputstream;
import com.tvd12.test.assertion.Asserts;

public class EzyFileListInputstreamTest {
    
    @Test
    public void emptyFilesTest() throws IOException {
        // given
        EzyFileListInputstream sut = new EzyFileListInputstream(Collections.emptyList());
        
        // when
        // then
        Asserts.assertEquals(sut.read(), -1);
        sut.close();
    }
    
    @Test
    public void listFilesTest() throws IOException {
        // given
        List<File> files = Arrays.asList(new File("pom.xml"), new File("pom.xml"));
        EzyFileListInputstream sut = new EzyFileListInputstream(files);
        
        // when
        while(true) {
            if (sut.read() <= 0) {
                break;
            }
        }
        
        // then
        Throwable e = Asserts.assertThrows(sut::read);
        Asserts.assertEquals(e.getClass(), IOException.class);
        sut.close();
    }
    
    @Test
    public void colseWhenReadTest() throws IOException {
        // given
        List<File> files = Arrays.asList(new File("pom.xml"), new File("pom.xml"));
        EzyFileListInputstream sut = new EzyFileListInputstream(files);
        
        // when
        sut.read();
        
        // then
        sut.close();
        Throwable e = Asserts.assertThrows(sut::read);
        Asserts.assertEquals(e.getClass(), IOException.class);
        sut.close();
    }
}
