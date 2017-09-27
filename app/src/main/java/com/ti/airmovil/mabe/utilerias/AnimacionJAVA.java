package com.ti.airmovil.mabe.utilerias;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class AnimacionJAVA {

    public static void rotarXLeft(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ObjectAnimator oa = ObjectAnimator.ofFloat(view, "rotationX", 0, 720);
                oa.setDuration(tiempo);
                oa.start();
            }
        };
        r.run();
    }

    public static void rotarYLeft(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ObjectAnimator oa = ObjectAnimator.ofFloat(view, "rotationY", 0, 360);
                oa.setDuration(1000);
                oa.start();
            }
        };
        r.run();

    }

    public static void rotarXYLeft(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ObjectAnimator oa = ObjectAnimator.ofFloat(view, "rotationY", 0, 360);
                oa.setDuration(3000);

                ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "rotationX", 0, 360);
                oa2.setDuration(3000);

                AnimatorSet as = new AnimatorSet();
                as.playTogether(oa,oa2);
                as.start();
            }
        };
        r.run();
    }


    public static void rotarXRightSplash(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                AnimatorSet set1 = new AnimatorSet();
                set1.play(ObjectAnimator.ofFloat(view, "rotationX", 360, 0));
                set1.setDuration(tiempo);

                AnimatorSet set2 = new AnimatorSet();
                set2.play(ObjectAnimator.ofFloat(view, "rotationX", 360, 0));
                set2.setDuration(tiempo);

                AnimatorSet set = new AnimatorSet();
                set.playSequentially(set1,set2);
                set.start();


                ObjectAnimator oa = ObjectAnimator.ofFloat(view, "rotationX", 360, 0);
                oa.setDuration(1000);
                oa.start();
            }
        };
        r.run();
    }

    public static void rotarXRight(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ObjectAnimator oa = ObjectAnimator.ofFloat(view, "rotationX", 360, 0);
                oa.setDuration(1000);
                oa.start();
            }
        };
        r.run();
    }

    public static void rotarYRight(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ObjectAnimator oa = ObjectAnimator.ofFloat(view, "rotationY", 360, 0);
                oa.setDuration(1000);
                oa.start();
            }
        };
        r.run();
    }

    public static void rotarXYRight(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ObjectAnimator oa = ObjectAnimator.ofFloat(view, "rotationY", 360, 0);
                oa.setDuration(3000);
                ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "rotationX", 360, 0);
                oa2.setDuration(3000);
                AnimatorSet as = new AnimatorSet();
                as.playTogether(oa,oa2);
                as.start();
            }
        };
        r.run();
    }



    public static void desaparecerAnimacion(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                AnimatorSet set1 = new AnimatorSet();
                set1.play(ObjectAnimator.ofFloat(view, "alpha", 1, 0.25f).setDuration(500));
                AnimatorSet set2 = new AnimatorSet();
                set2.play(ObjectAnimator.ofFloat(view, "alpha", 0.25f,1).setDuration(500));
                AnimatorSet set3 = new AnimatorSet();
                set3.playSequentially(set1,set2);
                set3.start();
            }
        };
        r.run();
    }

    public static void aparecerAnimacion(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                AnimatorSet set1 = new AnimatorSet();
                set1.play(ObjectAnimator.ofFloat(view, "alpha", 0.25f, 1).setDuration(500));
                AnimatorSet set2 = new AnimatorSet();
                set2.play(ObjectAnimator.ofFloat(view, "alpha", 1, 0.25f).setDuration(500));
                AnimatorSet set3 = new AnimatorSet();
                set3.playSequentially(set1,set2);
                set3.start();
            }
        };
        r.run();
    }

    public static void littleScaleAnimacion_spinner(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 0.8f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 0.8f);
                scaleDownY.setDuration(500);
                AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);

                ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f);
                ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f);
                scaleDownY.setDuration(500);
                AnimatorSet scaleUp = new AnimatorSet();
                scaleUp.play(scaleUpX).with(scaleUpY);


                AnimatorSet scale = new AnimatorSet();
                scale.playSequentially(scaleDown,scaleUp);
                scale.start();
            }
        };
        r.run();
    }

    public static void bigScaleAnimacion(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f);
                scaleDownY.setDuration(500);
                AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);

                ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 1.7f);
                ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 1.7f);
                scaleDownY.setDuration(500);
                AnimatorSet scaleUp = new AnimatorSet();
                scaleUp.play(scaleUpX).with(scaleUpY);

                ObjectAnimator scaleNormalX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f);
                ObjectAnimator scaleNormalY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f);
                AnimatorSet scaleNormal = new AnimatorSet();
                scaleNormal.play(scaleNormalX).with(scaleNormalY);

                AnimatorSet scale = new AnimatorSet();
                scale.playSequentially(scaleDown,scaleUp,scaleNormal);
                scale.start();
            }
        };
        r.run();
    }

    public static void littleScaleAnimacion(final View view, final int tiempo){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f);
                scaleDownY.setDuration(tiempo);
                AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);

                ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 0.9f);
                ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 0.9f);
                scaleDownY.setDuration(tiempo);
                AnimatorSet scaleUp = new AnimatorSet();
                scaleUp.play(scaleUpX).with(scaleUpY);

                ObjectAnimator scaleNormalX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f);
                ObjectAnimator scaleNormalY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f);
                AnimatorSet scaleNormal = new AnimatorSet();
                scaleNormal.play(scaleNormalX).with(scaleNormalY);

                AnimatorSet scale = new AnimatorSet();
                scale.playSequentially(scaleDown,scaleUp,scaleNormal);
                scale.start();
            }
        };
        r.run();
    }

}
