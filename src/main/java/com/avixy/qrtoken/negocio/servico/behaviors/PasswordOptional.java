package com.avixy.qrtoken.negocio.servico.behaviors;

import com.avixy.qrtoken.negocio.servico.operations.NoPasswordPolicy;

/**
 * Defines a {@link com.avixy.qrtoken.negocio.servico.servicos.Service} in which it's {@link com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy}
 * can be turned on and off.
 * Example implementation:
 * <pre>
 *     {@code
 *          class TestService extends AbstractService implements PinAble, PasswordOptional {
 *             final PasswordPolicy originalPasswordPolicy;
 *             public TestService(HeaderPolicy headerPolicy, PasswordPolicy passwordPolicy){
 *                 super(headerPolicy);
 *                 this.passwordPolicy = passwordPolicy;
 *                 this.originalPasswordPolicy = passwordPolicy;
 *             }
 *             @Override
 *             public void togglePasswordOptional(boolean passwordOptional){
 *                 if(passwordOptional){
 *                     this.password = NO_PASSWORD_POLICY;
 *                 } else {
 *                     this.passwordPolicy = originalPasswordPolicy;
 *                 }
 *             }
 *             @Override
 *             public ServiceCode getServiceCode(){
 *                 if(passwordPolicy == originalPasswordPolicy) {
 *                     return NORMAL_SERVICE_CODE_HERE;
 *                 } else {
 *                     return NO_PIN_SERVICE_CODE_VARIATION_HERE;
 *                 }
 *             }
 *          }
 *     }
 * </pre>
 *
 * Created on 14/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public interface PasswordOptional {
    static final NoPasswordPolicy NO_PASSWORD_POLICY = new NoPasswordPolicy();

    void togglePasswordOptional(boolean passwordOptional);
}
