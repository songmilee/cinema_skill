package mi.song.skill.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mi.song.skill.util.SkillSSL;

@Controller
public class CinemaController {

    @RequestMapping("/cinemainfo")
    @ResponseBody
    public String index() {
        // getCinemaInfo();
        return getCinemaInfo().toString();//"Hello, Spring";
    }

    //영화 정보 받아오는 함수
    public JsonObject getCinemaInfo() {        
        JsonObject result = new JsonObject();
        String baseUrl = "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=%EC%98%81%ED%99%94+%EC%83%81%EC%98%81%EC%9E%91";
        try {

            if (baseUrl.contains("https://"))
                SkillSSL.setSSL();

            Document cinemaWholePage = Jsoup.connect(baseUrl).get();
            Elements section = cinemaWholePage.select("ul.thumb_list>li>dl>dt>a");
                        
            JsonArray jsonArray = new JsonArray();

            for (Element element : section) {
                Attributes attr = element.attributes();
                String title = attr.get("title");
                jsonArray.add(title);                
            }          
            JsonObject data = new JsonObject();
            JsonArray empty = new JsonArray();     
            JsonArray contentType = new JsonArray();

            result.addProperty("code", "1000");
            contentType.add("textRandom");

            //data append
            data.add("contentType", contentType);
            data.addProperty("inputType", "text");
            data.add("responseButtons", empty);
            data.add("responseText", jsonArray);     
            data.addProperty("responseTitle", "");       
            data.add("imagePath", JsonNull.INSTANCE);
            data.add("imageUrl", JsonNull.INSTANCE);
            data.add("entities", empty);
            data.add("requiredEntities", empty);
            data.addProperty("lifespan", 5);

            result.add("data", data);

        } catch(Exception e){
            e.printStackTrace();
            result.addProperty("code", "9000");
        }finally{
            return result;
        }        
    }

}