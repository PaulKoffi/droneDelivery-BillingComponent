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
import org.junit.runner.RunWith;
import utils.MyDate;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

import java.text.ParseException;

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
    @EJB(name = "bill-stateless") private BillingGeneratedInterface billinggenerator;

    private Customer c = new Customer("Pm", "adresse1");

    private Provider pro3;
    private Provider pro2;

    private Package package5;
    private Package package2;
    private Package package3;
    private Package package4;

    private Delivery delivery1;
    private Delivery delivery2;
    private Delivery delivery3;
    private Delivery delivery4;

    private Drone drone1 = new Drone(12,0,"1");
    private Drone drone2 = new Drone(12,0,"2");


    @Before
    public void setUp() {
        entityManager.persist(c);
        drone1.addStatut(new DroneStatus(DRONE_STATES.AVAILABLE,"12/12/2020"));
        entityManager.persist(drone1);
        drone2.addStatut(new DroneStatus(DRONE_STATES.AVAILABLE,"12/12/2020"));
        entityManager.persist(drone2);

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
        drone1 = entityManager.merge(drone1);
        entityManager.remove(drone1);
        drone2 = entityManager.merge(drone2);
        entityManager.remove(drone2);

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

        utx.commit();
    }

    @Quand("^l'employé envoie les (\\d+) livraisons du fournisseurs (.*) et (.*)$")
    public void livraisonsenvoyées(Integer arg0,String arg1,String arg2) throws ParseException {
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
            if(i == 1) {
                drone1 = entityManager.find(Drone.class,drone1.getId());
                entityManager.refresh(drone1);
                MyDate dt = new MyDate("14/04/2020","10h00");
                DroneStatus status= new DroneStatus(DRONE_STATES.AVAILABLE,dt.toString());
                drone1.addStatut(status);
                entityManager.persist(drone1);
            }
            nextDeliveryInterface.getNextDelivery();
        }

    }
    @Alors("^(\\d+) factures sont générées$")
    public void facturegenerees(Integer arg0){
        billinggenerator.generateBill();
        assertEquals(arg0.intValue(),billinggenerator.get_bills().size());
    }
}