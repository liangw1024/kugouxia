package com.liangwei.kugouxia.behavior;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * https://blog.csdn.net/qibin0506/article/details/50290421
 * https://blog.csdn.net/yanzhenjie1003/article/details/51941288
 */
public class ScrollUpHideBehavior extends CoordinatorLayout.Behavior {
    //防止could not inflate subclass ex
    public ScrollUpHideBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //返回false表示child不依赖dependency，ture表示依赖
        Log.d("behavior---->","views:"+dependency.getClass().getName());
        boolean isDependen = dependency.getClass().getName().contains("RecyclerView");
        return isDependen;
    }

      //返回true表示child的位置或者是宽高要发生改变，否则就返回false   child就是表示使用behavior的view
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    //箱套滑动开始 关注滑动方向
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        boolean isY = (axes== ViewCompat.SCROLL_AXIS_VERTICAL);
        Log.d("behavior","y滑动"+String.valueOf(isY));
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;//监听y滑动

    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        Log.d("behavior---->","onNestedScroll()");


//        try {
//            RelativeLayout relativeLayout = (RelativeLayout) child;
//            //对ExpandView 进行特殊处理
//            for (int i=0;i<relativeLayout.getChildCount();i++){
//                View view = relativeLayout.getChildAt(i);
//                if(view.getClass().getName().contains("ExpandView")){
//                    ExpandView expandView = (ExpandView) child;
//                    expandView.collapse();
//                }
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }


//       if ((dyConsumed>0&&dyUnconsumed==0)|(dyConsumed == 0 && dyUnconsumed > 0)){
//           child.setVisibility(View.INVISIBLE);
//           Log.d("behavior---->","onNestedScroll()-------》上滑");
//       }
//        if ((dyConsumed < 0 && dyUnconsumed == 0)|(dyConsumed == 0 && dyUnconsumed < 0)){
//            child.setVisibility(View.VISIBLE);
//            Log.d("behavior---->","onNestedScroll()-------》下滑");
//        }

//        if (dyConsumed > 0 && dyUnconsumed == 0) {
//            System.out.println("上滑中。。。");
//        }
//        if (dyConsumed == 0 && dyUnconsumed > 0) {
//            System.out.println("到边界了还在上滑。。。");
//        }
//        if (dyConsumed < 0 && dyUnconsumed == 0) {
//            System.out.println("下滑中。。。");
//        }
//        if (dyConsumed == 0 && dyUnconsumed < 0) {
//            System.out.println("到边界了，还在下滑。。。");
//        }


    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        Log.d("behavior---->","OnNestedPreScroll()");

    }

    //用来监听滑动状态，对象消费滚动距离前回调
    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        Log.d("behavior---->","onNestedPreFling()");
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.d("behavior---->","onNestedFling()---VelocityY;;;;"+velocityY);
        if(velocityY>500){
            child.setVisibility(View.INVISIBLE);
        }else if(velocityY<-1700){
            child.setVisibility(View.VISIBLE);
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    public void showChild(View child){

    }
    public void hideChild(View child){

    }

}
