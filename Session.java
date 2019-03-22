package IA;

import java.io.Serializable;
import java.util.Date;

public class Session
implements Serializable
{
    String description;
    Date start;
    Date end;
    int rating;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("rating must be between 1 and 5");
        this.rating = rating;
    }
}
