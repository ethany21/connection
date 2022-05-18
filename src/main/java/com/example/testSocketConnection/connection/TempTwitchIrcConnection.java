package com.example.testSocketConnection.connection;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Builder
public class TempTwitchIrcConnection implements Runnable {

    private final String channel;
    private final Logger logger;

    public void run() {

        while (true) {
            SocketOutputStreamDto streamDto = null;
            try {
                streamDto = CreateSocketOutputStream.builder().logger(logger).build().createSocketOutputStream();
                streamDto.getOutputStream().write(("JOIN " + channel + "\n").getBytes(StandardCharsets.UTF_8));
                logger.info("check if socket Connected: " + streamDto.getSocket().isConnected());
                //            logger.info("check outputstream status: " + streamDto.getOutputStream());
                readMessageFromOutputStream(streamDto);
            } catch (IOException e) {
                logger.info("cannot connect to " + channel + ", reconnection");
            } finally {
                try {
                    assert streamDto != null;
                    streamDto.getSocket().close();
                    streamDto.getOutputStream().close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }
    }


    public void readMessageFromOutputStream(SocketOutputStreamDto streamDto) throws IOException {
        String line = null;
        InputStreamReader inputStreamReader = new InputStreamReader(streamDto.getSocket().getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while ((line = bufferedReader.readLine()) != null) {  /* SocketException: Connection Reset occurs on this line*/
            catchUnexpectedMessage(line, streamDto);
        }
    }

    public void catchUnexpectedMessage(String line, SocketOutputStreamDto streamDto) throws IOException {
        try {
            kafkaOrIrcMultiplexer(line, streamDto);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info("I Caught exception " + e.getMessage() + " which is : " + line);
        }
    }

    public void kafkaOrIrcMultiplexer(String line, SocketOutputStreamDto streamDto) throws IOException {
        if (line.split(" ")[0].equals("PING")) {
            streamDto.getOutputStream().write(("PONG\n").getBytes(StandardCharsets.UTF_8));
        } else {
            System.out.println(line);
        }
    }

}