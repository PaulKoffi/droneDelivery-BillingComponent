package fr.unice.polytech.isa.dd;

import fr.unice.polytech.isa.dd.entities.*;
import org.apache.cxf.common.i18n.UncheckedException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
@Stateless(name="bill-stateless")
public class BillingBean implements BillingGeneratedInterface, CheckTransferStatus {

    @PersistenceContext private EntityManager entityManager;

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    @EJB(name = "delivery-stateless") private DeliveryInterface delivery;

    private BankAPI bank = new BankAPI();

    /*public BillingBean() {
        bank = new BankAPI();
    }*/
    public BillingBean() {

    }


    @Override
    public void generateBill() throws Exception {
        int i = 1;
        for (Map.Entry<Provider, List<Delivery>> entry : delivery.getAllDayDeliveries().entrySet()) {
            // System.out.println("[Key] : " + entry.getKey() + " [Value] : " + entry.getValue().size());
            if (!entry.getValue().isEmpty()) {
                Bill new_bill = new Bill();
                new_bill.setProvider(entry.getKey());
                new_bill.setDeliveries(entry.getValue());
                double sum = 0.0;
                for (Delivery d: entry.getValue()) {
                    sum+=d.getPrice();
                }
                new_bill.setBillAmount(sum);
                entry.getKey().add(new_bill);
                entityManager.persist(new_bill);
                //db.getBillList().add(new Bill( i,entry.getKey(), entry.getValue()));
                //i++;
            }
        }
    }

    @Override
    public boolean checkstatut(int id) {
        boolean status = false;
        try {
            JSONObject resp = bank.getPayment(id);
            double amount = resp.getDouble("Amount");
            System.out.println("Amount " + amount);
            //TODO
//            status = true;
            //Must also update the paymentDate  with the date of the transfer and paymentStatus attribute after checking the amount of the transfer of the bill in database
            Bill b = findBillById(id);

            if (b != null) {
                // verifier si le montant de la facture est bon normalement
                status = true;
                b.setBillStatus("PAID");
            } else {
                System.out.println("b is null");
            }

        } catch (ExternalPartnerException e) {
            return false;
//            System.out.println("ERROR");
//            log.log(Level.INFO, "Error while exchanging with external partner", e);
        }
        return status;
    }

    @Override
    public List<Bill> getAllPaidBills() throws ExternalPartnerException {
        List<Bill> bills = new ArrayList<>();
        JSONArray res = bank.getPayements();
        for (int i = 0; i < res.length(); i++) {
            Bill b = findBillById(res.getInt(i));
            if (b != null) {
                b.setBillStatus("PAID");
                //  System.out.println(b.getBillStatus());
                bills.add(b);
            }
        }
        return bills;
    }

    @Override
    public Bill findBillById(int id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bill> criteria = builder.createQuery(Bill.class);
        Root<Bill> root =  criteria.from(Bill.class);
        criteria.select(root).where(builder.equal(root.get("id"),id));
        TypedQuery<Bill> query = entityManager.createQuery(criteria);
        try {
            return Optional.of(query.getSingleResult()).get();
        } catch (NoResultException nre){
            return null;
        }
    }

    @Override
    public List<Bill> get_bills(){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bill> criteria = builder.createQuery(Bill.class);
        Root<Bill> root =  criteria.from(Bill.class);
        criteria.select(root);
        TypedQuery<Bill> query = entityManager.createQuery(criteria);
        try {
            List<Bill> toReturn = new ArrayList<>(query.getResultList());
            return Optional.of(toReturn).get();
        } catch (NoResultException nre){
            return null;
        }
    }
}

