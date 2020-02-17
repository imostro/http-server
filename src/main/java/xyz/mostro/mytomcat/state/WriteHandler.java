package xyz.mostro.mytomcat.state;

import xyz.mostro.mytomcat.ServerHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author: MOSTRO
 */
public class WriteHandler extends AbstractHandler{


    public WriteHandler(SocketChannel sc, ServerHandler serverHandler) {
        super(sc, serverHandler);
    }

    @Override
    public void processor() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("HTTP/1.1 200 OK\n")
                .append("Content-Type: text/html;\n")
                .append("\r\n")
                .append("Hello World");
            ByteBuffer wrap = ByteBuffer.wrap(sb.toString().getBytes(StandardCharsets.UTF_8));
            sc.write(wrap);
            sc.close();
        } catch (IOException e) {
            System.out.println("写操作时连接关闭");
            try {
                sc.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
