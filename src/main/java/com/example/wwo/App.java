package com.example.wwo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Scanner;

import com.example.wwo.Location.LocationType;
import com.example.wwo.search.Search;
import com.example.wwo.search.Search.Format;
import com.example.wwo.search.Search.PackageType;

/**
 * Hello world!
 * 
 */
public class App {

	public static void main(String[] args) {
		String key = "d426094d4249a864d493db100c9a1";
		String location = "195.56.119.209";

		Search lw = new Search(PackageType.FREE, key, LocationType.IP_ADDRESS,
				location);
		lw.setFormat(Format.JSON);
		try {
			InputStream input = lw.buildRequestAttributes("http").run();
			Scanner sc = new Scanner(input);
			while (sc.hasNextLine()) {
				System.out.println(sc.nextLine());
			}
			sc.close();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
