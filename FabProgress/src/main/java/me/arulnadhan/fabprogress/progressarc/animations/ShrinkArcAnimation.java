
package me.arulnadhan.fabprogress.progressarc.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;


public class ShrinkArcAnimation implements ArcAnimation {

  private ValueAnimator shrinkAnim;

  ShrinkArcAnimation(ValueAnimator.AnimatorUpdateListener updateListener,
      Animator.AnimatorListener listener) {

    shrinkAnim = ValueAnimator.ofFloat(ArcAnimationFactory.MAXIMUM_SWEEP_ANGLE,
        ArcAnimationFactory.MINIMUM_SWEEP_ANGLE);
    shrinkAnim.setInterpolator(new DecelerateInterpolator());
    shrinkAnim.setDuration(ArcAnimationFactory.SWEEP_ANIM_DURATION);
    shrinkAnim.addUpdateListener(updateListener);
    shrinkAnim.addListener(listener);
  }

  @Override public ValueAnimator getAnimator() {
    return shrinkAnim;
  }
}
