package com.example.projekt_pp.formatters;

import com.example.projekt_pp.filters.Filter;
import com.example.projekt_pp.models.Kategorie;

import java.text.ParseException;
import java.util.Locale;

public class Formatter implements org.springframework.format.Formatter<Filter> {
    @Override
    public Filter parse(String text, Locale locale) throws ParseException {
        var newText =text.split(";");
        if(newText.length==3){
            String phase = newText[0];
            boolean active = Boolean.valueOf(newText[1]);
            Kategorie k = new Kategorie();
            k.setNazwa(newText[2]);
            return new Filter(phase, active, k);
        }
        throw new ParseException("Nieprawidłowy format danych",1);
    }

    @Override
    public String print(Filter filter, Locale locale) {
        if(filter==null || filter.getPhase()==null || filter.kategoriaIsNull()){
            return "";
        }
        return String.format("%s;%s;%s", filter.getPhase(), filter.isActive(), filter.getKategoria().getNazwa());
    }
}
