package com.avixy.qrtoken.negocio.servico;

import com.avixy.qrtoken.negocio.servico.servicos.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Avixy on 5/5/2015.
 */
public class ServiceCodeLookup {
    private static final ServiceCode[] BASE_SERVICE_CODES = new ServiceCode[] {ServiceCode.SERVICE_HMAC_TEMPLATE_MESSAGE, ServiceCode.SERVICE_HMAC_FORMATTED_MESSAGE};
    private static final ServiceCode[][] SERVICE_CODE_CONFIGS = new ServiceCode[][] {
            new ServiceCode[] {ServiceCode.SERVICE_HMAC_TEMPLATE_MESSAGE_WITHOUT_PIN, ServiceCode.SERVICE_HMAC_TEMPLATE_MESSAGE_RANGED_RTC, ServiceCode.SERVICE_HMAC_TEMPLATE_MESSAGE_RANGED_RTC_WITHOUT_PIN},
            new ServiceCode[] {ServiceCode.SERVICE_HMAC_FORMATTED_MESSAGE_WITHOUT_PIN, ServiceCode.SERVICE_HMAC_FORMATTED_MESSAGE_RANGED_RTC, ServiceCode.SERVICE_HMAC_FORMATTED_MESSAGE_RANGED_RTC_WITHOUT_PIN},
    };

    private static final Map<ServiceCode, List<ServiceCode>> MAP = new HashMap<>();

    static {
        for (int i = 0; i < BASE_SERVICE_CODES.length; i++) {
            ServiceCode baseServiceCode = BASE_SERVICE_CODES[i];
            MAP.put(baseServiceCode, Arrays.asList(SERVICE_CODE_CONFIGS[i]));
        }
    }

    public static ServiceCode getServiceCode(Service service, boolean pin, boolean ranged) {

        List<ServiceCode> serviceCodes1 = MAP.get(service.getServiceCode());

        if (pin) {
            if (ranged) {
                return serviceCodes1.get(1);
            } else {
                return service.getServiceCode();
            }
        } else {
            if (ranged) {
                return serviceCodes1.get(2);
            } else {
                return serviceCodes1.get(0);
            }
        }
    }
}
