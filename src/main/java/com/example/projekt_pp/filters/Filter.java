package com.example.projekt_pp.filters;

import com.example.projekt_pp.models.Kategorie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private String phase;
    private boolean active;
    private Kategorie kategoria;

    public boolean kategoriaIsNull() {
        if(kategoria == null) {
            return true;
        }
        else {
            return false;
        }
    }
}
