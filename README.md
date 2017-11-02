# Netty UDP file demo

This is a demo code for Netty UDP C/S communication, which employs a simple customized message format and codec.

The client sends request to server to read certain segment of a file. The message client sends has a 8 bytes format,of which the first 4 bytes represents the start position of file, and the last 4 bytes represent the end position.
The byte order of positions is little endian.

The server listens on port 4444 and sends back file segment.

## Notice
Instead of NIO transport, Epoll transport is used here. So if you are using Windows/MacOS, you have to change the underlying transport.
Also, zero copy FileRegion is **not supported** with Epoll transport.