/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.cloud.demo.oe.sessions;

import com.oracle.cloud.demo.oe.entities.ProductInformation;
import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface ProductInformationFacade extends AbstractFacade<ProductInformation> {

}
