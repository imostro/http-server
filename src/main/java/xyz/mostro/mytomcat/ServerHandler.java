package xyz.mostro.mytomcat;

import xyz.mostro.mytomcat.state.ReadHandler;
import xyz.mostro.mytomcat.state.StateHandler;
import xyz.mostro.mytomcat.state.WriteHandler;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @Author: MOSTRO
 */
public class ServerHandler{

    private SocketChannel sc;

    private SelectorHelper selectorHelper;

    private StateHandler readHandler;

    private StateHandler writeHandler;

    private SelectionKey key;


    public ServerHandler(SocketChannel sc, SelectionKey key, SelectorHelper selectorHelper) {
        this.sc = sc;
        this.selectorHelper = selectorHelper;
        this.key = key;
        readHandler = new ReadHandler(sc,  this);
        writeHandler = new WriteHandler(sc, this);
    }

    public void readProcessor(){
        readHandler.processor();
    }

    public void writeProcessor(){
        writeHandler.processor();
    }

    public SelectorHelper getSelectorHelper() {
        return selectorHelper;
    }

    public SelectionKey getKey(){
        return this.key;
    }
}
