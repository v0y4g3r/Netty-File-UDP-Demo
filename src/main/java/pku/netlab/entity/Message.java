package pku.netlab.entity;

import java.net.InetSocketAddress;

public class Message {
    public int getStart() {
        return start;
    }

    private int start;
    private int end;
    private InetSocketAddress sender;

    public Message(int start, int end, InetSocketAddress sender) {
        this.start = start;
        this.end = end;
        this.sender = sender;
    }

    public int getEnd() {
        return end;
    }

    public Message setStart(int start) {
        this.start = start;
        return this;
    }

    public Message setEnd(int end) {
        this.end = end;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("start=").append(start);
        sb.append(", end=").append(end);
        sb.append('}');
        return sb.toString();
    }

    public InetSocketAddress getSender() {
        return sender;
    }

    public Message setSender(InetSocketAddress sender) {
        this.sender = sender;
        return this;
    }
}
