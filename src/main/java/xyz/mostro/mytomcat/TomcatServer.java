package xyz.mostro.mytomcat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @Author: MOSTRO
 */
public class TomcatServer {

    private int port;

    private ServerSocketChannel ssc;

    private Selector selector;

    public TomcatServer() {
        this(8080);
    }

    public TomcatServer(int port) {
        this.port = port;
        try {
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress(this.port));
            selector = Selector.open();
            SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);
            key.attach(new Acceptor(ssc, selector));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(){

    }

    public void start(){

        try {
            while(true){
                System.out.println("Server start, waiting client connect....");

                if(selector.select() == 0){
                    continue;
                }

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    dispatch(key);
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key){
        Runnable r = (Runnable) key.attachment();
        if(r != null){
            r.run();
        }
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args){
        TomcatServer server = new TomcatServer();
        server.start();
    }
}
