package Riot.API.Riot.API.service;



import Riot.API.Riot.API.dto.MatchIdDto;
import Riot.API.Riot.API.dto.SummonerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:riotApiKey.properties")
public class SummonerService {


        private ObjectMapper objectMapper = new ObjectMapper();

        @Value("${riot.api.key}")
        private String mykey;

        public SummonerDTO callRiotAPISummonerByName(String summonerName){

            SummonerDTO result;

            String serverUrl = "https://kr.api.riotgames.com";

            try {

                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(serverUrl + "/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + mykey);
                HttpResponse response = client.execute(request);

                if(response.getStatusLine().getStatusCode() != 200){
                    return null;
                }

                HttpEntity entity = response.getEntity();
                result = objectMapper.readValue(entity.getContent(), SummonerDTO.class);

            } catch (IOException e){
                e.printStackTrace();
                return null;
            }

            return result;
        }

        public List<String> callRiotAPISummonerGameRecord(String puuid){

            List<String>  result;

            String serverUrl = "https://asia.api.riotgames.com";

            try {

                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(serverUrl + "/lol/match/v5/matches/by-puuid/" + puuid + "/ids?api_key=" + mykey);
                HttpResponse response = client.execute(request);

                if(response.getStatusLine().getStatusCode() != 200){
                    return null;
                }

                HttpEntity entity = response.getEntity();
//                InputStream content = entity.getContent();
//                String messagebody = StreamUtils.copyToString(content, StandardCharsets.UTF_8);
                result = objectMapper.readValue(entity.getContent(),List.class);

                for (String s : result) {
                    System.out.println(s);
                }

            } catch (IOException e){
                e.printStackTrace();
                return null;
            }

            return result;
        }



}
