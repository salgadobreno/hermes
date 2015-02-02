package com.avixy.qrtoken.negocio.servico;

/**
 * Created on 29/01/2015
 *
 * @author I7
 */
// from token firmware
public enum ServiceCode {
    SERVICE_EMPTY,										/**< 00000000 - Vazio para sempre */
    SERVICE_PING,										/**< 00000001 - Ping: mostra data e hora, fuso, versão de HW, FW,SN lógico */
    SERVICE_STORE_USER_INFO,							/**< 00000010 - Serviço para armazenar as informa??es do usuário*/
    SERVICE_00000011,									/**< 00000011 - */
    SERVICE_00000100,									/**< 00000100 - */
    SERVICE_00000101,									/**< 00000101 - */
    SERVICE_00000110,									/**< 00000110 - */
    SERVICE_00000111,									/**< 00000111 - */
    SERVICE_00001000,									/**< 00001000 - */
    SERVICE_00001001,									/**< 00001001 - */
    SERVICE_HMAC_TEMPLATE_MESSAGE,						/**< 00001010 - Mensagem segura (cifrada, com PIN) com um template */
    SERVICE_SIGNED_TEMPLATE_MESSAGE,					/**< 00001011 - Mensagem segura (cifrada, com PIN) com um template */
    SERVICE_00001100,									/**< 00001100 - */
    SERVICE_HMAC_FORMATTED_MESSAGE,						/**< 00001101 - Mensagem segura (cifrada, com PIN) com formatação */
    SERVICE_SIGNED_FORMATTED_MESSAGE,					/**< 00001110 - Mensagem segura (cifrada, com PIN) com formatação */
    SERVICE_00001111,									/**< 00001111 - */
    SERVICE_DIGITAL_SIGNATURE,							/**< 00010000 - Assinatura digital */
    SERVICE_SESSION_KEY_DECRYPT,						/**< 00010001 - Chave de sessão, encriptada com chave assimétrica */
    SERVICE_00010010,									/**< 00010010 -   */
    SERVICE_00010011,									/**< 00010011 -   */
    SERVICE_GENERATE_KTAMPER,							/**< 00010100 - Crie K_Tamper */
    SERVICE_ERASE_KTAMPER,								/**< 00010101 - Apague K_Tamper */
    SERVICE_STORE_PIN,									/**< 00010110 - Armazene o PIN */
    SERVICE_UPDATE_PIN,									/**< 00010111 - Alterar o PIN */
    SERVICE_OVERRIDE_PIN,								/**< 00011000 - Novo PIN */
    SERVICE_STORE_PUK,									/**< 00011001 - Armazene o PUK */
    SERVICE_UPDATE_PUK,									/**< 00011010 - Alterar o PUK */
    SERVICE_00011011,									/**< 00011011 -  */
    SERVICE_00011100,									/**< 00011100 -  */
    SERVICE_00011101,									/**< 00011101 -   */
    SERVICE_ONE_STEP_ENCRYPTED_SYM_KEY_IMPORT,			/**< 00011110 - Importação de chave simétrica em 1 passo - canal inseguro */
    SERVICE_ONE_STEP_ENCRYPTED_DOUBLE_SYM_KEY_IMPORT,	/**< 00011111 - Importação de chave simétrica em 1 passo - canal inseguro - Modelo BB code */
    SERVICE_ONE_STEP_CLEARTEXT_AVIXY_SYM_KEY_IMPORT,			/**< 00100000 - Importação de chave simétrica em 1 passo - canal seguro */
    SERVICE_ONE_STEP_CLEARTEXT_CLIENT_SYM_KEY_IMPORT,			/**< 00100000 - Importação de chave simétrica em 1 passo - canal seguro */
    SERVICE_ONE_STEP_CLEARTEXT_DOUBLE_SYM_KEY_IMPORT,	/**< 00100001 - Importação de chave simétrica em 1 passo - canal seguro - Modelo BB code */
    SERVICE_TWO_STEP_SYM_KEY_IMPORT,					/**< 00100010 - Importação de chave simétrica em 2 passos */
    SERVICE_TWO_STEP_DOUBLE_SYM_KEY_IMPORT,				/**< 00100011 - Importação de chave simétrica em 2 passos - modelo BB Code */
    SERVICE_DELETE_SYM_KEY_AVIXY,								/**< 00100100 - Deleção de chave simétrica */
    SERVICE_DELETE_SYM_KEY_CLIENT,
    SERVICE_UPDATE_AVIXY_SYM_KEY,								/**< 00100101 - Troca de chave simétrica */
    SERVICE_UPDATE_CLIENT_SYM_KEY,
    SERVICE_CREATE_AND_EXPORT_SYM_KEY,					/**< 00100110 - Criação de chave simétrica e exportação em claro */
    SERVICE_EXPORT_SYM_KEY,								/**< 00100111 - Exportação de chave simétrica em claro */
    SERVICE_00101000,									/**< 00101000 -   */
    SERVICE_00101001,									/**< 00101001 -   */
    SERVICE_CREATE_AND_EXPORT_ASY_KEY,					/**< 00101010 - Criação de par de chaves com exportação de K_Pub */
    SERVICE_EXPORT_ASY_KEY,								/**< 00101011 - Exportação de K_Pub */
    SERVICE_IMPORT_DIGITAL_CERTIFICATE,					/**< 00101100 - Importação de Certificado Digital, cuja K_Priv é do token */
    SERVICE_DELETE_DIGITAL_CERTIFICATE,					/**< 00101101 - Deleção de Certificado Digital, cuja K_Priv é do token */
    SERVICE_SHOW_DEVICES_DIGITAL_CERTIFICATE,			/**< 00101110 - Exibição dos certificados de chaves que pertencem ao token */
    SERVICE_SHOW_EXTERNAL_DIGITAL_CERTIFICATE,			/**< 00101111 - Exibição dos certificados externos armazenados no token */
    SERVICE_ASY_SIGNATURE_TEST,							/**< 00110000 - Teste de chaves assimétricas - assinatura */
    SERVICE_ASY_DECRYPT_TEST,							/**< 00110001 - Teste de chaves assimétricas - decriptação */
    SERVICE_AVIXY_RTC_SYM_UPDATE,						/**< 00110010 - Atualizar RTC - Avixy com HMAC */
    SERVICE_AVIXY_RTC_ASY_UPDATE,						/**< 00110011 - Atualizar RTC - Avixy com certificado */
    SERVICE_CLIENT_RTC_SYM_UPDATE,						/**< 00110100 - Atualizar RTC - cliente com HMAC */
    SERVICE_CLIENT_RTC_ASY_UPDATE,						/**< 00110101 - Atualizar RTC - cliente com certificado */
    SERVICE_00110110,									/**< 00110110 -   */
    SERVICE_00110111,									/**< 00110111 -   */
    SERVICE_00111000,									/**< 00111000 -   */
    SERVICE_SHOW_LOG,									/**< 00111001 - Exibição do log de operações */
    SERVICE_SHOW_LOG_PARAMS,							/**< 00111010 - Exibição do log de operações - busca com parâmetros */
    SERVICE_IMPORT_CLIENT_CERTIFICATE,					/**< 00111011 - Importação de Certificado Digital do cliente, para atualização de template, assinado pela Avixy */
    SERVICE_TEMPLATE_ASY_UPDATE,						/**< 00111100 - Atualização de template com certificado */
    SERVICE_TEMPLATE_SYM_UPDATE,						/**< 00111101 - Atualização de template com HMAC */
    SERVICE_FIRMWARE_ASY_UPDATE,						/**< 00111110 - Atualização de firmware/bibliotecas do token com certificado */
    SERVICE_FIRMWARE_SYM_UPDATE,						/**< 00111111 - Atualização de firmware/bibliotecas do token com HMAC */
    SERVICE_LANGUAGE_SELECTION,							/**< 01000000 - Selecão de idioma */
    SERVICE_01000001,									/**< 01000001 -   */
}
