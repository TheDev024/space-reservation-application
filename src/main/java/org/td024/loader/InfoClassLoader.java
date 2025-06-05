package org.td024.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InfoClassLoader extends ClassLoader {
    private static final String PARENT_DIR = "ext-dir";

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> loadedClass;

        try {
            loadedClass = delegate(name);
        } catch (ClassNotFoundException ex) {
            loadedClass = load(name);
        }

        if (resolve) {
            resolveClass(loadedClass);
        }

        return loadedClass;
    }

    private Class<?> delegate(String name) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass == null) {
            loadedClass = super.loadClass(name, false);
        }
        return loadedClass;
    }

    private Class<?> load(String name) throws ClassNotFoundException {
        String classPath = PARENT_DIR + File.separator + name.replace('.', File.separatorChar) + ".class";

        try (FileInputStream fileInputStream = new FileInputStream(classPath)) {
            byte[] binaryClassData = new byte[fileInputStream.available()];
            if (fileInputStream.read(binaryClassData) == -1) throw new IOException("Failed to load class " + name);
            return defineClass(name, binaryClassData, 0, binaryClassData.length);
        } catch (IOException ex) {
            throw new ClassNotFoundException("Can't load class " + name + " from the file. With error " + ex.getMessage());
        }
    }
}
