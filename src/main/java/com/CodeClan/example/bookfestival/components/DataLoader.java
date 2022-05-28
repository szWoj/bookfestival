package com.CodeClan.example.bookfestival.components;

import com.CodeClan.example.bookfestival.models.*;
import com.CodeClan.example.bookfestival.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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



    public DataLoader(){}

    public void run(ApplicationArguments args){

        Author author = new Author("Faridah Àbíké-Íyímídé", "images.api.edinburghfestivalcity.com/5a/44/18/5a4418cbbf1692b496f15234efd65953d2c51870-original");
        authorRepository.save(author);

        Book book = new Book("Play the Cards You're Dealt",author, "Children","images.api.edinburghfestivalcity.com/5a/44/18/5a4418cbbf1692b496f15234efd65953d2c51870-original");
        bookRepository.save(book);

        Author author2 = new Author("Faridah Àbíké-Íyímídé", "images.api.edinburghfestivalcity.com/5a/44/18/5a4418cbbf1692b496f15234efd65953d2c51870-original");
        authorRepository.save(author2);

        Book book2 = new Book("Play the Cards You're Dealt",author2, "Children","images.api.edinburghfestivalcity.com/5a/44/18/5a4418cbbf1692b496f15234efd65953d2c51870-original");
        bookRepository.save(book2);

        Event event = new Event("<p>\\n\\tFaridah &Agrave;b&iacute;k&eacute;-&Iacute;y&iacute;m&iacute;d&eacute; is the instant New York Times and IndieBound bestselling author of <em>Ace of Spades</em>.", 10.50, "2021-08-15 17:15:00");
        eventRepository.save(event);

        Customer customer = new Customer("Szymon", 798914046, "szymon@szymon.com");
        customerRepository.save(customer);

        Booking booking = new Booking(customer, event);
        bookingRepository.save(booking);


    }
}
