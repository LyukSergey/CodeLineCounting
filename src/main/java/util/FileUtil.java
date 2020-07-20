package util;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {

    public static boolean isJavClass(String className) {
        return FilenameUtils.getExtension(className).equalsIgnoreCase("java");
    }
}
