package love.pangteen.codec.compress.ignore;

import love.pangteen.codec.compress.Compress;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/16 9:56
 **/
public class IgnoreCompress implements Compress {

    @Override
    public byte[] compress(byte[] bytes) {
        return bytes;
    }

    @Override
    public byte[] decompress(byte[] bytes) {
        return bytes;
    }

}
