package org.td024;

import org.td024.console.AppConsole;
import org.td024.loader.InfoClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        info();

        AppConsole console = new AppConsole();
        console.mainMenu();
    }

    private static void info() {
        InfoClassLoader infoClassLoader = new InfoClassLoader();
        String className = "org.td024.info.AppInfo";
        String methodName = "inform";

        try {
            Class<?> appInfoClass = infoClassLoader.loadClass(className, true);
            Object appInfoInstance = appInfoClass.getDeclaredConstructor().newInstance();
            Method method = appInfoClass.getMethod(methodName);
            method.invoke(appInfoInstance);

            System.out.println("\nClassLoader: " + appInfoClass.getClassLoader());
        } catch (ClassNotFoundException e) {
            System.out.println(className + " class not found");
        } catch (InstantiationException e) {
            System.out.println("Couldn't create instance from the class: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.print(methodName + "() method not found");
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.out.println("Couldn't invoke " + methodName + "() method: " + e.getMessage());
        }
    }
}
