package features;

import arquillian.AbstractBillingTest;
import cucumber.api.CucumberOptions;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Et;
import cucumber.api.java.fr.Quand;
import cucumber.runtime.arquillian.CukeSpace;
import fr.unice.polytech.isa.dd.*;
import fr.unice.polytech.isa.dd.entities.*;
import fr.unice.polytech.isa.dd.entities.Package;
import io.cucumber.java8.Fr;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(CukeSpace.class)
@CucumberOptions(features = "src/test/resources/features/GeneratingBillsFR.feature")
@Transactional(TransactionMode.COMMIT)
public class GeneratingBillsArquillianTest extends AbstractBillingTest implements Fr {

    @PersistenceContext private EntityManager entityManager;
    @Inject
    private UserTransaction utx;

    @EJB(name = "delivery-stateless") private NextDeliveryInterface nextDelivery ;
    @EJB(name = "bill-stateless") private BillingGeneratedInterface billinggenerator ;

    private Customer c;
    private Provider pro1;
    private Package package1;
    private Delivery delivery1;

    @Before
    public void setUp(){
        c = new Customer("Doni","10 rue des lucioles");

        pro1 = new Provider();

        package1 = new Package();
        package1.setWeight(10.0);
        package1.setProvider(pro1);
        package1.setSecret_number("AXXXX2");
        pro1.add(package1);

        delivery1 = new Delivery();
        delivery1.setCustomer(c);
        delivery1.setPackageDelivered(package1);
        c.add_a_customer_delivery(delivery1);
    }

    @After
    public void cleanUp() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        utx.begin();
            delivery1 = entityManager.merge(delivery1);
            entityManager.remove(delivery1);

            package1 = entityManager.merge(package1);
            entityManager.remove(package1);

            pro1 = entityManager.merge(pro1);
            entityManager.remove(pro1);

            c = entityManager.merge(c);
            entityManager.remove(c);

            Bill _bill = entityManager.merge(billinggenerator.get_bills().get(0));
            entityManager.remove(_bill);

        utx.commit();
    }

    @Quand("^l'employé demande la prochaine livraison$")
    public void demandeprochainelivraison(){
    }
    @Alors("^il y a (\\d+) livraisons$")
    public void ilyanombredelivraison(Integer arg0){
        assertNull(nextDelivery.getNextDelivery());
    }
    @Et("^donc il y a (\\d+) facture$")
    public void nombredefactures(Integer arg0){
        billinggenerator.generateBill();
        assertEquals(arg0.intValue(),billinggenerator.get_bills().size());
    }
    @Quand("^l'employé effectue les (\\d+) prochaines livraisons du fournisseur (.*)$")
    public void efftecueprochainelivraisons(Integer arg0, String arg1){
        pro1.setName(arg1);
        entityManager.persist(package1);
        entityManager.persist(delivery1);
        entityManager.persist(pro1);
        entityManager.persist(c);
        nextDelivery.getNextDelivery();
    }
    @Alors("^il y a (\\d+) facture$")
    public void facturenombre(Integer arg0) {
        billinggenerator.generateBill();
        assertEquals(arg0.intValue(),billinggenerator.get_bills().size());
    }
}
