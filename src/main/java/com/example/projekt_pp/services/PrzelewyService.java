package com.example.projekt_pp.services;

import com.example.projekt_pp.models.Przelewy;
import com.example.projekt_pp.repositories.PrzelewyRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class PrzelewyService {

    private final PrzelewyRepository przelewyRepository;

    public void addPrzelew(Przelewy p) {
        p.setStatus("Oczekujący");
        przelewyRepository.save(p);
    }

    public void changeStatus(long id) {
        przelewyRepository.potwierdz(id);
    }

    public List<Przelewy> getOczekujace() {
        return przelewyRepository.findAllByStatus("Oczekujący");
    }

    public List<Przelewy> getPotwierdzone() {
        return przelewyRepository.findAllByStatus("Potwierdzone");
    }

    public List<Przelewy> getHistoria(long id) {
        return przelewyRepository.findAllByK(id);
    }

    public void stworzPotwierdzeniePDF(long id) throws FileNotFoundException, DocumentException {
        Przelewy p = przelewyRepository.getById(id);
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(
                p.getId()+".pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Font font2 = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);

        document.add(Chunk.NEWLINE);

        document.add(new Paragraph(String.valueOf(
                "POTWIERDZENIE:" + "\n" +
                "Nazwa odbiorcy: " + p.getOdbiorca_nazwa() + "\n" +
                        "Numer konta: " + p.getNr_konta_odbiorcy() + "\n" +
                        "Kwota: " + p.getKwota() + "\n" +
                        "Tytuł: " + p.getTytul()
        ),font2));
        document.add(Chunk.NEWLINE);

        document.close();
    }
}
