package com.example.testSocketConnection.connection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionApplication {

	private static final Logger LOGGER = Logger.getLogger(ConnectionApplication.class.getName());

	public static void main(String[] args) throws IOException {

		List<String> channels = new ArrayList<>();
		channels.add("#sooflower");
		channels.add("#ddahyoni");
		channels.add("#amouranth");
		channels.add("#handongsuk");
		channels.add("#pacific8815");
		channels.add("#ok_ja");

		for (String channel : channels){
			new Thread(TempTwitchIrcConnection.builder().channel(channel)
					.logger(LOGGER).streamDto(CreateSocketOutputStream.builder()
							.logger(LOGGER).build().createSocketOutputStream()).build())
					.start();
		}
	}

}
