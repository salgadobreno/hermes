package com.avixy.qrtoken.negocio.servico.operations;

public class NoPasswordPolicy extends PasswordPolicy {
//    static final Logger logger = LoggerFactory.getLogger(PasswordPolicy.class);
    public byte[] get() {
        return new byte[0];
    }

    @Override
    public void setPassword(String password) {
//        logger.info("setPassword() called on " + this.getClass().getName());
    }
}
