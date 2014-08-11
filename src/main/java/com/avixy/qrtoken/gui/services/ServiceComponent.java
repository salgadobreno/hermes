package com.avixy.qrtoken.gui.services;

import com.avixy.qrtoken.negocio.servico.Service;

/**
 * Created by I7 on 08/08/2014.
 */
public interface ServiceComponent {
    String getServiceName();

    Service getService();

    String getFxmlPath();

}

