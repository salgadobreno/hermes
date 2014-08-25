package com.avixy.qrtoken.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 25/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class HermesLogger {
    private static Logger log = LoggerFactory.getLogger("Hermes");
    public static Logger get(){
        return log;
    }
}
