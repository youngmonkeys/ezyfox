package com.tvd12.ezyfox.testing;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@SuppressWarnings("unused")
public class JarClassLoader extends ClassLoader {

    //used to cache already defined classes
    @SuppressWarnings("rawtypes")
    private final Hashtable classes = new Hashtable();

    //calls the parent class loader's constructor
    public JarClassLoader() {
        super(JarClassLoader.class.getClassLoader());
    }

    @SuppressWarnings({"rawtypes"})
    public Class loadClass(String className) {
        return findClass(className);
    }

    @SuppressWarnings({"unchecked", "resource", "rawtypes"})
    public Class findClass(String className) {
        byte[] classByte;
        Class result;

        result = (Class) classes.get(className); //checks in cached classes
        if (result != null) {
            return result;
        }

        try {
            return findSystemClass(className);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //Path to the jar file
            String jarFile = "jar/test.jar";
            JarFile jar = new JarFile(jarFile);
            JarEntry entry = jar.getJarEntry(className + ".class");
            InputStream is = jar.getInputStream(entry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = is.read();
            while (-1 != nextValue) {
                byteStream.write(nextValue);
                nextValue = is.read();
            }

            classByte = byteStream.toByteArray();
            result = defineClass(className, classByte, 0, classByte.length, null);
            classes.put(className, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
