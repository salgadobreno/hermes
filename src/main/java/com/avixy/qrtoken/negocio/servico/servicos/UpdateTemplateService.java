package com.avixy.qrtoken.negocio.servico.servicos;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.AesCrypted;
import com.avixy.qrtoken.negocio.servico.behaviors.HmacAble;
import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.operations.AesCryptedMessagePolicy;
import com.avixy.qrtoken.negocio.servico.operations.RandomGenerator;
import com.avixy.qrtoken.negocio.servico.operations.TimestampPolicy;
import com.avixy.qrtoken.negocio.servico.params.StringWrapperParam;
import com.avixy.qrtoken.negocio.servico.params.template.TemplateParam;
import com.avixy.qrtoken.negocio.servico.params.template.TemplateSlotParam;
import com.avixy.qrtoken.negocio.servico.operations.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.template.Template;
import com.avixy.qrtoken.negocio.template.TemplateSize;
import com.google.inject.Inject;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created on 03/03/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class UpdateTemplateService extends AbstractService implements TimestampAble, AesCrypted, HmacAble {
    private TemplateSlotParam templateSlotParam;
    private TemplateParam templateParam;
    private RandomGenerator paddingGenerator;

    @Inject
    public UpdateTemplateService(HeaderPolicy headerPolicy, TimestampPolicy timestampPolicy, AesCryptedMessagePolicy messagePolicy, HmacKeyPolicy hmacKeyPolicy, RandomGenerator paddingGenerator) {
        super(headerPolicy);
        this.timestampPolicy = timestampPolicy;
        this.messagePolicy = messagePolicy;
        this.hmacKeyPolicy = hmacKeyPolicy;
        this.paddingGenerator = paddingGenerator;
    }

    @Override
    public String getServiceName() {
        return "Atualizar Aplicações";
    }

    @Override
    public ServiceCode getServiceCode() {
        return ServiceCode.SERVICE_HMAC_TEMPLATE_UPDATE;
    }

    @Override
    public byte[] getMessage() {
        int size;
        switch (TemplateSize.getTemplateSizeFor(templateSlotParam.getValue())) {
            case SHORT:
                size = TemplateSize.SHORT.getSize();
                break;
            default:
                throw new RuntimeException("Unrecognized TemplateSize");
        }
        byte[] random = new byte[size];
        paddingGenerator.nextBytes(random);
        String msg = StringUtils.rightPad(templateSlotParam.toBinaryString() + templateParam.toBinaryString(), size * 8, new StringWrapperParam(new String(random, Charset.forName("ISO-8859-1"))).toBinaryString());
        return BinaryMsg.get(msg);
    }

    public void setTemplateSlot(byte slot) {
        this.templateSlotParam = new TemplateSlotParam(slot);
    }

    public void setTemplate(Template template) {
        this.templateParam = new TemplateParam(template);
    }

    @Override
    public void setHmacKey(byte[] key) {
        hmacKeyPolicy.setKey(key);
    }

    @Override
    public void setTimestamp(Date date) {
        timestampPolicy.setDate(date);
    }

    @Override
    public void setAesKey(byte[] key) {
        ((AesCryptedMessagePolicy) messagePolicy).setKey(key);
    }
}
