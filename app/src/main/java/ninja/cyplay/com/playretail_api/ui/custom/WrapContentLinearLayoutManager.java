package ninja.cyplay.com.playretail_api.ui.custom;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class WrapContentLinearLayoutManager extends android.support.v7.widget.LinearLayoutManager {

    private static final int CHILD_WIDTH = 0;
    private static final int CHILD_HEIGHT = 1;
    private static final int DEFAULT_CHILD_SIZE = 100;

    private final int[] childDimensions = new int[2];
    private final RecyclerView view;

    private int childSize = DEFAULT_CHILD_SIZE;
    private boolean hasChildSize;
    private int overScrollMode = ViewCompat.OVER_SCROLL_ALWAYS;

    @SuppressWarnings("UnusedDeclaration")
    public WrapContentLinearLayoutManager(Context context) {
        super(context);
        this.view = null;
    }

    @SuppressWarnings("UnusedDeclaration")
    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.view = null;
    }

    @SuppressWarnings("UnusedDeclaration")
    public WrapContentLinearLayoutManager(RecyclerView view) {
        super(view.getContext());
        this.view = view;
        this.overScrollMode = ViewCompat.getOverScrollMode(view);
    }

    @SuppressWarnings("UnusedDeclaration")
    public WrapContentLinearLayoutManager(RecyclerView view, int orientation, boolean reverseLayout) {
        super(view.getContext(), orientation, reverseLayout);
        this.view = view;
        this.overScrollMode = ViewCompat.getOverScrollMode(view);
    }

    public void setOverScrollMode(int overScrollMode) {
        if (overScrollMode < ViewCompat.OVER_SCROLL_ALWAYS || overScrollMode > ViewCompat.OVER_SCROLL_NEVER)
            throw new IllegalArgumentException("Unknown overscroll mode: " + overScrollMode);
        if (this.view == null) throw new IllegalStateException("view == null");
        this.overScrollMode = overScrollMode;
        ViewCompat.setOverScrollMode(view, overScrollMode);
    }

    public static int makeUnspecifiedSpec() {
        return View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);

        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);

        final boolean exactWidth = widthMode == View.MeasureSpec.EXACTLY;
        final boolean exactHeight = heightMode == View.MeasureSpec.EXACTLY;

        final int unspecified = makeUnspecifiedSpec();

        if (exactWidth && exactHeight) {
            // in case of exact calculations for both dimensions let's use response "onMeasure" implementation
            super.onMeasure(recycler, state, widthSpec, heightSpec);
            return;
        }

        final boolean vertical = getOrientation() == VERTICAL;

        initChildDimensions(widthSize, heightSize, vertical);

        int width = 0;
        int height = 0;

        // it's possible to get scrap views in recycler which are bound to old (invalid) adapter entities. This
        // happens because their invalidation happens after "onMeasure" method. As a workaround let's clear the
        // recycler now (it should not cause any performance issues while scrolling as "onMeasure" is never
        // called whiles scrolling)
        recycler.clear();

        final int stateItemCount = state.getItemCount();
        final int adapterItemCount = getItemCount();
        // adapter always contains actual data while state might contain old data (f.e. data before the animation is
        // done). As we want to measure the view with actual data we must use data from the adapter and not from  the
        // state
        for (int i = 0; i < adapterItemCount; i++) {
            if (vertical) {
                if (!hasChildSize) {
                    if (i < stateItemCount) {
                        // we should not exceed state count, otherwise we'll get IndexOutOfBoundsException. For such items
                        // we will use previously calculated dimensions
                        measureChild(recycler, i, widthSpec, unspecified, childDimensions);
                    } else {
                        logMeasureWarning(i);
                    }
                }
                height += childDimensions[CHILD_HEIGHT];
                if (i == 0) {
                    width = childDimensions[CHILD_WIDTH];
                }
                if (height >= heightSize) {
                    break;
                }
            } else {
                if (!hasChildSize) {
                    if (i < stateItemCount) {
                        // we should not exceed state count, otherwise we'll get IndexOutOfBoundsException. For such items
                        // we will use previously calculated dimensions
                        measureChild(recycler, i, unspecified, heightSpec, childDimensions);
                    } else {
                        logMeasureWarning(i);
                    }
                }
                width += childDimensions[CHILD_WIDTH];
                if (i == 0) {
                    height = childDimensions[CHILD_HEIGHT];
                }
                if (width >= widthSize) {
                    break;
                }
            }
        }

        final boolean fit = (vertical && height < heightSize) || (!vertical && width < widthSize);
        if (fit) {
            // we really should wrap the contents of the view, let's do it

            if (exactWidth) {
                width = widthSize;
            } else {
                width += getPaddingLeft() + getPaddingRight();
            }

            if (exactHeight) {
                height = heightSize;
            } else {
                height += getPaddingTop() + getPaddingBottom();
            }

            setMeasuredDimension(width, height);
        } else {
            // if calculated height/width exceeds requested height/width let's use response "onMeasure" implementation
            super.onMeasure(recycler, state, widthSpec, heightSpec);
        }

        if (view != null && overScrollMode == ViewCompat.OVER_SCROLL_IF_CONTENT_SCROLLS) {
            ViewCompat.setOverScrollMode(view, fit ? ViewCompat.OVER_SCROLL_NEVER : ViewCompat.OVER_SCROLL_ALWAYS);
        }
    }

    private void logMeasureWarning(int child) {
//        if (BuildConfig.DEBUG) {
//            Log.w("LinearLayoutManager", "Can't measure child #" + child + ", previously used dimensions will be reused." +
//                    "To remove this message either use #setChildSize() method or don't run RecyclerView animations");
//        }
    }

    private void initChildDimensions(int width, int height, boolean vertical) {
        if (childDimensions[CHILD_WIDTH] != 0 || childDimensions[CHILD_HEIGHT] != 0) {
            // already initialized, skipping
            return;
        }
        if (vertical) {
            childDimensions[CHILD_WIDTH] = width;
            childDimensions[CHILD_HEIGHT] = childSize;
        } else {
            childDimensions[CHILD_WIDTH] = childSize;
            childDimensions[CHILD_HEIGHT] = height;
        }
    }

    @Override
    public void setOrientation(int orientation) {
        // might be called before the constructor of this class is called
        //noinspection ConstantConditions
        if (childDimensions != null) {
            if (getOrientation() != orientation) {
                childDimensions[CHILD_WIDTH] = 0;
                childDimensions[CHILD_HEIGHT] = 0;
            }
        }
        super.setOrientation(orientation);
    }

    public void clearChildSize() {
        hasChildSize = false;
        setChildSize(DEFAULT_CHILD_SIZE);
    }

    public void setChildSize(int childSize) {
        hasChildSize = true;
        if (this.childSize != childSize) {
            this.childSize = childSize;
            requestLayout();
        }
    }

    private void measureChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec, int[] dimensions) {
        final View child = recycler.getViewForPosition(position);

        final RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) child.getLayoutParams();

        final int hPadding = getPaddingLeft() + getPaddingRight();
        final int vPadding = getPaddingTop() + getPaddingBottom();

        final int hMargin = p.leftMargin + p.rightMargin;
        final int vMargin = p.topMargin + p.bottomMargin;

        final int hDecoration = getRightDecorationWidth(child) + getLeftDecorationWidth(child);
        final int vDecoration = getTopDecorationHeight(child) + getBottomDecorationHeight(child);

        final int childWidthSpec = getChildMeasureSpec(widthSpec, hPadding + hMargin + hDecoration, p.width, canScrollHorizontally());
        final int childHeightSpec = getChildMeasureSpec(heightSpec, vPadding + vMargin + vDecoration, p.height, canScrollVertically());

        child.measure(childWidthSpec, childHeightSpec);

        dimensions[CHILD_WIDTH] = getDecoratedMeasuredWidth(child) + p.leftMargin + p.rightMargin;
        dimensions[CHILD_HEIGHT] = getDecoratedMeasuredHeight(child) + p.bottomMargin + p.topMargin;

        recycler.recycleView(child);
    }
}

    /*
public class WrapContentLinearLayoutManager  extends LinearLayoutManager {

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    private int[] mMeasuredDimension = new int[2];

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                          int widthSpec, int heightSpec) {
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);
        int width = 0;
        int height = 0;
        for (int i = 0; i < getItemCount(); i++) {


            if (getOrientation() == HORIZONTAL) {

                measureScrapChild(recycler, i,
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        heightSpec,
                        mMeasuredDimension);

                width = width + mMeasuredDimension[0];
                if (i == 0) {
                    height = mMeasuredDimension[1];
                }
            } else {
                measureScrapChild(recycler, i,
                        widthSpec,
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        mMeasuredDimension);
                height = height + mMeasuredDimension[1];
                if (i == 0) {
                    width = mMeasuredDimension[0];
                }
            }
        }
        switch (widthMode) {
            case View.MeasureSpec.EXACTLY:
                width = widthSize;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
        }

        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                height = heightSize;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
        }

//        switch (heightMode) { case View.MeasureSpec.AT_MOST: if (height < heightSize) break; case View.MeasureSpec.EXACTLY: height = heightSize; case View.MeasureSpec.UNSPECIFIED: }

        setMeasuredDimension(width, height);
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                   int heightSpec, int[] measuredDimension) {
        View view = recycler.getViewForPosition(position);
        recycler.bindViewToPosition(view, position);
        if (view != null) {
            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
            int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                    getPaddingLeft() + getPaddingRight(), p.width);
            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                    getPaddingTop() + getPaddingBottom(), p.height);
            view.measure(childWidthSpec, childHeightSpec);
            measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
            measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
            recycler.recycleView(view);
        }
    }
}
    */