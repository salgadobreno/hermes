package com.avixy.qrtoken.negocio.servico;

/***
 * Service codes as they are set up in Token Firmware
 *
 * Created on 29/01/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public enum ServiceCode {
    SERVICE_EMPTY,
    SERVICE_WHOIS,
    SERVICE_STORE_USER_INFO,
    SERVICE_HMAC_TEMPLATE_MESSAGE,
    SERVICE_HMAC_TEMPLATE_MESSAGE_WITHOUT_PIN,
    SERVICE_HMAC_TEMPLATE_MESSAGE_RANGED_RTC,
    SERVICE_HMAC_TEMPLATE_MESSAGE_RANGED_RTC_WITHOUT_PIN,
    SERVICE_HMAC_FORMATTED_MESSAGE,
    SERVICE_HMAC_FORMATTED_MESSAGE_WITHOUT_PIN,
    SERVICE_HMAC_FORMATTED_MESSAGE_RANGED_RTC,
    SERVICE_HMAC_FORMATTED_MESSAGE_RANGED_RTC_WITHOUT_PIN,
    SERVICE_SESSION_KEY,
    SERVICE_SESSION_KEY_WITHOUT_PIN,
    SERVICE_HMAC_TEMPLATE_UPDATE,
    SERVICE_SIGNED_TEMPLATE_MESSAGE,
    SERVICE_SIGNED_FORMATTED_MESSAGE,
    SERVICE_SIGNED_TEMPLATE_UPDATE,
    SERVICE_DIGITAL_SIGNATURE,
    SERVICE_GENERATE_KTAMPER,
    SERVICE_ERASE_KTAMPER,
    SERVICE_UPDATE_PIN,
    SERVICE_OVERRIDE_PIN,
    SERVICE_UPDATE_PUK,
    SERVICE_IMPORT_AVIXY_SYM_KEYSET,
    SERVICE_UPDATE_AVIXY_SYM_KEYSET,
    SERVICE_IMPORT_CLIENT_SYM_KEYSET,
    SERVICE_UPDATE_CLIENT_SYM_KEY,
    SERVICE_DELETE_CLIENT_SYM_KEYSET,
    SERVICE_CREATE_AND_EXPORT_ASY_KEY,
    SERVICE_EXPORT_ASY_KEY,
    SERVICE_IMPORT_DIGITAL_CERTIFICATE,
    SERVICE_DELETE_DIGITAL_CERTIFICATE,
    SERVICE_SHOW_DEVICES_DIGITAL_CERTIFICATE,
    SERVICE_SHOW_EXTERNAL_DIGITAL_CERTIFICATE,
    SERVICE_ASY_SIGNATURE_TEST,
    SERVICE_ASY_DECRYPT_TEST,
    SERVICE_IMPORT_CLIENT_CERTIFICATE,
    SERVICE_HMAC_RTC_UPDATE,
    SERVICE_SIGNED_RTC_UPDATE,
    SERVICE_SHOW_USAGE_STATISTICS,
    SERVICE_SHOW_LOG,
    SERVICE_SHOW_LOG_PARAMS,
    SERVICE_HMAC_FIRMWARE_UPDATE,
    SERVICE_SIGNED_FIRMWARE_UPDATE,
    SERVICE_LANGUAGE_SELECTION,
    SERVICE_FIRMWARE_SYM_UPDATE,
}
