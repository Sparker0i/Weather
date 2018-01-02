
package me.arulnadhan.fabprogress.progressarc.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;

public class ArcAnimationFactory {

  public enum Type {
    ROTATE, GROW, SHRINK, COMPLETE
  }

  public static final int MINIMUM_SWEEP_ANGLE = 20;
  public static final int MAXIMUM_SWEEP_ANGLE = 300;
  public static final int ROTATE_ANIMATOR_DURATION = 2000;

  public static final int SWEEP_ANIM_DURATION = 1000;
  public static final int COMPLETE_ANIM_DURATION = SWEEP_ANIM_DURATION * 2;
  public static final int COMPLETE_ROTATE_DURATION = COMPLETE_ANIM_DURATION * 6;

  public ValueAnimator buildAnimation(Type type,
      ValueAnimator.AnimatorUpdateListener updateListener,
      Animator.AnimatorListener animatorListener) {

    ArcAnimation arcAnimation;
    switch (type) {
      case ROTATE:
        arcAnimation = new RotateArcAnimation(updateListener);
        break;
      case GROW:
        arcAnimation = new GrowArcAnimation(updateListener, animatorListener);
        break;
      case SHRINK:
        arcAnimation = new ShrinkArcAnimation(updateListener, animatorListener);
        break;
      default:
        arcAnimation = new CompleteArcAnimation(updateListener, animatorListener);
    }

    return arcAnimation.getAnimator();
  }
}
