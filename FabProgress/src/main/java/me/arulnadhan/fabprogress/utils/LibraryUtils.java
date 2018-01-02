
package me.arulnadhan.fabprogress.utils;

import android.view.View;

/**
 * @author Jorge Castillo Pérez
 */
public class LibraryUtils {

  public static boolean isAFutureSimpleFAB(View view) {
    return view.getClass().getCanonicalName().startsWith("com.getbase");
  }
}
