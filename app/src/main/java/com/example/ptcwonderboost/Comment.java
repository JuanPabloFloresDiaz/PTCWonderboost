package com.example.ptcwonderboost;

public class Comment {
    private String authorName;
    private byte[] authorImage;
    private int rating;
    private String opinion;

    public Comment(String authorName, byte[] authorImage, int rating, String opinion) {
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.rating = rating;
        this.opinion = opinion;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public byte[] getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(byte[] authorImage) {
        this.authorImage = authorImage;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
