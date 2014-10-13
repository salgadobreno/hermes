package com.avixy.qrtoken.gui.servicos.components;

/**
 * Enum que define as categorias dos {@link ServiceComponent}.
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 26/08/2014
*/
public enum ServiceCategory {
    CICLO_DE_VIDA("Ciclo de vida"), RTC("RTC"), BANCARIO("Serviços Bancários"), CHAVES("Chaves"), OUTROS("Outros");

    private String name;
    ServiceCategory(String name) { this.name = name; }

    @Override
    public String toString() { return name; }
}
