package com.example.testSocketConnection.connection;

import lombok.*;

import java.io.OutputStream;
import java.net.Socket;


@Builder
@Data
public class SocketOutputStreamDto {
    private Socket socket;
    private OutputStream outputStream;
}
