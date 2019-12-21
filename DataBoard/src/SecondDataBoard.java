package com.company;

import java.text.SimpleDateFormat;
import java.util.*;

public class SecondDataBoard<E extends Data> implements DataBoard<E>{

    // Abstraction function(c) =
    /*
            < c.owner, c.ownerPassw,
             , { <c.boardFeed.get(i), c.dataPostDates.get(i), c.dataCategories.get(i), c.dataLikes.get(i)> | 0 <= i < c.boardFeed.size()}
             , { <c.categoriesDefined.get(j), c.friendsAllowed.get(j)> | 0 <= j < c.categoriesDefined.size()} >
        where c.boardFeed.size() == c.dataPostDates.size() == c.dataCategories.size() == c.dataLikes.size()
           && c.categoriesDefined.size() == c.friendsAllowed.size()
    */

    // Representation invariant(c) =
    /*
               c.owner != null
            && c.ownerPass != null
            && validUser(c.owner)
            && validPassw(c.ownerPassw)

            && c.boardFeed != null
            && c.dataPostDates != null
            && c.dataCategories != null
            && c.dataLikes != null
            && c.categoriesDefined != null
            && c.friendsAllowed != null

            && for all i,j in c.boardFeed . 0 <= i,j < c.boardFeed.size() && i != j ==>
                    !(c.boardFeed.get(i).equals(c.boardFeed.get(j))                                                                 (vincolo dati bacheca senza duplicati)
            && for all i,j in c.boardFeed . 0 <= i < j < c.boardFeed.size() ==>
                    c.dataPostDates.get(i).compareTo(c.dataPostDates.get(j) > 0                                                     (vincolo ordinamento dati per postDate)
            && for all i in c.dataCategories . 0 <= i < c.dataCategories.size() ==>
                    c.categoriesDefined.contains(c.dataCategories.get(i))                                                           (vincolo categorie dati esistenti)
            && for all i in c.dataLikes . 0 <= i < c.dataLikes.size() ==>
                    for all j,k in c.dataLikes.get(i) . 0 <= j,k < c.dataLikes.get(i).size() && j != k ==>
                        !(c.dataLikes.get(i).get(j).equals(c.dataLikes.get(i).get(j)))                                              (vincolo likes dati senza duplicati)
            && for all i in c.boardFeed . 0 <= i < c.boardFeed.size() ==>
                    for all j in c.dataLikes.get(i) . 0 <= j < c.dataLikes.get(i).size() ==>                                        (vincolo utenti che hanno messo like al dato autorizzati a
                        c.friendsAllowed.get(c.categoriesDefined.indexOf(c.dataCategory.get(i)).contains(c.dataLikes.get(i).get(j))  vedere la categoria del dato e quindi autorizzati a
                                                                                                                                     mettere like e quindi esistenti)
            && for all i in c.categoriesDefined . 0 <= i < categoriesDefined.size() ==>
                    validCategory(c.categoriesDefined.get(i)))                                                                      (vincolo categorie definite valide)
            && for all i,j in c.categoriesDefined . 0 <= i,j <  categoriesDefined.size() && i != j ==>
                    !(c.categoriesDefined.get(i).equals(c.categoriesDefined.get(j))                                                 (vincolo categorie definite senza duplicati)
            && for all i in c.friendsAllowed . 0 <= i < friendsAllowed.size() ==>
                    for all j in c.friendsAllowed.get(i) . 0 <= j < friendsAllowed.get(i).size() ==>
                        validUser(c.friendsAllowed.get(i).get(j))                                                                   (vincolo amici autorizzati per categoria con username validi)
            && for all i in c.friendsAllowed . 0 <= i < c.friendsAllowed.size() ==>
                    for all j,k in c.friendsAllowed.get(i) . 0 <= j,k < c.friendsAllowed.get(i).size() ==>
                        !(c.friendsAllowed.get(i).get(j).equals(c.friendsAllowed.get(i).get(k))                                     (vincolo amici autorizzati per categoria senza duplicati)
     */

    //owner info
    String owner;
    String ownerPassw;

    //data boards fields
    List<E> boardFeed;
    List<Calendar> dataPostDates;
    List<String> dataCategories;
    List<List<String>> dataLikes;

    //categories defined fields
    List<String> categoriesDefined;
    List<List<String>> friendsAllowed;


    // SecondDataBoard constructor
    /**
     * @requires  owner != null && passw != null
     *            && validUser(owner) && validPassw(passw)
     * @throws    NullPointerException if owner == null || passw == null
     *            (available in Java, unchecked)
     * @throws    InvalidEntryException if !(validUser(owner) && validPassw(passw))
     *            (available in Java, unchecked)
     * @modifies  this
     * @effects   informally, initializes all SecondDataBoard's fields
     */
    public SecondDataBoard(String owner, String passw)
            throws NullPointerException, InvalidEntryException {
        if(owner == null || passw == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(validUser(owner) && validPassw(passw)))
            throw new InvalidEntryException("Insert a valid username and a valid password to create the board!");

        this.owner = owner;
        this.ownerPassw = passw;

        dataPostDates = new ArrayList<>();
        dataCategories = new ArrayList<>();
        dataLikes = new ArrayList<>();
        boardFeed = new ArrayList<>();

        categoriesDefined = new ArrayList<>();
        friendsAllowed = new ArrayList<>();
        
    }


    /**
     * @requires  category != null && passw != null
     *            && validCategory(category)
     *            && passw.equals(ownerPassw)
     *            && !(categoryExists(category))
     * @throws    NullPointerException if category == null || passw == null
     *            (available in Java, unchecked)
     * @throws    InvalidEntryException if !(validCategory(category))
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassw))
     *            (not available in Java, checked)
     * @throws    DuplicateEntryException if categoryExists(category)
     *            (not available in Java, checked)
     * @modifies  categoriesDefined && friendAllowed
     * @effects   categoriesDefined.add(category) && friendsAllowed.add(new ArrayList<>())
     *            informally, create a new category and add it into categoryDefined and new empty list of friend associated
     */
    @Override
    public void createCategory(String category, String passw)
            throws NullPointerException, InvalidEntryException, WrongPasswordException, DuplicateEntryException {
        
        if(category == null || passw == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(validCategory(category)))
            throw new InvalidEntryException("Insert a valid category to create it!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(categoryExists(category))
            throw new DuplicateEntryException("This category is already present!");

        categoriesDefined.add(category);
        friendsAllowed.add(new ArrayList<>());
        
    }

    /**
     * @requires  category != null && passw != null
     *            && passw.equals(ownerPassw)
     *            && categoryExists(category)
     * @throws    NullPointerException if category == null || passw == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassw))
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if !(categoryExists(category))
     *            (not available in Java, checked)
     * @modifies  (categoryDefined && friendAllowed)
     *            || ((categoryDefined && friendAllowed) && boardFeed && dataCategories && dataPostDates && dataLikes)
     * @effects   if(getDataCategory(ownerPassw, category).isEmpty())
     *                 friendsAllowed.remove(categoriesDefined.indexOf(category));
     *                 && categoriesDefined.remove(category);
     *            else
     *                 remove(ownerPassw, boardFeed.get(i)) where dataCategories.get(i).equals(category)
     *            informally, delete a category from categoryDefined and his associated list of allowed friends from
     *            friendAllowed if board not contains data of that category, else delete all the data in boardFeed
     *            of that category too
     */
    @Override
    public void removeCategory(String category, String passw)
            throws NullPointerException, WrongPasswordException, CategoryNotFoundException {
        
        if(category == null || passw == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(!(categoryExists(category)))
            throw new CategoryNotFoundException("Insert an existing category to remove it!");

        if(getDataCategory(ownerPassw, category).isEmpty()) {
            friendsAllowed.remove(categoriesDefined.indexOf(category));
            categoriesDefined.remove(category);
        }
        else{
            for (int i = boardFeed.size() - 1; i >= 0; i--) { // per mantenere la corrispondenza degli indici
                if(dataCategories.get(i).equals(category)){
                    try {
                        remove(ownerPassw, boardFeed.get(i));
                    }
                    catch (DataNotFoundException e){ // non dovrebbe mai succedere
                        e.printStackTrace();
                    }
                }
            }
            friendsAllowed.remove(categoriesDefined.indexOf(category));
            categoriesDefined.remove(category);
        }
        
    }

    /**
     * @requires  category != null && passw != null && friend != null
     *            && validUser(friend)
     *            && passw.equals(ownerPassw)
     *            && categoryExists(category)
     *            && !(friendAllowed(category, friend))
     * @throws    NullPointerException if category == null || passw == null || friend == null
     *            (available in Java, unchecked)
     * @throws    InvalidEntryException if !(validUser(friend))
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassw))
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if !(categoryExists(category))
     *            (not available in Java, checked)
     * @throws    DuplicateEntryException if (friendAllowed(category, friend)
     *            (not available in Java, checked)
     * @modifies  friendAllowed.get(categoryDefined.indexOf(category))
     * @effects   friendsAllowed.get(categoriesDefined.indexOf(category)).add(friend)
     *            informally, add(allow) a friend to the list of friend that can view data of category
     */
    @Override
    public void addFriend(String category, String passw, String friend)
            throws NullPointerException, InvalidEntryException, WrongPasswordException, CategoryNotFoundException,
                   DuplicateEntryException {
        
        if(category == null || passw == null || friend == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(validUser(friend)))
            throw new InvalidEntryException("Insert a valid username to add friend!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(!(categoryExists(category)))
            throw new CategoryNotFoundException("Insert an existing category to add friend into it!");
        if(friendAllowed(category, friend))         // l'amico è già autorizzato in quella categoria
            throw new DuplicateEntryException("This friend is already present!");

        friendsAllowed.get(categoriesDefined.indexOf(category)).add(friend);
        
    }

    /**
     * @requires  category != null && passw != null && friend != null
     *            && passw.equals(ownerPassword)
     *            && categoryExists(category)
     *            && friendAllowed(category, friend)
     * @throws    NullPointerException if category == null || passw == null || friend == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassword))
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if !(categoryExists(category))
     *            (not available in Java, checked)
     * @throws    FriendNotFoundException if !(friendAllowed(category, friend))
     *            (not available in Java, checked)
     * @modifies  friendsAllowed.get(categoriesDefined.indexOf(category))
     * @effects   friendsAllowed.get(categoriesDefined.indexOf(category)).remove(friend)
     *            informally, delete(denied) a friend from the list of friend that can view data of category
     */
    @Override
    public void removeFriend(String category, String passw, String friend)
            throws NullPointerException, WrongPasswordException, CategoryNotFoundException, FriendNotFoundException {
        
        if(category == null || passw == null || friend == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(!(categoryExists(category)))
            throw new CategoryNotFoundException("Insert an existing category to remove friend from it!");
        if(!(friendAllowed(category, friend)))
            throw new FriendNotFoundException("Insert an existing friend to remove him!");

        friendsAllowed.get(categoriesDefined.indexOf(category)).remove(friend);
        
    }

    /**
     * @requires  categoria != null && passw != null && dato != null
     *            && passw.equals(ownerPassword)
     *            && categoryExists(category)
     *            && !(dataExists(dato))
     * @throws    NullPointerException if category == null || passw == null || dato == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassword))
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if !(categoryExists(category))
     *            (not available in Java, checked)
     * @throws    DuplicateEntryException if (dataExists(dato)
     *            (not available in Java, checked)
     * @modifies  boardFeed
     * @effects   boardFeed.add(0, dato)
     *            && dataPostDates.add(0,Calendar.getInstance())
     *            && dataCategories.add(0, categoria)
     *            && dataLikes.add(0, new ArrayList<>())
     *            informally, add a new data into dataBoard with his associated categoria and a new empty list of likes
     */
    @Override
    public boolean put(String passw, E dato, String categoria)
            throws NullPointerException, WrongPasswordException, CategoryNotFoundException, DuplicateEntryException {
        
        if(categoria == null || passw == null || dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(!(categoryExists(categoria)))
            throw new CategoryNotFoundException("Insert an existing category to put data!");
        if(dataExists(dato))
            throw new DuplicateEntryException("This data is already present!");

        boardFeed.add(0, dato);
        dataPostDates.add(0,Calendar.getInstance());
        dataCategories.add(0, categoria);
        dataLikes.add(0, new ArrayList<>());
        
        return true;
    }

    /**
     * @requires  passw != null && dato != null
     *            && passw.equals(ownerPassword)
     *            && dataExists(dato)
     * @throws    NullPointerException if passw == null || dato == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassword))
     *            (not available in Java, checked)
     * @throws    DataNotFoundException if !(dataExists(dato))
     *            (not available in Java, checked)
     * @effects   returns a copy of dato
     */
    @Override
    public E get(String passw, E dato)
            throws NullPointerException, WrongPasswordException, DataNotFoundException {
        
        if(passw == null || dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(!(dataExists(dato)))
            throw new DataNotFoundException("Insert an existing data to get it!");

        return (E) boardFeed.get(boardFeed.indexOf(dato)).clone();
    }

    /**
     * @requires  passw != null && dato != null
     *            && passw.equals(ownerPassword)
     *            && (dataExists(dato)
     * @throws    NullPointerException if passw == null || dato == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassword))
     *            (not available in Java, checked)
     * @throws    DataNotFoundException if !(dataExists(dato))
     *            (not available in Java, checked)
     * @modifies  boardFeed && dataCategories && dataPostDates && dataLikes
     * @effects   dataPostDates.remove(boardFeed.indexOf(dato))
     *            && dataCategories.remove(boardFeed.indexOf(dato))
     *            && dataLikes.remove(boardFeed.indexOf(dato))
     *            && boardFeed.remove(boardFeed.indexOf(dato))
     *            && returns a copy of dato removed
     *            informally, delete dato and his associated category and list of likes and returns a copy of dato removed
     */
    @Override
    public E remove(String passw, E dato)
            throws NullPointerException, WrongPasswordException, DataNotFoundException {
        
        if(passw == null || dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(!(dataExists(dato)))
            throw new DataNotFoundException("Insert an existing data to remove it!");

        dataPostDates.remove(boardFeed.indexOf(dato));
        dataCategories.remove(boardFeed.indexOf(dato));
        dataLikes.remove(boardFeed.indexOf(dato));
        return (E) boardFeed.remove(boardFeed.indexOf(dato)).clone();
    }

    /**
     * @requires  friend != null && dato != null
     *            && dataExists(dato)
     *            && friendAllowed(dataCategories.get(boardFeed.indexOf(dato)), friend)
     *            && !(dataLikes.get(boardFeed.indexOf(dato)).contains(friend))
     * @throws    NullPointerException if friend == null || dato == null
     *            (available in Java, unchecked)
     * @throws    DataNotFoundException if !(dataExist(dato))
     *            (not available in Java, checked)
     * @throws    FriendNotFoundException if !(friendAllowed(dataCategories.get(boardFeed.indexOf(dato)), friend))
     *            (not available in Java, checked)
     *            AlreadyLikedException if dataLikes.get(boardFeed.indexOf(dato)).contains(friend)
     * @modifies  dataLikes.get(boardFeed.indexOf(dato))
     * @effects   dataLikes.get(boardFeed.indexOf(dato)).add(friend)
     *            informally, add friend into dataLikes.get(boardFeed.indexOf(dato)), the list of friends who likes dato
     */
    @Override
    public void insertLike(String friend, E dato)
            throws NullPointerException, DataNotFoundException, FriendNotFoundException, AlreadyLikedException,
                   CategoryNotFoundException {
        
        if (friend == null || dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if (!(dataExists(dato)))
            throw new DataNotFoundException("Insert an existing data to insert like!");
        if (!(friendAllowed(dataCategories.get(boardFeed.indexOf(dato)), friend))) // friend must exist in general and must allowed
            throw new FriendNotFoundException("Insert an existing friend to insert his like!");                    // to see the dato category
        if (dataLikes.get(boardFeed.indexOf(dato)).contains(friend))
            throw new AlreadyLikedException("This like is already present!");

        dataLikes.get(boardFeed.indexOf(dato)).add(friend);
        
    }

    /**
     * @requires  category != null && passw != null
     *            && passw.equals(ownerPassword)
     *            && categoryExists(category)
     * @throws    NullPointerException if category == null || passw == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassword))
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if !(categoryExists(category))
     *            (not available in Java, checked)
     * @effects   returns a new List in which the elements are all the data of boardFeed where
     *            dataCategories.get(data).equals(category)
     */
    @Override
    public List<E> getDataCategory(String passw, String category)
            throws NullPointerException, WrongPasswordException, CategoryNotFoundException {
        
        if(passw == null || category == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(!(categoryExists(category)))
            throw new CategoryNotFoundException("Insert an existing category to get data of it!");

        // getting all the data of category
        List<E> dataCategoryList = new ArrayList<>();
        for (int i = 0; i < boardFeed.size(); i++) {
            if(dataCategories.get(i).equals(category))
                dataCategoryList.add(boardFeed.get(i));
        }
        return dataCategoryList;
    }

    /**
     * @requires  passw != null
     *            && passw.equals(ownerPassword)
     * @throws    NullPointerException if passw == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassword))
     *            (not available in Java, checked)
     * @effects   returns an Iterator (without remove operation) that iterate through the board
     *            in order of number of likes
     */
    @Override
    public Iterator<E> getIterator(String passw) throws NullPointerException, WrongPasswordException {
        
        if(passw == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");

        // sorting the board for number of likes
        List<E> likesOrderedList = new ArrayList<>(boardFeed);
        likesOrderedList.sort((o1, o2) -> {
            if (dataLikes.get(boardFeed.indexOf(o1)).size() == dataLikes.get(boardFeed.indexOf(o2)).size())
                return 0;
            return (dataLikes.get(boardFeed.indexOf(o1)).size() < dataLikes.get(boardFeed.indexOf(o2)).size() ? 1 : -1);
        });

        
        // making the iterator without remove operation
        List<E> likesOrderedUnmodifiableList = Collections.unmodifiableList(likesOrderedList);
        return likesOrderedUnmodifiableList.iterator();
    }

    /**
     * @requires  friend != null
     *            && friendExists(friend)
     * @throws    NullPointerException if friend == null
     *            (available in Java, unchecked)
     * @throws    FriendNotFoundException if !(friendExists(friend))
     *            (not available in Java, checked)
     * @effects   returns an Iterator (without remove operation) that iterate through the data (of any category)
     *            shared with friend
     */
    @Override
    public Iterator<E> getFriendIterator(String friend) throws NullPointerException, FriendNotFoundException {
        
        if(friend == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(friendExists(friend)))
            throw new FriendNotFoundException("Insert an existing friend to get iterator!");

        // getting data shared with friend
        List<E> dataSharedList = new ArrayList<>();
        List<String> friendCategory = getFriendCategories(friend);
        for (int i = 0; i < boardFeed.size() ; i++) {
            if(friendCategory.contains(dataCategories.get(i)))
                dataSharedList.add(boardFeed.get(i));
        }

        
        // making the iterator without remove operation
        List<E> dataSharedUnmodifiableList = Collections.unmodifiableList(dataSharedList);
        return dataSharedUnmodifiableList.iterator();

    }

// ---------------------------------------------------------------------------------------------------------------------
    //OTHERS OPERATIONS


    /**
     * @effects: returns a string that is the visual representation of this
     */
    @Override
    public String toString() {
        
        return "TABLE OF CATEGORIES DEFINED BY THE OWNER " + owner + ":\n\n" +
                categoriesToString() +
                "\n\n\n" +
                owner + "'s " + this.getClass().getSimpleName().toUpperCase() + " BOARD" + "\n" +
                boardToString();
    }

    /**
     * @effects: print categoriesToString()
     */
    @Override
    public void displayCategories() {
        
        System.out.println(categoriesToString());
    }

    /**
     * @effects: print boardToString()
     */
    @Override
    public void displayBoard() {
        
        System.out.println(boardToString());
    }

    /**
     * @requires  passw != null && dato != null
     *            && passw.equals(ownerPassword)
     *            && dataExists(dato)
     * @throws    NullPointerException if passw == null || dato == null
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassword))
     *            (not available in Java, checked)
     * @throws    DataNotFoundException if !(dataExists(dato))
     *            (not available in Java, checked)
     * @modifies  boardFeed
     * @effects   informally, remove dato from boardFeed and re-add them to the board's head with a new
     *            initialized set of likes
     */
    @Override
    public void repost(String passw, E dato)
            throws NullPointerException, DataNotFoundException, WrongPasswordException {
        
        if(passw == null || dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(!(dataExists(dato)))
            throw new DataNotFoundException("Insert an existing data to repost it!");

        String categoryTmp = dataCategories.get(boardFeed.indexOf(dato));
        E dataTmp = remove(ownerPassw, dato);
        try {                                      // non dovrebbe mai succedere perchè in questo caso chiamiamo la put
            put(ownerPassw, dataTmp, categoryTmp); // sempre su dati già esistenti e prima di metterlo lo togliamo,
        }                                          // quindi non avremo nemmeno DuplicateEntryException
        catch (CategoryNotFoundException | DuplicateEntryException e){
            e.printStackTrace();
        }
        
    }

    /**
     * @requires  dato != null
     *            && dataExists(dato)
     * @throws    NullPointerException if dato == null
     *            (available in Java, unchecked)
     * @throws    DataNotFoundException if !(dataExists(dato))
     *            (not available in Java, checked)
     * @effects   returns a new set of dato's likes
     */
    @Override
    public LinkedHashSet<String> getLikes(E dato)
            throws NullPointerException, DataNotFoundException {
        
        if (dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if (!(dataExists(dato)))
            throw new DataNotFoundException("Insert an existing data to get likes!");

        return new LinkedHashSet<>(dataLikes.get(boardFeed.indexOf(dato)));
    }

    /**
     * @requires  friend != null && dato != null
     *            && dataExists(dato)
     *            && friendAllowed(dataCategories.get(boardFeed.indexOf(dato)), friend)
     *            && dataLikes.get(boardFeed.indexOf(dato)).contains(friend)
     * @throws    NullPointerException if friend == null || dato == null
     *            (available in Java, unchecked)
     * @throws    DataNotFoundException if !(dataExist(dato))
     *            (not available in Java, checked)
     * @throws    FriendNotFoundException if !(friendAllowed(dataCategories.get(boardFeed.indexOf(dato)), friend))
     *            (not available in Java, checked)
     * @throws    LikeNotFoundException if !(dataLikes.get(boardFeed.indexOf(dato)).contains(friend))
     * @modifies  dataLikes.get(boardFeed.indexOf(dato))
     * @effects   dataLikes.get(boardFeed.indexOf(dato)).remove(friend)
     *            informally, remove friend from getDataOnBoard.likes, the list of friends who likes dato
     */
    @Override
    public void removeLike(String friend, E dato)
            throws LikeNotFoundException, DataNotFoundException{
        
        if (friend == null || dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if (!(dataExists(dato)))
            throw new DataNotFoundException("Insert an existing data to remove like!");
        if (!(dataLikes.get(boardFeed.indexOf(dato)).contains(friend)))
            throw new LikeNotFoundException("Insert an existing like to remove it!");

        dataLikes.get(boardFeed.indexOf(dato)).remove(friend);
        
    }

    /**
     * @requires  category != null && friend != null
     *            && categoryExists(category)
     * @throws    NullPointerException if category == null || friend == null
     *            (available in Java, unchecked)
     * @throws    CategoryNotFoundException if !(categoryExists(category))
     *            (not available in Java, checked)
     * @effects   friendsAllowed.get(categoriesDefined.indexOf(category)).contains(friend)
     *            informally, returns a boolean that indicate if friend is in the list of friends allowed
     *            to see category
     */
    @Override
    public boolean friendAllowed(String category, String friend)
            throws NullPointerException, CategoryNotFoundException {
        
        if(category == null || friend == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(categoryExists(category))) {
            //System.out.println("LA CATEGORIA NON ESISTE\n");
            throw new CategoryNotFoundException("Insert an existing category to know if friend is allowed!");
        }

        return friendsAllowed.get(categoriesDefined.indexOf(category)).contains(friend);
    }

    /**
     * @requires  friend != null
     *            && friendExists(friend)
     * @throws    NullPointerException if friend == null
     *            (available in Java, unchecked)
     * @throws    FriendNotFoundException if !(friendExists(friend))
     *            (not available in Java, checked)
     * @effects   returns a List of categories that friend is allowed to see
     */
    @Override
    public List<String> getFriendCategories(String friend)
            throws NullPointerException, FriendNotFoundException {
        
        if (friend == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(friendExists(friend)))
            throw new FriendNotFoundException("Insert an existing friend to get his categories!");

        List<String> friendCategoryList = new ArrayList<>();
        for (int i = 0; i < categoriesDefined.size() ; i++) {
            if(friendsAllowed.get(i).contains(friend))
                friendCategoryList.add(categoriesDefined.get(i));
        }
        return friendCategoryList;
    }

// ---------------------------------------------------------------------------------------------------------------------
    // AUX METHODS

    // checks if the string category complies with the accepted string rules
    private boolean validCategory(String category){
        if(category.isBlank())
            return false;

        return category.matches("^[a-z0-9_ -]{3,32}$");
    }

    // checks if the string category complies with the accepted string rules
    private boolean validPassw(String passw){
        if(passw.isBlank())                  // password che deve contenere un carattere minuscolo, un numero, un carattere maiuscolo
            return false;                    // e un carattere speciale e deve avere una lunghezza min 8 e max 20

        return passw.matches("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*\\W).{8,32})");
    }

    // checks if the string category complies with the accepted string rules
    private boolean validUser(String user){
        if(user.isBlank())
            return false;
        //(nome utente formato da soli caratteri alfanumerici più
        return user.matches("^[a-z0-9_-]{4,16}$"); // i caratteri'_' e '–' di lungezza min 3 e max 15)
    }


    // checks if category is present in the categories defined by the owner
    private boolean categoryExists(String category){
        return categoriesDefined.contains(category);
    }

    // checks if friend is present in general, in some list of allowed friend
    private boolean friendExists(String friend) {
        for (List<String> friends: friendsAllowed) {
            if(friends.contains(friend))
                return true;
        }
        return false;
    }

    // checks if dato is present in the board
    private boolean dataExists(E dato) {
        for (E data: boardFeed) {
            if(data.equals(dato))
                return true;
        }
        return false;
    }

    // returns a string that is the visual representation of the categories defined by the owner
    private String categoriesToString(){
        StringBuilder table = new StringBuilder();
        //getting the max between "CATEGORIES" and the longest category string
        final int charToSeparator = Math.max("CATEGORIES".length() , categoriesDefined.stream().map(String::length).max(Integer::compareTo).get());
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RESET = "\u001B[0m";
        table.append("CATEGORIES");
        table.append(" ".repeat(charToSeparator - "CATEGORIES".length()));
        table.append(ANSI_GREEN).append(" | ").append(ANSI_RESET);
        table.append("FRIENDS ALLOWED\n");
        for(int i = 0; i < categoriesDefined.size(); i++ ){
            table.append(categoriesDefined.get(i));
            table.append(" ".repeat(charToSeparator - categoriesDefined.get(i).length()));
            table.append(ANSI_GREEN).append(" | ").append(ANSI_RESET);
            table.append(friendsAllowed.get(i).toString());
            table.append("\n");
        }
        return table.toString();
    }

    // returns a string that is the visual representation of boardFeed
    private String boardToString() {
        StringBuilder feed = new StringBuilder();
        SimpleDateFormat date = new SimpleDateFormat("MMMMMMMM dd 'at' HH:mm:ss");
        final int SEPARATOR_LENGTH = 130;        // length of separator
        final int DEFAULT_CHARACTERS1 = 22+9;    // number of char to print postDate and "liked by "
        final int DEFAULT_CHARACTERS2 = 22+9+17; // number of char to print postDate, "liked by " and " and another one"
        final int DEFAULT_CHARACTERS3 = 22+9+14; // number of char to print postDate, "liked by " and " and others "
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLUE = "\u001B[34m";
        for (int i = 0; i < boardFeed.size() ; i++) {
            feed.append(ANSI_BLUE).append("-".repeat(SEPARATOR_LENGTH));
            feed.append(ANSI_RESET);
            feed.append("\n");
            feed.append(owner).append(" posted this ").append(boardFeed.get(i).getClass().getSimpleName()).append(" in category \"").append(dataCategories.get(i)).append("\"\n");
            feed.append(date.format(dataPostDates.get(i).getTime()));
            int offset;
            switch (dataLikes.get(i).size()){
                case 0:
                    break;
                case 1:
                    offset = DEFAULT_CHARACTERS1 + dataLikes.get(i).get(0).length() + String.valueOf(dataLikes.get(i).size()).length();
                    feed.append(" ".repeat(Math.max(0, SEPARATOR_LENGTH - offset)));
                    feed.append("Liked by ").append(dataLikes.get(i).get(0));
                    break;
                case 2:
                    offset = DEFAULT_CHARACTERS2 + dataLikes.get(i).get(0).length() + String.valueOf(dataLikes.get(i).size()).length();
                    feed.append(" ".repeat(Math.max(0, SEPARATOR_LENGTH - offset + 1)));
                    feed.append("Liked by ").append(dataLikes.get(i).get(0)).append(" and another one");
                    break;
                default:
                    offset = DEFAULT_CHARACTERS3 + dataLikes.get(i).get(0).length() + String.valueOf(dataLikes.get(i).size()).length();
                    feed.append(" ".repeat(Math.max(0, SEPARATOR_LENGTH - offset + 1)));
                    feed.append("Liked by ").append(dataLikes.get(i).get(0)).append(" and others ").append((dataLikes.get(i).size()) - 1);
            }
            feed.append("\n");
            feed.append(ANSI_BLUE).append("-".repeat(SEPARATOR_LENGTH));
            feed.append(ANSI_RESET);
            feed.append("\n");
            feed.append(boardFeed.get(i).toString()).append("\n");
        }
        feed.append(ANSI_BLUE).append("-".repeat(SEPARATOR_LENGTH));
        feed.append(ANSI_RESET);
        feed.append("\n");
        return feed.toString();
    }
}
