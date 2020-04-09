package features;
/*
import arquillian.AbstractBillingTest;
import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Et;
import cucumber.api.java.fr.Quand;
import fr.unice.polytech.isa.dd.DeliveryBean;
import fr.unice.polytech.isa.dd.DeliveryInterface;
import fr.unice.polytech.isa.dd.NextDeliveryInterface;
import fr.unice.polytech.isa.dd.BillingBean;
import fr.unice.polytech.isa.dd.BillingGeneratedInterface;
import fr.unice.polytech.isa.dd.entities.*;
import fr.unice.polytech.isa.dd.entities.Package;
import io.cucumber.java8.Fr;
import org.joda.time.DateTime;

import static org.junit.Assert.*;

import javax.ejb.EJB;
import java.util.Iterator;
import java.util.List;

public class GeneratingBillsStepDefs extends AbstractBillingTest implements Fr {

        @Quand("^l'employé demande la prochaine livraison$")
        public void demandeprochainelivraison(){
//            Database.getInstance().clearDatabase();
            del = nextDelivery.getNextDelivery();
        }
        @Alors("^il y a (\\d+) livraisons$")
        public void ilyanombredelivraison(Integer arg0){
            assertNull(del);
        }
        @Et("^donc il y a (\\d+) facture$")
        public void nombredefactures(Integer arg0){
            billinggenerator.generateBill();
            assertEquals(arg0.intValue(),bills.size());
            cleanDatabase();
        }
        @Quand("^l'employé effectue les (\\d+) prochaines livraisons du fournisseur (.*)$")
        public void efftecueprochainelivraisons(Integer arg0, String arg1){
            initialiatizeFirstTest(arg0,arg1);
            for(int i = 0; i < arg0; i++){
                del = nextDelivery.getNextDelivery();
            }
        }
        @Alors("^il y a (\\d+) facture$")
        public void facturenombre(Integer arg0) {
            billinggenerator.generateBill();
            assertEquals(arg0.intValue(),bills.size());
            cleanDatabase();
        }
        @Quand("^l'employé envoie les (\\d+) livraisons du fournisseurs (.*) et (.*)$")
        public void livraisonsenvoyées(Integer arg0,String arg1,String arg2){
            initializeDatabaseTestWithMutipleProviders(arg0,arg1,arg2);
            del = nextDelivery.getNextDelivery();
//            System.out.println("etat de del " + (del == null));
            while(del != null){
                del = nextDelivery.getNextDelivery();
            }
        }
        @Alors("^(\\d+) factures sont générées$")
        public void facturegenerees(Integer arg0){
            billinggenerator.generateBill();
            assertEquals(arg0.intValue(),Database.getInstance().getBillList().size());
            cleanDatabase();
        }


}*/
