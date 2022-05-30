package com.CodeClan.example.bookfestival.components;

import com.CodeClan.example.bookfestival.models.*;
import com.CodeClan.example.bookfestival.repositories.*;
import com.CodeClan.example.bookfestival.utilities.Utilities;
import com.CodeClan.example.bookfestival.utilities.UtilitiesInt;
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
    VenueRepository venueRepository;

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
            List<Author> authorList;
            List<Venue> venueList;
            List<Event> eventList;
            List<Book> bookList;


            authorList = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                Author author = new Author(Utilities.checkIfNull(jsonObj, "artist"), Utilities.checkIfNull(jsonObj, "url"));
                authorList.add(author);
            }

            for (int i = 0; i < authorList.size(); i++) {
                authorRepository.save(authorList.get(i));
            }

            venueList = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                Venue venue = new Venue(Utilities.checkIfNull(jsonObj.getJSONObject("venue"), "name"), Utilities.checkIfNull(jsonObj.getJSONObject("venue"), "address"), UtilitiesInt.checkIfNull(jsonObj.getJSONObject("venue"), "phone"),Utilities.checkIfNull(jsonObj.getJSONObject("performance_space"), "wheelchair_access"), UtilitiesInt.checkIfNull(jsonObj.getJSONObject("performance_space"), "capacity"), jsonObj.getDouble("latitude"), jsonObj.getDouble("longitude"));
                venueList.add(venue);
            }

            for (int i = 0; i < venueList.size(); i++) {
                venueRepository.save(venueList.get(i));
            }

            bookList = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++){
                Book book = new Book("hi", authorList.get(i), "hi", "http://www.staggeringbeauty.com/");
                bookList.add(book);
            }

            for (int i = 0; i < bookList.size(); i++) {
                bookRepository.save(bookList.get(i));
            }

            eventList = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++){
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                Event event = new Event(Utilities.checkIfNull(jsonObj, "description"),UtilitiesInt.checkIfNull(jsonObj.getJSONArray("performances").getJSONObject(0), "price"), Utilities.checkIfNull(jsonObj.getJSONArray("performances").getJSONObject(0), "start"), bookList.get(i), venueList.get(i));
                eventList.add(event);
            }

            for (int i = 0; i < eventList.size(); i++) {
                eventRepository.save(eventList.get(i));
            }




        }

    }
}
