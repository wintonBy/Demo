package com.winton.demo.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: winton
 * @time: 2018/8/23 17:41
 * @package: com.winton.demo.widget
 * @project: Demo
 * @mail:
 * @describe: 一句话描述
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    /**
     * 指定哪些View响应变化
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    /**
     * 当View变化时，执行哪些操作
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    /**
     * 来筛选响应哪种滑动，有垂直方向 和水平方向的滑动
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }
}
