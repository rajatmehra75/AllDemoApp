package com.rajat.alldemoapp.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;

/**
 * Created by rajat on 13/2/19.
 */

public class CurrencyMaskFilter {

//    final InputFilter[] FILTERS = new InputFilter[] {new NumericRangeFilter()};
//    final View.OnFocusChangeListener ON_FOCUS = new AmountOnFocusChangeListener();

    public void setCurrencyMask(EditText editText){
        InputFilter[] FILTERS = new InputFilter[] {new NumericRangeFilter(0.00,999999999999.00)};
        View.OnFocusChangeListener ON_FOCUS = new AmountOnFocusChangeListener();
        editText.setFilters(FILTERS);
        editText.setOnFocusChangeListener(ON_FOCUS);
    }

    /**
     * Numeric range Filter.
     */
    class NumericRangeFilter implements InputFilter {
        /**
         * Maximum value.
         */
        private final double maximum;
        /**
         * Minimum value.
         */
        private final double minimum;

        /**
         * Creates a new filter between 0.00 and 999,999.99.
         */
        NumericRangeFilter() {
            this(0.00, 999999.99);
        }

        /**
         * Creates a new filter.
         *
         * @param p_min Minimum value.
         * @param p_max Maximum value.
         */
        NumericRangeFilter(double p_min, double p_max) {
            maximum = p_max;
            minimum = p_min;
        }

        @Override
        public CharSequence filter(
                CharSequence p_source, int p_start,
                int p_end, Spanned p_dest, int p_dstart, int p_dend) {
            try {
                String v_valueStr = p_dest.toString().concat(p_source.toString());
                double v_value = Double.parseDouble(v_valueStr);
                if (v_value <= maximum && v_value >= minimum) {
                    // Returning null will make the EditText to accept more values.
                    return null;
                }
            } catch (NumberFormatException p_ex) {
                // do nothing
            }
            // Value is out of range - return empty string.
            return "";
        }
    }

    class AmountOnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View p_view, boolean p_hasFocus) {
            // This listener will be attached to any view containing amounts.
            EditText v_amountView = (EditText)p_view;
            if (p_hasFocus) {
                // v_value is using a currency mask - transfor over to cents.
                String v_value = v_amountView.getText().toString();
                int v_cents = AppUtils.parseAmountToCents(v_value);
                // Now, format cents to an amount (without currency mask)
                v_value = AppUtils.formatCentsToAmount(v_cents);
                v_amountView.setText(v_value);
                // Select all so the user can overwrite the entire amount in one shot.
                v_amountView.selectAll();
            } else {
                // v_value is not using a currency mask - transfor over to cents.
                String v_value = v_amountView.getText().toString();
                int v_cents = AppUtils.parseAmountToCents(v_value);
                // Now, format cents to an amount (with currency mask)
                v_value = AppUtils.formatCentsToCurrency(v_cents);
                v_amountView.setText(v_value);
            }
        }
    }

}
