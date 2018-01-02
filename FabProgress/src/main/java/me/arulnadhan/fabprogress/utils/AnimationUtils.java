
package me.arulnadhan.fabprogress.utils;

import android.animation.ValueAnimator;

public class AnimationUtils {

  public static final int SHOW_SCALE_ANIM_DELAY = 150;

  public static float getAnimatedFraction(ValueAnimator animator) {
    float fraction = ((float) animator.getCurrentPlayTime()) / animator.getDuration();
    fraction = Math.min(fraction, 1f);
    fraction = animator.getInterpolator().getInterpolation(fraction);
    return fraction;
  }
}
