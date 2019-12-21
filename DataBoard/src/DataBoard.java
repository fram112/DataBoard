package com.company;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public interface DataBoard<E extends Data> {

            //Overview:
                     /*
                        The mutable DataBoard <E extends Data> type is a container of generic objects that extend
                        the data type Date.
                        The collection intuitively behaves as a space for memorization and data visualizations that
                        can be of various types, but which implement mandatory the display () method.
                        The board must guarantee data privacy by providing its own mechanism of data sharing management.
                        Each data on the board has the category of the data associated with it.
                        The board owner can define his own categories and draw up a list of contacts (friends)
                        who the data will be visible, for each type of category.
                        The data is visible only in reading: in particular the data can be viewed by friends but
                        modified only by the owner of the board.
                        Friends can associate a "like" with shared content.
                        All the friends have unique username.
                     */

     //Typical element:
                     /* < ownerPassword, dataBoard, categoriesDefined >
                        where
                             ownerPassword = password of the board creator and is used for identity control before
                                             any operation that modifies the board,
                             dataBoard = [<data_{0}, category_{0}, likes_{0}>, ..., <data_{sizeB-1}, category_{sizeB-1}, likes_{sizeB-1}>]
                             where
                                  data_{i} = i-th data contained in the board, i.e. the generic object that extends the data type Date
                                  category{i} = name of the category of data_{i}
                                  likes_{i} = [like_{0}, ..., like_{sizeL-1}] i.e. the list of friends who liked data_{i}
                             categoriesDefined = [<category_{0}, friendsList_{0}>, ..., <category_{sizeC-1}, friendsList_{sizeC-1}>]>
                             where
                                  category{i} = i-th unique category created by owner
                                  friendsList{i} = [friend_{0}, ..., friend_{sizeF-1}] i.e the list of friends allowed to see the data of category_{i}
                    */

    // Crea una categoria di dati
    // se vengono rispettati i controlli di identità
    /**
     * @requires  category != null && passw != null
     *            && passw == ownerPassword
     *            && for all i in categoriesDefined . 0 <= i < sizeC ==> category_{i} != category
     * @throws    NullPointerException if category == null || passw == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if passw != ownerPassword
     *            (not available in Java, checked)
     * @throws    DuplicateEntryException if exist i in categoriesDefined . 0 <= i < sizeC ==> category{i} == category
     *            (not available in Java, checked)
     * @modifies  categoriesDefined
     * @effects   post(categoriesDefined) = [<category_{0}, friendList_{0}>, ..., <category_{sizeC-1}, friendList_{sizeC-1}>]
     *                                      U <category, new friendList> && sizeC++
     *            informally, create a new category and add it into categoriesDefined with a empty friendList associated
     */
    void createCategory(String category, String passw)
            throws NullPointerException, WrongPasswordException, DuplicateEntryException, InvalidEntryException;


    // Rimuove una categoria di dati
    // se vengono rispettati i controlli di identità
    /**
     * @requires  category != null && passw != null
     *            && passw == ownerPassword
     *            && exist i in categoriesDefined . 0 <= i < sizeC ==> category_{i} == category
     * @throws    NullPointerException if category == null || passw == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if passw != ownerPassword
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if for all i in categoriesDefined . 0 <= i < sizeC ==> category{i} != category
     *            (not available in Java, checked)
     * @modifies  categoriesDefined || (categoriesDefined && dataBoard)
     * @effects   post(categoriesDefined) = [<category_{0}, friendList_{0}>, ..., <category_{sizeC-1}, friendList_{sizeC-1}>]
     *                                      \ <category, friendList> && sizeC--
     *            && dataBoard = [<data_{0}, category_{0}, likes_{0}>, ..., <data_{sizeB-1}, category_{sizeB-1}, likes_{sizeB-1}>]
     *                            where category_{i} != category
     *            informally, delete a category and his associated friendList from categoriesDefined
     */
    void removeCategory(String category, String passw)
            throws NullPointerException, WrongPasswordException, CategoryNotFoundException;


    // Aggiunge un amico ad una categoria di dati
    // se vengono rispettati i controlli di identità
    /**
     * @requires  category != null && passw != null && friend != null
     *            && passw == ownerPassword
     *            && exist i in categoriesDefined . 0 <= i < sizeC ==> category_{i} == category
     *            && for all i in friendList_{category} . 0 <= i < sizeF ==> friend{i} != friend
     * @throws    NullPointerException if category == null || passw == null || friend == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if passw != ownerPassword
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if for all i in categoriesDefined . 0 <= i < sizeC ==> category{i} != category
     *            (not available in Java, checked)
     * @throws    DuplicateEntryException if exist i in friendList_{category} . 0 <= i < sizeF ==> friend{i} == friend
     *            (not available in Java, checked)
     * @modifies  friendList_{category}
     * @effects   post(friendList_{category}) = [friend_{0}, ..., friend_{sizeF-1}] U friend && sizeF++
     *            informally, add(allow) a friend to the list of friend that can view data of category
     */
    void addFriend(String category, String passw, String friend)
            throws NullPointerException, WrongPasswordException, CategoryNotFoundException, DuplicateEntryException, InvalidEntryException;


    // rimuove un amico da una categoria di dati
    // se vengono rispettati i controlli di identità
    /**
     * @requires  category != null && passw != null && friend != null
     *            && passw == ownerPassword
     *            && exist i in categoriesDefined . 0 <= i < sizeC ==> category_{i} == category
     *            && exist i in friendList_{category} . 0 <= i < sizeF ==> friend{i} == friend
     * @throws    NullPointerException if category == null || passw == null || friend == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if passw != ownerPassword
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if for all i in categoriesDefined . 0 <= i < sizeC ==> category{i} != category
     *            (not available in Java, checked)
     * @throws    FriendNotFoundException if for all i in friendList_{category} . 0 <= i < sizeF ==> friend{i} != friend
     *            (not available in Java, checked)
     * @modifies  friendList_{category}
     * @effects   post(friendList_{category}) = [friend_{0}, ..., friend_{sizeF-1}] \ friend && sizeF--
     *            informally, delete(denied) a friend from the list of friend that can view data of category
     */
    void removeFriend(String category, String passw, String friend)
            throws NullPointerException, WrongPasswordException, CategoryNotFoundException, FriendNotFoundException;


    // Inserisce un dato in bacheca
    // se vengono rispettati i controlli di identità
    /**
     * @requires  categoria != null && passw != null && dato != null
     *            && passw == ownerPassword
     *            && exist i in categoriesDefined . 0 <= i < sizeC ==> category_{i} == category
     *            && for all i in dataBoard . 0 <= i < sizeB ==> data_{i} != dato
     * @throws    NullPointerException if category == null || passw == null || dato == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if passw != ownerPassword
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if for all i in categoriesDefined . 0 <= i < sizeC ==> category{i} != category
     *            (not available in Java, checked)
     * @throws    DuplicateEntryException if exist i in dataBoard . 0 <= i < sizeB ==> data_{i} == dato
     *            (not available in Java, checked)
     * @modifies  dataBoard
     * @effects   post(dataBoard) = [<data_{0}, category_{0}, likes_{0}>, ..., <data_{sizeB-1}, category_{sizeB-1}, likes_{sizeB-1}>]
     *                               U <dato, categoria, new likes> && sizeB++
     *            informally, add a new data into dataBoard with his associated categoria and a new empty list of likes
     */
    boolean put(String passw, E dato, String categoria)
            throws NullPointerException, WrongPasswordException, CategoryNotFoundException, DuplicateEntryException;


    // Restituisce una copia del dato in bacheca
    // se vengono rispettati i controlli di identità
    /**
     * @requires  passw != null && dato != null
     *            && passw == ownerPassword
     *            && exist i in dataBoard . 0 <= i < sizeB ==> data_{i} == dato
     * @throws    NullPointerException if passw == null || dato == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if passw != ownerPassword
     *            (not available in Java, checked)
     * @throws    DataNotFoundException if for all i in dataBoard . 0 <= i < sizeB ==> data_{i} != dato
     *            (not available in Java, checked)
     * @effects   returns a copy of dato
     */
    E get(String passw, E dato)
            throws NullPointerException, WrongPasswordException, DataNotFoundException;


    // Rimuove il dato dalla bacheca
    // se vengono rispettati i controlli di identità
    /**
     * @requires  passw != null && dato != null
     *            && passw == ownerPassword
     *            && exist i in dataBoard . 0 <= i < sizeB ==> data_{i} == dato
     * @throws    NullPointerException if passw == null || dato == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if passw != ownerPassword
     *            (not available in Java, checked)
     * @throws    DataNotFoundException if for all i in dataBoard . 0 <= i < sizeB ==> data_{i} != dato
     *            (not available in Java, checked)
     * @modifies  dataBoard
     * @effects   post(dataBoard) = [<data_{0}, category_{0}, likes_{0}>, ..., <data_{sizeB-1}, category_{sizeB-1}, likes_{sizeB-1}>]
     *                               \ <dato, category, likes>
     *            && sizeB--
     *            && returns a copy of dato
     *            informally, delete dato and his associated category and list of likes from dataBoard and returns a copy of dato removed
     */
    E remove(String passw, E dato)
            throws NullPointerException, WrongPasswordException, DataNotFoundException;


    // Aggiunge un like a un dato
    // (se vengono rispettati i controlli di identità)??
    // forse si riferisce al fatto se friend può vedere la categoria di quel dato a cui vuole mettere like
    /**
     * @requires  friend != null && dato != null
     *            && exist i in dataBoard . 0 <= i < sizeB ==> data_{i} == dato
     *            && exist i in friendList_{catergory_{dato}} . 0 <= i < sizeF ==> friend_{i} == friend (friend can view(like) the data)
     *            && for all i in likes_{dato} . 0 <= i < sizeL ==> like_{i} != friend
     * @throws    NullPointerException if friend == null || dato == null
     *            (available in Java, unchecked)
     * @throws    DataNotFoundException if for all i in dataBoard . 0 <= i < sizeB ==> data_{i} != dato
     *            (not available in Java, checked)
     * @throws    FriendNotFoundException if for all i in friendList_{category_{dato}} . 0 <= i < sizeF ==> friend{i} != friend
     *            (not available in Java, checked)
     * @throws    AlreadyLikedException if exist i in likes_{dato} . 0 <= i < sizeL ==> like_{i} == friend
     * @modifies  likes_{dato}
     * @effects   post(likes_{dato}) = [like_{0}, ..., like_{sizeL-1}] U friend && sizeL++
     *            informally, add friend into likes_{dato}, the list of friends who likes dato
     */
    void insertLike(String friend, E dato)
            throws NullPointerException, DataNotFoundException, FriendNotFoundException,
                   AlreadyLikedException, CategoryNotFoundException;


    // Crea la lista dei dati in bacheca di una determinata categoria
    // se vengono rispettati i controlli di identità
    /**
     * @requires  category != null && passw != null
     *            && passw == ownerPassword
     *            && exist i in categoriesDefined . 0 <= i < sizeC ==> category_{i} == category
     * @throws    NullPointerException if category == null || passw == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if passw != ownerPassword
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if for all i in categoriesDefined . 0 <= i < sizeC ==> category{i} != category
     *            (not available in Java, checked)
     * @effects   returns a new List in which the elements are all the data_{i} of dataBoard where category_{i} == category
     */
    List<E> getDataCategory(String passw, String category)
            throws NullPointerException, WrongPasswordException, CategoryNotFoundException;


    // restituisce un iteratore (senza remove) che genera tutti i dati in
    // bacheca ordinati rispetto al numero di like
    // se vengono rispettati i controlli di identità
    /**
     * @requires  passw != null
     *            && passw == ownerPassword
     * @throws    NullPointerException if passw == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if passw != ownerPassword
     *            (not available in Java, checked)
     * @effects   returns an Iterator (without remove operation) that iterate through the board
     *            in order of number of likes
     */
    Iterator<E> getIterator(String passw)
            throws NullPointerException, WrongPasswordException;


    // restituisce un iteratore (senza remove) che genera tutti i dati in
    // bacheca condivisi
    /**
     * @requires  friend != null
     *            && exist i in categoriesDefined . 0 <= i < sizeC ==>
     *                  exist j in friendList_{i} . 0 <= j < sizeF ==> friend_{j} == friend
     * @throws    NullPointerException if friend == null
     *            (available in Java, unchecked)
     * @throws    FriendNotFoundException if for all i in categoriesDefined . 0 <= i < sizeC ==>
     *                                          for all j in friendList_{i} . 0 <= j < sizeF ==> friend_{j} != friend
     *            (not available in Java, checked)
     * @effects   returns an Iterator (without remove operation) that iterate through the data (of any category) shared with friend
     */
    Iterator<E> getFriendIterator(String friend)
            throws NullPointerException, FriendNotFoundException;


    // ... altre operazioni da definire a scelta

    /**
     * @effects  returns a string that is the visual representation of this
     */
    String toString();

    /**
     * @effects  print the table of categories defined by the owner
     */
    void displayCategories();

    /**
     * @effects  print the board that contains data
     */
    void displayBoard();

    /**
     * @requires  passw != null && dato != null
     *            && passw == ownerPassword
     *            && && exist i in dataBoard . 0 <= i < sizeB ==> data_{i} == dato
     * @throws    NullPointerException if passw == null || dato == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if passw != ownerPassword
     *            (not available in Java, checked)
     * @throws    DataNotFoundException if for all i in dataBoard . 0 <= i < sizeB ==> data_{i} != dato
     *            (not available in Java, checked)
     * @modifies  dataBoard
     * @effects   informally, remove dato from boardFeed and re-add them to the board's head with a new
     *            initialized set of likes
     */
    void repost(String passw, E dato)
            throws NullPointerException, DataNotFoundException, WrongPasswordException;

    /**
     * @requires  dato != null
     *            && && exist i in dataBoard . 0 <= i < sizeB ==> data_{i} == dato
     * @throws    NullPointerException if dato == null
     *            (available in Java, unchecked)
     * @throws    DataNotFoundException if for all i in dataBoard . 0 <= i < sizeB ==> data_{i} != dato
     *            (not available in Java, checked)
     * @effects   returns a new set of dato's likes
     */
    LinkedHashSet<String> getLikes(E dato) throws NullPointerException, DataNotFoundException;

    /**
     * @requires  friend != null && dato != null
     *            && && exist i in dataBoard . 0 <= i < sizeB ==> data_{i} == dato
     *            && exist i in likes_{dato} . 0 <= i < sizeL ==> like_{i} == friend
     * @throws    NullPointerException if friend == null || dato == null
     *            (available in Java, unchecked)
     * @throws    DataNotFoundException if for all i in dataBoard . 0 <= i < sizeB ==> data_{i} != dato
     *            (not available in Java, checked)
     * @throws    LikeNotFoundException if for all i in likes_{dato} . 0 <= i < sizeL ==> like_{i} != friend
     * @modifies  likes_{dato)
     * @effects   post(likes_{dato}) = [like_{0}, ..., like_{sizeL-1}] \ friend
     *            informally, remove friend from likes_{dato}, the list of friends who likes dato
     */
    void removeLike(String friend, E dato) throws LikeNotFoundException, DataNotFoundException;

    /**
     * @requires  category != null && friend != null
     *            && && exist i in categoriesDefined . 0 <= i < sizeC ==> category_{i} == category
     * @throws    NullPointerException if category == null || friend == null
     *            (available in Java, unchecked)
     * @throws    CategoryNotFoundException if for all i in categoriesDefined . 0 <= i < sizeC ==> category{i} != category
     *            (not available in Java, checked)
     * @effects   returns a boolean that indicate if friend is in the list of friends allowed
     *            to see category
     */
    boolean friendAllowed(String category, String friend) throws CategoryNotFoundException;

    // restituisce la lista delle categorie condivise con un amico,
    // cioè le categorie dei dati che un amico può visualizzare
    /**
     * @requires  friend != null
     *            && exist i in categoriesDefined . 0 <= i < sizeC ==>
     *                  exist j in friendList_{i} . 0 <= j < sizeF ==> friend_{j} == friend
     * @throws    NullPointerException if passw == null
     *            (available in Java, unchecked)
     * @throws    FriendNotFoundException if for all i in categoriesDefined . 0 <= i < sizeC ==>
     *                                          for all j in friendList_{i} . 0 <= j < sizeF ==> friend_{j} != friend
     *            (not available in Java, checked)
     * @effects   returns a List of categories that friend is allowed to see
     */
    List<String> getFriendCategories(String friend)
            throws NullPointerException, FriendNotFoundException;

}

