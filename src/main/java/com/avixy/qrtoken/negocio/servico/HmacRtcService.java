package com.avixy.qrtoken.negocio.servico;

import java.util.Date;

/**
 * Created by I7 on 31/07/2014.
 */
//parametros de serviço + aplicação de lógica de serviço
public class HmacRtcService implements Service {
    private final int SERVICE_CODE = 50;
    private final String SERVICE_NAME = "Atualizar RTC - HMAC";

    private Date data;

    private int fusoHorario;

    private String key;

    //TODO: HMAC a parada

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public byte getServiceCode() {
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
