/*
 * Copyright 2012 Phil Pratt-Szeliga and other contributors
 * http://chirrup.org/
 *
 * See the file LICENSE for copying permission.
 */

package org.trifort.rootbeer.testcases.rootbeertest.serialization;

import org.trifort.rootbeer.runtime.Kernel;

public class ShortArraysRunOnGpu implements Kernel {

  private short element;
  private short[] element1;
  private short[][] element2;
  private short[][][] element3;

  public ShortArraysRunOnGpu(){
    element = 10;
    element1 = new short[10];
    element2 = new short[10][10];
    element3 = new short[10][10][10];

    for(int m = 0; m < 10; m++){
      for(int n = 0; n < 10; n++){
        for(int p = 0; p < 10; ++p){
          element3[m][n][p] = (short) p;
          element2[n][p] = (short) p;
          element1[p] = (short) p;
        }
      }
    }
  }

  public void gpuMethod() {
    element++;
    element1[0]++;
    element2[0][0]++;
    element3[0][0][0]++;
  }

  boolean compare(ShortArraysRunOnGpu brhs) {

    if(element != brhs.element){
      return false;
    }

    for(int m = 0; m < 10; m++){
      for(int n = 0; n < 10; n++){
        for(int p = 0; p < 10; ++p){
          if(element1[p] != brhs.element1[p])
            return false;
          if(element2[n][p] != brhs.element2[n][p])
            return false;
          if(element3[m][n][p] != brhs.element3[m][n][p])
            return false;
        }
      }
    }

    return true;
  }
}
