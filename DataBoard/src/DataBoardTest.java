package com.company;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class DataBoardTest {

    public static void main(String[] args) {
        DataBoardTest.testFirst();
        DataBoardTest.testSecond();
    }


    public static void testFirst() {

        // colori per evidenziare le stringhe nel terminale
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_YELLOW = "\u001B[33m";
        final int SEPARATOR_LENGTH = 130; // lunghezza separatore visualizzazione dei dati
        
        // utenti di prova
        String user1 = "fram3";
        String user2 = "giuseppe-212";
        String user3 = "";
        String user4 = "username_lunghissimissimo";
        String user5 = "user%#";

        // password di prova
        String passw1 = "A35sksTsh#svw!gE";
        String passw2 = "Pass1%";
        String passw3 = "12345678";
        String passw4 = "qwerTy12345$";
        String passw5 = "";

        System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ TEST PRIMA IMPLEMENTAZIONE ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        // test Text board
        try {
            System.out.println("> Creo le Text boards degli utenti di test ...");

            System.out.println("    • Creo una board con un username e password validi. Non mi aspetto nessuna eccezione. ");
            DataBoard<Text> board1 = new FirstDataBoard<>(user1, passw1);

            try {
                System.out.println("    • Creo una board con un username valido ma con password troppo corta. Mi aspetto InvalidEntryException. ");
                DataBoard<Text> board2 = new FirstDataBoard<>(user2, passw2);
            }
            catch (InvalidEntryException e){
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Creo una board con un username vuoto e password con soli numeri. Mi aspetto InvalidEntryException. ");
                DataBoard<Text> board3 = new FirstDataBoard<>(user3, passw3);
            }
            catch (InvalidEntryException e){
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Creo una board con un username troppo lungo ma password valida. Mi aspetto InvalidEntryException.  ");
                DataBoard<Text> board4 = new FirstDataBoard<>(user4, passw4);
            }
            catch (InvalidEntryException e){
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Creo una board con un username con caratteri non ammessi e password vuota. Mi aspetto InvalidEntryException. ");
                DataBoard<Text> board5 = new FirstDataBoard<>(user5, passw5);
            }
            catch (InvalidEntryException e){
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                //test creazione categories
            System.out.println("> Creo le categorie della board di test ...");

            System.out.println("    • Creo una categoria valida e uso la password giusta, non mi aspetto nessuna eccezione.");
            board1.createCategory("citazioni", "A35sksTsh#svw!gE");
            System.out.println("    • Creo una categoria valida e uso la password giusta, non mi aspetto nessuna eccezione.");
            board1.createCategory("testi importanti", "A35sksTsh#svw!gE");
            System.out.println("    • Creo una categoria valida e uso la password giusta, non mi aspetto nessuna eccezione.");
            board1.createCategory("programs output", "A35sksTsh#svw!gE");
            System.out.println("    • Creo una categoria valida e uso la password giusta, non mi aspetto nessuna eccezione.");
            board1.createCategory("foo", "A35sksTsh#svw!gE");

            try {
                System.out.println("    • Creo una categoria con caratteri non ammessi e uso la password giusta, mi aspetto InvalidEntryException.");
                board1.createCategory("#categoria_Non_valida#", "A35sksTsh#svw!gE");
            } catch (InvalidEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Creo una categoria valida ma uso una password sbagliata, mi aspetto WrongPasswordException.");
                board1.createCategory("todo", "password");
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Creo una categoria valida ma già presente e uso la password giusta, mi aspetto DuplicateEntryException.");
                board1.createCategory("foo", "A35sksTsh#svw!gE");
            } catch (DuplicateEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test aggiunta amici ad una categoria

            System.out.println("> Aggiungo(autorizzo) amici ad una categoria ... ");

            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("citazioni", "A35sksTsh#svw!gE", "giuseppe-212");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("citazioni", "A35sksTsh#svw!gE", "lino99");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("citazioni", "A35sksTsh#svw!gE", "alexanderes13");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("citazioni", "A35sksTsh#svw!gE", "silent77");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("testi importanti", "A35sksTsh#svw!gE", "giuseppe-212");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("testi importanti", "A35sksTsh#svw!gE", "alexanderes13");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("testi importanti", "A35sksTsh#svw!gE", "lino99");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("testi importanti", "A35sksTsh#svw!gE", "calo_98");

            try {
                System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando una password sbagliata. Mi aspetto WrongPasswordException. ");
                board1.addFriend("citazioni", "password", "pippo");
            }
            catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Aggiungo un nuovo amico con username non valido, ad una categoria esistente, usando la password giusta. Mi aspetto InvalidEntryException. ");
                board1.addFriend("citazioni", "A35sksTsh#svw!gE", "&uSer%!#");
            }
            catch (InvalidEntryException e){
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria non esistente, usando la password giusta. Mi aspetto CategoryNotFoundException. ");
                board1.addFriend("categoria inesistente", "A35sksTsh#svw!gE", "gian_marco7");
            }
            catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Aggiungo un amico già esistente, ad una categoria esistente, usando la password giusta. Mi aspetto DuplicateEntryException. ");
                board1.addFriend("citazioni", "A35sksTsh#svw!gE", "giuseppe-212");
            } catch (DuplicateEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test rimozione amici da una categoria

            System.out.println("> Rimuovo(nego) amici da una categoria ...");

            try {
                System.out.println("    • Rimuovo un amico da una categoria esistente e che è autorizzato a vedere, usando una password sbagliata. Mi aspetto WrongPasswordException.");
                board1.removeFriend("testi importanti", "password", "lino99");
            }
            catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Rimuovo un amico da una categoria esistente ma che non è autorizzato a vedere, usando la password giusta. Mi aspetto FriendNotFoundException.");
                board1.removeFriend("testi importanti", "A35sksTsh#svw!gE", "silent77");
            }
            catch (FriendNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try{
                System.out.println("    • Rimuovo un amico da una categoria non esistente, usando la password giusta. Mi aspetto CategoryNotFoundException.");
                board1.removeFriend("categoria inesistente", "A35sksTsh#svw!gE", "alexanderes13");
            } catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Rimuovo un amico da una categoria esistente e che è autorizzato a vedere, usando la password giusta. Non mi aspetto nessuna eccezione.");
            board1.removeFriend("testi importanti", "A35sksTsh#svw!gE", "calo_98");

            System.out.println();
            System.out.println();
            System.out.println();

            // =========================================================================================================
                // testi di prova

            Text ipsum = new Text("Cicerone", "ipsum", "txt",
                    "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, \n" +
                            "totam rem aperiam eaque ipsa, quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt, explicabo. \n" +
                            "Nemo enim ipsam voluptatem, quia voluptas sit, aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos, \n" +
                            "qui ratione voluptatem sequi nesciunt neque porro quisquam est, qui dolorem ipsum, quia dolor sit, \n" +
                            "amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt, ut labore \n" +
                            "et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis\n" +
                            "suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? \n" +
                            "Quis autem vel eum iure reprehenderit, qui in ea voluptate velit esse, quam nihil molestiae consequatur, \n" +
                            "vel illum, qui dolorem eum fugiat, quo voluptas nulla pariatur? [33] At vero eos et accusamus et iusto odio \n" +
                            "dignissimos ducimus, qui blanditiis praesentium voluptatum deleniti atque corrupti, quos dolores et quas molestias \n" +
                            "excepturi sint, obcaecati cupiditate non provident, similique sunt in culpa, qui officia deserunt mollitia animi, \n" +
                            "id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, \n" +
                            "cum soluta nobis est eligendi optio, cumque nihil impedit, quo minus id, quod maxime placeat, facere possimus, \n" +
                            "omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum \n" +
                            "necessitatibus saepe eveniet, ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic \n" +
                            "tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus\n" +
                            "asperiores repellat.",
                    "Helvetica", 16, "white", false, true, false);

            Text text1 = new Text("fram3", "myFirstProgram", "java", "Hello, world!");
            Text text2 = new Text("giuseppe-212", "le montagne Parole", "pdf", "In una terra lontana, dietro le montagne Parole, \n" +
                    "lontani dalle terre di Vocalia e Consonantia, vivono i testi casuali. Vivono isolati nella cittadina di Lettere, \n" +
                    "sulle coste del Semantico, un immenso oceano linguistico. Un piccolo ruscello chiamato Devoto Oli attraversa quei luoghi, \n" +
                    "rifornendoli di tutte le regolalie di cui hanno bisogno. È una terra paradismatica, un paese della cuccagna in cui golose \n" +
                    "porzioni di proposizioni arrostite volano in bocca a chi le desideri. Non una volta i testi casuali sono stati dominati \n" +
                    "dall’onnipotente Interpunzione, una vita davvero non ortografica. Un giorno però accadde che la piccola riga di un testo casuale, \n" +
                    "di nome Lorem ipsum, decise di andare a esplorare la vasta Grammatica. \n" +
                    "Il grande Oximox tentò di dissuaderla, poiché quel luogo pullulava di virgole spietate, punti interrogativi selvaggi e subdoli \n" +
                    "punti e virgola, ma il piccolo testo casuale non si fece certo fuorviare.\n" +
                    "Raccolse le sue sette maiuscole, fece scorrere la sua iniziale nella cintura, e si mise in cammino. \n" +
                    "Quando superò i primi colli dei monti Corsivi, si voltò a guardare un’ultima volta la skyline di Lettere, \n" +
                    "la sua città, la headline del villaggio Alfabeto e la subline della sua stessa strada, il vicolo Riga. Una domanda retorica");
            Text text3 = new Text("Lao Tzu", "cit", "txt", "Fa più rumore un albero che cade di una foresta che cresce.");
            Text text4 = new Text("Bertrand Meyer", "Meyer", "txt", "Require no more, promise no less.");
            Text text5 = new Text("fram3", "Li Europan lingues", "docx", "Li Europan lingues es membres del sam familie. \n" +
                    "Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. \n" +
                    "Li lingues differe solmen in li grammatica, li pronunciation e li plu commun vocabules. \n" +
                    "Omnicos directe al desirabilite de un nov lingua franca: On refusa continuar payar custosi traductores. \n" +
                    "At solmen va esser necessi far uniform grammatica, pronunciation e plu sommun paroles. Ma quande lingues coalesce, \n" +
                    "li grammatica del resultant lingue es plu simplic e regulari quam ti del coalescent lingues. \n" +
                    "Li nov lingua franca va esser plu simplic e regulari quam li existent Europan lingues. \n" +
                    "It va esser tam simplic quam Occidental in fact, it va esser Occidental. A un Angleso it va semblar un \n" +
                    "simplificat Angles, quam un skeptic Cambridge amico dit me que Occidental es.Li Europan lingues es membres \n" +
                    "del sam familie. Lor separat existentie es un myth. Por scientie, musica, sport etc, litot Europa usa li sam vocabular. \n" +
                    "Li lingues differe solmen in li grammatica, li pronunciation e li plu commun vocabules. \n" +
                    "Omnicos directe al desirabilite de un nov lingua franca: On refusa continuar payar custosi traductores. \n" +
                    "At solmen va esser necessi far uniform grammatica, pronunciation e plu sommun paroles.");
            Text text6 = new Text("Lev Tolstoj", "citazione", "docx", "Tutti pensano a cambiare il mondo, ma nessuno pensa a cambiar se stesso.");
            Text text7 = new Text("Mark Twain", "cit2", "txt", "È meglio tenere la bocca chiusa e lasciare che le persone pensino che sei uno sciocco piuttosto che aprirla e \n" +
                                                                                        "togliere ogni dubbio.");
            Text text8 = new Text("fram3", "prova", "txt", "Questo testo è solo una prova");
            Text text9 = new Text("fram3", "asjygsjdhg","txt", "lalalalalalala");


            // =========================================================================================================
                // test inserimento testi nella board
            System.out.println("> Inserisco dati nella board ...");

            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", ipsum, "testi importanti");
            Thread.sleep(1000); // inserisco un ritardo di un secondo per far vedere la differenza tra dati pubblicati in momenti diversi
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", text1, "programs output");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", text2, "testi importanti");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", text3, "citazioni");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", text4, "citazioni");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", text5, "testi importanti");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", text7, "citazioni");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", text8, "foo");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", text9, "foo");
            Thread.sleep(1000);

            try {
                System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando una password sbagliata. Mi aspetto WrongPasswordException. ");
                board1.put("password", text1, "programs output");
                Thread.sleep(1000);
            }
            catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Inserisco un nuovo dato in una categoria non esistente, usando la password giusta. Mi aspetto CategoryNotFoundException. ");
                board1.put("A35sksTsh#svw!gE", text6, "categoria inesistente");
                Thread.sleep(1000);
            }
            catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Inserisco un dato già esistente in una categoria esistente, usando la password giusta. Mi aspetto DuplicateEntryException. ");
                board1.put("A35sksTsh#svw!gE", text7, "citazioni");
            }
            catch (DuplicateEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test getter dati dalla board

            System.out.println("> Recupero dati dalla board ...");

            Text getting = null;

            try {
                System.out.println("    • Recupero un dato esistente, usando una password sbagliata. Mi aspetto WrongPasswordException.");
                getting = board1.get("password", ipsum);
            }
            catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Recupero un dato non esistente, usando la password giusta. Mi aspetto DataNotFoundException.");
                getting = board1.get("A35sksTsh#svw!gE", text6);
            } catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Recupero un dato esistente, usando la password giusta. Non mi aspetto nessuna eccezione.");
            getting = board1.get("A35sksTsh#svw!gE", ipsum);
            System.out.println("       • Stampo il dato recuperato:");
            System.out.println("-".repeat(SEPARATOR_LENGTH));
            getting.display(); // stampo il dato usando il metodo overrided da Data
            System.out.println("-".repeat(SEPARATOR_LENGTH));
            //System.out.println("hashcode del copyout: "+ getting.hashCode()); //per verificare che si ha effettivamente il copyout

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test remove dati dalla board

            System.out.println("> Rimuovo dati dalla board ...");

            Text removed = null;

            try {
                System.out.println("    • Rimuovo un dato esistente dalla board, usando una password sbagliata. Mi aspetto WrongPasswordException.");
                removed = board1.remove("password", text2);
            }
            catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Rimuovo un dato non esistente dalla board, usando la password giusta. Mi aspetto DataNotFoundException.");
                removed = board1.remove("A35sksTsh#svw!gE", text6);
            } catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Rimuovo un dato esistente dalla board, usando la password giusta. Non mi aspetto nessuna eccezione.");
            removed = board1.remove("A35sksTsh#svw!gE", text2);
            System.out.println("       • Stampo il dato rimosso:");
            System.out.println("-".repeat(SEPARATOR_LENGTH));
            removed.display();
            System.out.println("-".repeat(SEPARATOR_LENGTH));

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test rimozione categories

            System.out.println("> Rimuovo categorie nella board ...");

            try {
                System.out.println("    • Rimuovo una categoria esistente ma uso una password sbagliata. Mi aspetto WrongPasswordException. ");
                board1.removeCategory("foo", "password");
            }
            catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Rimuovo una categoria non esistente e uso la password giusta. Mi aspetto CategoryNotFoundException.");
                board1.removeCategory("categoria inesistente", "A35sksTsh#svw!gE");
            }
            catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            //board1.displayBoard();
            System.out.println("    • Rimuovo una categoria esistente e uso la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.removeCategory("foo", "A35sksTsh#svw!gE");
            //board1.displayBoard(); // decommentando si può vedere che se la categoria rimossa conteneva dei dati allora
                                    // anche questi sono stati rimossi
            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test inserimento like ad un dato da parte di un amico

            System.out.println("> Inserisco like ai dati ...");

            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("giuseppe-212", ipsum);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("lino99", ipsum);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("alexanderes13", ipsum);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("lino99", text4);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("alexanderes13", text3);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("silent77", text7);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("lino99", text7);

            try {
                System.out.println("    • Inserisco un nuovo like da parte di un non amico esistente, ad un dato esistente. Mi aspetto FriendNotFoundException.");
                board1.insertLike("notexistingfriend", ipsum);
            }
            catch (FriendNotFoundException e){
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Inserisco un like già inserito da parte di un amico esistente, ad un dato esistente. Mi aspetto AlreadyLikedException.");
                board1.insertLike("giuseppe-212", ipsum);
            }
            catch (AlreadyLikedException e){
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un non dato esistente. Mi aspetto DataNotFoundException.");
                board1.insertLike("lino99", text6);
            } catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test richiesta lista dei dati di una categoria

            System.out.println("> Recupero le liste dei dati di una categoria ...");

            List<Text> dataCategory = null;
            try {
                System.out.println("    • Recupero la lista dei dati di una categoria esistente, usando una password sbagliata. Mi aspetto WrongPasswordException.");
                dataCategory = board1.getDataCategory("password", "citazioni");
            }
            catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Recupero la lista dei dati di una categoria non esistente, usando la password giusta. Mi aspetto CategoryNotFoundException.");
                dataCategory = board1.getDataCategory("A35sksTsh#svw!gE", "citazioni inesistenti");
            } catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Recupero la lista dei dati di una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione.");
            dataCategory = board1.getDataCategory("A35sksTsh#svw!gE", "citazioni");
            System.out.println("       • Stampo la lista:");
            System.out.println(dataCategory);

            System.out.println();
            System.out.println();
            System.out.println();


            // ---------------------------------------------------------------------------------------------------------
                // test iteratore sui dati della board ordinati per like

            System.out.println("> Richiedo iteratori sui dati della bacheca, ordinati per totale di like ...");

            Iterator<Text> likeIterator = null;

            try {
                System.out.println("    • Richiedo un iteratore, usando una password sbagliata. Mi aspetto WrongPasswordException. ");
                likeIterator = board1.getIterator("password");
            }
            catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Dimostro che l'iteratore è senza operazione di remove. Mi aspetto UnsupportedOperationException.");
                likeIterator = board1.getIterator("A35sksTsh#svw!gE");
                while (likeIterator.hasNext()) {
                    likeIterator.remove();
                }
            }
            catch (UnsupportedOperationException e){
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Richiedo un iteratore, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            likeIterator = board1.getIterator("A35sksTsh#svw!gE");

            System.out.println("       • Itero e stampo i dati ...");
            while (likeIterator.hasNext()) {
                System.out.println("-".repeat(SEPARATOR_LENGTH));
                likeIterator.next().display();
            }
            System.out.println("-".repeat(SEPARATOR_LENGTH));

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test iteratore dei dati condivisi con un amico

            System.out.println("> Richiedo iteratori sui dati della bacheca, condivisi con un amico");

            Iterator<Text> friendIterator = null;

            try {
                System.out.println("    • Richiedo un iteratore sui dati di un amico non esistente. Mi aspetto FriendNotFoundException. ");
                friendIterator = board1.getFriendIterator("alex");
            } catch (FriendNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Dimostro che l'iteratore è senza operazione di remove. Mi aspetto UnsupportedOperationException.");
                friendIterator = board1.getFriendIterator("silent77");
                while (friendIterator.hasNext()) {
                    friendIterator.remove();
                }
            }
            catch (UnsupportedOperationException e){
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            System.out.println("    • Richiedo un iteratore sui dati di un amico esistente. Non mi aspetto nessuna eccezione. ");
            friendIterator = board1.getFriendIterator("silent77");

            System.out.println("       • Itero e stampo i dati ...");
            while (friendIterator.hasNext()) {
                System.out.println("-".repeat(SEPARATOR_LENGTH));
                friendIterator.next().display();
            }
            System.out.println("-".repeat(SEPARATOR_LENGTH));

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test getter lista di categorie che un amico è autorizzato a vedere

            System.out.println("> Richiedo la lista di categorie che un amico è autorizzato a visualizzare ...");

            List<String> friendCategories = null;

            try {
                System.out.println("    • Richiedo la lista di categorie di un amico non esistente. Mi aspetto FriendNotFoundException.");
                friendCategories = board1.getFriendCategories("alex");
            }
            catch (FriendNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Richiedo la lista di categorie di un amico esistente. Non mi aspetto nessuna eccezione.");
            friendCategories = board1.getFriendCategories("lino99");
            System.out.println("       • Stampo la lista:\n");
            System.out.println("         "+friendCategories);

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test metodo che ci dice se un amico è autorizzato a visualizzare una categoria o no

            System.out.println("> Chiedo se un amico è autorizzato a visualizzare una categoria ...");

            try {
                System.out.println("    • Chiedo se l'amico è autorizzato a visualizzare una categoria non esistente. Mi aspetto CategoryNotFoundException.");
                System.out.println(board1.friendAllowed("categoria inesistente", "lino99"));
            }
            catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Chiedo se l'amico è autorizzato a visualizzare una categoria esistente. Non mi aspetto nessuna eccezione.\n");
            System.out.println("      "+board1.friendAllowed("citazioni", "lino99"));

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test rimozione di un like ad un dato

            System.out.println("> Rimuovo like ai dati ...");

            try {
                System.out.println("    • Rimuovo un like non inserito, ad un dato esistente. Mi aspetto LikeNotFoundException.");
                board1.removeLike("giuseppe-212", text4);
            }
            catch (LikeNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Rimuovo un like inserito, ad un dato non esistente. Mi aspetto DataNotFoundException.");
                board1.removeLike("giuseppe-212", text6);
            }
            catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Rimuovo un like inserito, da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.removeLike("lino99", text4);

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test richiesta insieme dei likes di un dato

            System.out.println("> Richiedo insieme dei likes dei dati ...");

            LinkedHashSet<String> likes = null;

            try {
                System.out.println("    • Richiedo insieme dei likes di un dato non esistente. Mi aspetto DataNotFoundException.");
                likes = board1.getLikes(text6);
            }
            catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Richiedo insieme dei likes di un dato esistente. Non mi aspetto nessuna eccezione.");
            likes = board1.getLikes(ipsum);
            System.out.println("       • Stampo l'insieme:\n");
            System.out.println("         "+likes);

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test del repost di un dato

            System.out.println("> Faccio il repost dei dati ...\n");

            try {
                System.out.println("    • Faccio il repost di un dato esistente, usando una password sbagliata. Mi aspetto WrongPasswordException.");
                board1.repost("password", text4);
            }
            catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Faccio il repost di un dato non esistente, usando la password giusta. Mi aspetto DataNotFoundException.");
                board1.repost("A35sksTsh#svw!gE", text6);
            }
            catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            //board1.displayBoard();
            try {
                System.out.println("    • Faccio il repost di un dato esistente, usando la password giusta. Non mi aspetto nessuna eccezione.");
                board1.repost("A35sksTsh#svw!gE", text4);
            }
            catch (DataNotFoundException e){
                e.printStackTrace();
            }
            //board1.displayBoard(); //decommentando si può vedere la differenza delle due board stampate prima e dopo il repost

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
                // test metodi display e toString

            // stampa board
            System.out.println("> Uso il metodo display della board ...\n");
            board1.displayBoard();
            System.out.println();

            System.out.println("> Uso il metodo display della tabella delle categorie ...\n");
            // stampa categorie definite
            board1.displayCategories();
            System.out.println();

            // test toString di DataBoard
            System.out.println("> Test toString dell'oggetto DataBoard ...\n");
            System.out.println(board1);
        } catch (Exception e) {
            System.out.println(ANSI_RED+e+": this should never have happened");
        }
    }








    public static void testSecond() {

        // colori per evidenziare le stringhe nel terminale
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_YELLOW = "\u001B[33m";
        final int SEPARATOR_LENGTH = 130; // lunghezza separatore visualizzazione dei dati

        // utenti di prova
        String user1 = "fram3";
        String user2 = "giuseppe-212";
        String user3 = "";
        String user4 = "username_lunghissimissimo";
        String user5 = "user%#";

        // password di prova
        String passw1 = "A35sksTsh#svw!gE";
        String passw2 = "Pass1%";
        String passw3 = "12345678";
        String passw4 = "qwerTy12345$";
        String passw5 = "";

        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ TEST SECONDA IMPLEMENTAZIONE +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        // test Image board
        try {
            System.out.println("> Creo le Image boards degli utenti di test ...");

            System.out.println("    • Creo una board con un username e password validi. Non mi aspetto nessuna eccezione. ");
            DataBoard<Image> board1 = new SecondDataBoard<>(user1, passw1);

            try {
                System.out.println("    • Creo una board con un username valido ma con password troppo corta. Mi aspetto InvalidEntryException. ");
                DataBoard<Image> board2 = new SecondDataBoard<>(user2, passw2);
            } catch (InvalidEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Creo una board con un username vuoto e password con soli numeri. Mi aspetto InvalidEntryException. ");
                DataBoard<Image> board3 = new SecondDataBoard<>(user3, passw3);
            } catch (InvalidEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Creo una board con un username troppo lungo ma password valida. Mi aspetto InvalidEntryException.  ");
                DataBoard<Image> board4 = new SecondDataBoard<>(user4, passw4);
            } catch (InvalidEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Creo una board con un username con caratteri non ammessi e password vuota. Mi aspetto InvalidEntryException. ");
                DataBoard<Image> board5 = new SecondDataBoard<>(user5, passw5);
            } catch (InvalidEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            //test creazione categories
            System.out.println("> Creo le categorie della board di test ...");

            System.out.println("    • Creo una categoria valida e uso la password giusta, non mi aspetto nessuna eccezione.");
            board1.createCategory("foto belle", "A35sksTsh#svw!gE");
            System.out.println("    • Creo una categoria valida e uso la password giusta, non mi aspetto nessuna eccezione.");
            board1.createCategory("my_photos", "A35sksTsh#svw!gE");
            System.out.println("    • Creo una categoria valida e uso la password giusta, non mi aspetto nessuna eccezione.");
            board1.createCategory("java", "A35sksTsh#svw!gE");
            System.out.println("    • Creo una categoria valida e uso la password giusta, non mi aspetto nessuna eccezione.");
            board1.createCategory("foo", "A35sksTsh#svw!gE");

            try {
                System.out.println("    • Creo una categoria con caratteri non ammessi e uso la password giusta, mi aspetto InvalidEntryException.");
                board1.createCategory("#categoria_Non_valida#", "A35sksTsh#svw!gE");
            } catch (InvalidEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Creo una categoria valida ma uso una password sbagliata, mi aspetto WrongPasswordException.");
                board1.createCategory("todo", "password");
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Creo una categoria valida ma già presente e uso la password giusta, mi aspetto DuplicateEntryException.");
                board1.createCategory("foo", "A35sksTsh#svw!gE");
            } catch (DuplicateEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test aggiunta amici ad una categoria

            System.out.println("> Aggiungo(autorizzo) amici ad una categoria ... ");

            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("foto belle", "A35sksTsh#svw!gE", "giuseppe-212");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("foto belle", "A35sksTsh#svw!gE", "lino99");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("foto belle", "A35sksTsh#svw!gE", "alexanderes13");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("foto belle", "A35sksTsh#svw!gE", "silent77");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("my_photos", "A35sksTsh#svw!gE", "giuseppe-212");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("my_photos", "A35sksTsh#svw!gE", "alexanderes13");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("my_photos", "A35sksTsh#svw!gE", "lino99");
            System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.addFriend("my_photos", "A35sksTsh#svw!gE", "calo_98");

            try {
                System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria esistente, usando una password sbagliata. Mi aspetto WrongPasswordException. ");
                board1.addFriend("foto belle", "password", "pippo");
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Aggiungo un nuovo amico con username non valido, ad una categoria esistente, usando la password giusta. Mi aspetto InvalidEntryException. ");
                board1.addFriend("foto belle", "A35sksTsh#svw!gE", "&uSer%!#");
            } catch (InvalidEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Aggiungo un nuovo amico con username valido, ad una categoria non esistente, usando la password giusta. Mi aspetto CategoryNotFoundException. ");
                board1.addFriend("categoria inesistente", "A35sksTsh#svw!gE", "gian_marco7");
            } catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Aggiungo un amico già esistente, ad una categoria esistente, usando la password giusta. Mi aspetto DuplicateEntryException. ");
                board1.addFriend("foto belle", "A35sksTsh#svw!gE", "giuseppe-212");
            } catch (DuplicateEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test rimozione amici da una categoria

            System.out.println("> Rimuovo(nego) amici da una categoria ...");

            try {
                System.out.println("    • Rimuovo un amico da una categoria esistente e che è autorizzato a vedere, usando una password sbagliata. Mi aspetto WrongPasswordException.");
                board1.removeFriend("my_photos", "password", "lino99");
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Rimuovo un amico da una categoria esistente ma che non è autorizzato a vedere, usando la password giusta. Mi aspetto FriendNotFoundException.");
                board1.removeFriend("my_photos", "A35sksTsh#svw!gE", "silent77");
            } catch (FriendNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Rimuovo un amico da una categoria non esistente, usando la password giusta. Mi aspetto CategoryNotFoundException.");
                board1.removeFriend("categoria inesistente", "A35sksTsh#svw!gE", "alexanderes13");
            } catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Rimuovo un amico da una categoria esistente e che è autorizzato a vedere, usando la password giusta. Non mi aspetto nessuna eccezione.");
            board1.removeFriend("my_photos", "A35sksTsh#svw!gE", "calo_98");

            System.out.println();
            System.out.println();
            System.out.println();

            // =========================================================================================================
            // testi di prova

            Image leaningTower = new Image("fram3", "leaning", "jpeg", "*****************************************/*************************************\n" +
                    "//*************************************///*************************************\n" +
                    "///**//**********************************/*************************************\n" +
                    "///////*/////****************************/*************************************\n" +
                    "////////////////**/********************,,,*,*,*/***************************/***\n" +
                    "///////////////////********/********,,,,,,,*//((((*,**/*************///////////\n" +
                    "/////////////////////////////**/***,,,,,(,,#*/%#&%(#(//*///////////////////////\n" +
                    "//////////////////////////////////*,,,,*( ,%###%(*/%(//////////////////////////\n" +
                    "//////////////////////////////////*,,..*#/*#*.,#(*(#///////////////////////////\n" +
                    "//////////////////////////////////*,,. /(*/#,*,#%@%#///////////////////////////\n" +
                    "////////////////////////////////**,..,,*///(##(%#/(%///////////////////////////\n" +
                    "////////////////////////////////,,,,,,/**%/%##&%%((#/*/////////////////////////\n" +
                    "///////////////////////////////*,,,.*./,,/,/.*/*#*%(&%(////////////////////////\n" +
                    "///////////////////////////////*..,.,.#*/(*# /*%/%(//#(////////////////////////\n" +
                    "///////////////////////////////*,,,.,,((#%(#.(/%@&@%(((////////////////////////\n" +
                    "///////////////////////////////*,,*******//(#%&&@&@&%&/////////////////////////\n" +
                    "///////////////////////////////,,,,,,**,#.(,/%*%%&&%%&/////////////////////////\n" +
                    "///////////////////////////////,,,.,,/**(*(/#(((/(*#%&/////////////////////////\n" +
                    "(((((((//(/////////////////////,,,,/,(*(%(%&%%@#@@%(#%/////////////////////////\n" +
                    "(((((((((((((/(////////////////,,**/*/*//(#%%&&&@@%%&#//////////////////(//////\n" +
                    "((((((((((((((((((((((((/(((//,,,,**,(,#/*%/&&%&%##%&((((((((((((((((((((((/(((\n" +
                    "(((((((((((((((((((((((((((((/,,..,,,/,(,*/*(,/*/*((&((((((((((((((((((((((((((\n" +
                    "(((((((((((((((((((((((((((((/*,,,/,*/*%//&%%@%@%&(/(((((((((((((((((((((((((((\n" +
                    "(((((((((((((((((((((((((((((/,,,.*.(*/#((%%&&%@&&%%%((((((((((((((((((((((((((\n" +
                    "(((((((((((((((((((((((((((((,,,,,,,**/((%#%%%##&%&#%((((((((((((((((((((((((((\n" +
                    "(((((((((((((((((((((((((((((,,..,,././,,#,(*,%/&%@&#((((((((((((((((((((((((((\n" +
                    "(((((((((((((((((((((((((((((,,.,*,,(*#**(%#%&##%/##%((((((((((((((((((((((((((\n" +
                    "(((((((((((((((((((((((((((((,,,,/**(#%(/@@&@&@@@%%%%((((((((((((((((((((((((((\n" +
                    "((((((((((((((((((((((((((((/,,****///(##&@@@&@&@&%&#((((((((((((((((((((((((((\n" +
                    "#########(((((((((((((((((((*,,,.*.(,*(*#((&#&@@&%%%#((((((((((((((((((((((((((\n" +
                    "#######################(((#(,..,,,,/,/**(*//*#*#*##&#(#((((((((((#(#(((##(###(#\n" +
                    "###########################(,.,,,**#*##/&@#&&%@&%##############################\n" +
                    "###########################(.,,,**/(#&&#@@%@&&@@%&&&###########################\n" +
                    "###########################*,,,,,,,****//(#%&@&@%%&&###########################\n" +
                    "###########################*,.,.,.(,*(,#*,%*#%#&%&@%###########################\n" +
                    "###########################*..,,,,(**/*#*(((#/##(/%&###########################\n" +
                    "##########((###############,,,,*,*(/#%(&@#*@%@%@%#%############################\n" +
                    "#####################((///*,,**/*///%%(@@(,@&@%&&#&,.,*(#############(,(#######\n" +
                    "((##########((//*******/**,,,,,,,,,,****//((#%&&&&@. ....////((###/.*.   ./((#(\n" +
                    ".......,***/****.,*****,*,..,,,.,%#/,*#@&(/%@@#&@&&,.  . ... .//*,        . .,,\n" +
                    "(#((#*///,***(/* ..*,**   .,,,,,,(##/*#&&%%%@&#%&%&     .... .****          . .\n" +
                    "(#/##/(***,./%.    . .    .,.,,,,%#%#,%@@@&(@@@(@&%      ...  /%%(. ,****,,,,,,\n" +
                    "#######**,,,,/       .   ..,.,,,,##%%,%&&&##@@@%@@&.. ...     */%(, **,..,,,,**\n" +
                    "##,((,(((#(,,*           ..,.,,,*((#(,&@@@(%@@@%&@@%/,,**,,   *%,*   .       ..\n" +
                    "%#,%(*(*&(/,,,           .,,.  .//*,,*((@@/&@&*/&&(*, .,*.,.,,,. ,   ,((//*(%##\n" +
                    "*/*,,,*.,*.,((,,/*,,  ..*.,... ...,,...,((,%%#..,( .,...... .  ...   .,* ,.,,,*\n" +
                    "//,****,,,,,,,,,.,,,,,,,,,,,,,........,,.,,,,,,,,,.,,,,...,,, .,  ., ,,,..,.,,,\n" +
                    "...............................................................................\n" +
                    ",..............................................................................\n" +
                    "...............................................................................\n" +
                    "...............................................................................\n" +
                    "...............................................................................\n" +
                    ".......................              .......                    ....    ...... ", 300, 224);

            Image image1 = new Image("fram3", "java", "logo", "                      (\n" +
                    "                        )     (\n" +
                    "                 ___...(-------)-....___\n" +
                    "             .-\"\"       )    (          \"\"-.\n" +
                    "       .-'``'|-._             )         _.-|\n" +
                    "      /  .--.|   `\"\"---...........---\"\"`   |\n" +
                    "     /  /    |                             |\n" +
                    "     |  |    |                             |\n" +
                    "      \\  \\   |                             |\n" +
                    "       `\\ `\\ |                             |\n" +
                    "         `\\ `|                             |\n" +
                    "         _/ /\\                             /\n" +
                    "        (__/  \\                           /\n" +
                    "     _..---\"\"` \\                         /`\"\"---.._\n" +
                    "  .-'           \\                       /          '-.\n" +
                    " :               `-.__             __.-'              :\n" +
                    " :                  ) \"\"---...---\"\" (                 :\n" +
                    "  '._               `\"--...___...--\"`              _.'\n" +
                    "    \\\"\"--..__                              __..--\"\"/\n" +
                    "     '._     \"\"\"----.....______.....----\"\"\"     _.'\n" +
                    "        `\"\"--..,,_____            _____,,..--\"\"`\n" +
                    "                      `\"\"\"----\"\"\"`", 100, 100);
            Image image2 = new Image("giuseppe-212", "decaedro", "tiff", "       _----------_,\n" +
                    "    ,\"__         _-:, \n" +
                            "   /    \"\"--_--\"\"...:\\\n" +
                            "  /         |.........\\\n" +
                            " /          |..........\\\n" +
                            "/,         _'_........./:\n" +
                            "! -,    _-\"   \"-_... ,;;:\n" +
                            "\\   -_-\"         \"-_/;;;;\n" +
                            " \\   \\             /;;;;'\n" +
                            "  \\   \\           /;;;;\n" +
                            "   '.  \\         /;;;'\n" +
                            "     \"-_\\_______/;;'", 60, 55);
            Image image3 = new Image("web", "mountain", "gif", "                      _\n" +
                    "                     /#\\\n" +
                    "                    /###\\     /\\\n" +
                    "                   /  ###\\   /##\\  /\\\n" +
                    "                  /      #\\ /####\\/##\\\n" +
                    "                 /  /      /   # /  ##\\             _       /\\\n" +
                    "               // //  /\\  /    _/  /  #\\ _         /#\\    _/##\\    /\\\n" +
                    "              // /   /  \\     /   /    #\\ \\      _/###\\_ /   ##\\__/ _\\\n" +
                    "             /  \\   / .. \\   / /   _   { \\ \\   _/       / //    /    \\\\\n" +
                    "     /\\     /    /\\  ...  \\_/   / / \\   } \\ | /  /\\  \\ /  _    /  /    \\ /\\\n" +
                    "  _ /  \\  /// / .\\  ..%:.  /... /\\ . \\ {:  \\\\   /. \\     / \\  /   ___   /  \\\n" +
                    " /.\\ .\\.\\// \\/... \\.::::..... _/..\\ ..\\:|:. .  / .. \\\\  /.. \\    /...\\ /  \\ \\\n" +
                    "/...\\.../..:.\\. ..:::::::..:..... . ...\\{:... / %... \\\\/..%. \\  /./:..\\__   \\\n" +
                    " .:..\\:..:::....:::;;;;;;::::::::.:::::.\\}.....::%.:. \\ .:::. \\/.%:::.:..\\\n" +
                    "::::...:::;;:::::;;;;;;;;;;;;;;:::::;;::{:::::::;;;:..  .:;:... ::;;::::..\n" +
                    ";;;;:::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;];;;;;;;;;;::::::;;;;:.::;;;;;;;;:..\n" +
                    ";;;;;;;;;;;;;;ii;;;;;;;;;;;;;;;;;;;;;;;;[;;;;;;;;;;;;;;;;;;;;;;:;;;;;;;;;;;;;\n" +
                    ";;;;;;;;;;;;;;;;;;;iiiiiiii;;;;;;;;;;;;;;};;ii;;iiii;;;;i;;;;;;;;;;;;;;;ii;;;\n" +
                    "iiii;;;iiiiiiiiiiIIIIIIIIIIIiiiiiIiiiiii{iiIIiiiiiiiiiiiiiiii;;;;;iiiilliiiii\n" +
                    "IIIiiIIllllllIIlllIIIIlllIIIlIiiIIIIIIIIIIIIlIIIIIllIIIIIIIIiiiiiiiillIIIllII\n" +
                    "IIIiiilIIIIIIIllTIIIIllIIlIlIIITTTTlIlIlIIIlIITTTTTTTIIIIlIIllIlIlllIIIIIIITT\n" +
                    "IIIIilIIIIITTTTTTTIIIIIIIIIIIIITTTTTIIIIIIIIITTTTTTTTTTIIIIIIIIIlIIIIIIIITTTT\n" +
                    "IIIIIIIIITTTTTTTTTTTTTIIIIIIIITTTTTTTTIIIIIITTTTTTTTTTTTTTIIIIIIIIIIIIIITTTTT", 75, 75);
            Image image4 = new Image("Da Vinci", "Monna Lisa", "png", "\n" +
                    "                                  _______\n" +
                            "                           _,,ad8888888888bba,_\n" +
                            "                        ,ad88888I888888888888888ba,\n" +
                            "                      ,88888888I88888888888888888888a,\n" +
                            "                    ,d888888888I8888888888888888888888b,\n" +
                            "                   d88888PP\"\"\"\" \"\"YY88888888888888888888b,\n" +
                            "                 ,d88\"'__,,--------,,,,.;ZZZY8888888888888,\n" +
                            "                ,8IIl'\"                ;;l\"ZZZIII8888888888,\n" +
                            "               ,I88l;'                  ;lZZZZZ888III8888888,\n" +
                            "             ,II88Zl;.                  ;llZZZZZ888888I888888,\n" +
                            "            ,II888Zl;.                .;;;;;lllZZZ888888I8888b\n" +
                            "           ,II8888Z;;                 `;;;;;''llZZ8888888I8888,\n" +
                            "           II88888Z;'                        .;lZZZ8888888I888b\n" +
                            "           II88888Z; _,aaa,      .,aaaaa,__.l;llZZZ88888888I888\n" +
                            "           II88888IZZZZZZZZZ,  .ZZZZZZZZZZZZZZ;llZZ88888888I888,\n" +
                            "           II88888IZZ<'(@@>Z|  |ZZZ<'(@@>ZZZZ;;llZZ888888888I88I\n" +
                            "          ,II88888;   `\"\"\" ;|  |ZZ; `\"\"\"     ;;llZ8888888888I888\n" +
                            "          II888888l            `;;          .;llZZ8888888888I888,\n" +
                            "         ,II888888Z;           ;;;        .;;llZZZ8888888888I888I\n" +
                            "         III888888Zl;    ..,   `;;       ,;;lllZZZ88888888888I888\n" +
                            "         II88888888Z;;...;(_    _)      ,;;;llZZZZ88888888888I888,\n" +
                            "         II88888888Zl;;;;;' `--'Z;.   .,;;;;llZZZZ88888888888I888b\n" +
                            "         ]I888888888Z;;;;'   \";llllll;..;;;lllZZZZ88888888888I8888,\n" +
                            "         II888888888Zl.;;\"Y88bd888P\";;,..;lllZZZZZ88888888888I8888I\n" +
                            "         II8888888888Zl;.; `\"PPP\";;;,..;lllZZZZZZZ88888888888I88888\n" +
                            "         II888888888888Zl;;. `;;;l;;;;lllZZZZZZZZW88888888888I88888\n" +
                            "         `II8888888888888Zl;.    ,;;lllZZZZZZZZWMZ88888888888I88888\n" +
                            "          II8888888888888888ZbaalllZZZZZZZZZWWMZZZ8888888888I888888,\n" +
                            "          `II88888888888888888b\"WWZZZZZWWWMMZZZZZZI888888888I888888b\n" +
                            "           `II88888888888888888;ZZMMMMMMZZZZZZZZllI888888888I8888888\n" +
                            "            `II8888888888888888 `;lZZZZZZZZZZZlllll888888888I8888888,\n" +
                            "             II8888888888888888, `;lllZZZZllllll;;.Y88888888I8888888b,\n" +
                            "            ,II8888888888888888b   .;;lllllll;;;.;..88888888I88888888b,\n" +
                            "            II888888888888888PZI;.  .`;;;.;;;..; ...88888888I8888888888,\n" +
                            "            II888888888888PZ;;';;.   ;. .;.  .;. .. Y8888888I88888888888b,\n" +
                            "           ,II888888888PZ;;'                        `8888888I8888888888888b,\n" +
                            "           II888888888'                              888888I8888888888888888b\n" +
                            "          ,II888888888                              ,888888I88888888888888888\n" +
                            "         ,d88888888888                              d888888I8888888888ZZZZZZZ\n" +
                            "      ,ad888888888888I                              8888888I8888ZZZZZZZZZZZZZ\n" +
                            "    ,d888888888888888'                              888888IZZZZZZZZZZZZZZZZZZ\n" +
                            "  ,d888888888888P'8P'                               Y888ZZZZZZZZZZZZZZZZZZZZZ\n" +
                            " ,8888888888888,  \"                                 ,ZZZZZZZZZZZZZZZZZZZZZZZZ\n" +
                            "d888888888888888,                                ,ZZZZZZZZZZZZZZZZZZZZZZZZZZZ\n" +
                            "888888888888888888a,      _                    ,ZZZZZZZZZZZZZZZZZZZZ888888888\n" +
                            "888888888888888888888ba,_d'                  ,ZZZZZZZZZZZZZZZZZ88888888888888\n" +
                            "8888888888888888888888888888bbbaaa,,,______,ZZZZZZZZZZZZZZZ888888888888888888\n" +
                            "88888888888888888888888888888888888888888ZZZZZZZZZZZZZZZ888888888888888888888\n" +
                            "8888888888888888888888888888888888888888ZZZZZZZZZZZZZZ88888888888888888888888\n" +
                            "888888888888888888888888888888888888888ZZZZZZZZZZZZZZ888888888888888888888888\n" +
                            "8888888888888888888888888888888888888ZZZZZZZZZZZZZZ88888888888888888888888888\n" +
                            "88888888888888888888888888888888888ZZZZZZZZZZZZZZ8888888888888888888888888888\n" +
                            "8888888888888888888888888888888888ZZZZZZZZZZZZZZ88888888888888888 Normand  88\n" +
                            "88888888888888888888888888888888ZZZZZZZZZZZZZZ8888888888888888888 Veilleux 88\n" +
                            "8888888888888888888888888888888ZZZZZZZZZZZZZZ88888888888888888888888888888888", 130, 92);
            Image image5 = new Image("Linus Torvarlds", "linuxPenguin", "tiff", "           .-\"\"\"-.\n" +
                    "           '       \\\n" +
                    "          |,.  ,-.  |\n" +
                    "          |()L( ()| |\n" +
                    "          |,'  `\".| |\n" +
                    "          |.___.',| `\n" +
                    "         .j `--\"' `  `.\n" +
                    "        / '        '   \\\n" +
                    "       / /          `   `.\n" +
                    "      / /            `    .\n" +
                    "     / /              l   |\n" +
                    "    . ,               |   |\n" +
                    "    ,\"`.             .|   |\n" +
                    " _.'   ``.          | `..-'l\n" +
                    "|       `.`,        |      `.\n" +
                    "|         `.    __.j         )\n" +
                    "|__        |--\"\"___|      ,-'\n" +
                    "   `\"--...,+\"\"\"\"   `._,.-' ", 75, 55);
            Image image6 = new Image("fram3", "emptyImage", "jpg", "", 0, 0);
            Image image7 = new Image("Penrose", "triangle", "txt", "\n" +
                    "           ____\n" +
                    "          /\\   \\        \n" +
                    "         /  \\   \\\n" +
                    "        /    \\   \\\n" +
                    "       /      \\   \\\n" +
                    "      /   /\\   \\   \\\n" +
                    "     /   /  \\   \\   \\\n" +
                    "    /   /    \\   \\   \\\n" +
                    "   /   /    / \\   \\   \\\n" +
                    "  /   /    /   \\   \\   \\\n" +
                    " /   /    /---------'   \\\n" +
                    "/   /    /_______________\\\n" +
                    "\\  /                     /\n" +
                    " \\/_____________________/   ", 60, 60);
            Image image8 = new Image("fram3", "prova", "gif", "Questo immagine è solo una prova", 1, 1);
            Image image9 = new Image("fram3", "asjygsjdhg", "gif", "image", 1, 1);



            // =========================================================================================================
            // test inserimento testi nella board
            System.out.println("> Inserisco dati nella board ...");

            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", leaningTower, "my_photos");
            Thread.sleep(1000); // inserisco un ritardo di un secondo per far vedere la differenza tra dati pubblicati in momenti diversi
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", image1, "java");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", image2, "my_photos");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", image3, "foto belle");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", image4, "foto belle");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", image5, "my_photos");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", image7, "foto belle");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", image8, "foo");
            Thread.sleep(1000);
            System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.put("A35sksTsh#svw!gE", image9, "foo");
            Thread.sleep(1000);

            try {
                System.out.println("    • Inserisco un nuovo dato in una categoria esistente, usando una password sbagliata. Mi aspetto WrongPasswordException. ");
                board1.put("password", image1, "programs output");
                Thread.sleep(1000);
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Inserisco un nuovo dato in una categoria non esistente, usando la password giusta. Mi aspetto CategoryNotFoundException. ");
                board1.put("A35sksTsh#svw!gE", image6, "categoria inesistente");
                Thread.sleep(1000);
            } catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Inserisco un dato già esistente in una categoria esistente, usando la password giusta. Mi aspetto DuplicateEntryException. ");
                board1.put("A35sksTsh#svw!gE", image7, "foto belle");
            } catch (DuplicateEntryException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test getter dati dalla board

            System.out.println("> Recupero dati dalla board ...");

            Image getting = null;

            try {
                System.out.println("    • Recupero un dato esistente, usando una password sbagliata. Mi aspetto WrongPasswordException.");
                getting = board1.get("password", leaningTower);
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Recupero un dato non esistente, usando la password giusta. Mi aspetto DataNotFoundException.");
                getting = board1.get("A35sksTsh#svw!gE", image6);
            } catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Recupero un dato esistente, usando la password giusta. Non mi aspetto nessuna eccezione.");
            getting = board1.get("A35sksTsh#svw!gE", leaningTower);
            System.out.println("       • Stampo il dato recuperato:");
            System.out.println("-".repeat(SEPARATOR_LENGTH));
            getting.display(); // stampo il dato usando il metodo overrided da Data
            System.out.println("-".repeat(SEPARATOR_LENGTH));
            //System.out.println("hashcode del copyout: "+ getting.hashCode()); //per verificare che si ha effettivamente il copyout

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test remove dati dalla board

            System.out.println("> Rimuovo dati dalla board ...");

            Image removed = null;

            try {
                System.out.println("    • Rimuovo un dato esistente dalla board, usando una password sbagliata. Mi aspetto WrongPasswordException.");
                removed = board1.remove("password", image2);
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Rimuovo un dato non esistente dalla board, usando la password giusta. Mi aspetto DataNotFoundException.");
                removed = board1.remove("A35sksTsh#svw!gE", image6);
            } catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Rimuovo un dato esistente dalla board, usando la password giusta. Non mi aspetto nessuna eccezione.");
            removed = board1.remove("A35sksTsh#svw!gE", image2);
            System.out.println("       • Stampo il dato rimosso:");
            System.out.println("-".repeat(SEPARATOR_LENGTH));
            removed.display();
            System.out.println("-".repeat(SEPARATOR_LENGTH));

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test rimozione categories

            System.out.println("> Rimuovo categorie nella board ...");

            try {
                System.out.println("    • Rimuovo una categoria esistente ma uso una password sbagliata. Mi aspetto WrongPasswordException. ");
                board1.removeCategory("foo", "password");
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Rimuovo una categoria non esistente e uso la password giusta. Mi aspetto CategoryNotFoundException.");
                board1.removeCategory("categoria inesistente", "A35sksTsh#svw!gE");
            } catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            //board1.displayBoard();
            System.out.println("    • Rimuovo una categoria esistente e uso la password giusta. Non mi aspetto nessuna eccezione. ");
            board1.removeCategory("foo", "A35sksTsh#svw!gE");
            //board1.displayBoard(); // decommentando si può vedere che se la categoria rimossa conteneva dei dati allora
            // anche questi sono stati rimossi
            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test inserimento like ad un dato da parte di un amico

            System.out.println("> Inserisco like ai dati ...");

            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("giuseppe-212", leaningTower);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("lino99", leaningTower);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("alexanderes13", leaningTower);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("lino99", image4);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("alexanderes13", image4);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("silent77", image4);
            System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.insertLike("lino99", image3);

            try {
                System.out.println("    • Inserisco un nuovo like da parte di un non amico esistente, ad un dato esistente. Mi aspetto FriendNotFoundException.");
                board1.insertLike("notexistingfriend", leaningTower);
            } catch (FriendNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Inserisco un like già inserito da parte di un amico esistente, ad un dato esistente. Mi aspetto AlreadyLikedException.");
                board1.insertLike("giuseppe-212", leaningTower);
            } catch (AlreadyLikedException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Inserisco un nuovo like da parte di un amico esistente, ad un non dato esistente. Mi aspetto DataNotFoundException.");
                board1.insertLike("lino99", image6);
            } catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test richiesta lista dei dati di una categoria

            System.out.println("> Recupero le liste dei dati di una categoria ...");

            List<Image> dataCategory = null;
            try {
                System.out.println("    • Recupero la lista dei dati di una categoria esistente, usando una password sbagliata. Mi aspetto WrongPasswordException.");
                dataCategory = board1.getDataCategory("password", "foto belle");
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Recupero la lista dei dati di una categoria non esistente, usando la password giusta. Mi aspetto CategoryNotFoundException.");
                dataCategory = board1.getDataCategory("A35sksTsh#svw!gE", "foto belle inesistenti");
            } catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Recupero la lista dei dati di una categoria esistente, usando la password giusta. Non mi aspetto nessuna eccezione.");
            dataCategory = board1.getDataCategory("A35sksTsh#svw!gE", "foto belle");
            System.out.println("       • Stampo la lista:");
            System.out.println(dataCategory);

            System.out.println();
            System.out.println();
            System.out.println();


            // ---------------------------------------------------------------------------------------------------------
            // test iteratore sui dati della board ordinati per like

            System.out.println("> Richiedo iteratori sui dati della bacheca, ordinati per totale di like ...");

            Iterator<Image> likeIterator = null;

            try {
                System.out.println("    • Richiedo un iteratore, usando una password sbagliata. Mi aspetto WrongPasswordException. ");
                likeIterator = board1.getIterator("password");
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Dimostro che l'iteratore è senza operazione di remove. Mi aspetto UnsupportedOperationException.");
                likeIterator = board1.getIterator("A35sksTsh#svw!gE");
                while (likeIterator.hasNext()) {
                    likeIterator.remove();
                }
            } catch (UnsupportedOperationException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Richiedo un iteratore, usando la password giusta. Non mi aspetto nessuna eccezione. ");
            likeIterator = board1.getIterator("A35sksTsh#svw!gE");

            System.out.println("       • Itero e stampo i dati ...");
            while (likeIterator.hasNext()) {
                System.out.println("-".repeat(SEPARATOR_LENGTH));
                likeIterator.next().display();
            }
            System.out.println("-".repeat(SEPARATOR_LENGTH));

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test iteratore dei dati condivisi con un amico

            System.out.println("> Richiedo iteratori sui dati della bacheca, condivisi con un amico");

            Iterator<Image> friendIterator = null;

            try {
                System.out.println("    • Richiedo un iteratore sui dati di un amico non esistente. Mi aspetto FriendNotFoundException. ");
                friendIterator = board1.getFriendIterator("alex");
            } catch (FriendNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Dimostro che l'iteratore è senza operazione di remove. Mi aspetto UnsupportedOperationException.");
                friendIterator = board1.getFriendIterator("silent77");
                while (friendIterator.hasNext()) {
                    friendIterator.remove();
                }
            } catch (UnsupportedOperationException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            System.out.println("    • Richiedo un iteratore sui dati di un amico esistente. Non mi aspetto nessuna eccezione. ");
            friendIterator = board1.getFriendIterator("silent77");

            System.out.println("       • Itero e stampo i dati ...");
            while (friendIterator.hasNext()) {
                System.out.println("-".repeat(SEPARATOR_LENGTH));
                friendIterator.next().display();
            }
            System.out.println("-".repeat(SEPARATOR_LENGTH));

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test getter lista di categorie che un amico è autorizzato a vedere

            System.out.println("> Richiedo la lista di categorie che un amico è autorizzato a visualizzare ...");

            List<String> friendCategories = null;

            try {
                System.out.println("    • Richiedo la lista di categorie di un amico non esistente. Mi aspetto FriendNotFoundException.");
                friendCategories = board1.getFriendCategories("alex");
            } catch (FriendNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Richiedo la lista di categorie di un amico esistente. Non mi aspetto nessuna eccezione.");
            friendCategories = board1.getFriendCategories("lino99");
            System.out.println("       • Stampo la lista:\n");
            System.out.println("         " + friendCategories);

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test metodo che ci dice se un amico è autorizzato a visualizzare una categoria o no

            System.out.println("> Chiedo se un amico è autorizzato a visualizzare una categoria ...");

            try {
                System.out.println("    • Chiedo se l'amico è autorizzato a visualizzare una categoria non esistente. Mi aspetto CategoryNotFoundException.");
                System.out.println(board1.friendAllowed("categoria inesistente", "lino99"));
            } catch (CategoryNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Chiedo se l'amico è autorizzato a visualizzare una categoria esistente. Non mi aspetto nessuna eccezione.\n");
            System.out.println("      " + board1.friendAllowed("foto belle", "lino99"));

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test rimozione di un like ad un dato

            System.out.println("> Rimuovo like ai dati ...");

            try {
                System.out.println("    • Rimuovo un like non inserito, ad un dato esistente. Mi aspetto LikeNotFoundException.");
                board1.removeLike("giuseppe-212", image4);
            } catch (LikeNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Rimuovo un like inserito, ad un dato non esistente. Mi aspetto DataNotFoundException.");
                board1.removeLike("giuseppe-212", image6);
            } catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Rimuovo un like inserito, da parte di un amico esistente, ad un dato esistente. Non mi aspetto nessuna eccezione.");
            board1.removeLike("lino99", image4);

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test richiesta insieme dei likes di un dato

            System.out.println("> Richiedo insieme dei likes dei dati ...");

            LinkedHashSet<String> likes = null;

            try {
                System.out.println("    • Richiedo insieme dei likes di un dato non esistente. Mi aspetto DataNotFoundException.");
                likes = board1.getLikes(image6);
            } catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            System.out.println("    • Richiedo insieme dei likes di un dato esistente. Non mi aspetto nessuna eccezione.");
            likes = board1.getLikes(leaningTower);
            System.out.println("       • Stampo l'insieme:\n");
            System.out.println("         " + likes);

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test del repost di un dato

            System.out.println("> Faccio il repost dei dati ...\n");

            try {
                System.out.println("    • Faccio il repost di un dato esistente, usando una password sbagliata. Mi aspetto WrongPasswordException.");
                board1.repost("password", image4);
            } catch (WrongPasswordException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }
            try {
                System.out.println("    • Faccio il repost di un dato non esistente, usando la password giusta. Mi aspetto DataNotFoundException.");
                board1.repost("A35sksTsh#svw!gE", image6);
            } catch (DataNotFoundException e) {
                System.out.println(ANSI_YELLOW + "    " + e + ANSI_RESET);
            }

            //board1.displayBoard();
            try {
                System.out.println("    • Faccio il repost di un dato esistente, usando la password giusta. Non mi aspetto nessuna eccezione.");
                board1.repost("A35sksTsh#svw!gE", image1);
            } catch (DataNotFoundException e) {
                e.printStackTrace();
            }
            //board1.displayBoard(); //decommentando si può vedere la differenza delle due board stampate prima e dopo il repost

            System.out.println();
            System.out.println();
            System.out.println();

            // ---------------------------------------------------------------------------------------------------------
            // test metodi display e toString

            // stampa board
            System.out.println("> Uso il metodo display della board ...\n");
            board1.displayBoard();
            System.out.println();

            System.out.println("> Uso il metodo display della tabella delle categorie ...\n");
            // stampa categorie definite
            board1.displayCategories();
            System.out.println();

            // test toString di DataBoard
            System.out.println("> Test toString dell'oggetto DataBoard ...\n");
            System.out.println(board1);
        } catch (Exception e) {
            System.out.println(ANSI_RED + e + ": this should never have happened");
        }
    }
}
