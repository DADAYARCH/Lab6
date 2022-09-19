package client.models;

import exceptions.ValidationError;

import java.io.Serializable;

public class Location implements Serializable {
    private long x;
    private long y;
    private String name; //Строка не может быть пустой, Поле может быть null

    public Location(){}

    public void setName(String name) {
        this.name = name;
    }
    public void setX(long x) {
        this.x = x;
    }
    public void setY(long y) {
        this.y = y;
    }

    public long getX() {
        return x;
    }
    public long getY() {
        return y;
    }
    public String getName() {
        return name;
    }

    public void validateName()throws ValidationError {
        validateName(this.name);
    }
    public void validateName(String name) throws ValidationError{
        if (name.isEmpty()){
            throw new ValidationError("Name location can't be empty");
        }

    }

    public boolean isLowerThan(Location other){
        return (this.name.length() < other.name.length() || this.x < other.x || this.y < other.y);
    }

}
