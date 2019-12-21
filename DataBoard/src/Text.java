package com.company;

public class Text extends Data {

    // Overview: mutable data type that represents a text-like data and its properties.
    //           Text extends Data will therefore inherit all its attributes and methods, and will have to implement
    //           the display and clone methods (otherwise it must be abstract and postpone the implementation
    //           to subsequent extensions).

    private String text;
    private String font;
    private int fontSize;
    private String color;
    private boolean bold;
    private boolean italic;
    private boolean underline;

    private final int MIN_FONTSIZE = 5;
    private final int MAX_FONTSIZE = 72;
    private static final String ANSI_BRIGHT_BLACK  = "\u001B[90m";
    private static final String ANSI_RESET = "\u001B[0m";



    public Text(String author, String name, String format, String text){
        super(author, name, format);

        this.text = text;
        font = "Times New Roman";
        fontSize = 12;
        color = "white";
        bold = false;
        italic = false;
        underline = false;
    }

    public Text(String author, String name, String format, String text, String font, int fontSize,
                String color, boolean bold, boolean italic, boolean underline){

        super(author, name, format);

        if(text == null || font == null || color == null)
            throw new NullPointerException();
        if(fontSize < MIN_FONTSIZE || fontSize > MAX_FONTSIZE)
            throw new IllegalArgumentException();

        this.text = text;
        this.font = font;
        this.fontSize = fontSize;
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underline = underline;
    }

    //copy constructor
    private Text(Text original){
        super(original);

        this.text = original.text;
        this.font = original.font;
        this.fontSize = original.fontSize;
        this.color = original.color;
        this.bold = original.bold;
        this.italic = original.italic;
        this.underline = original.underline;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Text))
            return false;
        Text otherObj = (Text) obj;
        return super.equals(otherObj)
                && this.text.equals(otherObj.text)
                && this.font.equals(otherObj.font)
                && this.fontSize == otherObj.fontSize
                && this.color.equals(otherObj.color)
                && this.bold == otherObj.bold
                && this.italic == otherObj.italic
                && this.underline == otherObj.underline;
    }

    @Override
    public Data clone() {
        return new Text(this);
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return super.toString()
                + "\""+ text +"\"" +
                "\n\n"+ANSI_BRIGHT_BLACK+"text information:\n" +
                "font: " + font + ", font size: " + fontSize +
                ", bold: " + bold + ", italic: " + italic +
                ", underline: " + underline + ", color: " + color +ANSI_RESET+ "\n";
    }

    // getters

    public String getText() {
        return text;
    }

    public String getFont() {
        return font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getColor() {
        return color;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public boolean isUnderline() {
        return underline;
    }

    // setters

    public void setText(String text) {
        this.text = text;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    // methods

    public void modifiesText(String update, int start, int end){
        if(update == null)
            throw new NullPointerException();

        text = text.replace(text.substring(start, end), update);

    }
}
