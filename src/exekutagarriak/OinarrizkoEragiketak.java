package exekutagarriak;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Album;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Artist;
import org.hibernate.ObjectNotFoundException;

public class OinarrizkoEragiketak {

    public static SessionFactory sf = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean irten = false;
        int aukera; //Guardaremos la opcion del usuario

        while (!irten) {
            System.out.println("\nCHINOOK DATUBASEA");
            System.out.println("-------------------");
            System.out.println("1. Datuak Gorde");
            System.out.println("2. Datuak Ezabatu");
            System.out.println("3. Datu Guztiak Ikusi");
            System.out.println("4. Datuak Ikusi Zatika");
            System.out.println("5. Datu Bakarra Ikusi");
            System.out.println("6. Datu Bat Aldatu");
            System.out.println("7. Irten");

            System.out.print("Sartu zenbaki bat aukeratzeko: ");
            aukera = readInt(sc);

            switch (aukera) {
                case 1:
                    System.out.print("Sartu artistaren id-a: ");
                    int idArtist = readInt(sc);
                    System.out.print("Sartu artistaren izena: ");
                    String iArtist = sc.next();
                    if (idArtist > 0)
                        datuaGorde(new Artist(idArtist, iArtist));
                    break;
                case 2:
                    System.out.print("Sartu ezabatu nahi duzun artistaren id-a: ");
                    int ezabatuArtist = readInt(sc);
                    if (ezabatuArtist > 0)
                        datuaEzabatu(ezabatuArtist);
                    break;
                case 3:
                    datuakIkusi();
                    //datuakIkusiAlbum();
                    break;
                case 4:
                    System.out.print("Sartu zenbat artista ikusi nahi dituzu (10/50/100/200): ");
                    int zenbZatika = readInt(sc);
                    if (zenbZatika > 0)
                        datuakIkusiZatika(zenbZatika);
                    break;
                case 5:
                    System.out.print("Sartu id bat ikusteko: ");
                    int ida = readInt(sc);
                    
                    if (ida > 0)
                        datuBakarIkusi(ida);
                    break;
                case 6:
                    System.out.print("Sartu id bat aldatzeko:");
                    int idAldatu = readInt(sc);
                    System.out.print("Sartu izen berria:");
                    String izenBerria = sc.next();
                    
                    if (idAldatu > 0)
                        datuBatAldatu(idAldatu, izenBerria);
                    break;
                case 7:
                    irten = true;
                    break;
                default:
                    System.out.println("Zenbaki bat sartu behar duzu 1-7");
            }

        }
    }

    private static int readInt(Scanner sc) {
        int value = -1;
        try {
            value = sc.nextInt();
        } catch (Exception e) {
            sc.nextLine(); // Limpio la linea que estaba mal (Limpiar el buffer)
            System.out.println("Zenbaki bat jarri behar duzu eta positiboa izan behar da.");
        }
        return value;
    }
    
    public static void datuaGorde(Artist a) {        
            try (Session saioa = sf.openSession()) {
                saioa.beginTransaction();
                saioa.save(a);
                saioa.getTransaction().commit();
                saioa.close();
                System.out.println("Artista gorde da, datubasean");
            }catch(Exception e){
                System.out.println("Errore bat gertatu da");
            }
    }

    public static void datuakIkusi() {

        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Artist").list(); // HQL deitzen dan lengoaia idatziko dugu Querya
        System.out.println("Artista");
        System.out.println("--------------------");
        for (Artist a : (List<Artist>) result) {
            System.out.printf("ID| %d, Izena:%s \n", a.getArtistid(), a.getName());
        }
        saioa.getTransaction().commit();
        saioa.close();
    }
    
    public static void datuakIkusiAlbum() {

        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Album").list(); // HQL deitzen dan lengoaia idatziko dugu Querya
        System.out.println("Album");
        System.out.println("--------------------");
        for (Album a : (List<Album>) result) {
            System.out.printf("ID| %d, Album:%s, ArtitsID| %d \n", a.getAlbumId(), a.getTitle(), a.getArtistId());
        }
        saioa.getTransaction().commit();
        saioa.close();
    }
    
    public static void datuakIkusiZatika(int zenbat){
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Artist").setMaxResults(zenbat).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
        System.out.println("Artista");
        System.out.println("--------------------");
        for (Artist a : (List<Artist>) result) {
            System.out.printf("ID| %d, Izena:%s \n", a.getArtistid(), a.getName());
        }
        saioa.getTransaction().commit();
        saioa.close();
    }

    public static void datuBakarIkusi(int id){
     Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Artist WHERE ArtistId = " + id).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
        System.out.println("Artista");
        System.out.println("--------------------");
        for (Artist a : (List<Artist>) result) {
            System.out.printf("ID| %d, Izena:%s \n", a.getArtistid(), a.getName());
        }
        saioa.getTransaction().commit();
        saioa.close();
    }
    
    public static void datuaEzabatu(int idArtist) {

        Session saioa;
        Artist art;
        Transaction tx;
        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            art = (Artist) saioa.load(Artist.class, idArtist);
            //ik = (Ikaslea)saioa.load("Ikaslea", idIkaslea); //horrela ere bai, ezta?
            //get metodoa antzekoa da baina ez du eszepziorik eragiten erregistroa existitzen ez bada.
            saioa.delete(art);
            tx.commit();
            
            System.out.println(art.getName() + " Artista ezabatu da datubasetik");
            saioa.close();
        } catch (ObjectNotFoundException onfe) {
            System.out.println("Artista hori ez dago");
        }
         
    }
    
    public static void datuBatAldatu(int idArtist, String izenBerria){
        Session saioa;
        Artist art;
        Transaction tx;
        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            art = (Artist) saioa.load(Artist.class, idArtist);
            art.setName(izenBerria);            
            tx.commit();
            
            System.out.println(art.getName() + " Artista hau aldatu da datubasetik. Horain: " + art + " da.");
            saioa.close();
        } catch (ObjectNotFoundException onfe) {
            System.out.println("Artista hori ez dago");
        }
    }
}
