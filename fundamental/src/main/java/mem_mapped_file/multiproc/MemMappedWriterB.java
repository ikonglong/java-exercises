package mem_mapped_file.multiproc;

import com.google.common.util.concurrent.Uninterruptibles;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MemMappedWriterB {
  public static void main(String[] args) throws IOException {
    Path path = Paths.get("stats.data");
    System.out.println("MemMappedWriterB: " + path.toFile().getAbsolutePath());
    try (FileChannel fileChannel =
        FileChannel.open(
            path,
            StandardOpenOption.CREATE,
            StandardOpenOption.READ,
            StandardOpenOption.WRITE,
            StandardOpenOption.DSYNC)) {
      MappedByteBuffer mapped = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024 * 10);
      if (Objects.isNull(mapped)) {
        System.err.println("The MappedByteBuffer object is null");
        return;
      }

      for (int i = 1; i <= 100; i++) {
        /*FileLock lock = fileChannel.lock(0, 1024 * 10, false)
        try {
          mapped.put(Charset.forName("UTF-8").encode("A" + i + "\n"));
        } finally{
          lock.release();
        }*/

        try (FileLock lock = fileChannel.lock(0, 1024 * 10, false)) {
          mapped.put(Charset.forName("UTF-8").encode("B" + i + "\n"));
        }

        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
      }
    }
  }
}
