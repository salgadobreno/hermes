package com.avixy.qrtoken.gui.servicos;

import com.avixy.qrtoken.negocio.servico.Servico;

/**
 * Created by I7 on 08/08/2014.
 */
public abstract class ServicoComponent {
    public ServicoComponent(){}

    public abstract String getServiceName();

    public abstract Servico getService();

    public abstract String getFxmlPath();

    public static enum Category {
        RTC("RTC"), OUTROS("Outros");

        private String name;
        Category(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

