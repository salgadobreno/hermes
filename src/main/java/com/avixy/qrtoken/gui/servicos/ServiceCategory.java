package com.avixy.qrtoken.gui.servicos;

/**
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 26/08/2014
*/
public enum ServiceCategory {
    RTC("RTC"), BANCARIO("Serviços Bancários"), CHAVES("Chaves"), OUTROS("Outros");

    private String name;
    ServiceCategory(String name) { this.name = name; }

    @Override
    public String toString() { return name; }
}
