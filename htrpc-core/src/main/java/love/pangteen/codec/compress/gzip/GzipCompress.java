package love.pangteen.codec.compress.gzip;

import love.pangteen.codec.compress.Compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/12 9:24
 **/
public class GzipCompress implements Compress {

    private static final int BUFFER_SIZE = 1024 << 2;

    @Override
    public byte[] compress(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(output)) {
            gzipOutputStream.write(bytes);
            gzipOutputStream.flush();
            gzipOutputStream.finish();
            return output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("gzip compress error", e);
        }
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();
             GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bytes))) {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while((n = gzipInputStream.read(buf)) != -1) {
                output.write(buf, 0, n);
            }
            return output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("gzip decompress error", e);
        }
    }
}