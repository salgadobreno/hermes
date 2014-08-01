package com.avixy.qrtoken.negocio.servico;

/**
 * Created by I7 on 31/07/2014.
 */
public class RtcService implements Service {

    private final int SERVICE_CODE = 50;

    @Override
    public String getServiceName() {
        return "Atualizar RTC - Avixy com HMAC";
    }

    @Override
    public int getServiceCode() {
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

    //TODO: parameter stuff
}
