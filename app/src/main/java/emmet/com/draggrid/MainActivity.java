package emmet.com.draggrid;

import android.app.Activity;
import android.content.ClipData;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import com.animoto.android.views.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {
    GridView gv;
    GridViewAdapter gva;
    RelativeLayout rightPanel;
    ImageView newShape;
    MyDragListener mainDragListener;
    TextView txtScore;
    Random r = new Random();
    String[][] gameBoard = new String[6][8];
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        int columnWidth = (int)(screenWidth*0.1);
        int panelWidth = (int)(screenWidth*0.2);
        if (columnWidth*6 < screenHeight) {
            columnWidth = (int)(screenHeight/5.95);
        }

        int rowHeight = (int)(screenHeight/6);
        hookUpUIElements();
        rightPanel.setMinimumWidth(panelWidth);
        gv.setColumnWidth(columnWidth);
        gv.setNumColumns(8);


        mainDragListener = new MyDragListener();
        gva = new GridViewAdapter(this,R.layout.grid_item_layout,getData(),mainDragListener);
        gva.setMaxHeight(rowHeight);
        gva.setMaxWidth(columnWidth);
        gv.setAdapter(gva);
        gv.setOnDragListener(new MyDragListener());
        getNewShape();
        //findViewById(R.id.image).setOnDragListener(new MyDragListener());

    }

    private void setScore(int increase) {
        score = score+increase;
        try {
            txtScore.setText("Score: " + score);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private int getScore() {
        return score;
    }

    private void hookUpUIElements() {
        gv = findViewById(R.id.gridView);
        rightPanel = findViewById(R.id.rightPanel);
        newShape = findViewById(R.id.newShape);
        txtScore = findViewById(R.id.txtScore);
        newShape.setOnTouchListener(new MyTouchListener());

    }
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        for (int i=0;i<48;i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.dot);
            imageItems.add(new ImageItem(bitmap));
        }
        return imageItems;
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    private void getNewShape() {
        int i1 = r.nextInt(13-1) + 1;
        String tag = "black";
        Bitmap newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blacksquare);;
        switch (i1){
            case 1:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.redsquare);
                tag = "red";
                break;
            case 2:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bluesquare);
                tag = "blue";
                break;
            case 3:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.greensquare);
                tag = "green";
                break;
            case 4:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pinksquare);
                tag = "pink";
                break;
            case 5:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brownsquare);
                tag = "brown";
                break;
            case 6:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blacksquare);
                tag = "black";
                break;
            case 7:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lightbluesquare);
                tag = "lightblue";
                break;
            case 8:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lightgreensquare);
                tag = "lightgreen";
                break;
            case 9:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.purplesquare);
                tag = "purple";
                break;
            case 10:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lavendarsquare);
                tag = "lavendar";
                break;
            case 11:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.orangesquare);
                tag = "orange";
                break;
            case 12:
                newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellowsquare);
                tag = "yellow";
                break;

        }
        newShape.setImageBitmap(newBitmap);
        newShape.setTag(tag);


    }


    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        // view is the new color, v is the old one.
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    LinearLayout ll = (LinearLayout)v.getParent();
                    Point gridRef = findGridCell(ll.getX(),ll.getY());
                    ImageView view = (ImageView) event.getLocalState();
                    Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
                    ImageView container = (ImageView) v;
                    if (container.getTag() != null) {
                        return false;
                    }
                    container.setImageBitmap(bitmap);
                    container.setTag(view.getTag().toString());
                    gameBoard[gridRef.x][gridRef.y] = view.getTag().toString();
                    matchThree();
                    getNewShape();
                    break;
                default:
                    break;
            }
            return true;
        }

        private Point findGridCell(float x, float y) {
            int row=0;
            int column =0;
            for(int i=0;i< gv.getAdapter().getCount(); i++) {

                Rect clipRect = new Rect(0,0,0,0);
                try {
                    clipRect = new Rect(gv.getChildAt(i).getLeft(),gv.getChildAt(i).getTop(),gv.getChildAt(i).getRight(),gv.getChildAt(i).getBottom());
                } catch (Exception e) {
                    System.out.println("Error in findGridCell " + e.getMessage());
                }
                if (clipRect.contains((int)x,(int)y)) {
                    return new Point(row,column);
                }
                if ((i+1)%8 == 0) {
                   row++;
                   column=0;
                }
                else
                    column++;
            }
            return new Point(-1,-1);
        }

        private void blankCell(int x,int y) {
            try {
                LinearLayout ll = (LinearLayout) gv.getChildAt(x * 8 + y);
                ImageView v = (ImageView)ll.getChildAt(0);
                v.setTag(null);
                gameBoard[x][y] =null;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dot);
                v.setImageBitmap(bitmap);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        private void matchThree() {
            String previousCell="";
            Boolean oneMatch=false;
            // do rows
            for (int i=0;i<6;i++) {
                for (int j=0;j<8;j++) {
                    if (j==0) { previousCell = gameBoard[i][j]; oneMatch = false;}
                    else if (j > 0) {
                        if (gameBoard[i][j] == previousCell && oneMatch) {
                            if (previousCell != null )
                            {
                                System.out.println("Match for row " + i + " columns " + (j-2) + " to " + j);
                                setScore(3);
                                blankCell(i,j);
                                blankCell(i,j-1);
                                blankCell(i,j-2);
                                return;
                            }
                        }
                        else if (gameBoard[i][j] == previousCell) {
                            oneMatch = true;
                        }
                        else {
                            previousCell = gameBoard[i][j];
                            oneMatch = false;
                        }
                    }
                }
            }
            // do columns
            for (int j=0;j<8;j++) {
                for (int i=0;i<6;i++) {
                    if (i==0) { previousCell = gameBoard[i][j]; oneMatch = false;}
                    else if (i > 0) {
                        if (gameBoard[i][j] == previousCell && oneMatch) {
                            if (previousCell != null )
                            {
                                System.out.println("Match for column " + j + " rows " + (i-2) + " to " + i);
                                setScore(3);
                                blankCell(i,j);
                                blankCell(i-1,j);
                                blankCell(i-2,j);
                                return;
                            }
                        }
                        else if (gameBoard[i][j] == previousCell) {
                            oneMatch = true;
                        }
                        else {
                            previousCell = gameBoard[i][j];
                            oneMatch = false;
                        }
                    }
                }
            }
        }
    }
}
