package xyz.mostro.mytomcat;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @Author: MOSTRO
 */
public class SubReactor implements Runnable {

    private final ServerSocketChannel ssc;

    private SelectorHelper selectorHelper;

    public SubReactor(ServerSocketChannel ssc) {
        this.ssc = ssc;
        try {
            selectorHelper = new SelectorHelper(Selector.open());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                //SubReactor中的selector事件监测
                if(selectorHelper.select()== 0){
                    continue;
                }

                Iterator<SelectionKey> iterator = selectorHelper.getSelector().selectedKeys().iterator();
                if(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    dispatch(key);
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key){
        ServerHandler handler = (ServerHandler) key.attachment();
        if(handler != null){
            if(key.isReadable()){
                handler.readProcessor();
            }else{
                System.out.println("触发了写事件");
                handler.writeProcessor();
            }
        }
    }

    public SelectorHelper getSelectorHelper() {
        return selectorHelper;
    }
}
