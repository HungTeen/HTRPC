package love.pangteen.codec.compress;

import love.pangteen.annotations.SPI;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/11 17:29
 **/
@SPI
public interface Compress {

    byte[] compress(byte[] bytes);


    byte[] decompress(byte[] bytes);
}
