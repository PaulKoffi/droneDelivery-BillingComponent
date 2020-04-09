package arquillian;

import fr.unice.polytech.isa.dd.*;
import fr.unice.polytech.isa.dd.entities.*;
import fr.unice.polytech.isa.dd.entities.Package;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;


public abstract class AbstractBillingTest {

    @Deployment
    public static WebArchive createDeployement(){
        return ShrinkWrap.create(WebArchive.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml")
                .addPackage(Delivery.class.getPackage())
                .addPackage(DeliverySchedule.class.getPackage())
                .addPackage(NextDeliveryInterface.class.getPackage())
                .addPackage(DeliveryInterface.class.getPackage())
                .addPackage(Customer.class.getPackage())
                .addPackage(Provider.class.getPackage())
                .addPackage(Database.class.getPackage())
                .addPackage(Package.class.getPackage())
                .addPackage(Bill.class.getPackage())
                .addPackage(BillingGeneratedInterface.class.getPackage())
                .addPackage(BillingBean.class.getPackage())
                .addAsManifestResource(new ClassLoaderAsset("META-INF/persistence.xml"), "persistence.xml")
                ;
    }
}