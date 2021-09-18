package playground.stanford.karatsuba;

@SuppressWarnings({"ManualArrayCopy", "ManualMinMaxCalculation"})
public class Main {

    /**
     * Provide the numbers to multiply as the first two arguments of the call.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new RuntimeException("provide two integers");
        }
        byte[] arg1 = toByteArray(args[0]);
        byte[] arg2 = toByteArray(args[1]);
        byte[] result = multiply(arg1, arg2);
        result = stripLeadingZeroes(result);
        System.out.println(byteArrayToString(result, true));
    }

    public static byte[] multiply(byte[] x, byte[] y) {
        // compute sizes m and n
        int m = 0, n = 0;
        int maxlength = x.length > y.length ? x.length : y.length;
        // pad
        if (x.length < maxlength) {
            byte[] x2 = new byte[maxlength];
            for(int i = x.length - 1; i >=0 ; i --) {
                x2[i + maxlength - x.length] = x[i];
            }
            x = x2;
        }
        else if (y.length < maxlength) {
            byte[] y2 = new byte[maxlength];
            for(int i = y.length - 1; i >=0 ; i --) {
                y2[i + maxlength - y.length] = y[i];
            }
            y = y2;
        }
        if (maxlength % 2 == 0) {
            m = maxlength / 2;
            n = m;
        }
        else {
            m = maxlength / 2;
            n = m + 1;
        }

        // extract a, b, c, d as byte[], n is the biggest on odd length
        byte[] a = first(x, m);
        byte[] b = last(x, n);
        byte[] c = first(y, m);
        byte[] d = last(y, n);

//        System.out.println("maxlength: " + maxlength + ", m: " + m + ", n: " + n);
//        System.out.println("arg1: " + byteArrayToString(x, true));
//        System.out.println("arg2: " + byteArrayToString(y, true));
//        System.out.println("a: " + byteArrayToString(a, true));
//        System.out.println("c: " + byteArrayToString(c, true));
//        System.out.println("b: " + byteArrayToString(b, true));
//        System.out.println("d: " + byteArrayToString(d, true));

        if (a.length == 0) {
            // base case, we multiply one digit b and d and exit recurrence
            int bi = b[0];
            int di = d[0];
            int r = bi * di;
            if (r < 10) {
                return new byte[] {(byte)r};
            }
            else {
                byte[] result = new byte[2];
                result[1] = (byte)(r % 10);
                result[0] = (byte)((r - result[1])/10);
                return result;
            }
        }

        // not a base case, enter recurrence
        byte[] ac = multiply(a, c);
        byte[] bd = multiply(b, d);
        byte[] s = multiply(add(a, b), add(c, d));
        byte[] adbc = subtract(s, ac);
        adbc = subtract(adbc, bd);
        byte[] result = shiftLeft(ac, 2*n);
        result = add(result, shiftLeft(adbc, n));
        result = add(result, bd);
        return result;
    }

    public static byte[] add(byte[] x, byte[] y) {
        byte[] result = new byte[Math.max(x.length, y.length) + 1];
        int xi = x.length - 1;
        int yi = y.length - 1;
        boolean carryover = false;
        for(int ri = result.length - 1; ri >= 0; ri --) {
            if (xi >= 0 && yi >= 0) {
                int r = x[xi] + y[yi] + (carryover ? 1 : 0);
                if (r < 10) {
                    carryover = false;
                    result[ri] = (byte)r;
                }
                else {
                    carryover = true;
                    result[ri] = (byte)(r % 10);
                }
            }
            else {
                // one or both indices negative
                int r;
                if (xi >= 0) {
                    r = carryover ? 1 + x[xi] : x[xi];
                }
                else if (yi >= 0) {
                    r = carryover ? 1 + y[yi] : y[yi];
                }
                else {
                    // both indices negative
                    r = carryover ? 1 : 0;
                }

                if (r < 10) {
                    carryover = false;
                    result[ri] = (byte)r;
                }
                else {
                    carryover = true;
                    result[ri] = (byte)(r % 10);
                }
            }
            xi --;
            yi --;
        }
        return stripLeadingZeroes(result);
    }

    /**
     * Only works if x >= y
     */
    public static byte[] subtract(byte[] x, byte[] y) {
        if (y.length > x.length) {
            throw new RuntimeException("NOT SUPPORTED: second argument longer than the first argument: " + byteArrayToString(x, false) + ", " + byteArrayToString(y, false));
        }
        byte[] result = new byte[x.length];
        int xi = x.length - 1;
        int yi = y.length - 1;
        boolean carryover = false;
        for(int ri = result.length - 1; ri >= 0; ri --) {
            int r;
            if (yi >= 0) {
                r = x[xi] - y[yi] - (carryover ? 1 : 0);
            }
            else {
                // nothing to subtract, just copy, possibly with carryover
                r = x[xi] - (carryover ? 1 : 0);
            }
            if (r >= 0) {
                result[ri] = (byte)r;
                carryover = false;
            }
            else {
                result[ri] = (byte)(r + 10);
                carryover = true;
            }
            xi --;
            yi --;
        }
        return stripLeadingZeroes(result);
    }

    public static byte[] shiftLeft(byte[] b, int positions) {
        byte[] result = new byte[b.length + positions];
        for(int i = 0; i < b.length; i ++) {
            result[i] = b[i];
        }
        return result;
    }

    public static byte[] toByteArray(String s) {
        byte[] result = new byte[s.length()];
        for(int i = 0; i < s.length(); i ++) {
            // TODO error detection when the input is not a digit
            result[i] = (byte)((int)s.charAt(i) - (int)'0');
        }
        return result;
    }

    public static String byteArrayToString(byte[] ba, boolean showLeadingZeros) {
        StringBuilder sb = new StringBuilder();
        for(byte b: ba) {
            if (showLeadingZeros) {
                sb.append(b);
            }
            else {
                if (sb.isEmpty() && (int)b == 0) {
                    continue;
                }
                sb.append(b);
            }
        }
        if (sb.isEmpty()) {
            return "0";
        }
        return sb.toString();
    }

    public static byte[] first(byte[] b, int count) {
        byte[] result = new byte[count];
        for(int i = 0; i < count; i ++) {
            result[i] = b[i];
        }
        return result;
    }

    public static byte[] last(byte[] b, int count) {
        byte[] result = new byte[count];
        for(int i = 0; i < count; i ++) {
            result[i] = b[b.length - count + i];
        }
        return result;
    }

    public static byte[] stripLeadingZeroes(byte[] b) {
        if (b.length <= 1) {
            return b;
        }
        if (b[0] == 0) {
            byte[] a = new byte[b.length - 1];
            for(int i = 1; i < b.length; i ++) {
                a[i - 1] = b[i];
            }
            return stripLeadingZeroes(a);
        }
        return b;
    }
}

