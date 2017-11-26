package molab.main.java.util;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.plot.DefaultDrawingSupplier;

public class ChartDrawingSupplier extends DefaultDrawingSupplier {

	public Paint[] paintSequence;
    public int paintIndex;
    public int fillPaintIndex;

    {
        paintSequence =  new Paint[] {
                new Color(167, 220, 96),
                new Color(242, 104, 102),
                new Color(89, 170, 231),
                new Color(150, 159, 6),
                new Color(152, 155, 255)
        };
    }

    @Override
    public Paint getNextPaint() {
        Paint result = paintSequence[paintIndex % paintSequence.length];
        paintIndex++;
        return result;
    }


    @Override
    public Paint getNextFillPaint() {
        Paint result = paintSequence[fillPaintIndex % paintSequence.length];
        fillPaintIndex++;
        return result;
    } 
    
}
