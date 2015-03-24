package com.avixy.qrtoken.negocio.template;

/**
 * Created on 19/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public enum TemplateFunction {
    TEMPLATE_FUNCTION_STRIPE, 			/**< 0000 - Faixa */
    TEMPLATE_FUNCTION_TEXT, 			/**< 0001 - Texto */
    TEMPLATE_FUNCTION_0010,			 	/**< 0010 - Ex-Texto piscante */
    TEMPLATE_FUNCTION_WAIT_FOR_BUTTON, 	/**< 0011 - Aguarde pressionamento do botÃ£o */
    TEMPLATE_FUNCTION_RECTANGLE, 		/**< 0100 - Retangulo */
    TEMPLATE_FUNCTION_HEADER, 			/**< 0101 - Cabeçalho */
    TEMPLATE_FUNCTION_FOOTER, 			/**< 0110 - Rodapé */
    TEMPLATE_FUNCTION_0111, 			/**< 0111 - Reservado */
    TEMPLATE_FUNCTION_1000, 			/**< 1000 - Reservado */
    TEMPLATE_FUNCTION_1001, 			/**< 1001 - Reservado */
    TEMPLATE_FUNCTION_1010, 			/**< 1010 - Reservado */
    TEMPLATE_FUNCTION_1011, 			/**< 1011 - Reservado */
    TEMPLATE_FUNCTION_1100, 			/**< 1100 - Reservado */
    TEMPLATE_FUNCTION_1101, 			/**< 1101 - Reservado */
    TEMPLATE_FUNCTION_1110, 			/**< 1110 - Reservado */
    TEMPLATE_FUNCTION_EOM;				/**< 1111 - Fim da mensagem */

    public String toBinaryString(){
        /*
            & 0xFF preserva os 8 bits do byte sem transformar em número negativo caso o primeiro bit esteja ligado
            + 0x10 liga o quinto bit para que o toBinaryString não perca os zero à esquerda, e remove o quinto bit com o substring.
         */
        return Integer.toBinaryString((ordinal() & 0xFF) + 0x10).substring(1);
    }
}
