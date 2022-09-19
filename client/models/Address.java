package client.models;


import java.io.Serializable;

public class Address implements Serializable {

    private String street; //Поле может быть null
    private Location town; //Поле может быть null

    public Address(){}
    public Address(String street){
        this.street = street;
    }
    public Address(Location town){
        this.town = town;
    }
    public Address(String street, Location town){
        this.street = street;
        this.town = town;
    }

    public void setStreet(String street){
        this.street = street;
    }
    public void setTown(Location town){
        this.town = town;
    }

    public String getStreet(){
        return street;
    }
    public Location getTown() {
        return town;
    }

    public boolean isLowerThan(Address other){
        return (this.street != null && other.street != null &&
                (this.street.length() < other.street.length() ||
                this.town.isLowerThan(other.town)
                )
        );
    }

    /*public boolean validateStreet(){
        return validateStreet(this.street);
    }
    public boolean validateTown(){
        return validateTown(this.town);
    }

    public boolean validateStreet(String street) {
        Boolean flags = true;
        if (street == null){
            flags = false;
        }
        return flags;
    }
    public boolean validateTown(Location town) {
        Boolean flagt = true;
        if (town == null){
            flagt = false;
        }
        return flagt;
    }

    public void isValid() throws ValidationError{
        try {
            Boolean flagv = validateStreet() || validateTown();
        }catch (ValidationError e){
            throw new ValidationError("The street and the city couldn't be equal to NULL at the same time");
        }
    }*/


    /*public void validateStreet(String street) throws ValidationError{
        if (street == null){
            throw new ValidationError("Street can't be null");
        }
    }
    public void validateTown(Location town) throws ValidationError{
        if(town == null){
            throw new ValidationError("Town can't be null");
        }
    }
*/


}
