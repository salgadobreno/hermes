package com.avixy.qrtoken.negocio.servico;

/**
 * Created by I7 on 31/07/2014.
 */
public interface Service {
    public String getServiceName();

    public int getServiceCode();

    public boolean isPinAuthed();

    public boolean isPukAuthed();
}
