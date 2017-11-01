package pku.netlab.entity;

import io.netty.buffer.ByteBuf;

public class FileSegment {
    private ByteBuf content;
    private int start;

    public FileSegment() {}

    public FileSegment(ByteBuf content, int start) {
        this.content = content;
        this.start = start;
    }

    public ByteBuf getContent() {
        return content;
    }

    public FileSegment setContent(ByteBuf content) {
        this.content = content;
        return this;
    }

    public int getStart() {
        return start;
    }

    public FileSegment setStart(int start) {
        this.start = start;
        return this;
    }
}
