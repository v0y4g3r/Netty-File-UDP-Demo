package pku.netlab;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.sun.org.apache.xpath.internal.SourceTree;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Test {

    public static void main(String[] args) {
        System.err.println(new File("sasa").getAbsolutePath());

        CompositeByteBuf b = Unpooled.compositeBuffer();
        int start = 10;
        int end = 20;

        b.addComponent(true, Unpooled.copiedBuffer("hello,".getBytes()));
        b.addComponent(true, Unpooled.copiedBuffer("world!\n".getBytes()));
        try {
//            RandomAccessFile raFile = new RandomAccessFile("data/data.txt", "r");
//
//            raFile.seek(start);
//            int length = end - start;
//            byte[] bArr = new byte[length];
//            raFile.read(bArr, 0, length);
//            raFile.close();
//            ByteBuf udbb = Unpooled.directBuffer().writeBytes(bArr);
//            b.addComponent(true, udbb);
//            System.err.println(b.toString(CharsetUtil.UTF_8));
            HashCode code = com.google.common.io.Files.hash(new File("data/data.txt"), Hashing.sha256());
            System.err.println(code.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
