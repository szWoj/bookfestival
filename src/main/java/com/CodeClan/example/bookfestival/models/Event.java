package com.CodeClan.example.bookfestival.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="events")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private Double price;

    @Column(name="dateTime")
    private String dateTime;

    @JsonIgnoreProperties({"event"})
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Booking> bookings;


//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "event")
//    private Book book;

    public Event(String description, Double price, String dateTime) {
        this.description = description;
        this.price = price;
        this.dateTime = dateTime;
    }

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    //    public Book getBook() {
//        return book;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//    }
}
