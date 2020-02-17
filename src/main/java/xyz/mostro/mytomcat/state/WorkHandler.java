package xyz.mostro.mytomcat.state;

import xyz.mostro.mytomcat.ServerHandler;
import xyz.mostro.mytomcat.web.HttpRequest;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 * @Author: MOSTRO
 */
public class WorkHandler extends AbstractHandler implements Runnable{

    private byte[] bytes;

    public WorkHandler(SocketChannel sc, ServerHandler serverHandler, byte[] bytes) {
        super(sc, serverHandler);
        this.bytes = bytes;
    }

    @Override
    public void processor() {
        decode();
    }

    private void decode(){
        String content = new String(bytes);
        System.out.println(content);
        String[] kvs = content.split("\r\n");
        String[] headers = kvs[0].split(" ");
        HttpRequest request = new HttpRequest();
        request.setMethod(headers[0]);
        request.setUrl(headers[1]);
        request.setVersion(headers[2]);
        Map<String, String> parameters = request.getHeaderParameters();
        for (int i = 1; i < kvs.length; i++) {
            if("".equals(kvs[i].trim()))    continue;
            String[] strings = kvs[i].split(": ");
            parameters.put(strings[0].trim(), strings[1].trim());
        }
        System.out.println(request);
        StringBuilder sb = new StringBuilder();
//        sb.append("HTTP/1.1 200 OK\n")
//                .append("Content-Type: text/html;\n")
//                .append("\r\n")
//                .append("Hello World");
//        ByteBuffer wrap = ByteBuffer.wrap(sb.toString().getBytes(StandardCharsets.UTF_8));
        this.serverHandler.getKey().interestOps(SelectionKey.OP_WRITE);
        this.serverHandler.getSelectorHelper().getSelector().wakeup();
    }

    @Override
    public void run() {
        processor();
    }
}
