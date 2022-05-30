package com.CodeClan.example.bookfestival.components;

import com.CodeClan.example.bookfestival.models.*;
import com.CodeClan.example.bookfestival.repositories.*;
import com.CodeClan.example.bookfestival.utilities.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookingRepository bookingRepository;

    URL url = new URL("https://api.edinburghfestivalcity.com/events?festival=book&year=2021&key=UJQ7TKisbTVqCDz&signature=aba911fff8bf4bd53bfbbc885808a7ccc69ce84d");

    public DataLoader() throws MalformedURLException {}

    public void run(ApplicationArguments args) throws IOException {

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HttpResponseCode: " + conn.getResponseCode());
        } else {
            Scanner scanner = new Scanner(url.openStream());
            String data = "";

            while (scanner.hasNext()) {
                data += scanner.nextLine();
            }

            scanner.close();

            JSONArray jsonArr = new JSONArray(data);
            List<Author> dataList;

            dataList = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                Author author = new Author(Utilities.checkIfNull(jsonObj, "artist"), Utilities.checkIfNull(jsonObj, "url"));
                dataList.add(author);
            }

            for (int i = 0; i < dataList.size(); i++) {
                authorRepository.save(dataList.get(i));
            }

        }

    }
}
