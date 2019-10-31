package be.keycu.k3android;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class K3Utils {

    private static final String TAG = K3Utils.class.getSimpleName();

    private static Map<String, Boolean> keyState;

    private static void InitKeyState()
    {
        keyState = new HashMap<>();

        // blue
        keyState.put("+u0", false); //

        keyState.put("+u1", false);
        keyState.put("+u2", false);
        keyState.put("+u3", false);

        keyState.put("+u4", false); //

        keyState.put("+u5", false);
        keyState.put("+u6", false);
        keyState.put("+u7", false);

        keyState.put("+u8", false); //

        keyState.put("+u9", false);
        keyState.put("+uA", false);
        keyState.put("+uB", false);

        keyState.put("+uC", false); //

        keyState.put("+uD", false);
        keyState.put("+uE", false);
        keyState.put("+uF", false);

        // yellow
        keyState.put("+y0", false);
        keyState.put("+y1", false);
        keyState.put("+y2", false);
        keyState.put("+y3", false);
        keyState.put("+y4", false);
        keyState.put("+y5", false);
        keyState.put("+y6", false);
        keyState.put("+y7", false);
        keyState.put("+y8", false);
        keyState.put("+y9", false);
        keyState.put("+yA", false);

        keyState.put("+yB", false); //
        keyState.put("+yC", false); //
        keyState.put("+yD", false); //
        keyState.put("+yE", false); //
        keyState.put("+yF", false); //

        // red
        keyState.put("+r0", false); //

        keyState.put("+r1", false);
        keyState.put("+r2", false);

        keyState.put("+r3", false); //
        keyState.put("+r4", false); //
        keyState.put("+r5", false); //
        keyState.put("+r6", false); //

        keyState.put("+r7", false);

        keyState.put("+r8", false); //
        keyState.put("+r9", false); //
        keyState.put("+rA", false); //

        keyState.put("+rB", false);

        keyState.put("+rC", false); //
        keyState.put("+rD", false); //
        keyState.put("+rE", false); //
        keyState.put("+rF", false); //

        // green
        keyState.put("+g0", false); //
        keyState.put("+g1", false); //
        keyState.put("+g2", false); //
        keyState.put("+g3", false); //
        keyState.put("+g4", false); //
        keyState.put("+g5", false); //
        keyState.put("+g6", false); //
        keyState.put("+g7", false); //
        keyState.put("+g8", false); //
        keyState.put("+g9", false); //
        keyState.put("+gA", false); //
        keyState.put("+gB", false); //
        keyState.put("+gC", false); //
        keyState.put("+gD", false); //
        keyState.put("+gE", false); //
        keyState.put("+gF", false); //

        // white
        keyState.put("+w0", false); //
        keyState.put("+w1", false); //
        keyState.put("+w2", false); //
        keyState.put("+w3", false); //
        keyState.put("+w4", false); //
        keyState.put("+w5", false); //
        keyState.put("+w6", false); //
        keyState.put("+w7", false); //
        keyState.put("+w8", false); //
        keyState.put("+w9", false); //
        keyState.put("+wA", false); //
        keyState.put("+wB", false); //
        keyState.put("+wC", false); //
        keyState.put("+wD", false); //
        keyState.put("+wE", false); //
        keyState.put("+wF", false); //
    }

    public static String GetCharacterFromKeycubeCode(String code)
    {
        if (keyState == null)
            InitKeyState();

        if (!keyState.containsKey(code))
        {
            Log.d(TAG,"Do not contains Key");
            return null;
        }

        if (keyState.get(code)) // if true it means we just release the key, therefore nothing to do
        {
            keyState.put(code, false);
            return null;
        }

        keyState.put(code, true);
        switch (code)
        {
            // blue
            case "+u1":
                return "w";
            case "+u2":
                return "q";
            case "+u3":
                return "a";

            case "+u5":
                return "x";
            case "+u6":
                return "s";
            case "+u7":
                return "z";

            case "+u9":
                return "c";
            case "+uA":
                return "d";
            case "+uB":
                return "e";

            case "+uD":
                return "v";
            case "+uE":
                return "f";
            case "+uF":
                return "r";

            // yellow
            case "+y0":
                return "u";
            case "+y1":
                return "i";
            case "+y2":
                return "o";
            case "+y3":
                return "p";
            case "+y4":
                return "j";
            case "+y5":
                return "k";
            case "+y6":
                return "l";
            case "+y7":
                return "m";
            case "+y8":
                return "b";
            case "+y9":
                return "n";

            // red
            case "+r0":
                return "_";
            case "+r4":
                return "_";
            case "+r8":
                return "_";
            case "+r1":
                return "g";
            case "+r2":
                return "t";
            case "+r7":
                return "y";
            case "+rB":
                return "h";

            case "+rC":
                return ">";
            case "+rD":
                return ">";
            case "+rE":
                return "<";
            case "+rF":
                return "<";
        }
        return code;
    }
}
