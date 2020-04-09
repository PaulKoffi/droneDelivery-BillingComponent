package utils;
/*
import fr.unice.polytech.isa.dd.BillingGeneratedInterface;
import fr.unice.polytech.isa.dd.CheckTransferStatus;
import fr.unice.polytech.isa.dd.NextDeliveryInterface;
import fr.unice.polytech.isa.dd.entities.*;
import fr.unice.polytech.isa.dd.entities.Package;
import org.joda.time.DateTime;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

public class Init {

    private Database database = Database.getInstance();
    private List<Delivery> deliveries = database.getDeliveryList();
    private List<fr.unice.polytech.isa.dd.entities.Provider> providers = database.getProviderList();
    private List<Bill> bills = database.getBillList();
    private Delivery del;
    private Bill bill;

    public Init(){}

    public void initialiatizeFirstTest(int arg1, String arg2){
        Customer c = new Customer("Pm", "adresse1");

        DateTime dt = new DateTime();

        fr.unice.polytech.isa.dd.entities.Provider pro1 = new fr.unice.polytech.isa.dd.entities.Provider(1, arg2);
        providers.add(pro1);

        for (int i = 0; i < arg1; i++){
            deliveries.add(new Delivery(c,new fr.unice.polytech.isa.dd.entities.Package(i,10.0,dt,pro1),dt,null));
        }
    }

    public void initializeDatabaseTestWithMutipleProviders(int arg1, String arg2,String arg3) {
        cleanDatabase();
        Customer c = new Customer("Pm", "adresse1");

        DateTime dt = new DateTime();

        fr.unice.polytech.isa.dd.entities.Provider pro1 = new fr.unice.polytech.isa.dd.entities.Provider(1, arg2);
        fr.unice.polytech.isa.dd.entities.Provider pro2 = new fr.unice.polytech.isa.dd.entities.Provider(1,arg3);
        providers.add(pro1);providers.add(pro2);

        for (int i = 0; i < arg1; i++){
            deliveries.add(new Delivery(c,new fr.unice.polytech.isa.dd.entities.Package(i,10.0,dt,pro1),dt,null));
        }
        for (int i = 0; i < arg1; i++){
            deliveries.add(new Delivery(c,new fr.unice.polytech.isa.dd.entities.Package(i*2,10.0,dt,pro2),dt,null));
        }
    }

    public void cleanDatabase(){
        providers.clear();
        deliveries.clear();
        bills.clear();
    }

    public void initUnitTests(){
        cleanDatabase();
        Database.getInstance().clearDatabase();
        Customer c = new Customer("Doni","10 rue des lucioles");
        DateTime d = new DateTime();
        fr.unice.polytech.isa.dd.entities.Provider p1 = new fr.unice.polytech.isa.dd.entities.Provider(1,"p1");
        fr.unice.polytech.isa.dd.entities.Provider p2 = new fr.unice.polytech.isa.dd.entities.Provider(2,"p2");
        database.getProviderList().add( p1);
        database.getProviderList().add( p2);
        fr.unice.polytech.isa.dd.entities.Package package1 = new Package(1,2.0,d,p1);
        Delivery d1 = new Delivery(c,package1,d,null) ;
        d1.setStatus(true);
        database.getDeliveryList().add(d1);
        List <Delivery> deliveries1 = new ArrayList<>();
        deliveries1.add(d1);
        bill = new Bill(4,p1, deliveries1);
    }

    public Bill getBill() {
        return bill;
    }

    public Delivery getDel() {
        return del;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public Database getDatabase() {
        return database;
    }
}*/