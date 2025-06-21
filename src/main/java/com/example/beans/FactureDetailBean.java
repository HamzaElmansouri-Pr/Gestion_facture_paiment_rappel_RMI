package com.example.beans;

import com.example.entities.Facture;
import com.example.sessions.FactureFacade;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class FactureDetailBean implements Serializable {

    private int factureId;
    private Facture facture;

    @Inject
    private FactureFacade factureFacade;

    @PostConstruct
    public void init() {
        if (factureId > 0) {
            facture = factureFacade.findWithLignes(factureId);
        }
    }

    public int getFactureId() {
        return factureId;
    }

    public void setFactureId(int factureId) {
        this.factureId = factureId;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public void generatePdf() {
        try {
            // Set status to 'Payé' and persist
            if (facture != null && (facture.getEtat() == null || !facture.getEtat().equals("Payé"))) {
                facture.setEtat("Payé");
                factureFacade.update(facture);
            }

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=facture_" + facture.getId() + ".pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Facture #" + facture.getId()));
            document.add(new Paragraph("Date: " + facture.getDateFacture()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.addCell("Référence Article");
            table.addCell("Quantité");
            table.addCell("Prix Unitaire");
            table.addCell("Total");

            double totalFacture = 0.0;
            for (var ligne : facture.getLignes()) {
                double totalLigne = ligne.getPrix() * ligne.getQuantite();
                table.addCell(ligne.getArticle().getReference());
                table.addCell(String.valueOf(ligne.getQuantite()));
                table.addCell(String.valueOf(ligne.getPrix()));
                table.addCell(String.format("%.2f", totalLigne));
                totalFacture += totalLigne;
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Facture: " + String.format("%.2f", totalFacture) + " €"));
            document.close();
            facesContext.responseComplete();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
} 