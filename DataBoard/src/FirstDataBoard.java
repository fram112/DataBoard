package com.company;

import java.text.SimpleDateFormat;
import java.util.*;

public class FirstDataBoard<E extends Data> implements DataBoard<E> {

    // Abstraction function(c) =
    /*
            < c.owner, c.ownerPassw,
              , { < c.boardFeed.getDataOnBoard(dato).data,
                    , c.boardFeed.getDataOnBoard(dato).category,
                    , c.boardFeed.geDataOnBoard(dato).postDate,
                    , c.boardFeed.getDataOnBoard(dato).likes > | dato is the generic E data in boardFeed Set },
              , { < category, c.categoriesDefined.get(category)> | category is the generic String that contains
                                                                   the category created by the owner } >
    */

    // Representation invariant(c) =
    /*
               c.owner != null
            && c.ownerPassw != null
            && c.boardFeed != null
            && c.categoriesDefined != null
            && c.validUser(owner)
            && c.validPassw(ownerPassw)

            && for all i,j in c.boardFeed.toArray() . 0 <= i,j < c.boardFeed.size() && i != j ==>
                    !(c.boardFeed.toArray()[i].data.equals(c.boardFeed.toArray()[j].data))                                                  (vincolo dati bacheca senza duplicati, ridondante per def di Set)
            && for all i,j in c.boardFeed.toArray() . 0 <= i < j < c.boardFeed.size() ==>
                    c.boardFeed.toArray()[i].postDate.compareTo(c.boardFeed.toArray()[j].postDate) > 0                                      (vincolo ordinamento dati per postDate)

            && for all category in c.categoriesDefined.keySet() ==> c.validCategory(category)                                               (vincolo chiavi/categorie valide)                                                            (vincolo chiavi/categorie valide)
            && for all i,j in c.categoriesDefined.keySet().toArray() . 0 <= i,j < c.categoriesDefined.size() && i != j ==>
                    !(c.categoriesDefined.keySet().toArray()[i].equals(c.categoriesDefined.keySet().toArray[j]))                            (vincolo chiavi/categorie senza duplicati, ridondante per def di Map)
            && for all category in c.categoriesDefined.keySet() .
                    (for all friend in c.categoriesDefined.get(category) ==> c.validUser(friend))                                           (vincolo amici autorizzati per categoria validi)
            && for all category in c.categoryDefined.keySet() .
                    (for all i,j in c.categoriesDefined.get(category).toArray() . 0 <= i,j < c.categoriesDefined.get(category).size() && i != j ==>
                          !(c.categoriesDefined.get(category).toArray()[i].equals(c.categoriesDefined.get(category).toArray()[j])))         (vincolo amici autorizzati per categoria senza duplicati, ridondante per def di Set)
     */

    private class DataOnBoard{

        // Abstraction function(c) =
        /*
                < c.data, c.category, c.postDate, { like_{0}, ..., like_{n-1} | n = likes.size()} >
        */

        // Representation invariant(c) =
        /*
                   c.data != null
                && c.category != null
                && c.postDate != null
                && c.likes != null

                && c.categoryDefined.containsKey(c.category)                                   (vincolo categorie dati esistenti)
                && for all i,j in c.likes.toArray() . 0 <= i,j < c.likes.size() && i != j ==>
                        !(c.likes.toArray()[i].equals(c.likes.toArray()[j])                    (vincolo likes dati senza duplicati, ridondante per def di set)    // && for all searchedLike in likes .
                && for all like in c.likes ==>                                                                                   //        for all like in likes ==> #{like.equals(searchedLike)} = 1
                        c.categoryDefined.get(category).contains(like)                         (vincolo utenti che hanno messo like al dato autorizzati a
                                                                                                vedere la categoria del dato e quindi autorizzati a
                                                                                                mettere like e quindi esistenti)
         */

        private E data;
        private String category;
        private Calendar postDate;
        private Set<String> likes;

        private DataOnBoard(E data, String category)
                throws NullPointerException {
            if(data == null || category == null)
                throw new NullPointerException("Null parameters inserted!");
            // la verifica che la categoria sia esistente in quelle definite, che sia valida e che
            // il dato non sia già presente nella bacheca viene fatta a monte, prima di invocare
            // questo costruttore (ad esempio nel metodo put)

            this.data = data;
            this.category = category;
            likes = new LinkedHashSet<>();          // postDate = data del momento in cui viene postato il dato
            postDate = Calendar.getInstance();      // (più precisamente momento in cui viene creato il dato da postare)
            
        }

        // get the first friend that liked data
        private String getFirstLike(){
            
            String firstLike = null;
            // doing only one cycle for getting the first like
            for(String like : likes) {
                firstLike = like;
                break;
            }
            
            return firstLike;
        }
    }


    private String owner; // username of the board owner
    private String ownerPassw; // password of the board owner

    private Set<DataOnBoard> boardFeed; // data container

    private Map<String, LinkedHashSet<String>> categoriesDefined; // table with categories defined,
                                                                // with associated list of friend allowed to see them

    //FirstDataBoard constructor
    /**
     * @requires  owner != null && passw != null
     *            && validUser(owner) && validPassw(passw)
     * @throws    NullPointerException if owner == null || passw == null
     *            (available in Java, unchecked)
     * @throws    InvalidEntryException if !(validUser(owner) && validPassw(passw))
     *            (available in Java, unchecked)
     * @modifies  this
     * @effects   informally, initializes all FirstDataBoard's fields
     */
    public FirstDataBoard(String owner, String passw)
            throws NullPointerException, InvalidEntryException {
        if(owner == null || passw == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(validUser(owner) && validPassw(passw)))
            throw new InvalidEntryException("Insert a valid username and a valid password to create the board!");

        boardFeed = new TreeSet<>((o1, o2) -> {    // lambda comparator to define the natural order,
            if (o1.data.equals(o2.data))   // the default way for ordering DataOnBoard object in boardFeed
                return 0;
            return (o1.postDate.after(o2.postDate) ? -1 : 1);
        });
        categoriesDefined = new HashMap<>();
        this.owner = owner;
        ownerPassw = passw;
        
    }


    // Crea una categoria di dati
    // se vengono rispettati i controlli di identità
    /**
     * @requires  category != null && passw != null
     *            && validCategory(category)
     *            && passw.equals(ownerPassword)
     *            && !(categoryExists(category))
     * @throws    NullPointerException if category == null || passw == null
     *            (available in Java, unchecked)
     * @throws    InvalidEntryException if !(validCategory(category))
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassword))
     *            (not available in Java, checked)
     * @throws    DuplicateEntryException if categoryExists(category)
     *            (not available in Java, checked)
     * @modifies  categoriesDefined
     * @effects   categoriesDefined.put(category, new LinkedHashSet<>())
     *            informally, create a new category and add it into categoryDefined with a empty friendList associated
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

        // making a new category with a new empty set of friend allowed to see that category
        categoriesDefined.put(category, new LinkedHashSet<>());
        
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
     * @modifies  categoriesDefined || (categoriesDefined && boardFeed)
     * @effects   if(this.getDataCategory(ownerPassw, category).isEmpty())
     *                  categoriesDefined.remove(category)
     *            else
     *                  boardFeed.removeAll(getDataOnBoardCategory(category));
     *                  categoriesDefined.remove(category);
     *            informally, delete a category and his associated friendList from categoryDefined if the board not
     *            contains data of that category, else delete all the data in boardFeed of that category too
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

        if(getDataCategory(ownerPassw, category).isEmpty())
            categoriesDefined.remove(category);
        else {
            boardFeed.removeAll(getDataOnBoardCategory(category)); //if there are data of category in the board we remove all
            categoriesDefined.remove(category);
        }
        
    }


    // Aggiunge un amico ad una categoria di dati
    // se vengono rispettati i controlli di identità
    /**
     * @requires  category != null && passw != null && friend != null
     *            && validUser(friend)
     *            && passw.equals(ownerPassword)
     *            && categoryExists(category)
     *            && !(friendAllowed(category, friend))
     * @throws    NullPointerException if category == null || passw == null || friend == null
     *            (available in Java, unchecked)
     * @throws    InvalidEntryException if !(validUser(friend))
     *            (available in Java, unchecked)
     * @throws    WrongPasswordException if !(passw.equals(ownerPassword))
     *            (not available in Java, checked)
     * @throws    CategoryNotFoundException if !(categoryExists(category))
     *            (not available in Java, checked)
     * @throws    DuplicateEntryException if friendAllowed(category, friend)
     *            (not available in Java, checked)
     * @modifies  categoriesDefined.get(category)
     * @effects   categoriesDefined.get(category).add(friend)
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

        categoriesDefined.get(category).add(friend);
        
    }


    // rimuove un amico da una categoria di dati
    // se vengono rispettati i controlli di identità
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
     * @modifies  categoriesDefined.get(category)
     * @effects   categoriesDefined.get(category).remove(friend)
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

        categoriesDefined.get(category).remove(friend);
        
    }


    // Inserisce un dato in bacheca
    // se vengono rispettati i controlli di identità
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
     * @effects   boardFeed.add(new DataOnBoard(dato, category))
     *            informally, add a new data into dataBoard with his associated categoria and a new empty list of likes
     */
    @Override
    public boolean put(String passw, E dato, String category)
            throws NullPointerException, WrongPasswordException, CategoryNotFoundException, DuplicateEntryException{
        
        if(category == null || passw == null || dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(!(categoryExists(category)))
            throw new CategoryNotFoundException("Insert an existing category to put data! ");
        if(dataExists(dato))
            throw new DuplicateEntryException("This data is already present!");

        return boardFeed.add(new DataOnBoard(dato, category));
    }


    // Restituisce una copia del dato in bacheca
    // se vengono rispettati i controlli di identità
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

        //System.out.println("hashcode dell'elemento dentro la board: "+ getDataOnBoard(dato).data.hashCode());
        return (E) getDataOnBoard(dato).data.clone();   // faccio il cast perchè sono sicuro che il tipo di
    }                                                   // getDataOnBoard.data è uguale a E


    // Rimuove il dato dalla bacheca
    // se vengono rispettati i controlli di identità
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
     * @modifies  boardFeed
     * @effects   boardFeed.remove(getDataOnBoard(dato)) && returns (E) getDataOnBoard(dato).data.clone()
     *            informally, delete dato and his associated category and list of likes from boardFeed and
     *            returns a copy of dato removed
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

        E copyOut = (E) getDataOnBoard(dato).data.clone();
        boardFeed.remove(getDataOnBoard(dato));
        
        return copyOut;
    }


    // Aggiunge un like a un dato
    // se vengono rispettati i controlli di identità
    // (se friend può vedere la categoria di quel dato a cui vuole mettere like)
    /**
     * @requires  friend != null && dato != null
     *            && dataExists(dato)
     *            && friendAllowed(getDataOnBoard(dato).category, friend)
     *            && !(getDataOnBoard(dato).likes.contains(friend))
     * @throws    NullPointerException if friend == null || dato == null
     *            (available in Java, unchecked)
     * @throws    DataNotFoundException if !(dataExist(dato))
     *            (not available in Java, checked)
     * @throws    FriendNotFoundException if !(friendAllowed(getDataOnBoard(dato).category, friend))
     *            (not available in Java, checked)
     * @throws    AlreadyLikedException if getDataOnBoard(dato).likes.contains(friend)
     * @modifies  getDataOnBoard(dato).likes
     * @effects   getDataOnBoard(dato).likes.add(friend)
     *            informally, add friend into getDataOnBoard.likes, the list of friends who likes dato
     */
    @Override
    public void insertLike(String friend, E dato)
            throws NullPointerException, DataNotFoundException, CategoryNotFoundException, FriendNotFoundException,
                   AlreadyLikedException {
        
        if (friend == null || dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if (!(dataExists(dato)))
            throw new DataNotFoundException("Insert an existing data to insert like");
        if (!(friendAllowed(getDataOnBoard(dato).category, friend)))                                // friend must exist in general and must allowed
            throw new FriendNotFoundException("Insert an existing friend to insert his like!"); // to see the dato category
        if (getDataOnBoard(dato).likes.contains(friend))
            throw new AlreadyLikedException("This like is already present!");

        // getting the version on board of data and add the like
        getDataOnBoard(dato).likes.add(friend);
        
    }


    // Crea la lista dei dati in bacheca di una determinata categoria
    // se vengono rispettati i controlli di identità
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
     *            getDataOnBoard(data).category.equals(category)
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
        for (DataOnBoard element : boardFeed) {
            if(element.category.equals(category)){
                dataCategoryList.add(element.data);
            }
        }
        return dataCategoryList;
    }


    // restituisce un iteratore (senza remove) che genera tutti i dati in
    // bacheca ordinati rispetto al numero di like
    // se vengono rispettati i controlli di identità
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
    public Iterator<E> getIterator(String passw)
            throws NullPointerException, WrongPasswordException{
        
        if(passw == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");

        // sorting the board for number of likes
        List<DataOnBoard> dataOnBoardList = new ArrayList<>(boardFeed);
        dataOnBoardList.sort((o1, o2) -> {          // lambda comparator, avrei potuto usare una chiamata anonima
            if (o1.likes.size() == o2.likes.size()) // di Comparator definendo compare, oppure avrei potuto creare
                return 0;                           // una classe che implementasse comparator e poi ridefinisse compare
            return (o1.likes.size() < o2.likes.size() ? 1 : -1);
        });

        // getting the values E of the data in DataOnBoard
        List<E> dataList = new ArrayList<>();
        for (DataOnBoard element : dataOnBoardList){
            dataList.add(element.data);
        }

        // making the iterator without remove operation
        List<E> dataUnmodifiableList = Collections.unmodifiableList(dataList);
        
        return dataUnmodifiableList.iterator();
    }


    // restituisce un iteratore (senza remove) che genera tutti i dati in
    // bacheca condivisi con un amico
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
    public Iterator<E> getFriendIterator(String friend)
            throws NullPointerException, FriendNotFoundException {
        
        if(friend == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(friendExists(friend)))
            throw new FriendNotFoundException("Insert an existing friend to get the iterator!");

        // getting data shared with friend
        List<E> dataSharedList = new ArrayList<>();
        List<String> friendCategory = getFriendCategories(friend);
        for(DataOnBoard element : boardFeed){
            if(friendCategory.contains(element.category))
                dataSharedList.add(element.data);
        }

        // making the iterator without remove operation
        List<E> dataSharedUnmodifiableList = Collections.unmodifiableList(dataSharedList);
        
        return dataSharedUnmodifiableList.iterator();
    }

// ---------------------------------------------------------------------------------------------------------------------
    //OTHERS OPERATIONS


    // restituisce la rappresentazione dell'oggetto sotto forma di una stringa
    /**
     * @effects  returns a string that is the visual representation of this
     */
    @Override
    public String toString() {
        
        return "TABLE OF CATEGORIES DEFINED BY THE OWNER " + owner + ":\n" +
                categoriesToString() +
                "\n\n\n" +
                owner+ "'s " + this.getClass().getSimpleName().toUpperCase() + " BOARD" + "\n" +
                boardToString();
    }


    // stampa a schermo la rappresentazione delle categorie create dall'owner
    /**
     * @effects  print categoriesToString()
     */
    @Override
    public void displayCategories(){
        
        System.out.println(categoriesToString());
        
    }


    // stampa a schermo la rappresentazione della bacheca
    /**
     * @effects  print boardToString()
     */
    @Override
    public void displayBoard(){
        
        System.out.println(boardToString());
        
    }


    // posta di nuovo un dato già presente in bacheca, in modo da farlo ritornare in testa
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
            throws NullPointerException, WrongPasswordException, DataNotFoundException {
        
        if(passw == null || dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if(!(passw.equals(ownerPassw)))
            throw new WrongPasswordException("Wrong password inserted!");
        if(!(dataExists(dato)))
            throw new DataNotFoundException("Insert an existing data to repost it!");

        String categoryTmp = getDataOnBoard(dato).category;
        E dataTmp = remove(ownerPassw, dato);
        try {                                      // non dovrebbe mai succedere perchè in questo caso chiamiamo la put
            put(ownerPassw, dataTmp, categoryTmp); // sempre su dati già esistenti e prima di metterlo lo togliamo,
        }                                          // quindi non avremo nemmeno DuplicateEntryException
        catch (CategoryNotFoundException | DuplicateEntryException e){
            e.printStackTrace();
        }
       // 
    }


    // restituisce l'insieme dei like(friend) che ha ricevuto un dato
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

        return new LinkedHashSet<>(getDataOnBoard(dato).likes);
    }

    // rimuove un like a un dato
    // se friend è autorizzato a vedere il dato e se ha già messo like
    /**
     * @requires  friend != null && dato != null
     *            && dataExists(dato)
     *            && friendAllowed(getDataOnBoard(dato).category, friend)
     *            && getDataOnBoard(dato).likes.contains(friend)
     * @throws    NullPointerException if friend == null || dato == null
     *            (available in Java, unchecked)
     * @throws    DataNotFoundException if !(dataExist(dato))
     *            (not available in Java, checked)
     * @throws    FriendNotFoundException if !(friendAllowed(getDataOnBoard(dato).category, friend))
     *            (not available in Java, checked)
     * @throws    LikeNotFoundException if !(getDataOnBoard(dato).likes.contains(friend))
     * @modifies  getDataOnBoard(dato).likes
     * @effects   getDataOnBoard(dato).likes.remove(friend)
     *            informally, remove friend from getDataOnBoard.likes, the list of friends who likes dato
     */
    @Override
    public void removeLike(String friend, E dato)
            throws NullPointerException, DataNotFoundException, LikeNotFoundException {
        
        if (friend == null || dato == null)
            throw new NullPointerException("Null parameters inserted!");
        if (!(dataExists(dato)))
            throw new DataNotFoundException("Insert an existing data to remove like!");
        if (!(getDataOnBoard(dato).likes.contains(friend)))                           // se il like è inserito da quell'amico automaticamente vuol dire
            throw new LikeNotFoundException("Insert an existing like to remove it!"); // che quest'ultimo era autorizzato a vederlo altrimenti non troviamo il like

        // getting the version on board of data and remove the like
        getDataOnBoard(dato).likes.remove(friend);
        
    }


    // restituisce un booleano che indica se friend è autorizzato a vedere i dati di category
    /**
     * @requires  category != null && friend != null
     *            && categoryExists(category)
     * @throws    NullPointerException if category == null || friend == null
     *            (available in Java, unchecked)
     * @throws    CategoryNotFoundException if !(categoryExists(category))
     *            (not available in Java, checked)
     * @effects   categoriesDefined.get(category).contains(friend)
     *            informally, returns a boolean that indicate if friend is in the list of friends allowed
     *            to see category
     */
    @Override
    public boolean friendAllowed(String category, String friend)
            throws NullPointerException, CategoryNotFoundException {
        
        if (category == null || friend == null)
            throw new NullPointerException("Null parameters inserted!");
        if (!(categoryExists(category)))
            throw new CategoryNotFoundException("Insert an existing category to know if friend is allowed!");

        return categoriesDefined.get(category).contains(friend);
    }


    // restituisce la lista delle categorie condivise con un amico,
    // cioè le categorie dei dati che un amico può visualizzare
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

        // getting friend categories
        List<String> friendCategoryList = new ArrayList<>();
        for (String category : categoriesDefined.keySet()) {
            for (String searchedFriend : categoriesDefined.get(category)) {
                if (searchedFriend.equals(friend))
                    friendCategoryList.add(category);
            }
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
        return categoriesDefined.containsKey(category);
    }

    // checks if friend is present in general, in some list of allowed friend
    private boolean friendExists(String friend) {
        for (String category : categoriesDefined.keySet()) {
            for (String searchedFriend : categoriesDefined.get(category)) {
                if (searchedFriend.equals(friend))
                    return true;
            }
        }
        return false;
    }

    // checks if dato is present in the board
    private boolean dataExists(E dato) {
        for (DataOnBoard element : boardFeed) {
            if (element.data.equals(dato))
                return true;
        }
        return false;
    }

    // returns the E value field of DataOnBoard
    private DataOnBoard getDataOnBoard(E dato) { // in tutti i posti dove invochiamo questo metodo controlliamo sempre
        DataOnBoard element = null;              // prima che il dato è nella board
        for (DataOnBoard el : boardFeed) {
            if (el.data.equals(dato)) {
                element = el;
                break;
            }
        }
        return element;
    }

    // like the public method getDataCategory but for inner class purpose
    private List<DataOnBoard> getDataOnBoardCategory(String category){
        // getting all the data of category
        List<DataOnBoard> dataOnBoardCategoryList = new ArrayList<>();
        for (DataOnBoard element : boardFeed) {
            if(element.category.equals(category)){
                dataOnBoardCategoryList.add(element);
            }
        }
        return dataOnBoardCategoryList;
    }

    // returns a string that is the visual representation of the categories defined by the owner
    private String categoriesToString(){
        StringBuilder table = new StringBuilder();
        //getting the max between "CATEGORIES" and the longest category string
        final int charToSeparator = Math.max("CATEGORIES".length() , categoriesDefined.keySet().stream().map(String::length).max(Integer::compareTo).get());
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RESET = "\u001B[0m";
        table.append("CATEGORIES");
        table.append(" ".repeat(charToSeparator - "CATEGORIES".length()));
        table.append(ANSI_GREEN).append(" | ").append(ANSI_RESET);
        table.append("FRIENDS ALLOWED\n");
        for(String category : categoriesDefined.keySet()){
            table.append(category);
            table.append(" ".repeat(charToSeparator - category.length()));
            table.append(ANSI_GREEN).append(" | ").append(ANSI_RESET);
            table.append(categoriesDefined.get(category).toString());
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
        for(DataOnBoard element : boardFeed){
            feed.append(ANSI_BLUE).append("-".repeat(SEPARATOR_LENGTH));
            feed.append(ANSI_RESET);
            feed.append("\n");
            feed.append(owner).append(" posted this ").append(element.data.getClass().getSimpleName()).append(" in category \"").append(element.category).append("\"\n");
            feed.append(date.format(element.postDate.getTime()));
            int offset;
            switch (element.likes.size()){
                case 0:
                    break;
                case 1:
                    offset = DEFAULT_CHARACTERS1 + element.getFirstLike().length() + String.valueOf(element.likes.size()).length();
                    feed.append(" ".repeat(Math.max(0, SEPARATOR_LENGTH - offset)));
                    feed.append("Liked by ").append(element.getFirstLike());
                    break;
                case 2:
                    offset = DEFAULT_CHARACTERS2 + element.getFirstLike().length() + String.valueOf(element.likes.size()).length();
                    feed.append(" ".repeat(Math.max(0, SEPARATOR_LENGTH - offset + 1)));
                    feed.append("Liked by ").append(element.getFirstLike()).append(" and another one");
                    break;
                default:
                    offset = DEFAULT_CHARACTERS3 + element.getFirstLike().length() + String.valueOf(element.likes.size()).length();
                    feed.append(" ".repeat(Math.max(0, SEPARATOR_LENGTH - offset + 1)));
                    feed.append("Liked by ").append(element.getFirstLike()).append(" and others ").append(element.likes.size() - 1);
            }
            feed.append("\n");
            feed.append(ANSI_BLUE).append("-".repeat(SEPARATOR_LENGTH));
            feed.append(ANSI_RESET);
            feed.append("\n");
            feed.append(element.data.toString()).append("\n");
        }
        feed.append(ANSI_BLUE).append("-".repeat(SEPARATOR_LENGTH));
        feed.append(ANSI_RESET);
        feed.append("\n");
        return feed.toString();
    }

}
