package com.example.bean_compiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 */

public class FileUtils {
    public static void print(String text)
    {
        File file=new File("C:\\Users\\lxc\\Desktop\\log.txt");
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter=new FileWriter(file.getAbsoluteFile(),true);
            fileWriter.write(text+"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
