package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {

    static final int BUFFER = 2048;

    public static List<File> unzipProjectFile(File zip) {
        String dir = zip.getParent();
        List<File> fileList = new ArrayList<File>();
        try {
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(zip);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                int count;
                byte data[] = new byte[BUFFER];
                File f = new File(dir + "/" + entry.getName());
                FileOutputStream fos = new FileOutputStream(f);
                dest = new BufferedOutputStream(fos, BUFFER);
                while ((count = zis.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
                fileList.add(f);
            }
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }
}
