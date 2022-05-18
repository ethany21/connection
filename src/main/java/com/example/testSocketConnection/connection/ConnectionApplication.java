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
		channels.add("#kimdoe");
		channels.add("#jungtaejune");
		channels.add("#2chamcham2");
		channels.add("#obm1025");
		channels.add("#rooftopcat99");
		channels.add("#tranth");
		channels.add("#dkwl025");

		channels.add("#sooflower");
		channels.add("#luna_ddd");
		channels.add("#eclipia");
		channels.add("#e_saem");
		channels.add("#agueppo");
		channels.add("#dda_ju");
		channels.add("#hanryang1125");

		channels.add("#handongsuk");
		channels.add("#tmxk319");
		channels.add("#woowakgood");
		channels.add("#lilpaaaaaa");
		channels.add("#vo_ine");
		channels.add("#gosegugosegu");
		channels.add("#maoruya");

		channels.add("#109ace");
		channels.add("#looksam");
		channels.add("#ao_o5");
		channels.add("#95pingman");
		channels.add("#kumikomii");
		channels.add("#kuiki771");

		channels.add("#esl_dota2");
		channels.add("#asmongold");
		channels.add("#kato_junichi0817");
		channels.add("#loltyler1");
		channels.add("#dota2mc");
		channels.add("#thisisnotgeorgenotfound");

		channels.add("#just_ns");
		channels.add("#woohankyung");
		channels.add("#ddahyoni");
		channels.add("#d_obby");
		channels.add("#stylishnoob4");
		channels.add("#fps_shaka");

		for (String channel : channels){
			new Thread(TempTwitchIrcConnection.builder().channel(channel)
					.logger(LOGGER).build()).start();
		}
	}

}
