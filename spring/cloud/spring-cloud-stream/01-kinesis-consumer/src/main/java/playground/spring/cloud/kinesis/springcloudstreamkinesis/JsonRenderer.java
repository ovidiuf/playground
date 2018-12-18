package playground.spring.cloud.kinesis.springcloudstreamkinesis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Renders ObjectMapper.readValue() results.
 */
public class JsonRenderer {

    public static void renderJson(String key, Object o, int indent, StringBuilder sb) {

        if (o == null || o instanceof String || o instanceof Number || o instanceof Boolean) {

            renderScalar(key, o, indent, sb);
        }
        else if (o instanceof List) {

            String s = indentation(indent) + (key == null ? "": "\"" + key + "\": ") + "[";

            sb.append(s).append("\n");

            renderList((List)o, s.length() + 1, sb);

            sb.append(indentation(s.length() - 1)).append("]").append("\n");
        }
        else if (o instanceof Map) {

            String s = indentation(indent) + (key == null ? "": "\"" + key + "\": ") + "{";

            sb.append(s).append("\n");

            renderMap((Map)o, s.length() + 1, sb);

            sb.append(indentation(s.length() - 1)).append("}").append("\n");
        }
        else {

            System.err.println("value associated with key \"" + key + "\" is a " + o.getClass() + " and we don't know how to handle it");
        }
    }

    /**
     * @param value can be null.
     */
    private static void renderScalar(String key, Object value, int indent, StringBuilder sb) {

        sb.append(indentation(indent));

        if (key != null) {

            sb.append('"').append(key).append('"').append(": ");
        }

        if (value instanceof String) {

            sb.append('"');
        }

        sb.append(value == null ? "null" : value);

        if (value instanceof String) {

            sb.append('"');
        }

        sb.append("\n");
    }

    /**
     * The outer layer renders ["key": ] { ... } and provides the correct indentation.
     */
    private static void renderMap(Map map, int indent, StringBuilder sb) {

        // holds strings, numbers, booleans and nulls
        Map<String, Object> scalars = new HashMap<>();
        Map<String, List> lists = new HashMap<>();
        Map<String, Map> maps = new HashMap<>();

        for(Object k: map.keySet()) {

            if (k instanceof String) {

                String key = (String)k;

                Object value = map.get(k);

                if (value == null || value instanceof String || value instanceof Number || value instanceof Boolean) {

                    scalars.put(key, value);
                }
                else if (value instanceof Map) {

                    maps.put(key, (Map)value);
                }
                else if (value instanceof List) {

                    lists.put(key, (List)value);
                }
                else {

                    System.err.println("value associated with key \"" + k + "\" is a " + value.getClass() + " and we don't know how to handle it");
                }
            }
            else {

                System.err.println("key " + k + " is not a String but " + (k == null ? "null" : k.getClass()));
            }
        }

        // sort the scalar keys
        List<String> sortedScalarKeys = new ArrayList<>(scalars.keySet());
        Collections.sort(sortedScalarKeys);
        for(String k: sortedScalarKeys) {

            renderJson(k, scalars.get(k), indent, sb);
        }

        for(String k: lists.keySet()) {

            renderJson(k, lists.get(k), indent, sb);
        }

        for(String k: maps.keySet()) {

            renderJson(k, maps.get(k), indent, sb);
        }
    }

    /**
     * The outer layer renders ["key": ] [ ... ] and provides the correct indentation.
     */
    private static void renderList(List list, int indent, StringBuilder sb) {

        for(Object o: list) {

            renderJson(null, o, indent, sb);
        }
    }

    private static String indentation(int spaces) {

        char[] c = new char[spaces];
        Arrays.fill(c, ' ');
        return new String(c);
    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        Map<String, Object> map = new HashMap<>();
//
//        map.put("a", 1);
//        map.put("b", "something");
//        map.put("c", Boolean.TRUE);
//
//        Map<String, Object> submap = new HashMap<>();
//
//        submap.put("d", 2);
//        submap.put("e", "something else");
//        submap.put("f", Boolean.TRUE);
//
//        map.put("x", submap);
//
//        List sublist = new ArrayList();
//
//        Map<String, Object> submap2 = new HashMap<>();
//        submap2.put("color", "blue");
//
//        sublist.add("blah");
//        sublist.add("blah2");
//        sublist.add("blah3");
//        sublist.add(submap2);
//        map.put("y", sublist);
//
//        StringBuilder sb = new StringBuilder();
//
//        renderJson(null, map, 0, sb);
//
//        System.out.println(sb.toString());
//    }
}
