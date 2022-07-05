/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package webscrapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Muhammad Asif
 */
public class pk {
    List<String> catagries = new ArrayList<>();
    List<String> story = new ArrayList<>(100);
    List<String> list2 = new ArrayList<>();
    String url = "https://www.bbc.com/urdu";
public void showLink(Elements link) throws Exception {
    System.out.println("Showing stories");
        for (Element links : link) {
            //  System.out.println(links.attr("href"));
            catagries.add(links.attr("href"));
        }
        int pageCount=2;
        String page="?page=";
        
        for (int i = 0; i < catagries.size(); i++) {
            //while(story.size()<=100){
                if (i ==1) {
                String s = "https://www.bbc.com/" + catagries.get(i);
                System.out.println("}\n\n\n\n\n"+s+"\n\n\n\n\n\n");
                
                Document tempDoc = Jsoup.connect(s).get();
                System.out.println("Title of story is = "+tempDoc.title());
                Element orderList = tempDoc.select("main").not("header").not("footer").select("div").select("ul").first();
                Elements stories = orderList.getElementsByTag("a");
                //System.out.println(stories);
                System.out.println("printing each story using loop ");
                int num = 1;
                for (Element st : stories) {
                    //if(story.size()<=100){
                    {                        
                        System.out.print(" num = " + num + "  ");
                         String href =st.attr("href");
                        if(i==0){
                            href = "https://www.bbc.com/"+st.attr("href");
                        
                        }
                        
                        System.out.print(href);
                        Document StoryDoc= Jsoup.connect(href).get();
                        System.out.println("Title of story  =  "+StoryDoc.title());
                        Elements para=StoryDoc.select("main").not("header").not("footer").select("p");                        
                        System.out.println(para.text());
                        story.add(para.text());
                    }
                    //}
                    
                    num++;
                }

            }//end of if
                
       // }
        }
    }
    public static void main(String[] args) {
        pk obj = new pk();
        try{
            Document doc= Jsoup.connect(obj.url).get();
            System.out.println("Title = "+doc.title());
            Element unOrderList= doc.getElementsByTag("ul").first();
            
            Elements cat = unOrderList.getElementsByTag("a");
            System.out.println("Categories ");
            for (Element s : cat) {
                System.out.println("Links: " + s.attr("href"));
           }
            obj.showLink(cat);
        }catch(Exception ev){
            System.out.println(ev);
        }
    }
    
}
