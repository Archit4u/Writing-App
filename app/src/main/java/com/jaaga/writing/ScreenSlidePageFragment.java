package com.jaaga.writing;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */

    DrawingView dv;
    private Paint mPaint;


    public static final String ARG_PAGE = "page";


    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    //ExpandableMenuOverlay menuOverlay;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */


    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);

    }

    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);


        final CircularButton red = (CircularButton) rootView.findViewById(R.id.button);
        final CircularButton green = (CircularButton) rootView.findViewById(R.id.button1);
        final CircularButton blue = (CircularButton) rootView.findViewById(R.id.button2);
        final CircularButton yellow = (CircularButton) rootView.findViewById(R.id.button3);
        final CircularButton cyan = (CircularButton) rootView.findViewById(R.id.button4);
        final CircularButton eraser = (CircularButton) rootView.findViewById(R.id.button5);
        //Here is shown how you can change the color of the button and shadow.
        red.setButtonColor(Color.RED);

        green.setButtonColor(Color.GREEN);
        blue.setButtonColor(Color.BLUE);
        yellow.setButtonColor(Color.YELLOW);
        cyan.setButtonColor(Color.CYAN);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    red.setShadowColor(Color.BLUE);
                    mPaint.setColor(Color.RED);

            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                green.setShadowColor(Color.YELLOW);
                mPaint.setColor(Color.GREEN);

            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                yellow.setShadowColor(Color.BLUE);
                mPaint.setColor(Color.YELLOW);

            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                blue.setShadowColor(Color.RED);
                mPaint.setColor(Color.BLUE);

            }
        });
       cyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                cyan.setShadowColor(Color.BLUE);
                mPaint.setColor(Color.CYAN);

            }
        });
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                eraser.setShadowColor(Color.BLACK);
                mPaint.setColor(Color.WHITE);

            }
        });

        RelativeLayout mtile = (RelativeLayout) rootView.findViewById(R.id.mtile);
        dv = new DrawingView(rootView.getContext());
        mtile.addView(dv);
       RelativeLayout rvtrans = new RelativeLayout(rootView.getContext());
        final TypedArray images = getResources().obtainTypedArray(R.array.random_imgs);
        rvtrans.setBackgroundResource(images.getResourceId(mPageNumber, 0));
       images.recycle();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        mtile.addView(rvtrans);
        rvtrans.setLayoutParams(lp);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
      //  mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(50);


        return rootView;
    }

    public class DrawingView extends View {

        public int width;
        public int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
            canvas.drawPath(circlePath, circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
            Log.d("start xy==>", x + "," + y);
        }

        private void touch_move(float x, float y) {
            Log.d("move xy==>", x + "," + y);
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if ((dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            /*  circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);*/
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            Log.d("end xy", mX + "," + mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }

        public int getPageNumber() {
            return mPageNumber;
        }
    }
}
