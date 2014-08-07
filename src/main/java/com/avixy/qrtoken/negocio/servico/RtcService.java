package com.avixy.qrtoken.negocio.servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by I7 on 31/07/2014.
 */
public class RtcService implements Service {
    //parametros de serviço + aplicação de lógicas de serviço

    //Params: Date data
    private Long timestamp;

    private String key;

    private final int SERVICE_CODE = 50;

    public RtcService(Long timestamp, String key) {
        this.timestamp = timestamp;
        this.key = key;
    }

    @Override
    public String getServiceName() {
        return "Atualizar RTC - Avixy com HMAC";
    }

    @Override
    public byte getServiceCode() {
        return SERVICE_CODE;
    }

    @Override
    public boolean isPinAuthed() {
        return false;
    }

    @Override
    public boolean isPukAuthed() {
        return false;
    }

    @Override
    public byte[] exec() {
        return new byte[1];
    }

}
