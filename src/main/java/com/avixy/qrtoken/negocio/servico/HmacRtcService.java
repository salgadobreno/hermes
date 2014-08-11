package com.avixy.qrtoken.negocio.servico;

import java.util.Date;

/**
 * Created on 31/07/2014
 * @author I7
 */
public class HmacRtcService implements Service {
    private final int SERVICE_CODE = 50;
    private final String SERVICE_NAME = "Atualizar RTC - HMAC";

    private Date data;

    private int fusoHorario;

    private String key;
    //TODO: HMAC a parada
    //TODO: TDD os serviços

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public int getServiceCode() {
        return SERVICE_CODE;
    }

    @Override
    public byte[] getData() {
        String res = "" + getServiceCode() + "" + getServiceName() + "" + data.getTime();
        return res.getBytes();
    }

    public void setDate(Date data) {
        this.data = data;
    }

    public void setFusoHorario(int fusoHorario) {
        this.fusoHorario = fusoHorario;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
