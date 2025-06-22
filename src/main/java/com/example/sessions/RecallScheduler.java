package com.example.sessions;

import com.example.entities.Facture;
import com.example.entities.Etat;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.ZoneId;  // added import for conversion

@Stateless
public class RecallScheduler {

    @PersistenceContext(unitName = "facture_dbPU")
    private EntityManager em;

    @Inject
    private EmailService emailService;

    @Schedule(minute = "0", hour = "4", persistent = false)
    public void sendRecallEmails() {
        try {
            System.out.println("Scheduler running at: " + LocalDate.now());

            LocalDate targetDate = LocalDate.now().minusDays(3);
            Date sqlDate = Date.valueOf(targetDate);

            List<Facture> factures = em.createQuery(
                "SELECT f FROM Facture f WHERE f.dateFacture = :date AND f.etat = :etat", Facture.class)
                .setParameter("date", sqlDate)
                .setParameter("etat", Etat.NON_PAYEE)
                .getResultList();

            if (factures.isEmpty()) {
                System.out.println("No unpaid invoices found for date: " + sqlDate);
            }

            // Create formatter once for French date formatting without time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);

            for (Facture f : factures) {
                String to = f.getClient().getEmail();
                if (to == null || to.isEmpty()) {
                    System.out.println("Skipping invoice #" + f.getId() + " because client email is missing.");
                    continue;
                }

                // Convert java.sql.Date to LocalDate correctly
                LocalDate localDate = f.getDateFacture().toInstant()
                                       .atZone(ZoneId.systemDefault())
                                       .toLocalDate();

                // Format the date in French
                String dateFactureFormatted = localDate.format(formatter);

                String subject = "Rappel de paiement - Facture #" + f.getId();
                String body = "Bonjour " + f.getClient().getNom() + ",\n\n" +
                              "Votre facture du " + dateFactureFormatted + " est toujours impayée.\n" +
                              "Merci de procéder au paiement dès que possible.";

                System.out.println("Sending email for invoice #" + f.getId() + " to " + to);
                emailService.sendEmail(to, subject, body);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in scheduler: " + e.getMessage());
        }
    }
}
