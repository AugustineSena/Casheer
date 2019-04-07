package misc;

import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

public class NumberRenderer extends FormatRenderer
{
    /*
     *  Use the specified number formatter and right align the text
     */
    public NumberRenderer(NumberFormat formatter)
    {
        super(formatter);
        setHorizontalAlignment( SwingConstants.RIGHT );
    }

    /*
     *  Use the default currency formatter for the default locale
     */
    public static NumberRenderer getCurrencyRenderer()
    {
        return new NumberRenderer( NumberFormat.getInstance() );
    }

    /*
     *  Use the default integer formatter for the default locale
     */
    public static NumberRenderer getIntegerRenderer()
    {
        return new NumberRenderer( NumberFormat.getIntegerInstance() );
    }

    /*
     *  Use the default percent formatter for the default locale
     */
    public static NumberRenderer getPercentRenderer()
    {
        return new NumberRenderer( NumberFormat.getPercentInstance() );
    }

    /*
    * Get default default number formatter for JFormattedTextField
     */
    public static NumberFormatter getCurrencyFormatter(/*String symbol*/){
        NumberFormat format = NumberFormat.getInstance();/*Number*/
        NumberFormatter formatter = new NumberFormatter(format){
            @Override
            public Object stringToValue(String string)
                    throws ParseException {
                if (string == null || string.length() == 0) {
                    return null;
                }
                return super.stringToValue(string);
            }
        };
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);//?
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        return  formatter;
    }
}