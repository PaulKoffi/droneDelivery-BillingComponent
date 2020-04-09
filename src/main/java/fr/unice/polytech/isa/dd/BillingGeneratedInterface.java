package fr.unice.polytech.isa.dd;

import fr.unice.polytech.isa.dd.entities.Bill;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BillingGeneratedInterface {
    void generateBill();
    public List<Bill> get_bills();
}
