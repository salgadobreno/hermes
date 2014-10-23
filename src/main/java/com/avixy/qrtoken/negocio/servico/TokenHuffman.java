package com.avixy.qrtoken.negocio.servico;

import java.util.*;

/**
 * Created on 22/10/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TokenHuffman {
    static String[] dictionary = {
            "a", "b", "B", "c", "C", "D", "d", "e", "E", "F", "f", "G", "g", "h", "H", "i", "I", "J", "j", "K", "k", "l", "L", "M", "m", "n", "N", "O", "o", "p", "P", "q", "Q", "r", "R", "S", "s", "t", "T", "u", "U", "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z", "í", "A", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Banco", "Banco Avixy", "Boleto", "Botão", "Cartão de crédito", "Código", "Confirmação", "Conta corrente", "CPF", "dados", "Digite", "Favorecido", "Poupança", "Pressione o botão", "Transação", "Valor", "Confirme", "Conta", "Data", "Depositado", "Parcelas", "Pressione", "R$ ", "Senha", "Token", "Total", "Transferência", "Verifique", "/02", "/03", "/04", "/05", "/06", "/07", "/08", "/09", "/10", "/11", "/12", "/2013", "/2014", "/2015", "/2016", "/2017", "/2018", "/2019", "/2020", "Abril", "Agosto", "de 2014", "de 2015", "de 2016", "de 2017", "de 2018", "de 2019", "de 2020", "Domingo", "Fevereiro", "Janeiro", "Julho", "Junho", "Maio", "Março", "Novembro", "Outubro", "Quarta-feira", "Quinta-feira", "Sábado", "Segunda-feira", "Setembro", "Sexta-feira", "Terça-feira", "/01", "Á", "á", "À", "à", "Â", "â", "Ã", "ã", "ç", "É", "é", "È", "è", "Ê", "ê", "Í", "í", "Ì", "ì", "Î", "î", "Ó", "ó", "Ò", "ò", "Ô", "ô", "Õ", "õ", "Ú", "ú", "Ù", "ù", "Û", "û", "-", ":", " ", "!", "#", "$", "%", "(", ")", "*", ",", ".", "/", "?", "@", "[", "\'", "\"", "]", "_", "€", "+", "=", "ª", "º", "\n", "|",
//            "arg" //arg é um caso especial
    };
    static String[] codes = {
            "1000011", "1010000", "1000001", "1011100", "1001001", "1010111", "0011111", "1000000", "1001010", "1011001", "0011101", "0100000", "0101000", "0100001", "1000111", "1001111", "1001101", "1011010", "1000010", "1011011", "0011011", "0111100", "1001000", "0101011", "0111001", "1010100", "1011000", "0101100", "0111110", "1000110", "1001011", "0101010", "0111111", "0100111", "1000101", "0110111", "0111101", "0110000", "0110010", "0011110", "0101001", "1010011", "0111000", "0101110", "0101111", "0100010", "0110011", "1011101", "0100011", "0011100", "0111011", "10011001", "1110001010", "0110101", "0011001", "0010000", "0010111", "0011000", "0010001", "0010110", "0010100", "0010101", "0010010", "0000011", "0000010", "0000001", "0001110", "0001111", "0001010", "0000000", "0001101", "0000100", "0001011", "0000101", "0000110", "0000111", "0001100", "0001001", "0001000", "11111011", "11110100", "11110110", "11111100", "11111010", "11111110", "11110101", "11111001", "11111000", "11110111", "11111101", "11111111", "11100011", "11101001", "11011111", "11101110", "11101010", "11011110", "11101000", "11100111", "11011001", "11100100", "11110011", "11010101", "11010000", "11001110", "11101111", "11010110", "11101011", "11011101", "11010100", "11001100", "11010001", "11001011", "11101101", "11001010", "11001001", "11011011", "11110010", "11010111", "11100001", "11001101", "11100110", "11010011", "11000011", "11011010", "11110001", "11010010", "11001000", "11001111", "11100101", "11000101", "11000100", "11110000", "11000001", "11011100", "1101100000", "01101101", "10111110", "10001001", "10100100", "01110100", "01100010", "10011101", "10111100", "01101000", "10101011", "01001000", "10011000", "10101101", "01100011", "11000000", "01001100", "01011010", "01001001", "10100101", "01011011", "01001010", "10111101", "01001101", "10101100", "01101001", "10100011", "10111111", "10011100", "00110101", "10101010", "01101100", "01110101", "10001000", "01001011", "10100010", "0010011", "11100000", "1110001011", "1101100010", "1100001010", "1101100011", "1100011011", "1100011100", "1100011000", "1100001001", "1110110001", "1110001001", "1110110011", "1101100001", "0011010011", "1110001000", "1100011110", "1100001011", "0011010000", "1100011101", "0011010001", "1110110010", "1110110000", "1100011001", "1100011111", "1100011010", "1100001000",
//            "0011010010", // arg é um caso especial
    };
    static Map<String, String> dictionaryCode = new HashMap<>();
    static SortedSet<String> alphabetSortedDict = new TreeSet<>();
    static Comparator<String> inverseLengthComparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            if (o1.length() == o2.length()){
                return 0;
            } else if (o1.length() > o2.length()){
                return -1;
            } else {
                return 1;
            }
        }
    };

    static {
        for (int i = 0; i < dictionary.length; i++) {
            String dict = dictionary[i];
            String code = codes[i];
            dictionaryCode.put(dict, code);
        }

        alphabetSortedDict.addAll(dictionaryCode.keySet());
    }

    public static String encode(String text){
//        System.out.println("text = " + text);
        String encoded = "";
        String encodedText = "";
        //varrendo o input
        for (int i = 0; i < text.length(); i++) {
//            System.out.println("i = " + i);
            char c = text.charAt(i);
//            System.out.println("char = " + c);
            //varrer o set pra pegar os elementos que iniciam com `c`
            SortedSet<String> lengthSortedDict = new TreeSet<>(inverseLengthComparator);
            for (String s : alphabetSortedDict) {
                if (s.charAt(0) == c) {
//                    System.out.println("Dict matching = " + s);
                    //o elemento começa com `c`, vai pro inverseLengthSortedDict
                    lengthSortedDict.add(s);
                }
            }
            for (String s : lengthSortedDict) {
                String subText = text.substring(i, i + s.length());
                if (subText.equals(s)) { //match
//                    System.out.println("match = " + subText);
                    encoded += dictionaryCode.get(s);
                    encodedText += s;
                    i += s.length() - 1; // -1 compensa o proximo loop
                    break;
                }
            }
//            System.out.println("encoded = " + encoded);
        }
        return encoded;
    }
}
