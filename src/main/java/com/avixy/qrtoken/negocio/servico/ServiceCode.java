package com.avixy.qrtoken.negocio.servico;

/***
 * Service codes as they are set up in Token Firmware
 *
 * Created on 29/01/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public enum ServiceCode {
    SERVICE_EMPTY, /** 00000000 - Vazio para sempre */
    SERVICE_WHOIS, /** 00000001 - Exibe data e hora, fuso, versão de HW, FW,SN lógico numa tela; User info noutra tela */
    SERVICE_STORE_USER_INFO, /** 00000010 - Armazena informações do usuário no filesystem */
    SERVICE_HMAC_TEMPLATE_MESSAGE, /** 00000011 - Mensagem segura (cifrada, com PIN) com um template */
    SERVICE_HMAC_TEMPLATE_MESSAGE_WITHOUT_PIN, /** 00000100 - Mensagem segura (cifrada) com um template */
    SERVICE_HMAC_FORMATTED_MESSAGE, /** 00000101 - Mensagem segura (cifrada, com PIN) com formatação */
    SERVICE_HMAC_FORMATTED_MESSAGE_WITHOUT_PIN, /** 00000110 - Mensagem segura (cifrada) com formatação */
    SERVICE_SESSION_KEY, /** 00000111 - Chave de sessão, encriptada com chave do Token */
    SERVICE_SESSION_KEY_WITHOUT_PIN, /** 00000111 - Chave de sessão, encriptada com chave do Token */
    SERVICE_HMAC_TEMPLATE_UPDATE, /** 00001000 - Atualização de template com HMAC */
    SERVICE_SIGNED_TEMPLATE_MESSAGE, /** 00001001 - Mensagem segura (cifrada, com PIN) com um template */
    SERVICE_SIGNED_FORMATTED_MESSAGE, /** 00001010 - Mensagem segura (cifrada, com PIN) com formatação */
    SERVICE_SIGNED_TEMPLATE_UPDATE, /** 00001011 - Atualização de template com certificado */
    SERVICE_DIGITAL_SIGNATURE, /** 00001100 - Assinatura digital */
    SERVICE_GENERATE_KTAMPER, /** 00001101 - Crie K_Tamper */
    SERVICE_ERASE_KTAMPER, /** 00001110 - Apague K_Tamper */
    SERVICE_UPDATE_PIN, /** 00001111 - Alterar o PIN */
    SERVICE_OVERRIDE_PIN, /** 00010000 - Novo PIN */
    SERVICE_UPDATE_PUK, /** 00010001 - Alterar o PUK */
    SERVICE_IMPORT_AVIXY_SYM_KEYSET, /** 00010010 - Importação de chave simétrica em da Avixy em ambiente seguro */
    SERVICE_UPDATE_AVIXY_SYM_KEYSET, /** 00010011 - Atualização de chave simétrica em da Avixy, encriptada com a chave atual */
    SERVICE_IMPORT_CLIENT_SYM_KEYSET, /** 00010100 - Importação de chave simétrica do cliente, encriptada com chave de transporte da Avixy */
    SERVICE_UPDATE_CLIENT_SYM_KEY, /** 00010101 - Troca de chave simétrica */
    SERVICE_DELETE_CLIENT_SYM_KEYSET, /** 00010110 - Deleção de chave simétrica */
    SERVICE_CREATE_AND_EXPORT_ASY_KEY, /** 00010111 - Criação de par de chaves com exportação de K_Pub */
    SERVICE_EXPORT_ASY_KEY, /** 00011000 - Exportação de K_Pub */
    SERVICE_IMPORT_DIGITAL_CERTIFICATE, /** 00011001 - Importação de Certificado Digital, cuja K_Priv é do token */
    SERVICE_DELETE_DIGITAL_CERTIFICATE, /** 00011010 - Deleção de Certificado Digital, cuja K_Priv é do token */
    SERVICE_SHOW_DEVICES_DIGITAL_CERTIFICATE, /** 00011011 - Exibição dos certificados de chaves que pertencem ao token */
    SERVICE_SHOW_EXTERNAL_DIGITAL_CERTIFICATE, /** 00011100 - Exibição dos certificados externos armazenados no token */
    SERVICE_ASY_SIGNATURE_TEST, /** 00011101 - Teste de chaves assimétricas - assinatura */
    SERVICE_ASY_DECRYPT_TEST, /** 00011110 - Teste de chaves assimétricas - decriptação */
    SERVICE_IMPORT_CLIENT_CERTIFICATE, /** 00011111 - Importação de Certificado Digital do cliente, para atualização de template, assinado pela Avixy */
    SERVICE_HMAC_RTC_UPDATE, /** 00100000 - Atualizar RTC - Avixy com HMAC */
    SERVICE_SIGNED_RTC_UPDATE, /** 00100001 - Atualizar RTC - Avixy com certificado */
    SERVICE_SHOW_USAGE_STATISTICS, /** 00100010 - Exibição das estatísticas de uso do token */
    SERVICE_SHOW_LOG, /** 00100011 - Exibição do log de operações */
    SERVICE_SHOW_LOG_PARAMS, /** 00100100 - Exibição do log de operações - busca com parâmetros */
    SERVICE_HMAC_FIRMWARE_UPDATE, /** 00100101 - Atualização de firmware/bibliotecas do token com HMAC */
    SERVICE_SIGNED_FIRMWARE_UPDATE, /** 00100110 - Atualização de firmware/bibliotecas do token com certificado */
    SERVICE_LANGUAGE_SELECTION, /** 00100111 - Selecão de idioma */
}
