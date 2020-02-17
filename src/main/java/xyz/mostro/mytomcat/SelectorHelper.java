package xyz.mostro.mytomcat;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * @Author: MOSTRO
 */
public class SelectorHelper {

    private volatile boolean mark = false;
    private final Selector selector;

    public SelectorHelper(Selector selector) {
        this.selector = selector;
    }

    public Selector getSelector() {
        return selector;
    }

    /**
     * 解决同一个Selector同时调用register和select出现堵塞问题
     * @param sc Selectable channel
     * @param op SelectKey option
     * @return Selection key
     * @throws ClosedChannelException
     */
    public synchronized SelectionKey register(SelectableChannel sc, int ops) throws ClosedChannelException {
        mark = true;
        selector.wakeup();
        SelectionKey key = sc.register(selector, ops);
        mark = false;
        return key;
    }

    /**
     * 代替原本的select
     * @return
     * @throws IOException
     */
    public int select() throws IOException {
        for (; ;){
            if(mark == true)    continue;
            int select = selector.select();
            if(select >0) return select;
        }
    }
}
