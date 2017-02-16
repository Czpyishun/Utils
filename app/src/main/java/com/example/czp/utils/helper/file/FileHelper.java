package com.example.czp.utils.helper.file;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileHelper {


    /**
     * 解压缩zip文件
     *
     * @param zipFile  zip文件的完整路径
     * @param location 解压路径
     */
    public boolean unpackZip(String zipFile, String location) {
        try {
            // Extract entries while creating required sub-directories
            ZipFile zf = new ZipFile(zipFile);
            Enumeration<?> e = zf.entries();

            while (e.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) e.nextElement();
                File destinationFilePath = new File(location, entry.getName());

                // create directories if required.
                destinationFilePath.getParentFile().mkdirs();

                // if the entry is directory, leave it. Otherwise extract it.
                if (!entry.isDirectory()) {
                    // Get the InputStream for current entry of the zip file
                    // using InputStream getInputStream(Entry entry) method.
                    BufferedInputStream bis = new BufferedInputStream(zf.getInputStream(entry));

                    int b;
                    byte buffer[] = new byte[1024];

                    // read the current entry from the zip file, extract it and
                    // write the extracted file.
                    FileOutputStream fos = new FileOutputStream(destinationFilePath);
                    BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);

                    while ((b = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, b);
                    }

                    bos.flush();
                    bos.close();
                    bis.close();
                }
            }
            zf.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    //删除文件
    public void deleteFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.isFile()) {
                file.delete();
                return;
            }

            if (file.isDirectory()) {
                File[] childFiles = file.listFiles();
                if (childFiles == null || childFiles.length == 0) {
                    file.delete();
                    return;
                }

                for (int i = 0; i < childFiles.length; i++) {
                    deleteFile(childFiles[i].getPath());
                }
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Copy file...
     *
     * @param from_path
     * @param to_path
     * @return
     */
    public boolean copyFile(String from_path, String to_path) {
        //check files
        if (TextUtils.isEmpty(from_path) || TextUtils.isEmpty(to_path)) {
            return false;
        }

        if (!new File(from_path).exists()) {
            return false;
        }

        FileInputStream from = null;
        FileOutputStream to = null;

        try {

            from = new FileInputStream(from_path);
            to = new FileOutputStream(to_path);

            return copyStream(from, to, new File(from_path).length());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (from != null) {
                    from.close();
                }
                if (to != null) {
                    to.close();
                }
            } catch (Exception e) {
                //
            }
        }
        return false;
    }

    /**
     * Copy InputStream to OutputStream
     * This func don't close streams...
     *
     * @param from
     * @param to
     * @param len  should given len when from.available() dont work...
     * @return
     */
    public boolean copyStream(InputStream from, OutputStream to, long len) {

        if (from == null || to == null) {
            return false;
        }
        try {
            //init length...
            if (len < 0) {
                len = from.available();
            }
            if (len < 0) {
                return false;
            }

            //init buffers
            byte[] buffer = new byte[1024 * 100];
            int readed = 0;
            int count = 0;

            //start to copy
            while ((readed = from.read(buffer)) > 0
                    && len > count) {
                to.write(buffer, 0, readed);
                count += readed;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isFileExists(String local_path, long size) {
        boolean isApkExists = false;
        if (!TextUtils.isEmpty(local_path)) {
            File file = new File(local_path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    isApkExists = getFolderSize(file) == size;
                } else {
                    isApkExists = file.length() == size;
                }


            }
        }
        return isApkExists;
    }


    public long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }


    public boolean isApkExists(String local_path) {
        boolean isApkExists = false;
        if (!TextUtils.isEmpty(local_path)) {
            File file = new File(local_path);
            if (file.exists()) {
                isApkExists = true;
            }
        }
        return isApkExists;
    }


    public boolean updateFileName(String oldPath, String newPath) {
        boolean isUpdateName = false;
        if (isApkExists(oldPath)) {
            File file = new File(oldPath);
            isUpdateName = file.renameTo(new File(newPath));
        }
        return isUpdateName;
    }

}
