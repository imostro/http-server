package xyz.mostro.mytomcat.state;

import xyz.mostro.mytomcat.ServerHandler;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @Author: MOSTRO
 */
public abstract class AbstractHandler implements StateHandler {


    protected SocketChannel sc;
    protected ServerHandler serverHandler;

    public AbstractHandler(SocketChannel sc,ServerHandler serverHandler) {
        this.sc = sc;
        this.serverHandler = serverHandler;
    }
}
