package com.doubao.finance.util;

import com.doubao.finance.constant.Constants;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class FileUtil
{
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static boolean checkFileName(String name)
    {
        return FilenameUtils.isExtension(name, Constants.UPLOAD_FILE_TYPES);
    }

    public static String getExtensionName(String filename)
    {
        return FilenameUtils.EXTENSION_SEPARATOR_STR + FilenameUtils.getExtension(filename);
    }

    public static boolean checkFileSize(InputStream stream)
            throws IOException
    {
        int fileSize = stream.available();
        return fileSize >= 10000000;
    }
}
