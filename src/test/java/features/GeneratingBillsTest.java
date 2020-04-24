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
import fr.unice.polytech.isa.dd.entities.*;
import fr.unice.polytech.isa.dd.entities.Package;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import utils.MyDate;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

import static org.junit.Assert.assertEquals;


@RunWith(CukeSpace.class)
@CucumberOptions(features = "src/test/resources/features/BillsFR.feature")
@Transactional(TransactionMode.COMMIT)
public class GeneratingBillsTest extends AbstractBillingTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private UserTransaction utx;

    @EJB(name = "delivery-stateless") NextDeliveryInterface nextDeliveryInterface;
    @EJB(name = "bill-stateless") private BillingGeneratedInterface billinggenerator ;
    @EJB(name = "delivery-stateless") private DeliverySchedule deliverySchedule;
    @EJB(name = "delivery-stateless") private DeliveryInterface deliveryInterface;

    Customer c = new Customer("Pm", "adresse1");

    Provider pro3;
    Provider pro2;

    Package package5;
    Package package2;
    Package package3;
    Package package4;

    Delivery delivery1;
    Delivery delivery2;
    Delivery delivery3;
    Delivery delivery4;


    @Before
    public void setUp() {
        entityManager.persist(c);

        pro3 = new Provider();

        pro2 = new Provider();

        package5 = new Package();
        package5.setWeight(10.0);

        package2 = new Package();
        package2.setWeight(15.0);

        delivery1 = new Delivery();
        delivery1.setCustomer(c);
        delivery1.setDeliveryDate("17/04/2020");

        delivery2 = new Delivery();
        delivery2.setCustomer(c);
        delivery2.setDeliveryDate("17/04/2020");

        package3 = new Package();
        package3.setWeight(20.0);

        package4 = new Package();
        package4.setWeight(25.0);

        delivery3 = new Delivery();
        delivery3.setCustomer(c);
        delivery3.setDeliveryDate("17/04/2020");

        delivery4 = new Delivery();
        delivery4.setCustomer(c);
        delivery4.setDeliveryDate("17/04/2020");


    }

    @After
    public void cleanUp() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        utx.begin();
        delivery1 = entityManager.merge(delivery1);
        entityManager.remove(delivery1);
        delivery2 = entityManager.merge(delivery2);
        entityManager.remove(delivery2);
        delivery3 = entityManager.merge(delivery3);
        entityManager.remove(delivery3);
        delivery4 = entityManager.merge(delivery4);
        entityManager.remove(delivery4);

        package5 = entityManager.merge(package5);
        entityManager.remove(package5);
        package2 = entityManager.merge(package2);
        entityManager.remove(package2);
        package3 = entityManager.merge(package3);
        entityManager.remove(package3);
        package4 = entityManager.merge(package4);
        entityManager.remove(package4);

        pro3 = entityManager.merge(pro3);
        entityManager.remove(pro3);
        pro2 = entityManager.merge(pro2);
        entityManager.remove(pro2);

        c = entityManager.merge(c);
        entityManager.remove(c);

        int size = billinggenerator.get_bills().size();
        for (int i = 0; i<size; i++){
           Bill _bill = entityManager.merge(billinggenerator.get_bills().get(0));
            entityManager.remove(_bill);
        }
        /*Bill _bill = entityManager.merge(billinggenerator.get_bills().get(0));
        entityManager.remove(_bill);*/

        utx.commit();
    }

    @Quand("^l'employé envoie les (\\d+) livraisons du fournisseurs (.*) et (.*)$")
    public void livraisonsenvoyées(Integer arg0,String arg1,String arg2) throws Exception {
       /* init.initializeDatabaseTestWithMutipleProviders(arg0,arg1,arg2);
        for(int i= 0; i < init.getDeliveries().size();i++){
            Delivery delivery = nextDelivery.getNextDelivery();
        }*/
        pro3.setName(arg1);
        entityManager.persist(pro3);

        pro2.setName(arg2);
        entityManager.persist(pro2);

        package5.setProvider(pro3);
        package5.setSecret_number("AXXXX2");
        entityManager.persist(package5);

        package2.setProvider(pro3);
        package2.setSecret_number("AXXXX8");
        entityManager.persist(package2);
        pro3.add(package5);
        pro3.add(package2);

        delivery1.setPackageDelivered(package5);
        entityManager.persist(delivery1);

        delivery2.setPackageDelivered(package2);
        entityManager.persist(delivery2);

        package3.setProvider(pro2);
        package3.setSecret_number("AXXXX3");
        entityManager.persist(package3);

        package4.setProvider(pro2);
        package4.setSecret_number("AXXX2");
        entityManager.persist(package4);
        pro2.add(package3);
        pro2.add(package4);

        delivery3.setPackageDelivered(package3);
        entityManager.persist(delivery3);

        delivery4.setPackageDelivered(package4);
        entityManager.persist(delivery4);

        MyDate.date_now="17/04/2020";

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
    public void facturegenerees(Integer arg0) throws Exception {
        billinggenerator.generateBill();
        assertEquals(arg0.intValue(),billinggenerator.get_bills().size());
    }
}