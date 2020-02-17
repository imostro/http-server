package xyz.mostro.mytomcat.state;

import xyz.mostro.mytomcat.ServerHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: MOSTRO
 */
public class ReadHandler extends AbstractHandler {


    private static final ExecutorService threadPool = Executors.newFixedThreadPool(8);

    public ReadHandler(SocketChannel sc, ServerHandler serverHandler) {
        super(sc, serverHandler);
    }


    @Override
    public void processor() {
        try {
            byte[] bytes = new byte[1024];
            ByteBuffer wrap = ByteBuffer.wrap(bytes);
            if(sc.read(wrap) > 0){
                threadPool.execute(new WorkHandler(sc, serverHandler, bytes));
            }
        } catch (IOException e) {
            try {
                System.out.println("读操作时连接关闭");
                sc.close();
            } catch (IOException ex) {
            }
        }
    }
}
