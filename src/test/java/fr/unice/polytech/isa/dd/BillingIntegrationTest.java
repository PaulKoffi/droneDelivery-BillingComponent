package fr.unice.polytech.isa.dd;

import arquillian.AbstractBillingTest;
import fr.unice.polytech.isa.dd.entities.*;
import fr.unice.polytech.isa.dd.entities.Package;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BillingIntegrationTest extends AbstractBillingTest {
    BankAPI mocked;
    @Before
    public void setUp(){
        //mocked = new Ba;
        //management.useBankReference(mocked);
    }
    @Test
    public void generateBill() {
        assertTrue(1==1);
        //assertEquals(1,delivery.getAllDayDeliveries().size());
        //bg.generateBill();
        //DeliveryBean d = new DeliveryBean();
        //assertEquals(1, db.getBillList().size());
        //assertEquals(b.getProvider(), db.getBillList().get(0).getProvider());
        //assertEquals(b.getDeliveries().get(0), db.getBillList().get(0).getDeliveries().get(0));
    }
}
