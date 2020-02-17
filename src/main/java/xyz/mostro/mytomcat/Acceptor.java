package xyz.mostro.mytomcat;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: MOSTRO
 */
public class Acceptor implements Runnable{

    private final ServerSocketChannel ssc;

    private final Selector selector;

    private final SubReactor[] subReactors;

    private final Thread[] threads;

    private final int DEFAULT_THREAD_NUM = 4;

    private int currentThread = 0;

    private int tNums;

    public Acceptor(ServerSocketChannel ssc, Selector selector) {
        this(ssc, selector, 0);
    }

    public Acceptor(ServerSocketChannel ssc, Selector selector, int tNums) {
        this.ssc = ssc;
        this.selector = selector;
        if(tNums <=0){
            tNums = DEFAULT_THREAD_NUM;
        }
        this.tNums = tNums;
        threads = new Thread[tNums];
        subReactors = new SubReactor[tNums];
        for (int i = 0; i < tNums; i++) {
            subReactors[i] = new SubReactor(ssc);
            threads[i] = new Thread(subReactors[i]);
            threads[i].start();
        }
    }

    @Override
    public void run() {
        try {
            SocketChannel client = ssc.accept();
            System.out.println("client connect success......");
            // 从一个线程组中取出一个SubReactor绑定的Selector
            SelectorHelper selectorHelper = subReactors[getAndIncrementCurrentThread()].getSelectorHelper();
            client.configureBlocking(false);
            // 对客户端的Socket注册读事件。
            SelectionKey key = selectorHelper.register(client, SelectionKey.OP_READ);
            key.attach(new ServerHandler(client, key,selectorHelper));
            key.selector().wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getAndIncrementCurrentThread(){
        if(currentThread >= tNums)  currentThread = 0;

        return currentThread++;
    }


}
