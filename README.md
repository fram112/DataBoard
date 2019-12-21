# DataBoard
First intermediate project for the Programming 2 course @ unipi

DataBoard is a container of generic objects that extend the Data data type. 
Intuitively, the collection acts as a space for storing and displaying data which can be of various types 
but which necessarily implement a display() method.
The board guarantees data privacy by providing its own data sharing management mechanism. Each data item on the board has associated the data category. The owner of the board can define his own categories and draw up a list of contacts (friends) to whom the data for each type of category will be visible.
The data are visible in read only: in particular the data can be viewed by friends but modified only by the owner of the board. Friends can associate a "like" with the shared content.
