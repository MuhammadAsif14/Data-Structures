/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package webscrapping;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Muhammad Asif
 */
public class WebScrapping {

    List<String> catagories = new ArrayList<>();

    List<String> list2 = new ArrayList<>();
    String url = "https://www.bbc.com/urdu";

    /**
     * @param args the command line arguments
     */
    public void showLink(Elements link) throws Exception {
        System.out.println("SHOW LINK");
        for (Element links : link) {
            //  System.out.println(links.attr("href"));
            catagories.add(links.attr("href"));
        }
        int storyCount = 1;
        int count=0;
        String filePath = "StoryFile.csv";
        File file = new File(filePath);
        FileWriter outputfile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputfile);

        FileWriter storyFile = new FileWriter("StoryFile.csv", true);
        storyFile.write("CATAGORY\tSTORY NUMBER\tTITLE\tSTORY\t\n");
         ArrayList<String> story = new ArrayList<>();
            ArrayList<String> storyTitle = new ArrayList<>();
            
            Set<String> words = new HashSet<String>();
        for (int i = 1; i < catagories.size(); i++) {
           
           

                int pageNumber = 1;
                String page;
                storyCount = 0;
                String s = "https://www.bbc.com/" + catagories.get(i);
             
                //System.out.println(s + "                   size" + story.size());
                while (storyCount < 100) {
                    if (storyCount == 100) {
                        break;
                    }
                    page = s + "?page=" + pageNumber;
                    //System.out.println("\n\n\n\n\n" + page + "\n\n\n\n\n\n");

                    Document tempDoc = Jsoup.connect(page).get();
                    //System.out.println("\nTitle of Catagory is = " + tempDoc.title());
                    Element orderList = tempDoc.select("main").not("header").not("footer").select("div").select("ul").first();
                    String cat=tempDoc.title();
                    System.out.println("Cat                 "+cat.substring(0,7));
                    Elements stories = orderList.getElementsByTag("a");
                    //System.out.println(stories);
                    for (Element st : stories) {

                       // System.out.print("\n\nStory Number = (" + (story.size() % 25) + ") LINK:  ");
                        String href = st.attr("href");

                        //System.out.print(href);
                        Document StoryDoc = Jsoup.connect(href).get();
                        //System.out.println("\nTitle of story  =  " + StoryDoc.title() + "\n");
                        Elements para = StoryDoc.select("main").not("header").not("footer").select("p");
                        String stor=para.text();
                        story.add(stor);
                        String[] storyWords=stor.split(" ");
                        for(int j=0;j<storyWords.length;j++){
                            words.add(storyWords[j]);
                            System.out.print(storyWords[j]+ " ");
                        }
                        //System.out.println("\n\n\n\nstorySize "+storyWords.length + " \n\n\n\n\n\nsetSIZE"+words.size());
                        storyTitle.add(StoryDoc.title());
                        storyCount++;
                        count++;
                        
                        System.out.println("\n\n\nSize           "+count +"         ");

                        if (para.text() != null) {
                            storyFile.write(tempDoc.attr("data-original-title")+","+storyCount + "," + StoryDoc.title() + "," + para.text()+"\n");

                        }
                        if (storyCount == 100) {
                            break;
                        }
                        if(count==600)
                            return;
                    }
                   
                        pageNumber++;
                    
                    //System.out.println(pageNumber + "                                " + story.size());

                }

            

        }//end of for loop
        //writer.close();
        storyFile.close();
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Scanner input = new Scanner(System.in);
        WebScrapping obj = new WebScrapping();

        String url = obj.url;

        try {
            Document doc = Jsoup.connect(url).get();
            //System.out.println(doc.title());
            Elements ul = doc.getElementsByTag("ul");
            Element e = ul.first();

            Elements links = e.getElementsByTag("a");
//            for (Element s : links) {
//                System.out.println("Links: " + s.attr("href"));
//            }
            obj.showLink(links);

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

}
