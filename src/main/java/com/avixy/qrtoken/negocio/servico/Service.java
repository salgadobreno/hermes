package com.avixy.qrtoken.negocio.servico;

import java.util.Map;

/**
 * Created by I7 on 31/07/2014.
 */
public interface Service {
    public String getServiceName();

    public byte getServiceCode();

    byte[] getData();

}
