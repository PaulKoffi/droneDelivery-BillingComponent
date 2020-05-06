package fr.unice.polytech.isa.dd;

import fr.unice.polytech.isa.dd.entities.Bill;
import fr.unice.polytech.isa.dd.entities.ExternalPartnerException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CheckTransferStatus {

    Bill findBillById(int id);

    boolean checkstatut(int id);

    List<Bill> getAllPaidBills() throws ExternalPartnerException;

    public List<Integer> allIdBillPaid() throws ExternalPartnerException;
}
