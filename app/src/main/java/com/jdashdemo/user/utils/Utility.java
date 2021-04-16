package com.jdashdemo.user.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Ourdevelops Team on 12/2/2019.
 */

public class Utility {


    public static TextWatcher currencyTW(final EditText editText, final Context context) {
        final SettingPreference sp = new SettingPreference(context);
        return new TextWatcher() {
            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(".")) {
                        originalString = originalString.replaceAll("[$.]", "");
                    }
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    if (originalString.contains(sp.getSetting()[0]+" ")) {
                        originalString = originalString.replaceAll(sp.getSetting()[0]+" ", "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replaceAll(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replace(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replace(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(" ")) {
                        originalString = originalString.replaceAll(" ", "");
                    }
                    longval = Long.parseLong(originalString);
                    if (longval == 0) {
                        editText.setText("");
                        editText.setSelection(editText.getText().length());
                    } else if (String.valueOf(longval).length() == 1) {
                        editText.setText(sp.getSetting()[0]+"0.0" + String.valueOf(longval));
                        editText.setSelection(editText.getText().length());
                    } else if (String.valueOf(longval).length() == 2) {
                        editText.setText(sp.getSetting()[0]+"0." + String.valueOf(longval));
                        editText.setSelection(editText.getText().length());
                    } else {

                        SettingPreference sp = new SettingPreference(context);
                        DecimalFormat formatter = new DecimalFormat("#,###,##");
                        String formattedString = formatter.format(longval);
                        editText.setText(sp.getSetting()[0] + formattedString.replace(",","."));
                        editText.setSelection(editText.getText().length());
                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                editText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        };
    }


    public static void currencyTXT(TextView text, String nomninal, Context context) {
        SettingPreference sp = new SettingPreference(context);
        if (nomninal.length() == 1) {
            text.setText(sp.getSetting()[0]+"0.0" + nomninal);
        } else if (nomninal.length() == 2) {
            text.setText(sp.getSetting()[0]+"0." + nomninal);
        } else {
            Double getprice = Double.valueOf(nomninal);
            DecimalFormat formatter = new DecimalFormat("#,###,##");
            String formattedString = formatter.format(getprice);
            text.setText(sp.getSetting()[0] + formattedString.replace(",","."));
        }
    }
    public static void currencyTXT1(TextView text, String nomninal, Context context) {
        SettingPreference sp = new SettingPreference(context);
        if (nomninal.length() == 1) {
            text.setText(sp.getSetting()[0]+"0.0" + nomninal);
        } else if (nomninal.length() == 2) {
            text.setText(sp.getSetting()[0]+"0." + nomninal);
        }
        else if(nomninal.length() >= 6){
            String narsss = nomninal.substring(0, nomninal.length() - 2);
            int nars = Integer.parseInt(narsss);
            text.setText(sp.getSetting()[0] + NumberFormat.getNumberInstance(Locale.getDefault()).format(nars) + ".00");
        }
        else {
            double getprice = Double.valueOf(nomninal);
            int intValue = (int) getprice;

            DecimalFormat formatter = new DecimalFormat("#,###,##");
            String formattedString = formatter.format(intValue);
            String nars = formattedString.replace(",",".");
            double nars1 = Double.valueOf(nars);
            int nars2 = (int) nars1;
//            String nars2 = String.format("%.0f",nars1);

            text.setText(sp.getSetting()[0] + nars2 + ".00");
        }
    }

    public static void currencyTXT2(TextView text, String nomninal, Context context) {
        SettingPreference sp = new SettingPreference(context);
        if (nomninal.length() == 1) {
            text.setText(sp.getSetting()[0]+"0.0" + nomninal);
        } else if (nomninal.length() == 2) {
            text.setText(sp.getSetting()[0]+"0." + nomninal);
        } else {
            String narsss = nomninal.substring(0, nomninal.length() - 2);
            int nars = Integer.parseInt(narsss);
//            Double getprice = Double.valueOf(nomninal);
//            NumberFormat.getNumberInstance(Locale.getDefault()).format(getprice);
//            DecimalFormat formatter = new DecimalFormat("#,###,##");
//            String formattedString = formatter.format(getprice);
            text.setText(sp.getSetting()[0] + NumberFormat.getNumberInstance(Locale.getDefault()).format(nars) + ".00");
//            text.setText(sp.getSetting()[0] + formattedString.replace(",","."));
        }
    }

}
