package client.models;

import exceptions.ValidationError;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Integer x; //Поле не может быть null
    private long y;

    public Coordinates() {}

    public Coordinates(Integer x, long y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public void validateX() throws ValidationError {
        validateX(this.x);
    }

    public void validateX(Integer x) throws ValidationError {
        if (x == null) {
            throw new ValidationError("X must be not null");
        }
    }

    public boolean isValid() {
        try {
            validateX();
            return true;
        } catch (ValidationError e) {
            return false;
        }
    }

    public boolean isLowerThan(Coordinates other){
        return (this.isValid() && other.isValid()) && (this.x < other.x || this.y < other.y);
    }

    @Override
    public String toString() {
        return "(" + x +  ", " + y + ")";
    }
}
