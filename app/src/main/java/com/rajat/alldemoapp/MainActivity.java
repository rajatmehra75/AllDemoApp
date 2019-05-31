package com.rajat.alldemoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rajat.alldemoapp.activity.M1Activity;
import com.rajat.alldemoapp.bean.History;
import com.rajat.alldemoapp.retrofit.RequestHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonAction = findViewById(R.id.button_action);
        et = findViewById(R.id.et);
//        checkEditTextForCurrency(et);
//        CurrencyMaskFilter currencyMaskFilter = new CurrencyMaskFilter();
//        currencyMaskFilter.setCurrencyMask(et);

//        et.addTextChangedListener(new CurrencyTextWatcher(et));
        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new HttpCallAsync().execute();

//                Intent intent = new Intent(MainActivity.this, FireBaseActivity.class);
//                startActivity(intent);
//                Log.d(TAG, "current time :"+AppUtils.convertLongDateToStringDate(System.currentTimeMillis(),"yyyy-MM-dd-hh-mm-ss"));
//                Log.d(TAG, "current time formatted :"+AppUtils.convertLongDateToStringDate(System.currentTimeMillis(),"dd MMM, yyyy 'at' hh:mm aa"));

//                AppUtils.writeLogFile(AppUtils.convertLongDateToStringDate(System.currentTimeMillis(), "yyyy-MM-dd-hh-mm-ss") + " new logs added",
//                        "tempfile", false);
                Intent intent = new Intent(MainActivity.this, M1Activity.class);
                startActivity(intent);
            }
        });

//        Intent intent = new Intent(MainActivity.this,PaymentGatewayActivity.class);
//        startActivity(intent);

        String response = RequestHandler.getInstance().getHttpResponseAsync("todos/1");
        Log.d(TAG, "response : " + response);
    }

    class HttpCallAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String response = RequestHandler.getInstance().getHttpResponseSync("http://date.jsontest.com/");
//            response = RequestHandler.getInstance().getHttpResponseSync("posts/1/comments");

            String url = "http://192.168.103.6:8080/AppLocationServer/SaveHistory";
            History history = new History(11, "from tab", 22, 77);
            List<History> historyList = new ArrayList<>();
            historyList.add(history);
            response = RequestHandler.getInstance().postHttpResponseSync(url, historyList);
            Log.d(TAG, "response : " + response);
            return null;
        }
    }

    private String downloadFile() {
        return "";
    }

    public static void checkEditTextForCurrency(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            private String current = "";
            int start, before, count;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                this.start = start;
                this.before = before;
                this.count = count;

//                if (!s.toString().equals(current)) {
//                    Log.d(TAG, "checkEditTextForCurrency s : " + s.toString());
//                    Log.d(TAG, "checkEditTextForCurrency start : " + start);
//                    Log.d(TAG, "checkEditTextForCurrency before : " + before);
//                    Log.d(TAG, "checkEditTextForCurrency count : " + count);
//                    et.removeTextChangedListener(this);
//
//                    String cleanString = s.toString().replaceAll("[,]", "");
//                    String formatted = "";
//                    try {
//                        double parsed = Double.parseDouble(cleanString);
////                    String formatted = NumberFormat.getCurrencyInstance().format((parsed /*/ 100*/));
//                        formatted = convertNumberToCurrencyFormatToEdit(parsed);
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//
//                    current = formatted;
//                    et.setText(formatted);
////                    et.setSelection(formatted.length());
//                    try {
//                        et.setSelection(start + count - before);
//                    } catch (Exception e){
//                        e.printStackTrace();
//                        et.setSelection(s.length());
//                    }
//                    et.addTextChangedListener(this);
//                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String strValue = s.toString();
                Log.d(TAG, "checkEditTextForCurrency strValue : " + strValue);
                Log.d(TAG, "checkEditTextForCurrency start : " + start);
                Log.d(TAG, "checkEditTextForCurrency before : " + before);
                Log.d(TAG, "checkEditTextForCurrency count : " + count);
                et.removeTextChangedListener(this);
                try {
                    String currencyFormat = convertNumberToCurrencyFormatToEdit(Double.parseDouble(strValue.replaceAll(",", "")));
                    if (strValue.contains(".") && !currencyFormat.contains(".")) {
                        currencyFormat = currencyFormat + ".";
                    }
                    if (!strValue.equals(currencyFormat)) {
//                        s.clear();
//                        s.append(currencyFormat);
                        et.setText(currencyFormat);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                et.addTextChangedListener(this);
            }
        });
    }

    public static String convertNumberToCurrencyFormatToEdit(Double number) {
        String formattedNumber = "0.0";
        if (number != null) {
            NumberFormat formatter;
            if (number % 1 > 0.0) {
                if ((number * 100) % 10 > 0.0) {
                    formatter = new DecimalFormat("#,###.00");
                } else {
                    formatter = new DecimalFormat("#,###.0");
                }
            } /*else if (number.toString().contains(".")) {
                formatter = new DecimalFormat("#,###.");
            }*/ else {
                formatter = new DecimalFormat("#,###");
            }
            formattedNumber = formatter.format(number);
        }
        return formattedNumber;
    }

    public class CurrencyTextWatcher implements TextWatcher {
        private String current = "";
        private int index;
        private boolean deletingDecimalPoint;
        private final EditText currency;

        public CurrencyTextWatcher(EditText p_currency) {
            currency = p_currency;
        }

        @Override
        public void onTextChanged(
                CharSequence p_s, int p_start, int p_before, int p_count) {
            // nothing to do
        }

        @Override
        public void beforeTextChanged(CharSequence p_s, int p_start, int p_count, int p_after) {
            if (p_after > 0) {
                index = p_s.length() - p_start;
            } else {
                index = p_s.length() - p_start - 1;
            }
            if (p_count > 0 && p_s.charAt(p_start) == '.') {
                deletingDecimalPoint = true;
            } else {
                deletingDecimalPoint = false;
            }
        }

        @Override
        public synchronized void afterTextChanged(Editable p_s) {
            if (!p_s.toString().equals(current)) {
                currency.removeTextChangedListener(this);
//                if (deletingDecimalPoint) {
//                    p_s.delete(p_s.length() - index - 1, p_s.length() - index);
//                }

                // Currency char may be retrieved from NumberFormat.getCurrencyInstance()
//                String v_text = p_s.toString().replaceAll("[$,.]", "");
//                double v_value = 0;
//                if (v_text!=null && v_text.length()>0) {
//                    v_value = Double.parseDouble(v_text);
//                }
//                // Currency instance may be retrieved from a static member.
//                String v_formattedValue = NumberFormat.getCurrencyInstance().format((v_value/100));
//                current = v_formattedValue;
//                currency.setText(v_formattedValue);
//                if (index>v_formattedValue.length()) {
//                    currency.setSelection(v_formattedValue.length());
//                } else {
//                    currency.setSelection(v_formattedValue.length()-index);
//                }
//                // inlude here anything you may want to do after the formatting is completed.
//                currency.addTextChangedListener(this);

                //my code
                String strValue = p_s.toString();
                try {
//                    if ((Double.parseDouble(strValue) * 100) % 1 <= 0) {
                    String currencyFormat = convertNumberToCurrencyFormatToEdit(Double.parseDouble(strValue.replaceAll(",", "")));
                    if (strValue.contains(".") && !currencyFormat.contains(".")) {
                        currencyFormat = currencyFormat + ".";
                    }
                    if (!strValue.equals(currencyFormat)) {
//                        s.clear();
//                        s.append(currencyFormat);
                        et.setText(currencyFormat);
                        if (index > currencyFormat.length()) {
                            currency.setSelection(currencyFormat.length());
                        } else {
                            currency.setSelection(currencyFormat.length() - index);
                        }
                    }
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currency.addTextChangedListener(this);
            }
        }
    }

}
