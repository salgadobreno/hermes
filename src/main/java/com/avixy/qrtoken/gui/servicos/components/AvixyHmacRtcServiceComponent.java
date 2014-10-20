package com.avixy.qrtoken.gui.servicos.components;

import com.avixy.qrtoken.negocio.qrcode.QrCodePolicy;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.AcceptsKey;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.avixy.qrtoken.negocio.servico.servicos.rtc.AvixyRtcService;
import com.google.inject.Inject;

/**
 * Created on 02/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
@ServiceComponent.Category(category = ServiceCategory.RTC)
@AcceptsKey(keyType = KeyType.HMAC)
public class AvixyHmacRtcServiceComponent extends HmacRtcServiceComponent {
    @Inject
    public AvixyHmacRtcServiceComponent(AvixyRtcService service, QrCodePolicy qrCodePolicy) {
        super(service, qrCodePolicy);
    }
}
