package exekutagarriak;

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
            System.out.println("    1. Datuak Gorde");
            System.out.println("    2. Datuak Ezabatu");
            System.out.println("    3. Datu Guztiak Ikusi");
            System.out.println("    4. Datuak Ikusi Zatika");
            System.out.println("    5. Datu Bakarra Ikusi");
            System.out.println("    6. Datu Bat Aldatu");
            System.out.println("    7. Artista guztien albunak ikusi");
            System.out.println("    8. Irten");

            System.out.print("Sartu zenbaki bat aukeratzeko: ");
            aukera = readInt(sc);
            
            switch (aukera) {
                case 1:
                    System.out.print("Zein taulatan gorde nahi duzu datuak (album/artist)? ");
                    String aukeraTaula = sc.next();
                    if(aukeraTaula.equals("album")){
                        System.out.print("Sartu alburen id-a: ");
                        int idAlbum = readInt(sc);
                        System.out.print("Sartu alburen tituloa: ");
                        String albumTituloa = sc.next();
                        System.out.println("Badakizu zein da artistaren id-a? (bai/ez)");
                        String erantzuna = sc.next();
                        if(erantzuna.equals("bai")){
                            System.out.print("Sartu artistaren id-a: ");
                            int idArtista = readInt(sc);       
                            datuaGordeAlbum(new Album(idAlbum, albumTituloa, idArtista));
                            break;
                        }else{
                            System.out.println("Berri bat egingo dugu!!");
                            System.out.print("Sartu artistaren id-a: ");
                            int idArtist = readInt(sc);
                            System.out.print("Sartu artistaren izena: ");
                            String iArtist = sc.next();                            
                            if (idArtist > 0){
                                datuaGordeArtist(new Artist(idArtist, iArtist));
                                System.out.println("Artista hau sartuko da idatzi nahi duzun albunarekin");
                                datuaGordeAlbum(new Album(idAlbum, albumTituloa, idArtist));
                                break;
                            }                                
                        }
                    }else{                        
                        System.out.print("Sartu artistaren id-a: ");
                        int idArtist = readInt(sc);
                        System.out.print("Sartu artistaren izena: ");
                        String iArtist = sc.next();
                        if (idArtist > 0)
                            datuaGordeArtist(new Artist(idArtist, iArtist));
                        break;
                    }
                case 2:
                    System.out.print("Zein taularen datuak ikusi nahi dituzu (album/artist)? ");
                    String taulaEzabatu = sc.next();
                    String taulaEzabatuToLower = taulaEzabatu.toLowerCase();
                    switch (taulaEzabatuToLower) {
                        case "artist":
                            System.out.print("Sartu ezabatu nahi duzun artistaren id-a: ");
                            int ezabatuArtist = readInt(sc);
                            if (ezabatuArtist > 0){
                                datuaEzabatuArtist(ezabatuArtist);
                                break;
                            }
                        case "album":
                            System.out.print("Sartu ezabatu nahi duzun alburen id-a: ");
                            int ezabatuAlbum = readInt(sc);
                            if (ezabatuAlbum > 0){
                                datuaEzabatuAlbum(ezabatuAlbum);
                                break;
                            }                                
                        default:
                            System.err.println("Bakarrik ezabatu ahal da artista edo album taulatik.!!");
                            break;
                    }
                    break;  
                case 3:
                    System.out.print("Zein taularen datuak ikusi nahi dituzu (album/artist)? ");
                    String taula = sc.next();
                    datuakIkusi(taula);                    
                    break;
                case 4:
                    System.out.print("Zein taularen datuak ikusi nahi dituzu zatika(album/artist)? ");
                    String taulak = sc.next();
                    String taulakToLower = taulak.toLowerCase();
                    System.out.print("Sartu zenbat " + taulakToLower + " ikusi nahi dituzu (10/50/100/200): ");
                    int zenbZatika = readInt(sc);
                    if (zenbZatika > 0)
                        datuakIkusiZatika(zenbZatika, taulak);
                    break;
                case 5:
                    System.out.print("Zein taularen datuak ikusi nahi dituzu zatika(album/artist)? ");
                    String taulaBakar = sc.next();
                    String taulaBakarToLower = taulaBakar.toLowerCase();
                    System.out.print("Sartu id bat ikusteko: ");
                    int ida = readInt(sc);
                    if (ida > 0)
                        datuBakarIkusi(ida, taulaBakarToLower);
                    break;
                case 6:
                    System.out.print("Zein taularen datuak aldatu nahi dituzu (album/artist)? ");
                    String taulaAldatu = sc.next();
                    String taulaAldatuToLower = taulaAldatu.toLowerCase();
                    System.out.print("Sartu id bat aldatzeko:");
                    int idAldatu = readInt(sc);
                    System.out.print("Sartu izen berria:");
                    String izenBerria = sc.next();
                    
                    if (idAldatu > 0)
                        datuBatAldatu(taulaAldatuToLower, idAldatu, izenBerria);
                    break;
                case 7:
                    datuGuztiakIkusi();
                    break;
                case 8:
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
    
    public static void datuaGordeArtist(Artist a) {        
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
    
    public static void datuaGordeAlbum(Album alb){
        try (Session saioa = sf.openSession()) {
                saioa.beginTransaction();
                saioa.save(alb);
                saioa.getTransaction().commit();
                saioa.close();
                System.out.println("Album gorde da, datubasean");
            }catch(Exception e){
                System.out.println("Errore bat gertatu da");
            }
    }

    public static void datuakIkusi(String taula){
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        String taulaToLower = taula.toLowerCase();
        switch (taulaToLower) {
            case "artist":
                {
                    List result = saioa.createQuery("from Artist").list(); // HQL deitzen dan lengoaia idatziko dugu Querya
                    System.out.println("Artista");
                    System.out.println("--------------------");
                    for (Artist a : (List<Artist>) result) {
                        System.out.printf("ID| %d, Izena:%s \n", a.getArtistid(), a.getName());
                    }
                    break;
                }
            case "album":
                {
                    List result = saioa.createQuery("from Album").list(); // HQL deitzen dan lengoaia idatziko dugu Querya
                    System.out.println("Album");
                    System.out.println("--------------------");
                    for (Album a : (List<Album>) result) {
                        System.out.printf("ID| %d, Album:%s, ArtitsID| %d \n", a.getAlbumId(), a.getTitle(), a.getArtistId());        
                    }
                    break;
                }
            default:
                System.err.println("Sartu behar duzuna Album edo Artist da.!");
                break;
        }
        saioa.getTransaction().commit();
        saioa.close();
    }
    
    public static void datuakIkusiZatika(int zenbat, String taula){
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        switch (taula) {
            case "artist":
                {
                    List result = saioa.createQuery("from Artist").setMaxResults(zenbat).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
                    System.out.println("Artista");
                    System.out.println("--------------------");
                    for (Artist a : (List<Artist>) result) {
                        System.out.printf("ID| %d, Izena:%s \n", a.getArtistid(), a.getName());
                    }
                    break;
                }
            case "album":
                {
                    List result = saioa.createQuery("from Album").setMaxResults(zenbat).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
                    System.out.println("Album");
                    System.out.println("--------------------");
                    for (Album a : (List<Album>) result) {
                        System.out.printf("ID| %d, Album:%s, ArtitsID| %d \n", a.getAlbumId(), a.getTitle(), a.getArtistId());
                    }
                    break;
                }
            default:
                System.err.println("Sartu behar duzuna Album edo Artist da.!");
                break;
        }
        saioa.getTransaction().commit();
        saioa.close();
    }

    public static void datuBakarIkusi(int id, String taula){
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        switch (taula) {
            case "artist":
                {
                    List result = saioa.createQuery("from Artist WHERE ArtistId = " + id).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
                    System.out.println("Artista");
                    System.out.println("--------------------");
                    for (Artist a : (List<Artist>) result) {
                        System.out.printf("ID| %d, Izena:%s \n", a.getArtistid(), a.getName());
                    }
                    break;
                }
            case "album":
                {
                    List result = saioa.createQuery("from Album WHERE albumId = " + id).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
                    System.out.println("Album");
                    System.out.println("--------------------");
                    for (Album a : (List<Album>) result) {
                        System.out.printf("ID| %d, Album:%s, ArtitsID| %d \n", a.getAlbumId(), a.getTitle(), a.getArtistId());
                    }
                    break;
                }
            default:
                System.err.println("Sartu behar duzuna Album edo Artist da.!");
                break;
        }
        saioa.getTransaction().commit();
        saioa.close();
    }
    
    public static void datuGuztiakIkusi(){
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List<Object[]> result = saioa.createQuery("from Album AS al INNER JOIN Artist AS art ON al.artistId = art.artistid").list(); // HQL deitzen dan lengoaia idatziko dugu Querya
                    System.out.println("Artista bere album-ekin");
                    System.out.println("--------------------");
                    for (Object[] aRow : result) {
                        Album alb = (Album) aRow[0];
                        Artist art = (Artist) aRow[1];
                        System.out.printf("ID| %d, Album: %s, ID| %d, Izena:%s \n", alb.getAlbumId() , alb.getTitle(), art.getArtistid(), art.getName());
                    }        
        saioa.getTransaction().commit();
        saioa.close();
    }
    
    public static void datuaEzabatuArtist(int idArtist) {
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
    
    public static void datuaEzabatuAlbum(int idAlbum) {
        Session saioa;
        Album alb;        
        Transaction tx;
        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            alb = (Album) saioa.load(Album.class, idAlbum);
            saioa.delete(alb);
            tx.commit();            
            System.out.println("Album hau: " + alb.getAlbumId() + " " + alb.getTitle() + " ezabatu da datubasetik");
            saioa.close();
        } catch (ObjectNotFoundException onfe) {
            System.out.println("Album hori ez dago");
        }         
    }
    
    public static void datuBatAldatu(String taula, int idA, String izenBerria){
        Session saioa;
        switch (taula) {
            case "artist":
                {
                    Artist art;
                    Transaction tx;
                    try {
                        saioa = sf.openSession();
                        tx = saioa.beginTransaction();
                        art = (Artist) saioa.load(Artist.class, idA);
                        art.setName(izenBerria);
                        tx.commit();
                        
                        System.out.println("Artista bat aldatu da datubasetik. Horain: " + art + " da.");
                        saioa.close();
                    } catch (ObjectNotFoundException onfe) {
                        System.out.println("Artista hori ez dago");
                    }
                    break;
                }
            case "album":
                {
                    Album alb;
                    Transaction tx;
                    try {
                        saioa = sf.openSession();
                        tx = saioa.beginTransaction();
                        alb = (Album) saioa.load(Album.class, idA);
                        alb.setTitle(izenBerria);
                        tx.commit();
                        
                        System.out.println("Album bat aldatu da datubasetik. Horain: " + alb + " da.");
                        saioa.close();
                    } catch (ObjectNotFoundException onfe) {
                        System.out.println("Artista hori ez dago");
                    }
                    break;
                }
            default:
                System.err.println("Sartu behar duzuna Album edo Artist da.!");
                break;
        }
    }
}
