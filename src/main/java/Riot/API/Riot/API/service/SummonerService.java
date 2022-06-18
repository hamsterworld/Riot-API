package Riot.API.Riot.API.service;



import Riot.API.Riot.API.dto.gamerecord.MetaData1;
import Riot.API.Riot.API.dto.summoner.SummonerDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:riotApiKey.properties")
public class SummonerService {


        private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        @Value("${riot.api.key}")
        private String mykey= "RGAPI-80e50dc4-9890-4133-82a5-f9daa52fb25d";

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
                InputStream content = entity.getContent();
                result = objectMapper.readValue(entity.getContent(), SummonerDTO.class);

            } catch (IOException e){
                e.printStackTrace();
                return null;
            }

            return result;
        }

        public List<String> callRiotAPISummonerMatchIdBypuuid(String puuid){

            List<String>  result;

            String serverUrl = "https://asia.api.riotgames.com";

            try {

                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(serverUrl + "/lol/match/v5/matches/by-puuid/" + puuid + "/ids?count=5&api_key=" + mykey);
                HttpResponse response = client.execute(request);

                if(response.getStatusLine().getStatusCode() != 200){
                    return null;
                }

                HttpEntity entity = response.getEntity();
//                InputStream content = entity.getContent();
//                String messagebody = StreamUtils.copyToString(content, StandardCharsets.UTF_8);
                result = objectMapper.readValue(entity.getContent(),List.class);

            } catch (IOException e){
                e.printStackTrace();
                return null;
            }

            return result;
        }

        public MetaData1 callRiotAPIGameRecordByMatchId(String MatchId) {

            MetaData1 result;

            String serverUrl = "https://asia.api.riotgames.com";

            try {

                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(serverUrl + "/lol/match/v5/matches/" + MatchId + "?api_key=" + mykey);
                HttpResponse response = client.execute(request);

                if (response.getStatusLine().getStatusCode() != 200) {
                    return null;
                }

                HttpEntity entity = response.getEntity();
                result = objectMapper.readValue(entity.getContent(), MetaData1.class);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return result;
        }



}
