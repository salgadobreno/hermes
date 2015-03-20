package com.avixy.qrtoken.negocio.servico;

/**
 * Created on 29/01/2015
 *
 * @author I7
 */
// from token firmware
public enum ServiceCode {
    SERVICE_EMPTY,										/**< Vazio para sempre */
    SERVICE_WHOIS,										/**< Ping: mostra data e hora, fuso, versão de HW, FW,SN lógico */
    SERVICE_STORE_USER_INFO,							/**< Serviço para armazenar as informa??es do usuário*/
    SERVICE_HMAC_TEMPLATE_MESSAGE,						/**< Mensagem segura (cifrada, com PIN) com um template */
    SERVICE_SIGNED_TEMPLATE_MESSAGE,					/**< Mensagem segura (cifrada, com PIN) com um template */
    SERVICE_HMAC_FORMATTED_MESSAGE,						/**< Mensagem segura (cifrada, com PIN) com formatação */
    SERVICE_SIGNED_FORMATTED_MESSAGE,					/**< Mensagem segura (cifrada, com PIN) com formatação */
    SERVICE_DIGITAL_SIGNATURE,							/**< Assinatura digital */
    SERVICE_SESSION_KEY_DECRYPT,						/**< Chave de sessão, encriptada com chave assimétrica */
    SERVICE_GENERATE_KTAMPER,							/**< Crie K_Tamper */
    SERVICE_ERASE_KTAMPER,								/**< Apague K_Tamper */
    SERVICE_STORE_PIN,									/**< Armazene o PIN */
    SERVICE_UPDATE_PIN,									/**< Alterar o PIN */
    SERVICE_OVERRIDE_PIN,								/**< Novo PIN */
    SERVICE_STORE_PUK,									/**< Armazene o PUK */
    SERVICE_UPDATE_PUK,									/**< Alterar o PUK */
    SERVICE_ONE_STEP_ENCRYPTED_SYM_KEY_IMPORT,			/**< Importação de chave simétrica em 1 passo - canal inseguro */
    SERVICE_ONE_STEP_ENCRYPTED_DOUBLE_SYM_KEY_IMPORT,	/**< Importação de chave simétrica em 1 passo - canal inseguro - Modelo BB code */
    SERVICE_ONE_STEP_CLEARTEXT_AVIXY_SYM_KEY_IMPORT,	/**< Importação de chave simétrica em 1 passo - canal seguro */
    SERVICE_ONE_STEP_CLEARTEXT_CLIENT_SYM_KEY_IMPORT,	/**< Importação de chave simétrica em 1 passo - canal seguro */
    SERVICE_TWO_STEP_AVIXY_SYM_KEY_IMPORT,				/**< Importação de chave simétrica em 2 passos */
    SERVICE_TWO_STEP_CLIENT_SYM_KEY_IMPORT,				/**< Importação de chave simétrica em 2 passos */
    SERVICE_DELETE_SYM_KEY_AVIXY,						/**< Deleção de chave simétrica */
    SERVICE_DELETE_SYM_KEY_CLIENT,						/**< Deleção de chave simétrica */
    SERVICE_UPDATE_AVIXY_SYM_KEY,						/**< Troca de chave simétrica */
    SERVICE_UPDATE_CLIENT_SYM_KEY,						/**< Troca de chave simétrica */
    SERVICE_CREATE_AND_EXPORT_SYM_KEY,					/**< Criação de chave simétrica e exportação em claro */
    SERVICE_EXPORT_SYM_KEY,								/**< Exportação de chave simétrica em claro */
    SERVICE_CREATE_AND_EXPORT_ASY_KEY,					/**< Criação de par de chaves com exportação de K_Pub */
    SERVICE_EXPORT_ASY_KEY,								/**< Exportação de K_Pub */
    SERVICE_IMPORT_DIGITAL_CERTIFICATE,					/**< Importação de Certificado Digital, cuja K_Priv é do token */
    SERVICE_DELETE_DIGITAL_CERTIFICATE,					/**< Deleção de Certificado Digital, cuja K_Priv é do token */
    SERVICE_SHOW_DEVICES_DIGITAL_CERTIFICATE,			/**< Exibição dos certificados de chaves que pertencem ao token */
    SERVICE_SHOW_EXTERNAL_DIGITAL_CERTIFICATE,			/**< Exibição dos certificados externos armazenados no token */
    SERVICE_ASY_SIGNATURE_TEST,							/**< Teste de chaves assimétricas - assinatura */
    SERVICE_ASY_DECRYPT_TEST,							/**< Teste de chaves assimétricas - decriptação */
    SERVICE_AVIXY_RTC_SYM_UPDATE,						/**< Atualizar RTC - Avixy com HMAC */
    SERVICE_AVIXY_RTC_ASY_UPDATE,						/**< Atualizar RTC - Avixy com certificado */
    SERVICE_CLIENT_RTC_SYM_UPDATE,						/**< Atualizar RTC - cliente com HMAC */
    SERVICE_CLIENT_RTC_ASY_UPDATE,						/**< Atualizar RTC - cliente com certificado */
    SERVICE_SHOW_USAGE_STATISTICS,						/**< Exibe as estatísticas de uso do token */
    SERVICE_SHOW_LOG,									/**< Exibição do log de operações */
    SERVICE_SHOW_LOG_PARAMS,							/**< Exibição do log de operações - busca com parâmetros */
    SERVICE_IMPORT_CLIENT_CERTIFICATE,					/**< Importação de Certificado Digital do cliente, para atualização de template, assinado pela Avixy */
    SERVICE_TEMPLATE_ASY_UPDATE,						/**< Atualização de template com certificado */
    SERVICE_TEMPLATE_SYM_UPDATE,						/**< Atualização de template com HMAC */
    SERVICE_FIRMWARE_ASY_UPDATE,						/**< Atualização de firmware/bibliotecas do token com certificado */
    SERVICE_FIRMWARE_SYM_UPDATE,						/**< Atualização de firmware/bibliotecas do token com HMAC */
    SERVICE_LANGUAGE_SELECTION,							/**< Selecão de idioma */
}
