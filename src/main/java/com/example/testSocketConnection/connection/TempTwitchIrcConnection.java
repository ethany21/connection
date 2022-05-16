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
    private final SocketOutputStreamDto streamDto;

    public void run() {
        try {
            streamDto.getOutputStream().write(("JOIN " + channel + "\n").getBytes(StandardCharsets.UTF_8));
            logger.info("check if socket Connected: " + streamDto.getSocket().isConnected());
//            logger.info("check outputstream status: " + streamDto.getOutputStream());
            readMessageFromOutputStream();
        } catch (IOException e) {
            logger.info("cannot connect to " + channel);
            e.printStackTrace();
        } finally {
            try {
                streamDto.getSocket().close();
                streamDto.getOutputStream().close();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }
    }


    public void readMessageFromOutputStream() throws IOException {
        String line = null;
        InputStreamReader inputStreamReader = new InputStreamReader(streamDto.getSocket().getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while ((line = bufferedReader.readLine()) != null) {  /* SocketException: Connection Reset occurs on this line*/
            catchUnexpectedMessage(line);
        }
    }

    public void catchUnexpectedMessage(String line) throws IOException {
        try {
            kafkaOrIrcMultiplexer(line);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info("I Caught exception " + e.getMessage() + " which is : " + line);
        }
    }

    public void kafkaOrIrcMultiplexer(String line) throws IOException {
        if (line.split(" ")[0].equals("PING")) {
            streamDto.getOutputStream().write(("PONG\n").getBytes(StandardCharsets.UTF_8));
        } else {
            System.out.println(line);
        }
    }

}