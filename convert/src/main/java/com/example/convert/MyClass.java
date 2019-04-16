package com.example.convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClass {
    public static void main(String[] args) {
        String org="        \"id\":\"12\",\n" +
                "        \"out_trade_no\":\"20181211434538\",\n" +
                "        \"trade_no\":\"\",\n" +
                "        \"user_id\":\"10\",\n" +
                "        \"goods_id\":\"3\",\n" +
                "        \"format_id\":\"0\",\n" +
                "        \"goods_price\":\"10.00\",\n" +
                "        \"goods_all_price\":\"20.00\",\n" +
                "        \"goods_name\":\"测试停车场\",\n" +
                "        \"goods_number\":\"2\",\n" +
                "        \"goods_img\":\"http://img.buyantang.cn/2018-12-06_5c0888ed10015.png\",\n" +
                "        \"goods_format\":\"30*30\",\n" +
                "        \"goods_unit\":\"把\",\n" +
                "        \"order_status\":\"12\",\n" +
                "        \"pay_status\":\"0\",\n" +
                "        \"pay_type\":\"0\",\n" +
                "        \"postage\":\"0.00\",\n" +
                "        \"address_id\":\"0\",\n" +
                "        \"receiver_name\":\"\",\n" +
                "        \"receiver_address\":\"\",\n" +
                "        \"receiver_mobile\":\"\",\n" +
                "        \"express_name\":\"\",\n" +
                "        \"express_no\":\"\",\n" +
                "        \"pay_fee\":\"0.00\",\n" +
                "        \"note\":\"\",\n" +
                "        \"cutoff_time\":\"1546592896\",\n" +
                "        \"take_time\":\"\",\n" +
                "        \"auto_take_time\":\"0\",\n" +
                "        \"create_time\":\"2018-12-11 17:08:16\",\n" +
                "        \"pay_time\":\"\",\n" +
                "        \"isdelete\":\"0\",\n" +
                "        \"action\":\"已取消\",\n" +
                "        \"order_status_text\":\"交易关闭\"";
        //parm参数
        Pattern pattern=null;
        Matcher matcher=null;
        pattern=Pattern.compile("public String(.*?);");
        matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                System.out.println("statAll."+tmp1+"=statComp."+tmp1+";");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        pattern=Pattern.compile("TextView(.*?);");
        matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                System.out.println(""+tmp1+".setText(bean."+tmp1+");");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("-------------隔断--------------");
         pattern=Pattern.compile("(.*):(.*)");
         matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                String textview=tmp1.split("\\.")[0];
                System.out.println("result.put(\""+tmp1+"\","+textview+".getText().toString());");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("-------------隔断--------------");
        //

         pattern=Pattern.compile("(.*?):(.*)");
        matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                System.out.println("public String "+tmp1+";");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pattern=Pattern.compile("(.*?):(.*)");
        matcher=pattern.matcher(org);
        while (matcher.find()){
            try {
                String tmp1=matcher.group(1).replace("\"","").trim();
                System.out.println("bean."+tmp1+"=jsonObject.optString(\""+tmp1+"\");");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
