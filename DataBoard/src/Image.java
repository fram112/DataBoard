package com.company;

public class Image extends Data {

    // Overview: mutable data type that represents a image-like data and its properties.
    //           Image extends Data will therefore inherit all its attributes and methods, and will have to implement
    //           the display and clone methods (otherwise it must be abstract and postpone the implementation
    //           to subsequent extensions).

    private String imageText; // simulates an image by building it using ASCII symbols
    private int height;
    private int width;

    private static final String ANSI_BRIGHT_BLACK  = "\u001B[90m";
    private static final String ANSI_RESET = "\u001B[0m";

    public Image(String author, String name, String format, String imageText, int height, int width) {
        super(author, name, format);

        if(height < 0 || width < 0)
            throw new IllegalArgumentException();

        this.imageText = imageText;
        this.height = height;
        this.width = width;
    }

    private Image(Image original) {
        super(original);

        this.imageText = original.imageText;
        this.height = original.height;
        this.width = original.width;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Image))
            return false;
        Image otherObj = (Image) obj;
        return super.equals(obj)
                && this.imageText.equals(otherObj.imageText)
                && this.height == otherObj.height
                && this.width == otherObj.width;
    }

    @Override
    public Image clone() {
        return new Image(this);
    }

    @Override
    public String toString() {
        return super.toString() +
                imageText +
                ANSI_BRIGHT_BLACK+"\n\nimage information:\n" +
                "resolution: " + width + " x " + height+ ANSI_RESET+"\n";
    }

    public int getResolution(){
        return height * width;
    }
}
