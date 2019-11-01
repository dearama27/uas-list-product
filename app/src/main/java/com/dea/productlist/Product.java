package com.dea.productlist;

public class Product {
    String title, poster_path, release_date, overview;

    public Product(String title, String poster_path, String release_date, String overview) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }
}
