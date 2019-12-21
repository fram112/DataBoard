package com.company;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class Data{

    // Overview: abstract data type that represents a generic data with its most essential fields.
    //           In addition to providing the classic operations for setting and getting the properties of the object,
    //           Data defines two abstract methods (display and clone) that will have to be implemented by all types that
    //           will extend Data in the future.

    // Abstraction function(c) = < c.author, c.name, c.format, c.creationDate >

    // Representation invariant(c) = c.author != null && c.name != null && c.format != null && c.creationDate != null

    private String author;
    private String name;
    private String format;
    private Calendar creationDate;

    /**
     * @requires author != null && name != null && format != null
     * @throws   NullPointerException if author == null && name == null && format == null
     * @modifies this
     * @effects  Informally, initializes all Data's fields
     */
    public Data(String author, String name, String format){
        if(author == null || name == null || format == null)
            throw new NullPointerException();

        this.author = author;
        this.name = name;
        this.format = format;
        creationDate = Calendar.getInstance();
    }

    // copy constructor
    /**
     * @requires original != null
     * @throws   NullPointerException if original == null
     * @modifies this
     * @effects  Informally, creates a new instance of Data and copies all the original fields into it
     */
    public Data(Data original){
        if(original == null)
            throw new NullPointerException();

        this.author = original.author;
        this.name = original.name;
        this.format = original.format;
        this.creationDate = original.creationDate;
    }

    /**
     * @effects print this
     */
    public abstract void display();

    /**
     * @effects clone this
     */
    @Override
    public abstract Data clone();

    /**
     * @requires obj != null
     * @throws   NullPointerException if obj == null
     * @effects  returns true if this is equals to obj else returns false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            throw new NullPointerException();

        if(!(obj instanceof Data))
            return false;
        Data otherObj = (Data) obj;
        return ((this.author.equals(otherObj.author))
                && (this.name.equals(otherObj.name))
                && (this.format.equals(otherObj.format)));
    }

    /**
     * @effects returns a string that is the visual representation of this
     */
    @Override
    public String toString() {
        SimpleDateFormat date = new SimpleDateFormat("MMMMMMMM dd 'at' HH:mm:ss");
        return "\n"+"(" + name + "." + format + " " + this.getClass().getSimpleName() +" from author: " + this.getAuthor() +
                ", created on: " + date.format(creationDate.getTime())+")\n\n";
    }

    /**
     * @effects returns format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @requires format != null
     * @throws   NullPointerException if format == null
     * @modifies this
     * @effects  this.format = format
     */
    public void setFormat(String format) {
        if(format == null){
            throw new NullPointerException();
        }
        this.format = format;
    }

    /**
     * @effects returns author
     */
    public String getAuthor(){
        return author;
    }

    /**
     * @effects returns name
     */
    public String getName() {
        return name;
    }

    /**
     * @effects returns creationDate
     */
    public Calendar getCreationDate() {
        return creationDate;
    }

    /**
     * @requires name != null
     * @throws   NullPointerException if name == null
     * @modifies this
     * @effects  this.name = name
     */
    public void setName(String name) {
        if(name == null){
            throw new NullPointerException();
        }
        this.name = name;
    }
}
