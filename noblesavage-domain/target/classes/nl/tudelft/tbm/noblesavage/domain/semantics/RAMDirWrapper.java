package nl.tudelft.tbm.noblesavage.domain.semantics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.store.RAMDirectory;

public class RAMDirWrapper {

  private static final Logger log = Logger.getLogger(RAMDirWrapper.class.getName());

  public DirectoryWrap wrap(Directory directory) {
    String header = "";
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      for (String name : directory.listAll()) {
        IndexInput input = directory.openInput(name);
        if (input != null) {
          int lenght = (int) input.length();
          header += name + ":" + lenght + ";";
          byte[] bytes = new byte[lenght];
          input.readBytes(bytes, 0, lenght);
          os.write(bytes);
        }
        input.close();
      }
      return new DirectoryWrap(header, os.toByteArray());
    } catch (Exception err) {
      log.error(err.getMessage(), err);
    }
    return null;
  }

  public Directory unwrap(DirectoryWrap wrap) {
    String[] items = wrap.getHeader().split(";");
    ByteArrayInputStream is = new ByteArrayInputStream(wrap.getContents());

    RAMDirectory directory = new RAMDirectory();
    try {
      for (String item : items) {
        String[] namelenght = item.split(":");
        String name = namelenght[0];
        int length = Integer.parseInt(namelenght[1]);
        byte[] b = new byte[length];
        if (is.read(b, 0, length) >= 0) {
          log.info("read bytes: " + b.length + "(length: " + length + ")");
          IndexOutput output = directory.createOutput(name);
          output.writeBytes(b, length);
          output.close();
        }
      }
      return directory;
    } catch (Exception err) {
      log.error(err.getMessage(), err);
    }
    return null;
  }

  class DirectoryWrap {

    String header;
    byte[] contents;

    public DirectoryWrap(String header, byte[] contents) {
      super();
      this.header = header;
      this.contents = contents;
    }

    public String getHeader() {
      return header;
    }

    public void setHeader(String header) {
      this.header = header;
    }

    public byte[] getContents() {
      return contents;
    }

    public void setContents(byte[] contents) {
      this.contents = contents;
    }

  }

}
