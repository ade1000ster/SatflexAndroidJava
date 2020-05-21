package util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MascaraQuant {

        private static final String mask1= "#.###";
       private static  final String mask2 = "##.###";
       private static  final String mask3 = "###.###";



        public static String unmask(String s) {
            return s.replaceAll("[^0-9]*", "");
        }

        public static TextWatcher insert(final EditText editText) {
            return new TextWatcher() {
                boolean isUpdating;
                String old = "";

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = MascaraCNPJIE.unmask(s.toString());
                    String mask;
                    String defaultMask = getDefaultMask(str);
                    switch (str.length()) {

                        case 4:
                            mask = mask1;
                            break;
                        case 5:
                            mask = mask2;
                            break;
                        case 6:
                            mask = mask3;
                            break;


                        default:
                            mask = defaultMask;
                            break;
                    }

                    String mascara = "";
                    if (isUpdating) {
                        old = str;
                        isUpdating = false;
                        return;
                    }
                    int i = 0;
                    for (char m : mask.toCharArray()) {
                        if ((m != '#' && str.length() > old.length()) || (m != '#' && str.length() < old.length() && str.length() != i)) {
                            mascara += m;
                            continue;
                        }

                        try {
                            mascara += str.charAt(i);
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }
                    isUpdating = true;
                    editText.setText(mascara);
                    editText.setSelection(mascara.length());
                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                public void afterTextChanged(Editable s) {
                }

            };
        }

        private static String getDefaultMask(String str) {
            String defaultMask = mask1;
            if (str.length() == 5){
                defaultMask = mask2;
            }
            if (str.length() == 6){
                defaultMask = mask3;
            }
            return defaultMask;
        }
    }

