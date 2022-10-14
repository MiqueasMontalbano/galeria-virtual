package com.GaleriaVirtual.entidades.enumeracion;

public enum Categoria {
    SERIGRAFIA("Serigrafia"), OLEO("Óleo"), FOTOGRAFIA("Fotografia"), ESCULTURA("Escultura"), ARTESANIA("Artesania");
    
    private String value;

    private Categoria(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
