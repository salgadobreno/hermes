package com.avixy.qrtoken.negocio.servico.params;

import java.util.Date;
import java.util.TimeZone;

/**
 * Util para encapsular algum objeto/valor em uma representação de <code>Param</code>.
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 15/09/2014
 */
public class ParamFactory {

    private ParamFactory() {}

    public static StringWrapperParam getParam(String s){
        return new StringWrapperParam(s);
    }

    public static ByteWrapperParam getParam(Byte b){
        return new ByteWrapperParam(b);
    }

    public static TimestampParam getParam(Date d){
        return new TimestampParam(d);
    }

    public static TimeZoneParam  getParam(TimeZone t){
        return new TimeZoneParam(t);
    }

}
