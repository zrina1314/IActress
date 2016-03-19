package com.sf.iactress.ui.widget.dialog.effects;

public class Effectstype {
    public static final int FADEIN = 1;
    public static final int SLIDE_LEFT = 2;
    public static final int SLIDE_TOP = 3;
    public static final int SLIDE_BOTTOM = 4;
    public static final int SLIDE_RIGHT = 5;
    public static final int FALL = 6;
    public static final int NEWS_PAGER = 7;
    public static final int FLIP_H = 8;
    public static final int FLIP_V = 9;
    public static final int ROTATE_BOTTOM = 10;
    public static final int ROTATE_LEFT = 11;
    public static final int SLIT = 12;
    public static final int SHAKE = 13;
    public static final int SIDEFALL = 14;

    public static BaseEffects getAnimator(int effectstype) {
        BaseEffects bEffects = null;
        Class<? extends BaseEffects> effectsClazz = null;
        try {
            switch (effectstype) {
                case FADEIN:
                    effectsClazz = FadeIn.class;
                    break;
                case SLIDE_LEFT:
                    effectsClazz = SlideLeft.class;
                    break;
                case SLIDE_TOP:
                    effectsClazz = SlideTop.class;
                    break;
                case SLIDE_BOTTOM:
                    effectsClazz = SlideBottom.class;
                    break;
                case SLIDE_RIGHT:
                    effectsClazz = SlideRight.class;
                    break;
                case FALL:
                    effectsClazz = Fall.class;
                    break;
                case NEWS_PAGER:
                    effectsClazz = NewsPaper.class;
                    break;
                case FLIP_H:
                    effectsClazz = FlipH.class;
                    break;
                case FLIP_V:
                    effectsClazz = FlipV.class;
                    break;
                case ROTATE_BOTTOM:
                    effectsClazz = RotateBottom.class;
                    break;
                case ROTATE_LEFT:
                    effectsClazz = RotateLeft.class;
                    break;
                case SLIT:
                    effectsClazz = Slit.class;
                    break;
                case SHAKE:
                    effectsClazz = Shake.class;
                    break;
                case SIDEFALL:
                    effectsClazz = SideFall.class;
                    break;
            }
            if (effectsClazz != null)
                bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (InstantiationException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (IllegalAccessException e) {
            throw new Error("Can not init animatorClazz instance");
        }
        return bEffects;
    }
}

