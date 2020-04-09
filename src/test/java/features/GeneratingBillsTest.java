package features;

import arquillian.AbstractBillingTest;
import cucumber.api.CucumberOptions;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Quand;
import cucumber.runtime.arquillian.CukeSpace;
import fr.unice.polytech.isa.dd.BillingGeneratedInterface;
import fr.unice.polytech.isa.dd.DeliveryInterface;
import fr.unice.polytech.isa.dd.DeliverySchedule;
import fr.unice.polytech.isa.dd.NextDeliveryInterface;
import fr.unice.polytech.isa.dd.entities.Customer;
import fr.unice.polytech.isa.dd.entities.Delivery;
import fr.unice.polytech.isa.dd.entities.Package;
import fr.unice.polytech.isa.dd.entities.Provider;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;


@RunWith(CukeSpace.class)
@CucumberOptions(features = "src/test/resources/features/BillsFR.feature")
@Transactional(TransactionMode.COMMIT)
public class GeneratingBillsTest extends AbstractBillingTest {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB(name = "delivery-stateless") NextDeliveryInterface nextDeliveryInterface;
    @EJB(name = "bill-stateless") private BillingGeneratedInterface billinggenerator ;
    @EJB(name = "delivery-stateless") private DeliverySchedule deliverySchedule;
    @EJB(name = "delivery-stateless") private DeliveryInterface deliveryInterface;

    @Quand("^l'employé envoie les (\\d+) livraisons du fournisseurs (.*) et (.*)$")
    public void livraisonsenvoyées(Integer arg0,String arg1,String arg2){
       /* init.initializeDatabaseTestWithMutipleProviders(arg0,arg1,arg2);
        for(int i= 0; i < init.getDeliveries().size();i++){
            Delivery delivery = nextDelivery.getNextDelivery();
        }*/
        Customer c = new Customer("Pm", "adresse1");
        entityManager.persist(c);

        Provider pro3 = new Provider();
        pro3.setName(arg1);
        entityManager.persist(pro3);

        Provider pro2 = new Provider();
        pro2.setName(arg2);
        entityManager.persist(pro2);

        Package package5 = new Package();
        package5.setWeight(10.0);
        package5.setProvider(pro3);
        entityManager.persist(package5);


        Package package2 = new Package();
        package2.setWeight(15.0);
        package2.setProvider(pro3);
        entityManager.persist(package2);
        pro3.add(package5);
        pro3.add(package2);

        Delivery delivery1 = new Delivery();
        delivery1.setCustomer(c);
        delivery1.setPackageDelivered(package5);
        entityManager.persist(delivery1);

        Delivery delivery2 = new Delivery();
        delivery2.setCustomer(c);
        delivery2.setPackageDelivered(package2);
        entityManager.persist(delivery2);

        Package package3 = new Package();
        package3.setWeight(20.0);
        package3.setProvider(pro2);
        entityManager.persist(package3);


        Package package4 = new Package();
        package4.setWeight(25.0);
        package4.setProvider(pro2);
        entityManager.persist(package4);
        pro2.add(package3);
        pro2.add(package4);

        Delivery delivery3 = new Delivery();
        delivery3.setCustomer(c);
        delivery3.setPackageDelivered(package3);
        entityManager.persist(delivery3);

        Delivery delivery4 = new Delivery();
        delivery4.setCustomer(c);
        delivery4.setPackageDelivered(package4);
        entityManager.persist(delivery4);


        for(int i= 0; i < 3;i++){
            nextDeliveryInterface.getNextDelivery();
        }

      /*  for(Provider provider : deliverySchedule.providerList()){
            System.out.println("\n\n/**********************\n"+provider.getName()+" - "+provider.getId()+"\n****************************\n\n");
        }
        Map<Provider,List<Delivery>>providerListHashMap = deliveryInterface.getAllDayDeliveries();
        for (Map.Entry<Provider, List<Delivery>> entry : providerListHashMap.entrySet()
             ) {
            System.out.println("\n\n/**********************\n"+entry.getKey().getName()+"\n****************************\n\n");
        }*/

    }
    //Ce test bug parce que la valeur doit être 2
    @Alors("^(\\d+) factures sont générées$")
    public void facturegenerees(Integer arg0){
        billinggenerator.generateBill();
        assertEquals(arg0.intValue(),billinggenerator.get_bills().size());
    }
}