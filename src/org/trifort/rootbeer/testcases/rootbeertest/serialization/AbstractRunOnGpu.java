/*
 * Copyright 2013 Phil Pratt-Szeliga and other contributors
 * http://chirrup.org/
 *
 * See the file LICENSE for copying permission.
 */

package org.trifort.rootbeer.testcases.rootbeertest.serialization;

import org.trifort.rootbeer.runtime.Kernel;

public class AbstractRunOnGpu implements Kernel {

  private int m_result;
  private int m_result2;

  public void gpuMethod() {
    AbstractTestBaseClass base_class = new AbstractTestDerivedClass();
    AbstractTestBaseClass base_class2 = new AbstractTestDerivedClass2();
    m_result = base_class.add(10, 10);
    m_result2 = base_class2.add(10, 10);
  }

  public boolean compare(AbstractRunOnGpu rhs) {
    if(m_result != rhs.m_result){
      System.out.println("m_result: " + m_result + " != " + rhs.m_result);
      return false;
    }
    if(m_result2 != rhs.m_result2){
      System.out.println("m_result2: " + m_result2 + " != " + rhs.m_result2);
      return false;
    }
    System.out.println("m_result: " + m_result + " == " + rhs.m_result);
    System.out.println("m_result2: " + m_result2 + " == " + rhs.m_result2);
    return true;
  }
}
