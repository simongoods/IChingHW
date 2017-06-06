/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ichinghw;

/**
 *
 * @author simonwei
 */

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.awt.Desktop;
import java.util.*;
import java.lang.String;
import java.io.*;
import org.json.simple.*;
import java.util.Random;
import static java.lang.Math.pow;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IChingHW {

    public static void main(String[] args) throws IOException, ParseException, URISyntaxException {
        int num = 50 - 1;
        int input, ranum, left, right, i, total, step , row , col;
        int result[] = new int [6];
        boolean look[] = new boolean [6];
        boolean change = false;
        
        Scanner in = new Scanner(System.in);      
        JSONParser parser = new JSONParser();
        
        //打開爻辭
        Object obj = parser.parse(new FileReader( "find.json"));
        JSONArray find = (JSONArray) obj;
        
        //System.out.print(find);
        
        char [] base = {'乾','兌','離','震','巽','坎','艮','坤'};
        
        int [][] set = {{  1 , 43 , 14 , 34 ,  9 ,  5 , 26 , 11 },
                        { 10 , 58 , 38 , 54 , 61 , 60 , 41 , 19 },
                        { 13 , 49 , 30 , 55 , 37 , 63 , 22 , 36 },
                        { 25 , 17 , 21 , 51 , 42 ,  3 , 27 , 24 },
                        { 44 , 28 , 50 , 32 , 57 , 48 , 18 , 46 },
                        {  6 , 47 , 64 , 40 , 59 , 29 ,  4 ,  7 },
                        { 33 , 31 , 56 , 62 , 53 , 39 , 52 , 15 },
                        { 12 , 45 , 35 , 16 , 20 ,  8 , 23 ,  2 }};
        
       //對稱
        
        String [][] sets={  {"乾為天" ,"澤天夬"  ,"火天大有","雷天大壯","風天小畜","水天需" ,"山天大畜","地天泰"},
                            {"天澤履" ,"兌為澤"  ,"火澤睽"  ,"雷澤歸妹","風澤中孚","水澤節" ,"山澤損" ,"地澤臨"},
                            {"天火同人","澤火革" ,"離為火"  ,"雷火豐"  ,"風火家人","水火既濟","山火賁","地火明夷"},
                            {"天雷无妄","澤雷隨" ,"火雷噬嗑","震為雷"  ,"風雷益"  ,"水雷屯" ,"山雷頤" ,"地雷復"},
                            {"天風姤" ,"澤風大過","火風鼎"  ,"雷風恒"  ,"巽為風" ,"水風井"  ,"山風蠱","地風升"},
                            {"天水訟" ,"澤水困"  ,"火水未濟","雷水解"  ,"風水渙" ,"坎為水"  ,"山水蒙","地水師"},
                            {"天山遯" ,"澤山咸"  ,"火山旅"  ,"雷山小過","風山漸" ,"水山蹇"  ,"艮為山","地山謙"},
                            {"天地否" ,"澤地萃"  ,"火地晉"  ,"雷地豫"  ,"風地觀" ,"水地比"  ,"山地剝","坤為地"}};
        

        System.out.printf("本程式為傅佩榮老師介紹的占卜方法，幫助提問者方便占卜\n"
    ,"----------------------------------------------------\n\n"
    ,"首先有50支籌策，我們將一支定為太極\n");
        System.out.printf("先算左邊堆的，一開始先挑出一根，再來以四支為一組，右邊規則也是一樣\n\n");

        System.out.printf("現在請想好自己的問題\n");

        for(step = 1; step < 7; step++){ //做六次
            total = num;

            for(i = 1; i < 4; i++){
                System.out.printf("\n現在請輸入一個介於1~%d的數字當作分兩堆的數字\n", total);
                input = in.nextInt();

                Random rand = new Random(System.currentTimeMillis() + input);
                
                ranum = rand.nextInt(total) + 1;//取剩下會造成right為0

                left = ranum;
                right = total - left; //分兩堆

                left--;//拿一根
                left = divide(left);
                right = divide(right);
                total = (left + right);
            }
            System.out.printf("\n第%d個結果為%d\n",step, total / 4);
            result[step-1] = total / 4;
        }
        //六爻有了
        
        
        //7 9陽 6 8陰 
        //本卦
        System.out.printf("\n總結得到的是");
        for(i = 0; i < 6; i++)    {
            System.out.printf("%d ",result[i]);
            look[i] = false;
        }
        
        row = col = 0;

        for(i = 2;i >= 0;i--){
            row += (Math.pow(2,2-i) * (result[i] % 2));
        }
        
        for(i = 5;i >= 3;i--){
            col += (Math.pow(2,5-i) * (result[i] % 2));
        }
        //找到卦辭
        JSONObject a = (JSONObject)find.get((set[7-row][7-col])-1);

        System.out.printf("\n本卦是第%d個卦：%s",set[7-row][7-col],sets[7-row][7-col]);
        
        System.out.printf("\n外卦是%c卦",base[7-col]);
        System.out.printf("\n內卦是%c卦",base[7-row]);
        
        //紀錄卦的順序
        String font;
        if(set[7-row][7-col] < 10)
            font = String.valueOf("0"+set[7-row][7-col]);
        else
            font = String.valueOf(set[7-row][7-col]);
        
        
        //之卦
        System.out.printf("\n\n變爻(6為老陰，9為老陽換): ");

        row = col = 0;

        for(i = 2;i >= 0;i--){
            if(result[i] == 6){
                result[i] = 9;
                look[i] = true;
                change = true;
            }
            else if(result[i] == 9){
                result[i] = 6;
                look[i] = true;
                change = true;
            }
            row += (Math.pow(2,2-i) * (result[i] % 2));
        }
        
        for(i = 5;i >= 3;i--){
            if(result[i] == 6){
                result[i] = 9;
                look[i] = true;
                change = true;
            }
            else if(result[i] == 9){
                result[i] = 6;
                look[i] = true;
                change = true;
            }
            col += (Math.pow(2,5-i) * (result[i] % 2));
        }
        //7 9陽 6 8陰
        for(i = 0; i < 6; i++)    {
            System.out.printf("%d ",result[i]);
        }
        //找到卦辭
        a = (JSONObject)find.get((set[7-row][7-col])-1);
        
        System.out.printf("\n之卦是第%d個卦：%s",set[7-row][7-col],sets[7-row][7-col]);
        System.out.printf("\n外卦是%c卦",base[7-col]);
        System.out.printf("\n內卦是%c卦",base[7-row]);
        
        // 變得要看
        //Object s = a.get("name");
        if(change){
            System.out.printf("\n\n我們需要看的爻辭為 : \n");
            step = 0;
            for(i = 0; i < 6;i++){
                String s = null;
                if(look[i]){
                    step++;
                    switch(i+1){
                        case 1:{s = "one";}break;
                        case 2:{s = "two";}break;
                        case 3:{s = "three";}break;
                        case 4:{s = "four";}break;
                        case 5:{s = "five";}break;
                        case 6:{s = "six";}break;
                    }
                    System.out.println("\t第 " + step + " 個 : " + a.get(s));
                }
            }
        }else{
            System.out.printf("\n剛好沒有變爻，直接看卦辭\n");
        }
        //紀錄卦的順序
        String font2;
        if(set[7-row][7-col] < 10)
            font2 = String.valueOf("0"+set[7-row][7-col]);
        else
            font2 = String.valueOf(set[7-row][7-col]);
        //連接易學網網站
        String s = "http://www.eee-learning.com/book/neweee";
        Desktop.getDesktop().browse(new URI(s+font));
        Desktop.getDesktop().browse(new URI(s+font2));
    }
    
    public static int divide (int get){
        //拿掉不足四的，餘0拿4
        if((get % 4) != 0)
        {
            get -= (get%4);
        }
        else if(get != 0)
        {
            get -= 4;
        }
        return get;
    }

}